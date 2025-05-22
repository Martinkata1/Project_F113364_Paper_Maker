package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;
import org.example.workers.Manager;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Added PrintingShop
 * implements Serializable, because of employees to be serialized and deserialized
 */
public class PrintingShop implements Serializable {
    /**
     * serial version for employees
     * name for shop
     * revenue for shop
     * when you can get discount and by percent 10%
     * price base for 1 paper
     */
    private static final long serialVersionUID = 1L;

    private final String name;

    private double revenue;
    //private final double expenseRate;

    private double discountThreshold = 500;
    private double discountPercent   = 0.10;

    private double priceBase;

    /**
     * LIST OF EMPLOYEE, MACHINES, PAPERSTOCK, EDITIONS, EDITIONMILEAGE
     */
    private final List<Employee> employees     = new ArrayList<>();
    private final List<PrintingMachine> machines = new ArrayList<>();
    private final List<Paper> paperStock       = new ArrayList<>();
    private final List<Edition> editions       = new ArrayList<>();
    private final Map<String, Integer> editionMileage = new HashMap<>();

    /**
     * SELECTED EDITION
     */

    private Edition selectedEdition;
    /**
     * CONSTRUCTOR
     */

    public PrintingShop(String name, double startRevenue/*, double expenseRate*/, double priceBase) {
        this.name         = name;
        this.revenue      = startRevenue;
        //this.expenseRate  = expenseRate;
        this.priceBase = priceBase;
    }


    /**
     * METHODS
     */
    public void addEdition(Edition e)      { editions.add(e); }
    public void addEmployee(Employee e)    { employees.add(e); }
    public void addPaper(Paper p)          { paperStock.add(p); }

    public void addMachine(PrintingMachine m){ machines.add(m); }
    public void setMachine(PrintingMachine m) { machines.add(m); }


    public void printEdition(Edition edition) throws InvalidPrintTypeException {
        if (edition == null) {
            throw new InvalidPrintTypeException("No edition specified.");
        }

        if (machines.isEmpty()) {
            throw new InvalidPrintTypeException("No printing machines available.");
        }

        PrintingMachine best = machines.get(0);

        for (PrintingMachine m : machines) {
            boolean hasEnoughPaper = m.getMaxSheets() >= edition.getCopies();
            boolean supportsColor = edition.isColor() || m.supportsColor();

            if (hasEnoughPaper && supportsColor) {
                best = m;
                // Pick the smallest machine (by maxSheets) that can handle it
               // if (m.getMaxSheets() < best.getMaxSheets()) {
                //    best = m;
                //}
            }
        }

        if (best == null) {
            throw new InvalidPrintTypeException(
                    "No suitable machine: check colour capability or load more paper.");
        }

        best.print(edition);
        editionMileage.merge(edition.getTitle(), 1, Integer::sum);
        System.out.printf("Printed on %s%n", best);
    }




    public int getEditionMileage(String title) {
        return editionMileage.getOrDefault(title, 0);
    }

    /**
     * SERIALIZATION
     */
    public void serializeEmployees(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(employees);
            System.out.println("Employees serialized to " + filePath);
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }
    }
    /**
     * GETTERS
     */
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
                new FileWriter(filePath, append)))
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
    public void printLatestEdition() throws InvalidPrintTypeException {
        printEdition(getLatestEdition()/*, (PrintingMachine) getAllMachines()*/);
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

    public void saveReportToFile(String file) {
    }
    public List<Employee> getAllEmployees(){
        return Collections.unmodifiableList(employees);
    }
}
