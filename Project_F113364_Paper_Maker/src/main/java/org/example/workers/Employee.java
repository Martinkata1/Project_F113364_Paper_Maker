package org.example.workers;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public double getBaseSalary() {
        return baseSalary;
    }
}

