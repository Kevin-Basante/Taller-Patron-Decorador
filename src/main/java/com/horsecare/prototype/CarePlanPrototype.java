package com.horsecare.prototype;

/**
 * Prototype interface.
 * Any care plan that can copy itself implements this contract,
 * so the client never needs to know the concrete class it is cloning.
 */
public interface CarePlanPrototype {

    CarePlanPrototype clone();
}
