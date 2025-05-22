package org.example.editions;


public  class Book extends Edition {
    private double price;
    private boolean color;

    public Book(String title, int copies, boolean color, Paper paper) {
        super(title, copies, color, paper);
        //this.price = price;
    }

    //public double getPrintPricePerCopy(double price) { return this.price; }
}