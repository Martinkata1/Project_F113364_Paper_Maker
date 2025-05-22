package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;
import org.example.workers.Manager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class PrintingShop implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;

    private double revenue;
    //private final double expenseRate;

    private double discountThreshold = 500;
    private double discountPercent   = 0.10;

    private double priceBase;

    private final List<Employee> employees     = new ArrayList<>();
    private final List<PrintingMachine> machines = new ArrayList<>();
    private final List<Paper> paperStock       = new ArrayList<>();
    private final List<Edition> editions       = new ArrayList<>();
    private final Map<String, Integer> editionMileage = new HashMap<>();


    private Edition selectedEdition;


    public PrintingShop(String name, double startRevenue/*, double expenseRate*/, double priceBase) {
        this.name         = name;
        this.revenue      = startRevenue;
        //this.expenseRate  = expenseRate;
        this.priceBase = priceBase;
    }



    public void addEdition(Edition e)      { editions.add(e); }
    public void addEmployee(Employee e)    { employees.add(e); }
    public void addPaper(Paper p)          { paperStock.add(p); }

    public void addMachine(PrintingMachine m){ machines.add(m); }
    public void setMachine(PrintingMachine m) { machines.add(m); }


    public void printEdition(Edition edition) {
        if (edition == null) {
            System.out.println("No edition specified.");
            return;
        }
        if (machines.isEmpty()) {
            System.out.println("No printing machines in this shop.");
            return;
        }

        List<PrintingMachine> candidates = machines.stream()
                .filter(m -> m.getCurrentSheets() >= edition.getCopies())
                .filter(m -> !edition.isColor() || m.supportsColor())
                .collect(Collectors.toList());

        if (candidates.isEmpty()) {
            System.out.println("No machine has enough sheets (or colour capability).");
            return;
        }

        candidates.sort(
                Comparator.comparingInt(PrintingMachine::getMaxSheets)
                        .thenComparing(Comparator.comparingInt(
                                PrintingMachine::getPagesPerMinute).reversed())
        );
        PrintingMachine best = candidates.get(0);

        best.print(edition);

        editionMileage.merge(edition.getTitle(), 1, Integer::sum);

        System.out.printf("Printed on %s%n", best);
    }


    public int getEditionMileage(String title) {
        return editionMileage.getOrDefault(title, 0);
    }


    public void serializeEmployees(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(employees);
            System.out.println("Employees serialized to " + filePath);
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }
    }
    public Employee getLatestEmployee() {
        if (employees.isEmpty()) return null;
        return employees.get(employees.size() - 1);
    }

    public void deserializeEmployees(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Employee> list = (List<Employee>) in.readObject();
            employees.clear();
            employees.addAll(list);
            System.out.println("Employees deserialized from " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }
    }


    public String buildFullReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Printing Shop Report ===\n");
        sb.append("Name      : ").append(name).append('\n');
        sb.append(String.format("Revenue   : %.2f%n", revenue));
        sb.append(String.format("Expenses  : %.2f%n", calculateExpenses()));

        sb.append("\nEmployees (").append(employees.size()).append(")\n");
        employees.forEach(e ->
                sb.append(String.format("  - %s (%s)%n",
                        e.getBaseSalary(),
                        e.getClass().getSimpleName()))
        );

        sb.append("\nEditions printed (").append(editions.size()).append(")\n");
        editions.forEach(ed ->
                sb.append(String.format("  - %s (%d copies)%n",
                        ed.getTitle(),
                        ed.getCopies()))
        );

        sb.append("================================\n");
        return sb.toString();
    }

    public void saveReportToFile(String filePath, boolean append) {
        try (PrintWriter pw = new PrintWriter(
                new FileWriter(filePath, append)))      // ← APPEND flag
        {
            pw.print(buildFullReport());
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }

    public void loadReportFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading report: " + e.getMessage());
        }
    }



    public String getName()                 { return name; }
    public double getRevenue()              { return revenue; }

    public double getPriceBase(){
        return priceBase;
    }
    public List<Employee> getEmployees()    { return Collections.unmodifiableList(employees); }

    public List<Manager> getManagers() {
        return employees.stream()
                .filter(e -> e instanceof Manager)
                .map(e -> (Manager) e)
                .collect(Collectors.toList());
    }

    public Edition getSelectedEdition()     { return selectedEdition; }
    public void setSelectedEdition(int idx) {
        if (idx >= 0 && idx < editions.size()) {
            selectedEdition = editions.get(idx);
            System.out.println("Selected edition: " + selectedEdition.getTitle());
        } else {
            System.out.println("Invalid edition index.");
        }
    }
    public Edition getLatestEdition() {
        if (editions.isEmpty()) return null;
        return editions.get(editions.size() - 1);
    }
    public void printLatestEdition() {
        printEdition(getLatestEdition());
    }

    // Prints a short summary of the newest edition
    public void showNewestEdition() {
        Edition e = getLatestEdition();
        if (e == null) {
            System.out.println("No editions created yet.");
            return;
        }
        System.out.printf(
                "Newest edition ➜ %s (%s) – %d copies, %s printing, paper: %s %s, price/pc: %.2f, total: %.2f%n",
                e.getTitle(),
                e.getClass().getSimpleName(),
                e.getCopies(),
                e.isColor() ? "color" : "b/w",
                e.getPaper().getType(),
                e.getPaper().getSize(),
                e.getPrintPricePerCopy(),
                e.getPrintPricePerCopy() * e.getCopies()
        );
    }

    public List<Edition> getAllEditions()   { return Collections.unmodifiableList(editions); }



    public double calculateExpenses() {
        return employees.stream()
                .mapToDouble(Employee::getBaseSalary)
                .sum() /* expenseRate*/; // simple rule: salary × shop‑expense‑rate
    }


    public List<PrintingMachine> getAllMachines() {
        return Collections.unmodifiableList(machines);
    }
}
