package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;

import java.util.ArrayList;
import java.util.List;

public class PrintingShop {
    private String name;
    private double revenue = 0;

    private double discountThreshold;

    private double discountPercent;

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

    public void printEdition(Edition edition, Paper paper, boolean color) throws Exception {
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
