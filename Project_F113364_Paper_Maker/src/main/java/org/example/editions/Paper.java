package org.example.editions;

public class Paper {
    private Type type;
    private Size size;
    private double basePrice;

    public Paper(Type type, Size size, double basePrice) {
        this.type = type;
        this.size = size;
        this.basePrice = basePrice;
    }

    public double getPrice() {
        return basePrice * size.getMultiplier();
    }

    public Type getType() { return type; }
    public Size getSize() { return size; }

    public enum Size {
        A5(1.0),
        A4(1.5),
        A3(2.0),
        A2(2.5),
        A1(3.0);

        private final double multiplier;

        Size(double m) { multiplier = m; }

        public double getMultiplier() { return multiplier; }
    }

    public enum Type {
        REGULAR,
        GLOSSY,
        NEWSPAPER
    }
}

