package com.horsecare.builder;

import com.horsecare.factory.HorseCareCreator;
import com.horsecare.model.HorseType;

/**
 * CONCRETE BUILDER: wellness package (premium food and bath).
 */
public class WellnessCarePlanBuilder extends AbstractCarePlanBuilder {

    public WellnessCarePlanBuilder(String horseName, HorseType horseType, HorseCareCreator careCreator) {
        super(horseName, horseType, careCreator);
    }

    @Override
    protected CarePackage getCarePackage() {
        return CarePackage.WELLNESS;
    }

    @Override
    public void buildExtraServices() {
        addPremiumFood();
        addBath();
    }

    @Override
    public void buildSchedule() {
        setWeeklySessions(2);
    }

    @Override
    public void buildRecommendations() {
        addRecommendation("Cepillar el pelaje a diario para mantenerlo sano.");
    }
}
