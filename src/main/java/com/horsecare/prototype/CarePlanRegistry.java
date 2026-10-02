package com.horsecare.prototype;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Prototype registry.
 * Keeps one template of each kind of plan. Clients never receive the template itself:
 * they always receive a clone, so the templates stay unchanged.
 */
public class CarePlanRegistry {

    private final Map<String, HorseCarePlan> templates = new LinkedHashMap<>();

    public CarePlanRegistry() {
        registerDefaultTemplates();
    }

    public void register(String key, HorseCarePlan template) {
        if (key == null || key.isBlank() || template == null) {
            throw new IllegalArgumentException("A template needs a key and a plan");
        }
        templates.put(normalize(key), template);
    }

    public HorseCarePlan createPlan(String key) {
        HorseCarePlan template = templates.get(normalize(key));
        if (template == null) {
            throw new IllegalArgumentException("Unknown care plan template: " + key);
        }
        return template.clone();
    }

    public List<String> getTemplateKeys() {
        return new ArrayList<>(templates.keySet());
    }

    private String normalize(String key) {
        return key == null ? "" : key.trim().toUpperCase();
    }

    private void registerDefaultTemplates() {
        register("RACEHORSE", new RacehorseCarePlan(
                4, 480,
                List.of("05:00 oats and barley", "12:00 alfalfa hay", "18:00 electrolytes and hay"),
                List.of("Tetanus", "Equine influenza", "Equine herpesvirus"),
                EnumSet.of(PlanExtra.PREMIUM_FOOD, PlanExtra.TRAINING, PlanExtra.VETERINARY),
                5));

        register("SENIOR", new SeniorHorseCarePlan(
                22, 450,
                List.of("07:00 soaked senior feed", "13:00 soft grass hay", "19:00 soaked beet pulp"),
                List.of("Tetanus", "Equine influenza", "Rabies"),
                EnumSet.of(PlanExtra.BATH, PlanExtra.VETERINARY),
                "glucosamine"));

        register("FOAL", new FoalCarePlan(
                0, 90,
                List.of("Mother's milk on demand", "10:00 creep feed", "16:00 creep feed"),
                List.of("Tetanus (first dose)", "Deworming"),
                EnumSet.of(PlanExtra.VETERINARY),
                true));
    }
}
