package com.backendboys.battlerace.controller;

import com.backendboys.battlerace.BattleRace;
import com.backendboys.battlerace.model.gamemodel.GameModel;
import com.backendboys.battlerace.model.gamemodel.opponent.OpponentPlayer;
import com.backendboys.battlerace.model.gamemodel.particles.IMissileListener;
import com.backendboys.battlerace.model.gamemodel.particles.WorldExplosions;
import com.backendboys.battlerace.model.gamemodel.world.GameWorld;
import com.backendboys.battlerace.view.screens.IGameScreen;
import com.backendboys.battlerace.view.screens.ScreenFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that handles inputs
 */
public class GameController implements InputProcessor, IMissileListener {

    private final GameModel gameModel;
    private final BattleRace game;

    private final List<Integer> keysDown;

    private boolean usedPowerUp = false;

    private final IGameScreen gameScreen;

    private final ServerController serverController;

    /**
     * @param game Created GameModel and set GameScreen.
     */
    public GameController(BattleRace game) {
        gameModel = new GameModel();

        WorldExplosions worldExplosions = gameModel.getWorldExplosions();
        worldExplosions.addMissileListener(this);

        this.game = game;

        keysDown = new ArrayList<>();

        gameScreen = ScreenFactory.createGameScreen(this);

        serverController = new ServerController(game, this);
        gameScreen.setServerController(serverController);
    }

    public void setGameScreen() {
        Gdx.input.setInputProcessor(this);
        game.setScreen(gameScreen);
    }

    public void handleAddOpponent(OpponentPlayer opponent) {
        gameModel.addOpponent(opponent);
    }

    public void handleRemoveOpponent(OpponentPlayer opponent) {
        gameModel.removeOpponent(opponent);
    }

    public void handleUpdateOpponentPosition(String name, float x, float y, float rotation) {
        gameModel.updateOpponentPosition(name, x, y, rotation);
    }

    /**
     * Calls stepWorld in GameModel
     */
    public void gameStepWorld() {
        gameModel.getGameWorld().stepWorld();
        handleKeyPresses();
    }

    private void handleKeyPresses() {
        for (int keycode : keysDown) {
            switch (keycode) {
                case Input.Keys.W:
                    gameModel.gas();
                    break;
                case Input.Keys.A:
                    gameModel.rotateLeft();
                    break;
                case Input.Keys.S:
                    gameModel.brake();
                    break;
                case Input.Keys.D:
                    gameModel.rotateRight();
                    break;
                case Input.Keys.ESCAPE:
                    toggleMenu();
                    break;
                case Input.Keys.SPACE:
                    if (!usedPowerUp) {
                        usedPowerUp = true;
                        gameModel.usePowerUp();
                    }
                default:
                    break;
            }
        }
    }

    private void toggleMenu() {
        game.startMenu();
    }

    @Override
    public boolean keyDown(int keycode) {
        keysDown.add(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            usedPowerUp = false;
        }
        keysDown.removeAll(Arrays.asList(keycode));
        return true;
    }

    /**
     * Handles key inputs.
     *
     * @param character the key typed.
     * @return is keyTyped was handled correctly.
     */
    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    public GameWorld getGameWorld() {
        return gameModel.getGameWorld();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public void onConnection() {

    }

    public ServerController getServerController() {
        return serverController;
    }

    @Override
    public void onMissileShot(Vector2 position, Vector2 velocity, float rotation) {
        serverController.sendMissile(position, velocity, rotation);
    }
}
