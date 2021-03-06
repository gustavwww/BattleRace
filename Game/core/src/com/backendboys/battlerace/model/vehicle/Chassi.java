package com.backendboys.battlerace.model.vehicle;

/**
 * Has properties of the chassi on a vehicle.
 */
class Chassi {

    private final float width;
    private final float height;
    private final float mass;

    Chassi(float width, float height, float mass) {
        this.width = width;
        this.height = height;
        this.mass = mass;
    }

    float getWidth() {
        return width;
    }

    float getHeight() {
        return height;
    }

    float getMass() {
        return mass;
    }

}
