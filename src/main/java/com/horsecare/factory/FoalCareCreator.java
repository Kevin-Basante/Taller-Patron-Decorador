package com.horsecare.factory;

import com.horsecare.model.FoalCare;
import com.horsecare.model.HorseService;

/**
 * CONCRETE CREATOR (Factory Method): creates the base care for foals.
 */
public class FoalCareCreator extends HorseCareCreator {

    @Override
    public HorseService createCare() {
        return new FoalCare();
    }
}
