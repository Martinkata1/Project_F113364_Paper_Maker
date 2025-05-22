package org.example.workers;

import java.io.Serializable;

/**
 * Added Role Operator as Employee
 */

public class Operator extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    public Operator(String name, double baseSalary) {
        super(name, baseSalary);
    }
}