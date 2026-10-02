package com.horsecare.model;

/**
 * CONCRETE PRODUCT (Factory Method) and CONCRETE COMPONENT (Decorator):
 * base care for competition horses, with extra exercise and hoof control.
 */
public class SportHorseCare implements HorseService {

    @Override
    public String getDescription() {
        return "Cuidado deportivo (ejercicio diario y control de cascos)";
    }

    @Override
    public double getPrice() {
        return 110000;
    }
}
