package com.horsecare.prototype;

import com.horsecare.decorator.BathDecorator;
import com.horsecare.decorator.PremiumFoodDecorator;
import com.horsecare.decorator.TrainingDecorator;
import com.horsecare.decorator.VeterinaryDecorator;
import com.horsecare.model.HorseService;

/**
 * Extra services a care plan can include.
 * Each value knows which existing decorator wraps the basic service,
 * which connects the Prototype pattern with the Decorator pattern of the project.
 */
public enum PlanExtra {

    PREMIUM_FOOD("Premium food") {
        @Override
        public HorseService applyTo(HorseService service) {
            return new PremiumFoodDecorator(service);
        }
    },
    BATH("Bath and grooming") {
        @Override
        public HorseService applyTo(HorseService service) {
            return new BathDecorator(service);
        }
    },
    TRAINING("Training") {
        @Override
        public HorseService applyTo(HorseService service) {
            return new TrainingDecorator(service);
        }
    },
    VETERINARY("Veterinary check-up") {
        @Override
        public HorseService applyTo(HorseService service) {
            return new VeterinaryDecorator(service);
        }
    };

    private final String label;

    PlanExtra(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public abstract HorseService applyTo(HorseService service);
}
