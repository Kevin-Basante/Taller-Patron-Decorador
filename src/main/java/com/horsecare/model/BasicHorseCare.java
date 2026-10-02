package com.horsecare.model;

/**
 * CONCRETE COMPONENT (Decorator) and CONCRETE PRODUCT (Factory Method):
 * base care for leisure horses, created by {@code LeisureHorseCareCreator}.
 */
public class BasicHorseCare implements HorseService {

    @Override
    public String getDescription() {
        return "Cuidado básico";
    }

    @Override
    public double getPrice() {
        return 80000;
    }
}
