package com.horsecare.builder;

import com.horsecare.factory.HorseCareCreator;
import com.horsecare.model.HorseType;

/**
 * CONCRETE BUILDER: health check package (bath and veterinary check).
 */
public class HealthCheckCarePlanBuilder extends AbstractCarePlanBuilder {

    public HealthCheckCarePlanBuilder(String horseName, HorseType horseType, HorseCareCreator careCreator) {
        super(horseName, horseType, careCreator);
    }

    @Override
    protected CarePackage getCarePackage() {
        return CarePackage.HEALTH_CHECK;
    }

    @Override
    public void buildExtraServices() {
        addBath();
        addVeterinaryCheck();
    }

    @Override
    public void buildSchedule() {
        setWeeklySessions(1);
    }

    @Override
    public void buildRecommendations() {
        addRecommendation("Mantener al día el calendario de vacunas y desparasitación.");
    }
}
