package com.backendboys.battlerace.view.game;

import com.backendboys.battlerace.model.gamemodel.powerups.IPowerUp;
import com.backendboys.battlerace.model.gamemodel.powerups.MissilePowerUp;
import com.backendboys.battlerace.model.gamemodel.powerups.NitroPowerUp;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class CurrentPowerUpRender extends AbstractRender<IPowerUp> {

    private final Sprite nitroSprite;
    private final Sprite missileSprite;
    private final Sprite noPowerUpSprite;

    private static final int SPRITE_SIZE = 50;
    private static final int CAMERA_OFFSET_X = 240;
    private static final int CAMERA_OFFSET_Y = 130;

    public CurrentPowerUpRender(OrthographicCamera camera) {
        super(camera);

        nitroSprite = new Sprite(new Texture("newredcar.png"));
        nitroSprite.setSize(SPRITE_SIZE, SPRITE_SIZE);
        nitroSprite.setOriginCenter();

        missileSprite = new Sprite(new Texture("wheel.png"));
        missileSprite.setSize(SPRITE_SIZE, SPRITE_SIZE);
        missileSprite.setOriginCenter();

        noPowerUpSprite = new Sprite(new Texture("badlogic.jpg"));
        noPowerUpSprite.setSize(SPRITE_SIZE, SPRITE_SIZE);
        noPowerUpSprite.setOriginCenter();
    }

    @Override
    public void render(SpriteBatch batch, IPowerUp powerUp) {
        renderPowerUp(batch, powerUp, getCamera().position);
    }

    private void renderPowerUp(SpriteBatch batch, IPowerUp powerUp, Vector3 position) {
        batch.begin();
        batch.setProjectionMatrix(getCamera().combined);

        if (powerUp == null) {
            noPowerUpSprite.setPosition(position.x + CAMERA_OFFSET_X, position.y + CAMERA_OFFSET_Y);
            noPowerUpSprite.draw(batch);
        }
        else if (powerUp instanceof NitroPowerUp) {
            nitroSprite.setPosition(position.x + CAMERA_OFFSET_X, position.y + CAMERA_OFFSET_Y);
            nitroSprite.draw(batch);
        }
        else if (powerUp instanceof MissilePowerUp) {
            missileSprite.setPosition(position.x + CAMERA_OFFSET_X, position.y+ CAMERA_OFFSET_Y);
            missileSprite.draw(batch);
        }

        batch.end();
    }

    public void dispose() {
    }
}
