package org.example;

import java.util.List;

public class Entrance implements Runnable {

    private final String name;
    private final SportsHall hall;
    private final List<Group> groups;

    public Entrance(String name, SportsHall hall, List<Group> groups) {
        this.name = name;
        this.hall = hall;
        this.groups = groups;
    }

    @Override
    public void run() {
        for (Group g : groups) {
            boolean success = hall.seatGroup(g.getCategory(), g.getSize());

            if (success) {
                System.out.println(name + " настани група от "
                        + g.getSize() + " души (" + g.getCategory() + ")");
            } else {
                System.out.println(name + " НЕ можа да настани група от "
                        + g.getSize() + " души (" + g.getCategory() + ")");
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}