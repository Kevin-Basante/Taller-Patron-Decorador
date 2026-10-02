package com.horsecare.model;

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
