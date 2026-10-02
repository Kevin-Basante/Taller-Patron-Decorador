package com.horsecare.factory;

import com.horsecare.model.SeniorHorseCare;
import com.horsecare.model.HorseService;

/**
 * CONCRETE CREATOR (Factory Method): creates the base care for senior horses.
 */
public class SeniorHorseCareCreator extends HorseCareCreator {

    @Override
    public HorseService createCare() {
        return new SeniorHorseCare();
    }
}
