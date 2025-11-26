package org.example.dto;

public class CreatingEmployeeDto {
    private String name;

    public CreatingEmployeeDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateEmployeeDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
