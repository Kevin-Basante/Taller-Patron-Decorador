package com.horsecare.decorator;

import com.horsecare.model.HorseService;

public class VeterinaryDecorator extends HorseServiceDecorator {

    public VeterinaryDecorator(HorseService service) {
        super(service);
    }

    @Override
    public String getDescription() {
        return service.getDescription() + " + Chequeo veterinario";
    }

    @Override
    public double getPrice() {
        return service.getPrice() + 40000;
    }
}
