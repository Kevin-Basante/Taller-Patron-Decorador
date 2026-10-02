package com.horsecare.prototype;

import java.util.List;
import java.util.Set;

/**
 * Concrete prototype for elderly horses.
 */
public class SeniorHorseCarePlan extends HorseCarePlan {

    private String jointSupplement;

    public SeniorHorseCarePlan(int ageYears, double weightKg, List<String> feedingSchedule,
                               List<String> vaccinations, Set<PlanExtra> extras, String jointSupplement) {
        super("Senior", ageYears, weightKg, feedingSchedule, vaccinations, extras);
        setJointSupplement(jointSupplement);
    }

    private SeniorHorseCarePlan(SeniorHorseCarePlan source) {
        super(source);
        this.jointSupplement = source.jointSupplement;
    }

    @Override
    public SeniorHorseCarePlan clone() {
        return new SeniorHorseCarePlan(this);
    }

    @Override
    public String getCategory() {
        return "Senior horse care plan";
    }

    @Override
    public String getSpecialCare() {
        return "Daily " + jointSupplement + " and dental check every six months";
    }

    public String getJointSupplement() {
        return jointSupplement;
    }

    public void setJointSupplement(String jointSupplement) {
        if (jointSupplement == null || jointSupplement.isBlank()) {
            throw new IllegalArgumentException("A senior plan needs a joint supplement");
        }
        this.jointSupplement = jointSupplement;
    }
}
