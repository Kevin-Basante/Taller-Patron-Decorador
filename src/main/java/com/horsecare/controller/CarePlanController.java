package com.horsecare.controller;

import com.horsecare.prototype.CarePlanRegistry;
import com.horsecare.prototype.HorseCarePlan;
import com.horsecare.prototype.PlanExtra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * REST endpoints for the Prototype pattern.
 * GET  /api/plans/templates -> templates available in the registry
 * POST /api/plans/clone     -> clones a template and customizes the copy
 */
@RestController
@RequestMapping("/api/plans")
@CrossOrigin
public class CarePlanController {

    private final CarePlanRegistry registry = new CarePlanRegistry();

    @GetMapping("/templates")
    public List<PlanResponse> getTemplates() {
        List<PlanResponse> responses = new ArrayList<>();
        for (String key : registry.getTemplateKeys()) {
            responses.add(PlanResponse.from(key, registry.createPlan(key)));
        }
        return responses;
    }

    @PostMapping("/clone")
    public CloneResponse clonePlan(@RequestBody CloneRequest request) {
        HorseCarePlan clone = registry.createPlan(request.templateKey());

        clone.setHorseName(request.horseName());
        if (request.weightKg() != null) {
            clone.setWeightKg(request.weightKg());
        }
        if (request.ageYears() != null) {
            clone.setAgeYears(request.ageYears());
        }
        if (request.extras() != null) {
            clone.replaceExtras(toExtras(request.extras()));
        }

        HorseCarePlan template = registry.createPlan(request.templateKey());
        return new CloneResponse(
                PlanResponse.from(request.templateKey(), clone),
                PlanResponse.from(request.templateKey(), template)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
    }

    private List<PlanExtra> toExtras(List<String> names) {
        List<PlanExtra> extras = new ArrayList<>();
        for (String name : names) {
            try {
                extras.add(PlanExtra.valueOf(name.trim().toUpperCase()));
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Unknown extra service: " + name);
            }
        }
        return extras;
    }

    public record CloneRequest(
            String templateKey,
            String horseName,
            Double weightKg,
            Integer ageYears,
            List<String> extras
    ) {}

    public record CloneResponse(
            PlanResponse clonedPlan,
            PlanResponse originalTemplate
    ) {}

    public record PlanResponse(
            String templateKey,
            String category,
            String horseName,
            int ageYears,
            double weightKg,
            List<String> feedingSchedule,
            List<String> vaccinations,
            List<String> extras,
            String specialCare,
            String serviceDescription,
            double price
    ) {
        static PlanResponse from(String key, HorseCarePlan plan) {
            List<String> extraNames = new ArrayList<>();
            for (PlanExtra extra : plan.getExtras()) {
                extraNames.add(extra.name());
            }
            return new PlanResponse(
                    key.trim().toUpperCase(),
                    plan.getCategory(),
                    plan.getHorseName(),
                    plan.getAgeYears(),
                    plan.getWeightKg(),
                    plan.getFeedingSchedule(),
                    plan.getVaccinations(),
                    extraNames,
                    plan.getSpecialCare(),
                    plan.getServiceDescription(),
                    plan.getPrice()
            );
        }
    }
}
