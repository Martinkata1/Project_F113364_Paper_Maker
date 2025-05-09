package org.example;

import org.example.editions.Book;
import org.example.editions.Newspaper;
import org.example.editions.Poster;
import org.example.editions.Paper;
import org.example.workers.*;


import java.util.*;

public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int selectedShopIndex = -1;

        /* Prices of the A5 availble paper. */
        List<Paper> a5s = new ArrayList<>();

        List<PrintingShop> houses = new ArrayList<>();
        //new PrintingShop(100, 0.1);
        //TODO SORT EVERYTHING BETTER
        // TODO like for employee and books to be in next choice

        while (true) {


            System.out.println("\nMenu:");
            System.out.println("1. Add printing shop");
            System.out.println("2. Select printing shop");
            System.out.println("3. Show me selected shop now");
            System.out.println("4. We have work here.");
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
                        houses.add(new PrintingShop(shopName, rev, rate));
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
                            System.out.println("9. Back");
                            System.out.println("10. Exit");
                            System.out.print("Choice: ");
                            int choice1 = scanner.nextInt();
                            scanner.nextLine();
                            try {
                                switch (choice1) {
                                    case 1:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
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
                                            //TODO
                                            houses.get(selectedShopIndex).addEmployee(new Manager(houses.get(selectedShopIndex), name, salary, bonus, minRev));
                                        } else {
                                            //TODO
                                            houses.get(selectedShopIndex).addEmployee(new Operator(name, salary));
                                        }
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
                                        System.out.print("Size (A5, A4, A3, A2, A1): ");
                                        Paper.Size size = Paper.Size.valueOf(scanner.next());
                                        System.out.print("Size (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Paper.Type type = Paper.Type.valueOf(scanner.next());
                                        Paper paper = new Paper(type, size, 0.5);
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
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        System.out.print("Paper type (REGULAR, GLOSSY, NEWSPAPER): ");
                                        Paper.Type pt = Paper.Type.valueOf(scanner.next());
                                        System.out.print("Size: ");
                                        Paper.Size ps = Paper.Size.valueOf(scanner.next());
                                        System.out.print("Base price: ");
                                        double bp = scanner.nextDouble();
                                        houses.get(selectedShopIndex).addPaper(new Paper(pt, ps, bp));
                                        break;
                                    case 4:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        System.out.print("Max sheets: ");
                                        int max = scanner.nextInt();
                                        System.out.print("Supports color? (true/false): ");
                                        boolean color = scanner.nextBoolean();
                                        System.out.print("Pages per minute: ");
                                        int ppm = scanner.nextInt();
                                        houses.get(selectedShopIndex).setMachine(new PrintingMachine(max, color, ppm));
                                        System.out.println("Successfully added printing machine with specs: " + max + " sheets, " + (color ? "color" : "black and white") + ", " + ppm + " pages per minute.");
                                        break;
                                    case 5:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }
                                        if (houses.get(selectedShopIndex).getLatestEdition() == null || /*houses.get(0).getMachine() == null ||*/ houses.get(0).paperStock == null || houses.get(00).paperStock.isEmpty()) {
                                            System.out.println("Not enough data to print.");
                                            break;
                                        }
                                        houses.get(selectedShopIndex).printEdition(
                                                houses.get(selectedShopIndex).getLatestEdition(),
                                                houses.get(selectedShopIndex).paperStock.get(0),
                                                true
                                        );
                                        System.out.println("Edition printed successfully!");
                                        break;
                                    case 6:
                                        if (selectedShopIndex == -1) {
                                            System.out.println("Please select a shop first using option 7.");
                                            break;
                                        }

                                        System.out.printf("Revenue: %.2f, Expenses: %.2f\n", houses.get(selectedShopIndex).getRevenue(), houses.get(0).calculateExpenses());
                                        break;
                                    case 0:
                                        return;
                                    case 9:
                                        back = true;
                                        break;
                                    default:
                                        //TODO Add return to previous menu
                                        System.out.println("Invalid choice!");
                                }
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                        }
                        break;
                    default:
                        //TODO add something
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        }
}