package org.example.editions;


public abstract class Book extends Edition {
    private double price;

    public Book(String title, int copies, boolean color, double price, Paper paper) {
        super(title, copies, color, paper);
        this.price = price;
    }

    public double getPrintPricePerCopy(double price) { return this.price; }
}