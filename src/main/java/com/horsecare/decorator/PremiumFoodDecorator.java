package com.horsecare.decorator;

import com.horsecare.model.HorseService;

public class PremiumFoodDecorator extends HorseServiceDecorator {

    public PremiumFoodDecorator(HorseService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return service.getDescription() + " + Alimentación premium";
    }

    @Override
    public double getPrice() {
        return service.getPrice() + 30000;
    }
}
