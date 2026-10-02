package com.horsecare.prototype;

import com.horsecare.model.BasicHorseCare;
import com.horsecare.model.HorseService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract prototype.
 * Holds everything a stable needs to look after a horse: profile, feeding schedule,
 * vaccinations and extra services. Building one of these from scratch takes a lot of
 * data, so the stable keeps ready-made templates and clones them for every new horse.
 */
public abstract class HorseCarePlan implements CarePlanPrototype {

    private static final String UNASSIGNED_HORSE = "Unassigned";

    private final String templateName;
    private String horseName;
    private int ageYears;
    private double weightKg;
    private final List<String> feedingSchedule;
    private final List<String> vaccinations;
    private final EnumSet<PlanExtra> extras;

    protected HorseCarePlan(String templateName, int ageYears, double weightKg,
                            List<String> feedingSchedule, List<String> vaccinations,
                            Set<PlanExtra> extras) {
        if (templateName == null || templateName.isBlank()) {
            throw new IllegalArgumentException("A care plan needs a template name");
        }
        this.templateName = templateName;
        this.horseName = UNASSIGNED_HORSE;
        setAgeYears(ageYears);
        setWeightKg(weightKg);
        this.feedingSchedule = new ArrayList<>(feedingSchedule);
        this.vaccinations = new ArrayList<>(vaccinations);
        this.extras = extras.isEmpty() ? EnumSet.noneOf(PlanExtra.class) : EnumSet.copyOf(extras);
    }

    /**
     * Copy constructor used by every clone() implementation.
     * Lists and sets are copied (deep copy) so that changing the clone
     * never changes the original template.
     */
    protected HorseCarePlan(HorseCarePlan source) {
        this.templateName = source.templateName;
        this.horseName = source.horseName;
        this.ageYears = source.ageYears;
        this.weightKg = source.weightKg;
        this.feedingSchedule = new ArrayList<>(source.feedingSchedule);
        this.vaccinations = new ArrayList<>(source.vaccinations);
        this.extras = EnumSet.copyOf(source.extras);
    }

    @Override
    public abstract HorseCarePlan clone();

    public abstract String getCategory();

    public abstract String getSpecialCare();

    /**
     * Builds the decorated service for this plan using the Decorator pattern.
     */
    public HorseService toHorseService() {
        HorseService service = new BasicHorseCare();
        for (PlanExtra extra : extras) {
            service = extra.applyTo(service);
        }
        return service;
    }

    public double getPrice() {
        return toHorseService().getPrice();
    }

    public String getServiceDescription() {
        return toHorseService().getDescription();
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        if (horseName == null || horseName.isBlank()) {
            throw new IllegalArgumentException("The horse name cannot be empty");
        }
        this.horseName = horseName.trim();
    }

    public int getAgeYears() {
        return ageYears;
    }

    public void setAgeYears(int ageYears) {
        if (ageYears < 0 || ageYears > 40) {
            throw new IllegalArgumentException("The age must be between 0 and 40 years");
        }
        this.ageYears = ageYears;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        if (weightKg < 20 || weightKg > 1200) {
            throw new IllegalArgumentException("The weight must be between 20 and 1200 kg");
        }
        this.weightKg = weightKg;
    }

    public List<String> getFeedingSchedule() {
        return Collections.unmodifiableList(feedingSchedule);
    }

    public void addFeeding(String meal) {
        feedingSchedule.add(meal);
    }

    public List<String> getVaccinations() {
        return Collections.unmodifiableList(vaccinations);
    }

    public void addVaccination(String vaccine) {
        vaccinations.add(vaccine);
    }

    public Set<PlanExtra> getExtras() {
        return Collections.unmodifiableSet(extras);
    }

    public void addExtra(PlanExtra extra) {
        extras.add(extra);
    }

    public void removeExtra(PlanExtra extra) {
        extras.remove(extra);
    }

    public void replaceExtras(Collection<PlanExtra> newExtras) {
        extras.clear();
        extras.addAll(newExtras);
    }

    @Override
    public String toString() {
        return getCategory() + " for " + horseName
                + " (" + ageYears + " years, " + weightKg + " kg)"
                + " | extras: " + extras
                + " | price: $" + String.format("%,.0f", getPrice());
    }
}
