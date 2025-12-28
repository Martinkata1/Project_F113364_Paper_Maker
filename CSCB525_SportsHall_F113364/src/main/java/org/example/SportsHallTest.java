package org.example;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SportsHallTest {

    @Test
    public void testConcurrentSeating() throws InterruptedException {
        SportsHall hall = new SportsHall(10, 20, 30);

        List<Group> g1 = Arrays.asList(
                new Group(5, SeatCategory.VIP),
                new Group(10, SeatCategory.STANDARD)
        );

        List<Group> g2 = Arrays.asList(
                new Group(6, SeatCategory.VIP),
                new Group(15, SeatCategory.ECONOMY)
        );

        Thread t1 = new Thread(new Entrance("Вход 1", hall, g1));
        Thread t2 = new Thread(new Entrance("Вход 2", hall, g2));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertTrue(hall.getOccupied(SeatCategory.VIP) <= 10);
        assertTrue(hall.getOccupied(SeatCategory.STANDARD) <= 20);
        assertTrue(hall.getOccupied(SeatCategory.ECONOMY) <= 30);
    }
}
