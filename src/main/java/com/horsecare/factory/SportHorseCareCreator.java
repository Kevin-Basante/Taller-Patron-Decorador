package com.horsecare.factory;

import com.horsecare.model.SportHorseCare;
import com.horsecare.model.HorseService;

/**
 * CONCRETE CREATOR (Factory Method): creates the base care for sport and competition horses.
 */
public class SportHorseCareCreator extends HorseCareCreator {

    @Override
    public HorseService createCare() {
        return new SportHorseCare();
    }
}
