package com.horsecare.builder;

import com.horsecare.decorator.BathDecorator;
import com.horsecare.decorator.PremiumFoodDecorator;
import com.horsecare.decorator.TrainingDecorator;
import com.horsecare.decorator.VeterinaryDecorator;
import com.horsecare.factory.HorseCareCreator;
import com.horsecare.model.HorseService;
import com.horsecare.model.HorseType;

import java.util.Objects;

/**
 * Shared implementation of the steps that are the same for every package.
 * <ul>
 *     <li>{@link #buildBaseCare()} uses the <b>Factory Method</b>: it asks the
 *     received {@link HorseCareCreator} for the base care, without knowing its class.</li>
 *     <li>The {@code add...} helpers use the <b>Decorator</b> pattern to wrap the
 *     current service with an additional one.</li>
 * </ul>
 * Each concrete builder only decides which extras, sessions and recommendations
 * its package has.
 */
public abstract class AbstractCarePlanBuilder implements CarePlanBuilder {

    private final String horseName;
    private final HorseType horseType;
    private final HorseCareCreator careCreator;
    private final CarePlan carePlan = new CarePlan();

    protected AbstractCarePlanBuilder(String horseName, HorseType horseType, HorseCareCreator careCreator) {
        this.horseName = Objects.requireNonNull(horseName, "The horse name is required");
        this.horseType = Objects.requireNonNull(horseType, "The horse type is required");
        this.careCreator = Objects.requireNonNull(careCreator, "The care creator is required");
    }

    /** @return the package this builder assembles. */
    protected abstract CarePackage getCarePackage();

    @Override
    public void buildHorseProfile() {
        carePlan.setHorseName(horseName);
        carePlan.setHorseType(horseType);
        carePlan.setCarePackage(getCarePackage());
        carePlan.setBuilderName(getClass().getSimpleName());
    }

    @Override
    public void buildBaseCare() {
        HorseService baseCare = careCreator.prepareBaseCare();
        carePlan.setCreatorName(careCreator.getClass().getSimpleName());
        carePlan.setBaseCareName(baseCare.getClass().getSimpleName());
        carePlan.setService(baseCare);
    }

    @Override
    public CarePlan getCarePlan() {
        if (carePlan.getService() == null) {
            throw new IllegalStateException("The base care must be built before getting the plan");
        }
        return carePlan;
    }

    protected HorseType getHorseType() {
        return horseType;
    }

    protected void setWeeklySessions(int sessions) {
        carePlan.setWeeklySessions(sessions);
    }

    protected void addRecommendation(String recommendation) {
        carePlan.addRecommendation(recommendation);
    }

    protected void addPremiumFood() {
        wrap(new PremiumFoodDecorator(currentService()));
    }

    protected void addBath() {
        wrap(new BathDecorator(currentService()));
    }

    protected void addTraining() {
        wrap(new TrainingDecorator(currentService()));
    }

    protected void addVeterinaryCheck() {
        wrap(new VeterinaryDecorator(currentService()));
    }

    private HorseService currentService() {
        HorseService service = carePlan.getService();
        if (service == null) {
            throw new IllegalStateException("buildBaseCare() must run before adding extra services");
        }
        return service;
    }

    private void wrap(HorseService decoratedService) {
        carePlan.setService(decoratedService);
        carePlan.addAppliedDecorator(decoratedService.getClass().getSimpleName());
    }
}
