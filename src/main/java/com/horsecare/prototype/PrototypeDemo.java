package com.horsecare.prototype;

/**
 * Console demo of the Prototype pattern, following the style of the class example.
 */
public class PrototypeDemo {

    public static void main(String[] args) {
        CarePlanRegistry registry = new CarePlanRegistry();

        // Clone the racehorse template for a new horse
        HorseCarePlan thunder = registry.createPlan("RACEHORSE");
        thunder.setHorseName("Thunder");
        thunder.setWeightKg(510);
        System.out.println("Clone 1 : " + thunder);

        // Clone the same template again and customize only what changes
        HorseCarePlan storm = registry.createPlan("RACEHORSE");
        storm.setHorseName("Storm");
        storm.removeExtra(PlanExtra.TRAINING);
        storm.addExtra(PlanExtra.BATH);
        storm.addVaccination("West Nile virus");
        System.out.println("Clone 2 : " + storm);

        // The template was not modified by the changes made to the clones
        HorseCarePlan untouched = registry.createPlan("RACEHORSE");
        System.out.println("Template: " + untouched);
        System.out.println("Template vaccinations: " + untouched.getVaccinations());
        System.out.println("Storm vaccinations   : " + storm.getVaccinations());

        // A clone is a different object with the same data
        HorseCarePlan copy = thunder.clone();
        System.out.println("Same object? " + (copy == thunder));
        System.out.println("Same data?   " + copy.toString().equals(thunder.toString()));
    }
}
