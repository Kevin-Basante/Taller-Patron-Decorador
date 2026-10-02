package com.horsecare.builder;

/**
 * BUILDER (Builder).
 * Declares the steps needed to assemble a {@link CarePlan}.
 */
public interface CarePlanBuilder {

    void buildHorseProfile();

    void buildBaseCare();

    void buildExtraServices();

    void buildSchedule();

    void buildRecommendations();

    CarePlan getCarePlan();
}
