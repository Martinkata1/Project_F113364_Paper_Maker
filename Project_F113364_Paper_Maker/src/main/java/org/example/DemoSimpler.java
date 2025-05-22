package org.example;

import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.workers.Employee;
import org.example.workers.Manager;
import org.example.workers.Operator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.example.util.Size.A4;
import static org.example.util.Type.GLOSSY;


public class DemoSimpler {

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
    public static void main(String[] args) {
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
                                        Employee newest = houses.get(selectedShopIndex).getLatestEmployee();
                                        if (newest != null) {
                                            System.out.println("Newest employee: " + newest.getName() +
                                                    ", Salary: " + newest.getBaseSalary() +
                                                    ", Role: " + (newest instanceof Manager ? "Manager" : "Operator"));
                                        }
                                        /**
                                         * HAPPY ENDING, WORKED
                                         **/
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
    }
}

