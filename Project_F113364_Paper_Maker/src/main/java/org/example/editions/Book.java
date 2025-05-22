package org.example.editions;

/**
 * Adding Book as Edition
 * @param title for book
 * @param copies for book
 * @param color as black/white or colored for book
 * @param paper for book
 */
public  class Book extends Edition {
    private double price;
    private boolean color;


    public Book(String title, int copies, boolean color, Paper paper) {
        super(title, copies, color, paper);
        //this.price = price;
    }

    //public double getPrintPricePerCopy(double price) { return this.price; }
}