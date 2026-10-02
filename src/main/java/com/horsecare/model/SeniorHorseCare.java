package com.horsecare.model;

/**
 * CONCRETE PRODUCT (Factory Method) and CONCRETE COMPONENT (Decorator):
 * base care for older horses, with soft diet and joint care.
 */
public class SeniorHorseCare implements HorseService {

    @Override
    public String getDescription() {
        return "Cuidado de caballo mayor (dieta blanda y cuidado articular)";
    }

    @Override
    public double getPrice() {
        return 100000;
    }
}
