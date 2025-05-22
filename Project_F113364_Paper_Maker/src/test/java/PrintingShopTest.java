
import org.example.InvalidPrintTypeException;
import org.example.PrintingMachine;
import org.example.PrintingShop;
import org.example.editions.Book;
import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.util.Size;
import org.example.util.Type;
import org.example.workers.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PrintingShopTest {
    private PrintingShop shop;

    /**
     * Added automatically shop
     */
    @BeforeEach
    public void setUp() {
        String name = "TestShop";
         shop = new PrintingShop(name, 1000, 5);

    }

    /**
     * Testing for adding Employee
     */
    @Test
    void testAddEmployee() {
        Employee employee = new Employee("John Doe", 1200);
        shop.addEmployee(employee);

        assertTrue(shop.getAllEmployees().contains(employee));
    }


    /**
     * Testing for adding Edition
     */
    @Test
    void testAddEdition() {
        Edition edition = new Book("Test Book", 100, false, new Paper(Type.GLOSSY, Size.A4, 5));
        shop.addEdition(edition);

        assertTrue(shop.getAllEditions().contains(edition));
    }

    /**
     * Testing for checking best Printer and by economy and possibility of copies
     * @throws InvalidPrintTypeException
     */
    @Test
    public void testPrintEdition_SelectsBestMachine() throws InvalidPrintTypeException {

        //String name = "TestShop";
        //PrintingShop shop = new PrintingShop(name, 1000, 5);

        PrintingMachine m1 = new PrintingMachine(100, true, 50); // Not enough paper
        PrintingMachine m2 = new PrintingMachine(150, true, 150);   // Suitable
        PrintingMachine m3 = new PrintingMachine(400, true, 400);   // Suitable but too big

        shop.addMachine(m1);
        shop.addMachine(m3);
        shop.addMachine(m2);




        Edition edition = new Edition("Test Book", 300, true, new Paper(Type.GLOSSY, Size.A4, 5)); // Needs color, 120 copies
        shop.addEdition(edition);

        shop.printEdition(edition);

        // Check that only the medium machine was used (machine with maxSheets = 150)
        assertEquals(0, m2.getCurrentSheets(), "Printed on [Max sheets: 150, Color: true, Speed: 150 ppm]");
        assertEquals(-300, m3.getCurrentSheets(), "Printed on [Max sheets: 400, Color: true, Speed: 400 ppm]");
        assertEquals(0, m1.getCurrentSheets(), "Printed on [Max sheets: 100, Color: false, Speed: 100 ppm]");
    }

}