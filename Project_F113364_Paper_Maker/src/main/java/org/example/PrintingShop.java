package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;
import org.example.workers.Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class PrintingShop implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;

    private double revenue;
    //private final double expenseRate;

    private double discountThreshold = 500;
    private double discountPercent   = 0.10;

    private double BasePrice;

    private final List<Employee> employees     = new ArrayList<>();
    private final List<PrintingMachine> machines = new ArrayList<>();
    private final List<Paper> paperStock       = new ArrayList<>();
    private final List<Edition> editions       = new ArrayList<>();

    private Edition selectedEdition;


    public PrintingShop(String name, double startRevenue/*, double expenseRate*/, double BasePrice) {
        this.name         = name;
        this.revenue      = startRevenue;
        //this.expenseRate  = expenseRate;
        this.BasePrice = BasePrice;
    }



    public void addEdition(Edition e)      { editions.add(e); }
    public void addEmployee(Employee e)    { employees.add(e); }
    public void addPaper(Paper p)          { paperStock.add(p); }

    public void addMachine(PrintingMachine m){ machines.add(m); }
    public void setMachine(PrintingMachine m) { machines.add(m); }


    public void printEdition(Edition edition, Paper paper, boolean color) throws Exception
    {
        if (edition == null) {
            System.out.println("No edition specified.");
            return;
        }
        if (machines.isEmpty()) {
            System.out.println("No printing machines available in this shop.");
            return;
        }

        // filter machines that can handle the job
        List<PrintingMachine> candidates = machines.stream()
                .filter(m -> (!color || m.supportsColor()) && m.getCurrentSheets() >= edition.getCopies())
                .collect(Collectors.toList());

        if (candidates.isEmpty()) {
            System.out.println("No suitable machine can print this job (color or paper capacity mismatch).");
            return;
        }

        // pick the fastest one
        PrintingMachine best = Collections.max(candidates, (a, b) -> Integer.compare(a.getPagesPerMinute(), b.getPagesPerMinute()));

        best.print(edition);

        /*double pricePerCopy = edition.getPrintPricePerCopy();
        if (edition.getCopies() > discountThreshold) {
            pricePerCopy *= (1 - discountPercent);
        }
        revenue += pricePerCopy * edition.getCopies();

        double minutes = (double) edition.getCopies() / best.getPagesPerMinute();
        System.out.printf("Printed %d copies in %.2f minutes on machine %s.%n", edition.getCopies(), minutes, best);
    */
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


    public void saveReportToFile(String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println("=== Printing Shop Report ===");
            pw.println("Name      : " + name);
            pw.printf ("Revenue   : %.2f%n", revenue);
            pw.printf ("Expenses  : %.2f%n", calculateExpenses());
            pw.println("\nEmployees (");
            employees.forEach(e -> pw.printf("  - %s (%s)%n", e.getBaseSalary(), e.getClass().getSimpleName()));
            pw.println(")\nEditions printed (");
            editions.forEach(e -> pw.printf("  - %s (%d copies)%n", e.getTitle(), e.getCopies()));
            pw.println(")");
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
        return getPriceBase();
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

    public List<Edition> getAllEditions()   { return Collections.unmodifiableList(editions); }



    public double calculateExpenses() {
        return employees.stream()
                .mapToDouble(Employee::getBaseSalary)
                .sum() /* expenseRate*/; // simple rule: salary × shop‑expense‑rate
    }


}
