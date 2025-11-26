package org.example.dto;

public class CreatingResidentDto {
    private String name;
    private int age;
    private boolean useElevator;

    public CreatingResidentDto(String name, int age, boolean useElevator) {
        this.name = name;
        this.age = age;
        this.useElevator = useElevator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isUseElevator() {
        return useElevator;
    }

    public void setUseElevator(boolean useElevator) {
        this.useElevator = useElevator;
    }

    @Override
    public String toString() {
        return "CreateResidentDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", useElevator=" + useElevator +
                '}';
    }
}
