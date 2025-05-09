package org.example.editions;

public abstract class Edition {

    private String title = "";

    private int copies;

    private Paper paper;

    public Edition(String title, int copies, Paper paper) {
        this.title = title;
        this.copies = copies;
        this.paper = paper;
    }

    public int getCopies() { return copies; }

    public Paper getPaper() { return paper; }

    public String getTitle() { return title; }

    public abstract double getPrintPricePerCopy();
}
