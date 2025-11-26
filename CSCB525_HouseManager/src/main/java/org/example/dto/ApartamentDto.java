package org.example.dto;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public class ApartamentDto {
    @NotBlank(message = "Number cannot be null blank!")
    private int number;

    @NotBlank(message = "Floors count cannot be null blank!")
    private int floor;

    @NotBlank(message = "Area cannot be null blank!")
    private float area;

    public ApartamentDto(int number, int floor, float area) {
        this.number = number;
        this.floor = floor;
        this.area = area;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }
}
