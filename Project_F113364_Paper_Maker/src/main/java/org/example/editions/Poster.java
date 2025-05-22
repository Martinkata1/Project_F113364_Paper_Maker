package org.example.editions;

public  class Poster extends Edition {
    private double price;
    public Poster(String title, int copies, boolean color, Paper paper) {
        super(title, copies, color, paper);
        //this.price = price;
    }
    //public double getPrintPricePerCopy(double price) { return this.price; }
}