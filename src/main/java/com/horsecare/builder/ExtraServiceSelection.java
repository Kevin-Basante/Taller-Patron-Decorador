package com.horsecare.builder;

/**
 * Additional services the customer ticked in the custom package.
 */
public record ExtraServiceSelection(
        boolean premiumFood,
        boolean bath,
        boolean training,
        boolean veterinary
) {
}
