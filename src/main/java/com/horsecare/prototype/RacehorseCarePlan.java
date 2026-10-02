package com.horsecare.prototype;

import java.util.List;
import java.util.Set;

/**
 * Concrete prototype for horses in competition.
 */
public class RacehorseCarePlan extends HorseCarePlan {

    private int weeklyGallops;

    public RacehorseCarePlan(int ageYears, double weightKg, List<String> feedingSchedule,
                             List<String> vaccinations, Set<PlanExtra> extras, int weeklyGallops) {
        super("Racehorse", ageYears, weightKg, feedingSchedule, vaccinations, extras);
        setWeeklyGallops(weeklyGallops);
    }

    private RacehorseCarePlan(RacehorseCarePlan source) {
        super(source);
        this.weeklyGallops = source.weeklyGallops;
    }

    @Override
    public RacehorseCarePlan clone() {
        return new RacehorseCarePlan(this);
    }

    @Override
    public String getCategory() {
        return "Racehorse care plan";
    }

    @Override
    public String getSpecialCare() {
        return weeklyGallops + " gallop sessions per week and hoof check after each race";
    }

    public int getWeeklyGallops() {
        return weeklyGallops;
    }

    public void setWeeklyGallops(int weeklyGallops) {
        if (weeklyGallops < 0 || weeklyGallops > 7) {
            throw new IllegalArgumentException("Gallop sessions must be between 0 and 7 per week");
        }
        this.weeklyGallops = weeklyGallops;
    }
}
