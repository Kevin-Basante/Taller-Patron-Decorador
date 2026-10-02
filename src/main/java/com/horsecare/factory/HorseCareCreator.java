package com.horsecare.factory;

import com.horsecare.model.HorseService;

/**
 * CREATOR (Factory Method).
 * Declares the factory method {@link #createCare()} and lets each subclass
 * decide which concrete base care is instantiated. The rest of the system
 * only depends on this abstract class and on {@link HorseService}.
 */
public abstract class HorseCareCreator {

    /** Factory method: subclasses decide which concrete product to create. */
    public abstract HorseService createCare();

    /**
     * Operation that uses the factory method. It creates the base care and
     * makes sure no subclass returns an invalid product.
     */
    public HorseService prepareBaseCare() {
        HorseService care = createCare();
        if (care == null) {
            throw new IllegalStateException(getClass().getSimpleName() + " returned no base care");
        }
        return care;
    }
}
