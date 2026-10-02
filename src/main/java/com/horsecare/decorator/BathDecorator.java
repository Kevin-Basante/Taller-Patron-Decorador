package com.horsecare.decorator;

import com.horsecare.model.HorseService;

public class BathDecorator extends HorseServiceDecorator {

    public BathDecorator(HorseService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return service.getDescription() + " + Baño y aseo";
    }

    @Override
    public double getPrice() {
        return service.getPrice() + 20000;
    }
}
