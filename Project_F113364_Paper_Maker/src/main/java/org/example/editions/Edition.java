package org.example.editions;

/**
 * Added Edition class
 * It has title, copies, paper, color, size for every Edition
 * like for book, poster, newspaper
 */
public  class Edition {

    private String title = "";

    private int copies;

    private Paper paper;

    private boolean color;
    private double getPrintPricePerCopy;


    public Edition(String title, int copies, boolean color, Paper paper) {
        this.title = title;
        this.copies = copies;
        //this.paper = paper;
        this.paper = paper;
        this.color = color;
        //this.size = size;
    }

    public int getCopies() { return copies; }

    public Paper getPaper() { return paper; }

    //public Paper.Size getSize() {
    //    return size;
    //}

    public String getTitle() { return title; }

    public boolean isColor() { return color; }

    public double getPrintPricePerCopy() {
        this.getPrintPricePerCopy = this.paper.getPrice();
        return getPrintPricePerCopy;
    }


}
