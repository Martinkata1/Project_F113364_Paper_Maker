import org.example.editions.Book;
import org.example.editions.Edition;
import org.example.editions.Paper;
import org.example.PrintingMachine;
import org.example.workers.Manager;
import org.example.workers.Operator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
//    @Test
//    public void testAddEditionAndPrint() throws Exception {
//        PrintHouse house = new PrintHouse(100, 0.1);
//        Edition book = new Book("Test Book", 50, Paper.Size.A4, 2.0);
//        house.addEdition(book);
//
//        Paper paper = new Paper(Paper.Type.REGULAR, Paper.Size.A4, 0.5);
//        PrintingMachine machine = new PrintingMachine(100, true, 10);
//        house.addPaper(paper);
//        house.setMachine(machine);
//
//        house.printEdition(book, paper, true);
//
//        assertEquals(100.0, house.getRevenue(), 0.01);
//    }
//
//    @Test
//    public void testDiscountApplied() throws Exception {
//        PrintHouse house = new PrintHouse(100, 0.2);
//        Edition book = new Book("Bulk Print", 200, Paper.Size.A4, 5.0);
//        house.addEdition(book);
//
//        Paper paper = new Paper(Paper.Type.REGULAR, Paper.Size.A4, 0.5);
//        PrintingMachine machine = new PrintingMachine(300, true, 15);
//        house.addPaper(paper);
//        house.setMachine(machine);
//
//        house.printEdition(book, paper, true);
//
//        assertEquals(200 * 5.0 * 0.8, house.getRevenue(), 0.01);
//    }
//
//    @Test
//    public void testEmployeeSalaries() {
//        PrintHouse house = new PrintHouse(100, 0.1);
//        house.addEmployee(new Operator("Ivan", 1000));
//        house.addEmployee(new Manager("Maria", 1500, 0.1, 5000));
//
//        double salary = house.calculateExpenses();
//        assertEquals(2500, salary, 0.01);
//    }
}

