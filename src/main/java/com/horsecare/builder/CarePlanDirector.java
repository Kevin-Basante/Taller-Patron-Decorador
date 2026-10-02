package com.horsecare.builder;

import java.util.Objects;

/**
 * DIRECTOR (Builder).
 * Knows the order of the construction steps, but not how each package
 * implements them.
 */
public class CarePlanDirector {

    private final CarePlanBuilder carePlanBuilder;

    public CarePlanDirector(CarePlanBuilder carePlanBuilder) {
        this.carePlanBuilder = Objects.requireNonNull(carePlanBuilder, "The builder is required");
    }

    public void buildCarePlan() {
        carePlanBuilder.buildHorseProfile();
        carePlanBuilder.buildBaseCare();
        carePlanBuilder.buildExtraServices();
        carePlanBuilder.buildSchedule();
        carePlanBuilder.buildRecommendations();
    }
}
