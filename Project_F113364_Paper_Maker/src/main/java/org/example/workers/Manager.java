package org.example.workers;


import org.example.PrintingShop;

import java.io.Serializable;
/**
 * Added Role Operator as Employee
 */

public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private double bonusPercent;
    private double revenueThreshold;

    /**
     * Reference to the printing shop where the manager is working.
     */
    private PrintingShop workplce = null;

    public Manager(PrintingShop workplce, String name, double baseSalary, double bonusPercent, double revenueThreshold) {
        super(name, baseSalary);
        this.workplce = workplce;
        this.bonusPercent = bonusPercent;
        this.revenueThreshold = revenueThreshold;
    }

    public double getCumulativeSalary() {
        double salary = getBaseSalary();
        if (workplce.getRevenue() > revenueThreshold) {
            salary += salary * bonusPercent;
        }
        return salary;
    }
}