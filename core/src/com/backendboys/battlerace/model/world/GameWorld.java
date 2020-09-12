package com.backendboys.battlerace.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {

    private World world;
    private GroundGenerator groundGenerator;

    private float accumulator;
    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    public GameWorld() {
        Box2D.init();
        world = new World(new Vector2(0, -10), true);
        groundGenerator = new GroundGenerator(10000, 1);
        groundGenerator.generateGround(world);
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);
        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    public void dispose() {
        world.dispose();
    }

    public World getWorld() {
        return world;
    }
}