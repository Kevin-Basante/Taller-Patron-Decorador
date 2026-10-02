package com.horsecare.controller;

import com.horsecare.decorator.*;
import com.horsecare.model.BasicHorseCare;
import com.horsecare.model.HorseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@CrossOrigin
public class HorseController {

    @PostMapping("/calculate")
    public ServiceResponse calculate(@RequestBody ServiceRequest request) {

        HorseService service = new BasicHorseCare();

        if (request.premiumFood()) {
            service = new PremiumFoodDecorator(service);
        }

        if (request.bath()) {
            service = new BathDecorator(service);
        }

        if (request.training()) {
            service = new TrainingDecorator(service);
        }

        if (request.veterinary()) {
            service = new VeterinaryDecorator(service);
        }

        return new ServiceResponse(
                service.getDescription(),
                service.getPrice()
        );
    }

    public record ServiceRequest(
            boolean premiumFood,
            boolean bath,
            boolean training,
            boolean veterinary
    ) {}

    public record ServiceResponse(
            String description,
            double price
    ) {}
}
