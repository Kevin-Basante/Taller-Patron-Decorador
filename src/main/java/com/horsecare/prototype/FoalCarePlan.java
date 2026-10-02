package com.horsecare.prototype;

import java.util.List;
import java.util.Set;

/**
 * Concrete prototype for young horses (foals).
 */
public class FoalCarePlan extends HorseCarePlan {

    private boolean nursing;

    public FoalCarePlan(int ageYears, double weightKg, List<String> feedingSchedule,
                        List<String> vaccinations, Set<PlanExtra> extras, boolean nursing) {
        super("Foal", ageYears, weightKg, feedingSchedule, vaccinations, extras);
        this.nursing = nursing;
    }

    private FoalCarePlan(FoalCarePlan source) {
        super(source);
        this.nursing = source.nursing;
    }

    @Override
    public FoalCarePlan clone() {
        return new FoalCarePlan(this);
    }

    @Override
    public String getCategory() {
        return "Foal care plan";
    }

    @Override
    public String getSpecialCare() {
        return nursing
                ? "Stays with its mother and gets a growth check every month"
                : "Weaned: gradual change to solid food and a growth check every month";
    }

    public boolean isNursing() {
        return nursing;
    }

    public void setNursing(boolean nursing) {
        this.nursing = nursing;
    }
}
