package com.horsecare.decorator;

import com.horsecare.model.HorseService;

public abstract class HorseServiceDecorator implements HorseService {

    protected HorseService service;

    public HorseServiceDecorator(HorseService service) {
        this.service = service;
    }

    @Override
    public String getDescription() {
        return service.getDescription();
    }

    @Override
    public double getPrice() {
        return service.getPrice();
    }
}
