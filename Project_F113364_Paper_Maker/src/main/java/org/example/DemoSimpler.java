package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import static org.example.util.Size.A4;
import static org.example.util.Type.GLOSSY;


public class DemoSimpler {

    public static void main(String[] args) {
        Paper paper1 = new Paper(GLOSSY, A4, 100.0);
        int copies = 100;
        Edition edition = new Edition("MyBook", copies, false, paper1);
        if (edition.isColor()) {
            System.out.println("This edition requires color printing.");
        } else{
            System.out.println("This edition does not require color printing.");
            System.out.println("Price: " + edition.getTitle() + ": " +
                    edition.getPrintPricePerCopy() + " " +
                    edition.getCopies() + " " +
                    edition.getPaper().getType() + " "
                    + edition.getPaper().getSize() + " "
                    + edition.isColor() + " " +
                    edition.getPaper().getPrice()*copies);
        }
    }
}
