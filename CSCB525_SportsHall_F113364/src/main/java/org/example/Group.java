package org.example;

public class Group {
    private final int size;
    private final SeatCategory category;

    public Group(int size, SeatCategory category) {
        this.size = size;
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public SeatCategory getCategory() {
        return category;
    }
}
