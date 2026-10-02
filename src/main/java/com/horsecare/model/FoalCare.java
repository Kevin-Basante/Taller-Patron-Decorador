package com.horsecare.model;

/**
 * CONCRETE PRODUCT (Factory Method) and CONCRETE COMPONENT (Decorator):
 * base care for foals, focused on growth and socialization.
 */
public class FoalCare implements HorseService {

    @Override
    public String getDescription() {
        return "Cuidado de potro (control de crecimiento y socialización)";
    }

    @Override
    public double getPrice() {
        return 95000;
    }
}
