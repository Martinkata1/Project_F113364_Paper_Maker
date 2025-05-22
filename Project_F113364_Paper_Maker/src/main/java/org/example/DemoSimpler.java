package org.example;

import org.example.editions.*;
import org.example.util.Size;
import org.example.util.Type;
import org.example.workers.Employee;
import org.example.workers.Manager;
import org.example.workers.Operator;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.util.Size.A4;
import static org.example.util.Type.GLOSSY;


public class DemoSimpler {
    /**
     * Demo for checking editions and adding
     */

    /*public static void main(String[] args) {
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
    }*/

    /**
     * Demo for Checking employees
     */
    /*public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int selectedShopIndex = -1;

        // Prices of the A5 available paper. One price per shop.
        List<Double> a5s = new ArrayList<>();

        List<PrintingShop> houses = new ArrayList<>();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add printing shop");
            System.out.println("2. Select printing shop");
            System.out.println("3. Show me selected shop now");
            System.out.println("4. We have work here.");
            System.out.println("5. Export report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter shop name: ");
                        String shopName = scanner.nextLine();
                        System.out.print("Enter starting revenue: ");
                        double rev = scanner.nextDouble();
                        System.out.print("Enter expense rate: ");
                        double rate = scanner.nextDouble();
                        System.out.print("Enter base price for A5 paper: ");
                        double baseA5Price = scanner.nextDouble();
                        houses.add(new PrintingShop(shopName, rev, rate));
                        a5s.add(baseA5Price);
                        System.out.println("Printing shop added!");

                        if (selectedShopIndex == -1) {
                            selectedShopIndex = houses.size() - 1;
                            System.out.println("Shop '" + shopName + "' automatically selected.");
                        }
                        break;
                    case 2:
                        if (houses.isEmpty()) {
                            System.out.println("No shops available. Add one first.");
                            break;
                        }
                        System.out.println("Available shops:");
                        for (int i = 0; i < houses.size(); i++) {
                            System.out.printf("%d. Shop %s with revenue %.2f\n", i + 1, houses.get(i).getName(), houses.get(i).getRevenue());
                        }
                        System.out.print("Choose shop number: ");
                        selectedShopIndex = scanner.nextInt() - 1;
                        if (selectedShopIndex < 0 || selectedShopIndex >= houses.size()) {
                            System.out.println("Invalid shop number.");
                            selectedShopIndex = -1;
                        } else {
                            System.out.printf("Shop selected: %s\n", houses.get(selectedShopIndex).getName());
                        }
                        break;
                    case 3:
                        if (selectedShopIndex == -1) {
                            System.out.println("No shop selected. Please select a shop first.");
                            break;
                        }
                        System.out.println("You choosed shop: " + houses.get(selectedShopIndex).getName());
                        break;
                    case 4:
                        boolean back = false;
                        while (!back) {
                            System.out.println("\nMenu:");
                            System.out.println("1. Add employee");
                            System.out.println("2. Add edition");
                            System.out.println("3. Add paper");
                            System.out.println("4. Set machine");
                            System.out.println("5. Print latest edition");
                            System.out.println("6. Show revenue and expenses");
                            System.out.println("7. Add printing machine");
                            System.out.println("8. Print the menu prices of paper types per piece.");
                            System.out.println("9. Select Edition");
                            System.out.println("10. Back");
                            System.out.println("0. Exit");
                            System.out.print("Choice: ");
                            int choice1 = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                switch (choice1) {
                                    case 1:
                                        System.out.print("Name: ");
                                        String name = scanner.nextLine();
                                        System.out.print("Salary: ");
                                        double salary = scanner.nextDouble();
                                        System.out.print("Is manager? (true/false): ");
                                        boolean isManager = scanner.nextBoolean();
                                        if (isManager) {
                                            System.out.print("Bonus percent (e.g. 0.1): ");
                                            double bonus = scanner.nextDouble();
                                            System.out.print("Revenue threshold: ");
                                            double minRev = scanner.nextDouble();
                                            houses.get(selectedShopIndex).addEmployee(new Manager(houses.get(selectedShopIndex), name, salary, bonus, minRev));
                                        } else {
                                            houses.get(selectedShopIndex).addEmployee(new Operator(name, salary));
                                        }
                                        /**
                                         * checker to return name
                                         */
            /*
                                        Employee newest = houses.get(selectedShopIndex).getLatestEmployee();
                                        if (newest != null) {
                                            System.out.println("Newest employee: " + newest.getName() +
                                                    ", Salary: " + newest.getBaseSalary() +
                                                    ", Role: " + (newest instanceof Manager ? "Manager" : "Operator"));
                                        }
                                        /**
                                         * HAPPY ENDING, WORKED
                                         **/
    /*
                                        break;
                                    default:
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                                scanner.nextLine();
                            }
                        }
                        break;
                    case 5:
                        if (selectedShopIndex != -1) {
                            houses.get(selectedShopIndex).saveReportToFile("report.txt");
                            System.out.println("Report exported.");
                        }
                        break;
                    case 0:
                        new File("report.txt").delete();
                        houses.clear();
                        return;
                    default:
                        System.out.println("Miss you sir, my beloved user!");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }*/
    /**
     * Demo fo checking  print machine
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int selectedShopIndex = -1;

        /* Prices of the A5 availble paper. */
        List<Paper> a5s = new ArrayList<>();

