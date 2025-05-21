package org.example.editions;

public abstract class Poster extends Edition {
    private double price;
    public Poster(String title, int copies, boolean color, double price, Paper paper) {
        super(title, copies, color, paper);
        this.price = price;
    }
    public double getPrintPricePerCopy(double price) { return this.price; }
}