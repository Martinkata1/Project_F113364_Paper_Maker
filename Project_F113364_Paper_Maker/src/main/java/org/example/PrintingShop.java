package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrintingShop {
    private String name;
    private double revenue = 0;

    private double discountThreshold;

    private double discountPercent;

    private Edition selectedEdition;


    private List<Employee> employees = new ArrayList<>();

    private List<PrintingMachine> machines = new ArrayList<>();

    //TODO Different prices for each print shop!
    List<Paper> paperStock = new ArrayList<>();

    private List<Edition> editions = new ArrayList<>();

    public PrintingShop(String name,double discountThreshold, double discountPercent) {
        this.name = name;
        this.discountThreshold = discountThreshold;
        this.discountPercent = discountPercent;
    }

    public void addEdition(Edition e) {
        editions.add(e);
    }

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public void setMachine(PrintingMachine m) {
//        this.machine = m;
    }

    public void addPaper(Paper p) {
        paperStock.add(p);
    }

    public Edition getLatestEdition() {
        return editions.isEmpty() ? null : editions.get(editions.size() - 1);
    }

    /*public void printEdition(Edition edition, Paper paper, boolean color) throws Exception {
        //TODO
        if (!machines.get(0).supportsColor() && color) {
            throw new Exception("Machine does not support color printing.");
        }
        int totalPages = edition.getCopies();
        //TODO
        machines.get(0).loadPaper(totalPages);
        //TODO
        machines.get(0).print(edition);

        double price = edition.getPrintPricePerCopy();
        if (edition.getCopies() > discountThreshold) {
            price *= (1 - discountPercent);
        }

        revenue += edition.getCopies() * price;
    }*/
    public void printEdition(Edition edition, Paper paper, boolean color) throws Exception {
        //TODO FIX EDITION
        Edition edition1 = getSelectedEdition();
        if (edition == null) {
            System.out.println("No editions available.");
            return;
        }
        boolean needsColor = true;

        List<PrintingMachine> suitableMachines = new ArrayList<>();
        for (PrintingMachine machine : machines) {
            if (needsColor && !machine.supportsColor()) {
                continue;
            }
            if (machine.getCurrentSheets() >= edition.getCopies()) {
                suitableMachines.add(machine);
            }
        }
        if (suitableMachines.isEmpty()) {
            boolean anyColorOk = machines.stream().anyMatch(PrintingMachine::supportsColor);
            int maxSheets = machines.stream().mapToInt(PrintingMachine::getCurrentSheets).max().orElse(0);

            if (!anyColorOk && needsColor) {
                System.out.println("No printers support color printing.");
            } else {
                System.out.printf("No printer has enough paper. Max available: %d sheets, but needed: %d.\n", maxSheets, edition.getCopies());
            }
            return;
        }
        PrintingMachine best = suitableMachines.stream()
                .max((a, b) -> Integer.compare(a.getPagesPerMinute(), b.getPagesPerMinute()))
                .orElse(null);

        if (best == null) {
            System.out.println("No suitable printer found.");
            return;
        }
        try {
            best.print(edition);
            double price = edition.getPrintPricePerCopy();
            if (edition.getCopies() > discountThreshold) {
                price *= (1 - discountPercent);
            }
            revenue += edition.getCopies() * price;

            double time = (double) edition.getCopies() / best.getPagesPerMinute();
            System.out.printf("Printed %d copies in %.2f minutes using machine: %s\n",
                    edition.getCopies(), time, best.toString());
        } catch (Exception e) {
            System.out.println("Error while printing: " + e.getMessage());
        }

    }
    public void setSelectedEdition(int index) {
        if (index >= 0 && index < editions.size()) {
            selectedEdition = editions.get(index);
            System.out.println("Selected edition: " + selectedEdition.getTitle());
        } else {
            System.out.println("Invalid index for edition.");
        }
    }
    public void saveReportToFile(String filePath) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("=== Report for Printing Shop ===");
            out.println("Name: " + name);
            out.printf("Revenue: %.2f\n", revenue);
            out.printf("Expenses: %.2f\n", calculateExpenses());
            out.println("Printed editions:");
            for (Edition e : editions) {
                out.printf("- %s (%d copies)\n", e.getTitle(), e.getCopies());
            }
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
    public void loadReportFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading report: " + e.getMessage());
        }
    }
    public void serializeEmployees(String filePath) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(employees);
            System.out.println("Employees serialized successfully.");
        } catch (IOException e) {
            System.out.println("Serialization error: " + e.getMessage());
        }
    }
    public void deserializeEmployees(String filePath) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            employees = (List<Employee>) in.readObject();
            System.out.println("Employees deserialized successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Deserialization error: " + e.getMessage());
        }
    }



    public Edition getSelectedEdition() {
        return selectedEdition;
    }

    public List<Edition> getAllEditions() {
        return editions;
    }

    public String getName() {
        return name;
    }

    public double getRevenue() {
        return revenue;
    }

    public double calculateExpenses() {
        double employeeCost = 0;
        for (Employee e : employees) {
            employeeCost += e.getBaseSalary();
        }
        return employeeCost;
    }

}
