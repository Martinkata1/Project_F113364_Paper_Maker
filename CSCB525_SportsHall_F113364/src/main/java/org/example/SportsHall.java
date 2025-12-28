package org.example;

import java.util.EnumMap;
import java.util.Map;

public class SportsHall {

    private final Map<SeatCategory, Integer> capacity;
    private final Map<SeatCategory, Integer> occupied;

    public SportsHall(int vip, int standard, int economy) {
        capacity = new EnumMap<>(SeatCategory.class);
        occupied = new EnumMap<>(SeatCategory.class);

        capacity.put(SeatCategory.VIP, vip);
        capacity.put(SeatCategory.STANDARD, standard);
        capacity.put(SeatCategory.ECONOMY, economy);

        for (SeatCategory c : SeatCategory.values()) {
            occupied.put(c, 0);
        }
    }

    // СИНХРОНИЗИРАН метод – критична секция
    public synchronized boolean seatGroup(SeatCategory category, int people) {
        int freeSeats = capacity.get(category) - occupied.get(category);

        if (freeSeats >= people) {
            occupied.put(category, occupied.get(category) + people);
            return true;
        }
        return false;
    }

    public void printResult() {
        System.out.println("\nЗаети места в залата:");
        for (SeatCategory c : SeatCategory.values()) {
            System.out.println(c + ": " + occupied.get(c));
        }
    }

    public int getOccupied(SeatCategory category) {
        return occupied.get(category);
    }
}