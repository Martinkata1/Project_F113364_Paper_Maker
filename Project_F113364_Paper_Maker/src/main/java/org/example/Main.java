package org.example;

import org.example.editions.*;
import org.example.util.Size;
import org.example.util.Type;
import org.example.workers.*;


import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.util.*;
import java.util.List;

public class Main {

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
                            System.out.println("3. Add printing machine");
                            System.out.println("4. Show in ticket.txt, how is the profit");//TODO
                            System.out.println("5. Print latest edition");
                            System.out.println("6. Show revenue and expenses");//TODO
                            System.out.println("7. Show every machine printer.");
                            System.out.println("8. Print the menu prices of paper types per piece.");//TODO
                            System.out.println("9. Select Edition");//TODO
                            System.out.println("10. Back");
                            System.out.println("0. Exit");
                            System.out.print("Choice: ");
                            int choice1 = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                switch (choice1) {
                                    case 1:
                                        if (selectedShopIndex < 0 || selectedShopIndex >= houses.size()) {
                                            System.out.println("Invalid shop number.");
                                            selectedShopIndex = -1;
                                        }
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
                                    case 2:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        System.out.print("Type (book/poster/newspaper): ");
                                        String edition = scanner.nextLine();

                                        System.out.print("Title: ");
                                        String title = scanner.nextLine();

                                        System.out.print("Copies: ");
                                        int copies = scanner.nextInt();
                                        scanner.nextLine();

                                        System.out.print("Size (A5, A4, A3, A2, A1): ");
                                        Size size;
                                        try {
                                            size = Size.valueOf(scanner.next().toUpperCase());
                                        } catch (IllegalArgumentException ex) {
                                            System.out.println("Invalid size. Try again.");
                                            break;
                                        }
                                        scanner.nextLine();

                                        System.out.print("Paper type (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Type type;
                                        try {
                                            type = Type.valueOf(scanner.next().toUpperCase());
                                        } catch (IllegalArgumentException ex) {
                                            System.out.println("Invalid type. Try again.");
                                            break;
                                        }

                                        scanner.nextLine();

                                        System.out.println("Is the edition coloured? false/true");
                                        boolean color = Boolean.parseBoolean(scanner.nextLine());

                                        double onePrice = houses.get(selectedShopIndex).getPriceBase();

                                        Paper paper = new Paper(type, size, onePrice);
                                        //System.out.println("DEBUG: size=%s type=%s base=%.2f -> finalPrice=%.2f%n", size, type, onePrice);
                                        //scanner.nextLine();
                                        //System.out.println();
                                        //System.out.printf("DEBUG edition: [" + edition.toLowerCase() + "]");
                                        //scanner.nextLine();
                                        //System.out.println("DEBUG edition: [" + edition.toLowerCase() + "]");
                                        //System.out.println("DEBUG: Before checking selectedShopIndex");
                                        //scanner.nextLine();
                                        switch (edition.toLowerCase()) {
                                            case "book":
                                                houses.get(selectedShopIndex).addEdition(new Book(title, copies, color, paper));
                                                houses.get(selectedShopIndex).showNewestEdition();
                                                //System.out.printf("TEST1");
                                                break;
                                            case "poster":
                                                houses.get(selectedShopIndex).addEdition(new Poster(title, copies, color, paper));
                                                houses.get(selectedShopIndex).showNewestEdition();
                                                //System.out.printf("TEST2");
                                                break;
                                            case "newspaper":
                                                houses.get(selectedShopIndex).addEdition(new Newspaper(title, copies, color, paper));
                                                houses.get(selectedShopIndex).showNewestEdition();
                                                //System.out.printf("TEST3");
                                                break;
                                            default:
                                                System.out.println("Unknown edition type.");
                                                System.out.printf("TEST4");
                                                break;
                                        }

                                        /**
                                         * HAPPY ENDING, WORKED
                                         **/
                                        break;
                                    case 3:
                                        if (houses.isEmpty()) {
                                            System.out.println("No printing shops available. Please add one first.");
                                            break;
                                        }

                                        if (selectedShopIndex < 0 || selectedShopIndex >= houses.size()) {
                                            System.out.println("Please select a valid printing shop first.");
                                            break;
                                        }

                                        try {
                                            System.out.print("Enter max sheets capacity: ");
                                            int maxSheets = Integer.parseInt(scanner.nextLine());

                                            System.out.print("Supports color (true/false): ");
                                            boolean supportsColor = Boolean.parseBoolean(scanner.nextLine());

                                            System.out.print("Enter pages per minute: ");
                                            int ppm = Integer.parseInt(scanner.nextLine());

                                            PrintingMachine machine = new PrintingMachine(maxSheets, supportsColor, ppm);
                                            houses.get(selectedShopIndex).addMachine(machine);
                                            System.out.println(" Machine added successfully!");
                                            System.out.println("Machine: " + machine);

                                        } catch (Exception ex) {
                                            System.out.println(" Error while adding machine: " + ex.getMessage());
                                        }
                                        break;
                                    /**
                                     * SUCCESS! ADDED MACHINES WITHOUT PROBLEM
                                     */
                                    case 4:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first.");
                                            break;
                                        }
                                        PrintingShop shop = houses.get(selectedShopIndex);

                                        shop.saveReportToFile("ticket.txt", true);

                                        System.out.println("Full shop snapshot appended to ticket.txt");
                                        break;
                                    case 5:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first.");
                                            break;
                                        }

