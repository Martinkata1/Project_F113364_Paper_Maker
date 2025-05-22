package org.example.editions;

import org.example.util.Size;
import org.example.util.Type;

/**
 * Added Paper class for Editions
 * It has type, size and basePrice
 * basePrice is multiplied by size
 */
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

}

