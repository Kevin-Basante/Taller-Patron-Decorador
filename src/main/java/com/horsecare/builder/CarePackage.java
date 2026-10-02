package com.horsecare.builder;

/**
 * Care packages offered by the center. Each one is assembled by a different
 * concrete builder.
 */
public enum CarePackage {

    CUSTOM("Personalizado"),
    COMPETITION("Competencia"),
    WELLNESS("Bienestar"),
    HEALTH_CHECK("Control de salud");

    private final String label;

    CarePackage(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