                                        houses.get(selectedShopIndex).printLatestEdition();
                                        break;
                                    case 6:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }

                                        System.out.printf("Revenue: %.2f, Expenses: %.2f\n", houses.get(selectedShopIndex).getRevenue(), houses.get(0).calculateExpenses());
                                        break;
                                    case 7:
                                        if (selectedShopIndex == -1 || houses.isEmpty()) {
                                            System.out.println("No shop selected.");
                                            break;
                                        }

                                        PrintingShop shop1 = houses.get(selectedShopIndex);
                                        List<PrintingMachine> printers = shop1.getAllMachines(); // you’ll need to add this method

                                        if (printers.isEmpty()) {
                                            System.out.println("No machines added yet.");
                                        } else {
                                            System.out.println("Machines in shop '" + shop1.getName() + "':");
                                            for (int i = 0; i < printers.size(); i++) {
                                                System.out.printf("  #%d %s%n", i + 1, printers.get(i));
                                            }
                                        }
                                        break;
                                    case 8:
                                        //TODO show menu for prices
                                        double baseA5Price = 1.0; // You can replace this with dynamic shop-specific pricing if needed

                                        System.out.println("Paper price multipliers (base A5 = " + baseA5Price + "):");
                                        for (Size size1 : Size.values()) {
                                            double finalPrice1 = baseA5Price * size1.getMultiplier();
                                            System.out.printf("- %s: %.2f\n", size1.name(), finalPrice1);
                                        }

                                    break;
                                    case 9:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first.");
                                            break;
                                        }
                                        List<Edition> all = houses.get(selectedShopIndex).getAllEditions();
                                        if (all.isEmpty()) {
                                            System.out.println("No editions available.");
                                            break;
                                        }
                                        System.out.println("Available editions:");
                                        for (int i = 0; i < all.size(); i++) {
                                            System.out.printf("%d. %s (%d copies)\n", i + 1, all.get(i).getTitle(), all.get(i).getCopies());
                                        }
                                        System.out.print("Enter edition number: ");
                                        int editionIndex = scanner.nextInt() - 1;
                                        houses.get(selectedShopIndex).setSelectedEdition(editionIndex);
                                        break;
                                    case 11:
                                        //TODO fix it
                                        //FileWriter writer = new FileWriter("report.txt");;
                                        //houses.get(selectedShopIndex).saveReportToFile("report.txt");
                                        //if (Desktop.isDesktopSupported()) {
                                        //    Desktop.getDesktop().open(new File("report.txt"));
                                        //} else {
                                        //    System.out.println("Desktop suck at this system.");
                                        //}
                                        //writer.close();
                                        break;
                                    case 12:
                                        //TODO fix it
                                        houses.get(selectedShopIndex).loadReportFromFile("report.txt");
                                        if (Desktop.isDesktopSupported()) {
                                            Desktop.getDesktop().open(new File("report.txt"));
                                        } else {
                                            System.out.println("Desktop suck at this system.");
                                        }
                                        break;
                                    case 13:
                                        houses.get(selectedShopIndex).serializeEmployees("employees.ser");
                                        break;
                                    case 14:
                                        houses.get(selectedShopIndex).deserializeEmployees("employees.ser");
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