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

                        if (selectedShopIndex == -1) {
                            selectedShopIndex = houses.size() - 1;
                            System.out.println("Shop '" + shopName + "' automatically selected with base price for A5 "+ houses.get(selectedShopIndex).getPriceBase() +".");
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
                                    case 2:
                                        //TODO No enum constant org.example.editions.Paper.Type.Regular, fix it
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
                                        System.out.print("Size (A5, A4, A3, A2, A1): ");
                                        Size size = Size.valueOf(scanner.next());
                                        System.out.print("Size (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Type type = Type.valueOf(scanner.next());
                                        System.out.println("Is the edition coloured? false/true");
                                        boolean color = Boolean.parseBoolean(scanner.nextLine());
                                        //System.out.print("Print price: ");
                                        //double price = scanner.nextDouble();
                                        Paper paper = new Paper(type, size, houses.get(selectedShopIndex).getPriceBase());
                                        switch (edition.toLowerCase()) {
                                            case "book":
                                                houses.get(selectedShopIndex).addEdition(new Book(title, copies, color, paper));
                                                break;
                                            case "poster":
                                                houses.get(selectedShopIndex).addEdition(new Poster(title, copies,color, paper));
                                                break;
                                            case "newspaper":
                                                houses.get(selectedShopIndex).addEdition(new Newspaper(title, copies, color, paper));
                                                break;
                                        }
                                        break;
                                    case 3:
                                        //TODO NO ENUM CONSTANT org.example.editions.Paper.Type.Regular
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        System.out.print("Paper type (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Type pt = Type.valueOf(scanner.next());
                                        System.out.print("Size: ");
                                        Size ps = Size.valueOf(scanner.next());
                                        System.out.print("Base price: ");
                                        double bp = scanner.nextDouble();
                                        houses.get(selectedShopIndex).addPaper(new Paper(pt, ps, bp));
                                        break;
                                    case 4:
                                        //TODO ERROR Index 1 out of bounds for length 0
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        System.out.print("Max sheets: ");
                                        int max = scanner.nextInt();
                                        scanner.nextLine();

                                        System.out.print("Supports color? (true/false): ");
                                        boolean color1 = scanner.nextBoolean();
                                        scanner.nextLine();

                                        System.out.print("Pages per minute: ");
                                        int ppm = scanner.nextInt();
                                        scanner.nextLine();

                                        houses.get(selectedShopIndex).setMachine(new PrintingMachine(max, color1, ppm));
                                        System.out.println("Successfully added printing machine with specs: " + max + " sheets, " + (color1 ? "color" : "black and white") + ", " + ppm + " pages per minute.");
                                        break;
                                    case 5:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        //if (houses.get(selectedShopIndex).getLatestEdition() == null || /*houses.get(0).getMachine() == null ||*/ //houses.get(0).paperStock == null || houses.get(00).paperStock.isEmpty()) {
                                          //  System.out.println("Not enough data to print.");
                                          //  break;
                                        //}
                                        //houses.get(selectedShopIndex).printEdition(
                                               // houses.get(selectedShopIndex).getLatestEdition(),
                                                //houses.get(selectedShopIndex).paperStock.get(0),
                                                //true
                                        //);
                                        System.out.println("Edition printed successfully!");
                                        break;
                                    case 6:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }

                                        System.out.printf("Revenue: %.2f, Expenses: %.2f\n", houses.get(selectedShopIndex).getRevenue(), houses.get(0).calculateExpenses());
                                        break;
                                    case 7:
                                        //TODO add option to add machine printer
                                        PrintingShop selectedShopForMachine = houses.get(selectedShopIndex);
                                        System.out.print("Enter max sheets capacity: ");
                                        int maxSheets = Integer.parseInt(scanner.nextLine());

                                        System.out.print("Supports color (true/false): ");
                                        boolean supportsColor = Boolean.parseBoolean(scanner.nextLine());

                                        System.out.print("Enter pages per minute: ");
                                        int pm = Integer.parseInt(scanner.nextLine());

                                        PrintingMachine newMachine = new PrintingMachine(maxSheets, supportsColor, pm);
                                        selectedShopForMachine.addMachine(newMachine);
                                        System.out.println("Machine added successfully!");
                                        break;

                                    case 8:
                                        //TODO show menu for prices
                                        double baseA5Price = 1.0; // You can replace this with dynamic shop-specific pricing if needed

                                        System.out.println("Paper price multipliers (base A5 = " + baseA5Price + "):");
                                        for (Size size1 : Size.values()) {
                                            double finalPrice = baseA5Price * size1.getMultiplier();
                                            System.out.printf("- %s: %.2f\n", size1.name(), finalPrice);
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
                                        houses.get(selectedShopIndex).saveReportToFile("report.txt");
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