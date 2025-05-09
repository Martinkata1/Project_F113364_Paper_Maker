package org.example.editions;

public class Book extends Edition {
    private double price;

    public Book(String title, int copies, Paper paper, double price) {
        super(title, copies, paper);
        this.price = price;
    }

    public double getPrintPricePerCopy() { return price; }
}