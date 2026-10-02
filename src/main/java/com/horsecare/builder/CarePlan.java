package com.horsecare.builder;

import com.horsecare.model.HorseService;
import com.horsecare.model.HorseType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PRODUCT (Builder).
 * Complex object with several parts: the horse profile, the base care created
 * by the Factory Method, the decorated service, the weekly schedule and the
 * recommendations. Its setters are package-private so only builders can fill it.
 */
public class CarePlan {

    private String horseName;
    private HorseType horseType;
    private CarePackage carePackage;
    private String builderName;
    private String creatorName;
    private String baseCareName;
    private HorseService service;
    private int weeklySessions;
    private final List<String> appliedDecorators = new ArrayList<>();
    private final List<String> recommendations = new ArrayList<>();

    CarePlan() {
        // Created only by the builders of this package.
    }

    void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    void setHorseType(HorseType horseType) {
        this.horseType = horseType;
    }

    void setCarePackage(CarePackage carePackage) {
        this.carePackage = carePackage;
    }

    void setBuilderName(String builderName) {
        this.builderName = builderName;
    }

    void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    void setBaseCareName(String baseCareName) {
        this.baseCareName = baseCareName;
    }

    void setService(HorseService service) {
        this.service = service;
    }

    void setWeeklySessions(int weeklySessions) {
        this.weeklySessions = weeklySessions;
    }

    void addAppliedDecorator(String decoratorName) {
        appliedDecorators.add(decoratorName);
    }

    void addRecommendation(String recommendation) {
        recommendations.add(recommendation);
    }

    public String getHorseName() {
        return horseName;
    }

    public HorseType getHorseType() {
        return horseType;
    }

    public CarePackage getCarePackage() {
        return carePackage;
    }

    public String getBuilderName() {
        return builderName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public String getBaseCareName() {
        return baseCareName;
    }

    public HorseService getService() {
        return service;
    }

    public String getDescription() {
        return service.getDescription();
    }

    public double getPrice() {
        return service.getPrice();
    }

    public int getWeeklySessions() {
        return weeklySessions;
    }

    public List<String> getAppliedDecorators() {
        return Collections.unmodifiableList(appliedDecorators);
    }

    public List<String> getRecommendations() {
        return Collections.unmodifiableList(recommendations);
    }
}
