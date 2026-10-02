package com.horsecare.model;

/**
 * Kinds of horses the care center works with. Each type needs a different
 * base care, which is created through the Factory Method pattern.
 */
public enum HorseType {

    LEISURE("Caballo de paseo"),
    SPORT("Caballo deportivo"),
    FOAL("Potro"),
    SENIOR("Caballo mayor");

    private final String label;

    HorseType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
