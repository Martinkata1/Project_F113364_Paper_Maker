package org.example.editions;

/**
 * Adding Newspaper as Edition
 * @param title for newspaper
 * @param copies for newspaper
 * @param color as black/white or colored for newspaper
 * @param paper for newspaper
 */
public  class Newspaper extends Edition {
    private double price;
    public Newspaper(String title, int copies, boolean color, Paper paper) {
        super(title, copies, color, paper);
        //this.price = price;
    }
    //public double getPrintPricePerCopy(double price) { return this.price; }
}