/*package org.example;

import org.example.editions.*;
import org.example.util.Size;
import org.example.util.Type;
import org.example.workers.*;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Demo {

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
                                        break;
                                    case 2:
                                        System.out.print("Type (book/poster/newspaper): ");
                                        String edition = scanner.nextLine();
                                        System.out.print("Title: ");
                                        String title = scanner.nextLine();
                                        System.out.print("Copies: ");
                                        int copies = scanner.nextInt();
                                        System.out.print("Size (A5, A4, A3, A2, A1): ");
                                        Size size = Size.valueOf(scanner.next().toUpperCase());
                                        System.out.print("Type (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Type type = Type.valueOf(scanner.next().toUpperCase());
                                        double basePrice = a5s.get(selectedShopIndex);
                                        Paper paper = new Paper(type, size, basePrice);
                                        System.out.print("Print price: ");
                                        double price = scanner.nextDouble();
                                        switch (edition.toLowerCase()) {
                                            case "book":
                                                houses.get(selectedShopIndex).addEdition(new Book(title, copies, paper, price));
                                                break;
                                            case "poster":
                                                houses.get(selectedShopIndex).addEdition(new Poster(title, copies, paper, price));
                                                break;
                                            case "newspaper":
                                                houses.get(selectedShopIndex).addEdition(new Newspaper(title, copies, paper, price));
                                                break;
                                        }
                                        break;
                                    case 3:
                                        System.out.print("Paper type (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Type pt = Type.valueOf(scanner.next().toUpperCase());
                                        System.out.print("Size (A5, A4, A3, A2, A1): ");
                                        Size ps = Size.valueOf(scanner.next().toUpperCase());
                                        double a5Base = a5s.get(selectedShopIndex);
                                        houses.get(selectedShopIndex).addPaper(new Paper(pt, ps, a5Base));
                                        break;
                                    case 4:
                                        System.out.print("Max sheets: ");
                                        int max = scanner.nextInt();
                                        System.out.print("Supports color? (true/false): ");
                                        boolean color = scanner.nextBoolean();
                                        System.out.print("Pages per minute: ");
                                        int ppm = scanner.nextInt();
                                        houses.get(selectedShopIndex).setMachine(new PrintingMachine(max, color, ppm));
                                        break;
                                    case 5:
                                        PrintingShop shop = houses.get(selectedShopIndex);

                                        if (shop.getSelectedEdition() == null) {
                                            System.out.println("No edition selected to print.");
                                            break;
                                        }


                                        //shop.printEdition(shop.getSelectedEdition(), new Paper(Paper.Type.REGULAR));
                                        System.out.println("Edition printed successfully!");
                                        break;
                                    case 6:
                                        System.out.printf("Revenue: %.2f, Expenses: %.2f\n",
                                                houses.get(selectedShopIndex).getRevenue(),
                                                houses.get(selectedShopIndex).calculateExpenses());
                                        break;
                                    case 7:
                                        //TODO
                                        break;
                                    case 8:
                                        //TODO
                                        break;
                                    case 9:
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
                                    case 10:
                                        back = true;
                                        break;
                                    case 0:
                                        return;
                                    default:
                                        System.out.println("Invalid choice! Try again");
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
}*/
