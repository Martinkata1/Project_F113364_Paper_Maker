package org.example;

import org.example.editions.Edition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Added Machine Printers
 */

public class PrintingMachine {
    /**
     *  List of all printing machines
     */
    private List<PrintingMachine> machines = new ArrayList<>();

    /**
     *  Maximum number of sheets,
     *  color and pages per minute
     */
    private int maxSheets;
    private boolean color;
    private int pagesPerMinute;
    private int currentSheets = 0;
    private Map<String, Integer> printLog = new HashMap<>();

    /**
     *  Constructor
     */
    public PrintingMachine(int maxSheets, boolean color, int ppm) {
        this.maxSheets = maxSheets;
        this.color = color;
        this.pagesPerMinute = ppm;
    }
    /**
     *  Methods
     */
    public void loadPaper(int sheets) throws Exception {
        if (sheets + currentSheets > maxSheets) {
            throw new Exception("Not enough paper capacity.");
        }
        currentSheets += sheets;
    }

    public boolean supportsColor() { return color; }

    public void print(Edition e) {
        currentSheets -= e.getCopies();
        printLog.put(e.getTitle(), printLog.getOrDefault(e.getTitle(), 0) + e.getCopies());
    }
    /**
     *  Getters
     */
    public List<PrintingMachine> getMachines() {
        return machines;
    }

    public int getTotalPrintedPages() {
        return printLog.values().stream().mapToInt(i -> i).sum();
    }
    public int getPagesPerMinute() {
        return pagesPerMinute;
    }
    public int getCurrentSheets() {
        return currentSheets;
    }

    public int getMaxSheets() {
        return maxSheets;
    }
    @Override
    public String toString() {
        return String.format("[Max sheets: %d, Color: %b, Speed: %d ppm]", maxSheets, color, pagesPerMinute);
    }


}