        List<PrintingShop> houses = new ArrayList<>();
        //new PrintingShop(100, 0.1);


        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add printing shop");
            System.out.println("2. Select printing shop");
            System.out.println("3. Show me selected shop now");
            System.out.println("4. We have work here.");
            System.out.println("5. Export report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter shop name: ");
                        String shopName = scanner.nextLine();
                        System.out.print("Enter starting revenue: ");
                        double rev = scanner.nextDouble();
                        //System.out.print("Enter expense rate: ");
                        //double rate = scanner.nextDouble();
                        System.out.println("Please write me base price for A5 size paper: ");
                        double BasePrice = scanner.nextDouble();
                        scanner.nextLine();
                        houses.add(new PrintingShop(shopName, rev/*, rate*/, BasePrice));
                        System.out.println("Printing shop added!");
                        //System.out.println("Shop '" + shopName + "' automatically selected with base price for A5 "+ houses.get(selectedShopIndex).getPriceBase() +".");

                        if (selectedShopIndex == -1) {
                            selectedShopIndex = houses.size() - 1;
                            System.out.println("Shop '" + shopName + "' automatically selected.");
                        }
                        break;
                    case 2:
                        if (houses.isEmpty()) {
                            System.out.println("No shops available. Add one first.");
                            break;
                        } else if (selectedShopIndex == -1) {
                            System.out.println("No shop selected. Please select a shop first.");
                            break;
                        }
                        System.out.println("Available shops:");
                        for (int i = 1; i < houses.size() + 1; i++) {
                            System.out.printf("%d. Shop %s with revenue %.2f\n", i, houses.get(i - 1).getName(), houses.get(i - 1).getRevenue());
                        }
                        System.out.print("Choose shop number: ");
                        selectedShopIndex = scanner.nextInt();
                        selectedShopIndex -= 1;
                        if (selectedShopIndex < 1 || selectedShopIndex >= houses.size()) {
                            System.out.println("Invalid shop number.");
                            selectedShopIndex = -1;
                        } else {
                            System.out.printf("Shop selected: %s", houses.get(selectedShopIndex).getName());
                        }
                        break;
                    case 3:
                        if (selectedShopIndex == -1) {
                            System.out.println("No shop selected. Please select a shop first.");
                            break;
                        }
                        System.out.println("You choosed shop: " + houses.get(selectedShopIndex).getName());
                        break;
                    case 4:
                        boolean back = false;
                        while (!back) {
                            System.out.println("\nMenu:");
                            System.out.println("1. Add employee");
                            System.out.println("2. Add edition");
                            System.out.println("3. Add printing machine");//TODO
                            System.out.println("4. Set machine");
                            System.out.println("5. Print latest edition");
                            System.out.println("6. Show revenue and expenses");
                            System.out.println("7. Add printing machine - remove");//TODO
                            System.out.println("8. Print the menu prices of paper types per piece.");
                            System.out.println("9. Select Edition");
                            System.out.println("10. Back");
                            System.out.println("0. Exit");
                            System.out.print("Choice: ");
                            int choice1 = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                switch (choice1) {
                                    case 7:
                                        if (houses.isEmpty()) {
                                            System.out.println("No printing shops available. Please add one first.");
                                            break;
                                        }

                                        if (selectedShopIndex < 0 || selectedShopIndex >= houses.size()) {
                                            System.out.println("Please select a valid printing shop first.");
                                            break;
                                        }

                                        PrintingShop currentShop = houses.get(selectedShopIndex);
                                        try {
                                            System.out.print("Enter max sheets capacity: ");
                                            int maxSheets = Integer.parseInt(scanner.nextLine());

                                            System.out.print("Supports color (true/false): ");
                                            boolean supportsColor = Boolean.parseBoolean(scanner.nextLine());

                                            System.out.print("Enter pages per minute: ");
                                            int ppm = Integer.parseInt(scanner.nextLine());

                                            PrintingMachine machine = new PrintingMachine(maxSheets, supportsColor, ppm);
                                            currentShop.addMachine(machine);

                                            System.out.println(" Machine added successfully!");
                                            System.out.println("Machine: " + machine);

                                        } catch (Exception ex) {
                                            System.out.println(" Error while adding machine: " + ex.getMessage());
                                        }
                                        break;
                                    case 8:
                                        if (selectedShopIndex == -1 || houses.isEmpty()) {
                                            System.out.println("No shop selected.");
                                            break;
                                        }

                                        PrintingShop shop = houses.get(selectedShopIndex);
                                        List<PrintingMachine> printers = shop.getAllMachines(); // you’ll need to add this method

                                        if (printers.isEmpty()) {
                                            System.out.println("No machines added yet.");
                                        } else {
                                            System.out.println("Machines in shop '" + shop.getName() + "':");
                                            for (int i = 0; i < printers.size(); i++) {
                                                System.out.printf("  #%d %s%n", i + 1, printers.get(i));
                                            }
                                        }
                                        break;


                                    case 0:
                                        return;
                                    case 10:
                                        back = true;
                                        break;
                                    default:
                                        System.out.println("Invalid choice! Try again");
                                }
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                        break;
                    default:
                        System.out.println("Miss you sir, my beloved user!");
                        new File("report.txt").delete();
                        houses.clear();
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }
}

