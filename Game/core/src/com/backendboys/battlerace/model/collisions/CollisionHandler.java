package com.backendboys.battlerace.model.collisions;

import com.backendboys.battlerace.model.GameModel;
import com.backendboys.battlerace.model.particles.AbstractExplosive;
import com.backendboys.battlerace.model.powerups.IPowerUp;
import com.backendboys.battlerace.model.vehicle.IVehicle;
import com.backendboys.battlerace.model.world.FinishLineGenerator;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

/**
 * A class that handles the logic for what happens when bodies collide in the world
 */
public class CollisionHandler implements ContactListener {

    private final GameModel model;
    private final ArrayList<IFinishLineListener> finishLineListeners;

    public CollisionHandler(GameModel model) {
        this.model = model;
        finishLineListeners = new ArrayList<>();
    }

    /**
     * Everytime two bodies collide in the world a contact is created between two fixtures
     * The data that a contact provides is used to make thing happen when specific bodies collide
     * Example: when a Missile collides with anything it explodes
     *
     * @param contact A contact has two Fixture objects which in turn can be used to check if the userData matches a specific object
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        checkExplosivesContact(fixtureA, fixtureB);
        checkPowerUpsContact(fixtureA, fixtureB);
        checkFinishLineContact(fixtureA, fixtureB);
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private void checkExplosivesContact(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof AbstractExplosive && !(fixtureB.getUserData() instanceof IVehicle)) {

            AbstractExplosive abstractExplosive = (AbstractExplosive) fixtureA.getUserData();
            abstractExplosive.explosiveCollided();
        }
        if (fixtureB.getUserData() instanceof AbstractExplosive && !(fixtureA.getUserData() instanceof IVehicle)) {

            AbstractExplosive abstractExplosive = (AbstractExplosive) fixtureB.getUserData();
            abstractExplosive.explosiveCollided();
        }
    }

    private void checkPowerUpsContact(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof IPowerUp && fixtureB.getUserData() instanceof IVehicle) {
            IPowerUp powerUp = (IPowerUp) fixtureA.getUserData();
            model.getPlayer().addPowerUp(powerUp);
            model.removePowerUp(powerUp);
        }
    }

    private void checkFinishLineContact(Fixture fixtureA, Fixture fixtureB) {
        if (fixtureA.getUserData() instanceof FinishLineGenerator && fixtureB.getUserData() instanceof IVehicle) {
            notifyFinishLineListeners();
        }
    }

    private void notifyFinishLineListeners() {
        for (IFinishLineListener finishLineListener : finishLineListeners) {
            finishLineListener.onTouchedFinishLine();
        }
    }

    public void addFinishLineListener(IFinishLineListener finishLineListener) {
        finishLineListeners.add(finishLineListener);
    }

    public void removeFinishLineListener(IFinishLineListener finishLineListener) {
        finishLineListeners.remove(finishLineListener);
    }


}
