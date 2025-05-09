package org.example.editions;

public class Poster extends Edition {
    private double price;
    public Poster(String title, int copies, Paper paper, double price) {
        super(title, copies, paper);
        this.price = price;
    }
    public double getPrintPricePerCopy() { return price; }
}