package com.horsecare.builder;

import com.horsecare.factory.HorseCareCreator;
import com.horsecare.model.HorseType;

import java.util.Objects;

/**
 * CONCRETE BUILDER: custom plan, the extras are the ones chosen by the customer.
 */
public class CustomCarePlanBuilder extends AbstractCarePlanBuilder {

    private final ExtraServiceSelection selection;

    public CustomCarePlanBuilder(String horseName, HorseType horseType, HorseCareCreator careCreator,
                                 ExtraServiceSelection selection) {
        super(horseName, horseType, careCreator);
        this.selection = Objects.requireNonNull(selection, "The service selection is required");
    }

    @Override
    protected CarePackage getCarePackage() {
        return CarePackage.CUSTOM;
    }

    @Override
    public void buildExtraServices() {
        if (selection.premiumFood()) {
            addPremiumFood();
        }
        if (selection.bath()) {
            addBath();
        }
        if (selection.training()) {
            addTraining();
        }
        if (selection.veterinary()) {
            addVeterinaryCheck();
        }
    }

    @Override
    public void buildSchedule() {
        setWeeklySessions(3);
    }

    @Override
    public void buildRecommendations() {
        addRecommendation("Revisar el plan con el cuidador cada mes.");
        if (getHorseType() == HorseType.FOAL && selection.training()) {
            addRecommendation("En potros, el entrenamiento debe ser suave y de corta duración.");
        }
        if (getHorseType() == HorseType.SENIOR && !selection.veterinary()) {
            addRecommendation("Para caballos mayores se aconseja agregar el chequeo veterinario.");
        }
    }
}
