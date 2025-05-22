package org.example.editions;

/**
 * Adding Poster as Edition
 * @param title for Poster
 * @param copies for Poster
 * @param color as black/white or colored for Poster
 * @param paper for Poster
 */
public  class Poster extends Edition {
    private double price;
    public Poster(String title, int copies, boolean color, Paper paper) {
        super(title, copies, color, paper);
        //this.price = price;
    }
    //public double getPrintPricePerCopy(double price) { return this.price; }
}