package com.horsecare.builder;

import com.horsecare.factory.HorseCareCreator;
import com.horsecare.model.HorseType;

/**
 * CONCRETE BUILDER: competition package (premium food, training and veterinary check).
 */
public class CompetitionCarePlanBuilder extends AbstractCarePlanBuilder {

    public CompetitionCarePlanBuilder(String horseName, HorseType horseType, HorseCareCreator careCreator) {
        super(horseName, horseType, careCreator);
    }

    @Override
    protected CarePackage getCarePackage() {
        return CarePackage.COMPETITION;
    }

    @Override
    public void buildExtraServices() {
        addPremiumFood();
        addTraining();
        addVeterinaryCheck();
    }

    @Override
    public void buildSchedule() {
        setWeeklySessions(5);
    }

    @Override
    public void buildRecommendations() {
        addRecommendation("Dar un día de descanso completo después de cada competencia.");
        addRecommendation("Hidratar con electrolitos en días de entrenamiento intenso.");
    }
}
