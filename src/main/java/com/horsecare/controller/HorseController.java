package com.horsecare.controller;

import com.horsecare.builder.CarePackage;
import com.horsecare.builder.CarePlan;
import com.horsecare.builder.ExtraServiceSelection;
import com.horsecare.model.HorseType;
import com.horsecare.service.CarePlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST API used by the frontend.
 * <ul>
 *     <li>GET  /api/options     - horse types and care packages for the selects</li>
 *     <li>POST /api/care-plans  - builds a care plan with Factory Method + Builder + Decorator</li>
 * </ul>
 */
@RestController
@RequestMapping("/api")
public class HorseController {

    private final CarePlanService carePlanService;

    public HorseController(CarePlanService carePlanService) {
        this.carePlanService = carePlanService;
    }

    @GetMapping("/options")
    public OptionsResponse options() {
        List<Option> horseTypes = new ArrayList<>();
        for (HorseType type : HorseType.values()) {
            horseTypes.add(new Option(type.name(), type.getLabel()));
        }
        List<Option> packages = new ArrayList<>();
        for (CarePackage carePackage : CarePackage.values()) {
            packages.add(new Option(carePackage.name(), carePackage.getLabel()));
        }
        return new OptionsResponse(horseTypes, packages);
    }

    @PostMapping("/care-plans")
    public CarePlanResponse createCarePlan(@RequestBody CarePlanRequest request) {
        CarePlan plan = carePlanService.createCarePlan(
                request.horseName(),
                parseEnum(HorseType.class, request.horseType(), "tipo de caballo"),
                parseEnum(CarePackage.class, request.carePackage(), "paquete"),
                new ExtraServiceSelection(request.premiumFood(), request.bath(),
                        request.training(), request.veterinary()));
        return CarePlanResponse.from(plan);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    private <E extends Enum<E>> E parseEnum(Class<E> type, String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Selecciona un " + fieldName + ".");
        }
        try {
            return Enum.valueOf(type, value.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Valor no válido para " + fieldName + ": " + value);
        }
    }

    public record CarePlanRequest(
            String horseName,
            String horseType,
            String carePackage,
            boolean premiumFood,
            boolean bath,
            boolean training,
            boolean veterinary
    ) {}

    public record CarePlanResponse(
            String horseName,
            String horseType,
            String carePackage,
            String description,
            double price,
            int weeklySessions,
            List<String> recommendations,
            String creator,
            String baseCare,
            String builder,
            List<String> decorators
    ) {
        static CarePlanResponse from(CarePlan plan) {
            return new CarePlanResponse(
                    plan.getHorseName(),
                    plan.getHorseType().getLabel(),
                    plan.getCarePackage().getLabel(),
                    plan.getDescription(),
                    plan.getPrice(),
                    plan.getWeeklySessions(),
                    plan.getRecommendations(),
                    plan.getCreatorName(),
                    plan.getBaseCareName(),
                    plan.getBuilderName(),
                    plan.getAppliedDecorators());
        }
    }

    public record Option(String id, String label) {}

    public record OptionsResponse(List<Option> horseTypes, List<Option> carePackages) {}

    public record ErrorResponse(String error) {}
}
