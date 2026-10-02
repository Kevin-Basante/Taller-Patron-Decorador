package com.horsecare.factory;

import com.horsecare.model.BasicHorseCare;
import com.horsecare.model.HorseService;

/**
 * CONCRETE CREATOR (Factory Method): creates the base care for leisure horses (the original basic care).
 */
public class LeisureHorseCareCreator extends HorseCareCreator {

    @Override
    public HorseService createCare() {
        return new BasicHorseCare();
    }
}
