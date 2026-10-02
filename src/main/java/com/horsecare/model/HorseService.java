package com.horsecare.model;

/**
 * COMPONENT (Decorator) and PRODUCT (Factory Method).
 * Common contract for every base care and every additional service.
 */
public interface HorseService {
    String getDescription();
    double getPrice();
}
