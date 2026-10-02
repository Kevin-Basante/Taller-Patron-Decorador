package com.horsecare.service;

import com.horsecare.builder.CarePackage;
import com.horsecare.builder.CarePlan;
import com.horsecare.builder.CarePlanBuilder;
import com.horsecare.builder.CarePlanDirector;
import com.horsecare.builder.CompetitionCarePlanBuilder;
import com.horsecare.builder.CustomCarePlanBuilder;
import com.horsecare.builder.ExtraServiceSelection;
import com.horsecare.builder.HealthCheckCarePlanBuilder;
import com.horsecare.builder.WellnessCarePlanBuilder;
import com.horsecare.factory.FoalCareCreator;
import com.horsecare.factory.HorseCareCreator;
import com.horsecare.factory.LeisureHorseCareCreator;
import com.horsecare.factory.SeniorHorseCareCreator;
import com.horsecare.factory.SportHorseCareCreator;
import com.horsecare.model.HorseType;
import org.springframework.stereotype.Service;

/**
 * Client of the three patterns:
 * <ol>
 *     <li>Chooses the concrete creator for the horse type (Factory Method).</li>
 *     <li>Chooses the concrete builder for the package and lets the director run it (Builder).</li>
 *     <li>The builder wraps the base care with decorators (Decorator).</li>
 * </ol>
 */
@Service
public class CarePlanService {

    private static final int MAX_NAME_LENGTH = 40;

    public CarePlan createCarePlan(String horseName, HorseType horseType, CarePackage carePackage,
                                   ExtraServiceSelection selection) {
        String cleanName = validateName(horseName);
        if (horseType == null) {
            throw new IllegalArgumentException("Selecciona el tipo de caballo.");
        }
        if (carePackage == null) {
            throw new IllegalArgumentException("Selecciona un paquete de cuidado.");
        }

        HorseCareCreator creator = creatorFor(horseType);
        CarePlanBuilder builder = builderFor(carePackage, cleanName, horseType, creator, selection);

        CarePlanDirector director = new CarePlanDirector(builder);
        director.buildCarePlan();
        return builder.getCarePlan();
    }

    /** Factory Method: the client only picks the concrete creator. */
    private HorseCareCreator creatorFor(HorseType horseType) {
        return switch (horseType) {
            case LEISURE -> new LeisureHorseCareCreator();
            case SPORT -> new SportHorseCareCreator();
            case FOAL -> new FoalCareCreator();
            case SENIOR -> new SeniorHorseCareCreator();
        };
    }

    /** Builder: each package has its own concrete builder. */
    private CarePlanBuilder builderFor(CarePackage carePackage, String horseName, HorseType horseType,
                                       HorseCareCreator creator, ExtraServiceSelection selection) {
        return switch (carePackage) {
            case CUSTOM -> new CustomCarePlanBuilder(horseName, horseType, creator,
                    selection != null ? selection : new ExtraServiceSelection(false, false, false, false));
            case COMPETITION -> new CompetitionCarePlanBuilder(horseName, horseType, creator);
            case WELLNESS -> new WellnessCarePlanBuilder(horseName, horseType, creator);
            case HEALTH_CHECK -> new HealthCheckCarePlanBuilder(horseName, horseType, creator);
        };
    }

    private String validateName(String horseName) {
        if (horseName == null || horseName.isBlank()) {
            throw new IllegalArgumentException("Escribe el nombre del caballo.");
        }
        String clean = horseName.trim();
        if (clean.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("El nombre del caballo no puede superar "
                    + MAX_NAME_LENGTH + " caracteres.");
        }
        return clean;
    }
}
