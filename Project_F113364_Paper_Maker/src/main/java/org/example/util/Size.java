package org.example.util;

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
