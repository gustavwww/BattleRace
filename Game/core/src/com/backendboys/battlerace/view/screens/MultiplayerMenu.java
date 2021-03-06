package com.backendboys.battlerace.view.screens;


import com.backendboys.battlerace.controller.GameController;
import com.backendboys.battlerace.controller.MenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * The MultiplayerMenu, here you can chose between create game or join game.
 */
class MultiplayerMenu extends AbstractMenuScreen implements IScreen {

    private final SpriteBatch batch;
    private Stage stage;

    private TextField inputPlayerName;
    private TextField inputGameId;

    private final GameController gameController;

    MultiplayerMenu(MenuController menuController) {
        super(menuController);
        batch = new SpriteBatch();

        this.gameController = new GameController(getMenuController().getGame(), true);
    }

    /**
     * Shows the MultiplayerMenu and calls the method that add the buttons to the menu
     */
    @Override
    public void show() {
        super.show();

        stage = new Stage(getViewport(), batch);
        stage.addActor(createMultiplayerTable());

        Gdx.input.setInputProcessor(stage);
    }

    private Table createMultiplayerTable() {
        Table multiplayerTable = new Table();
        multiplayerTable.setFillParent(true);
        multiplayerTable.center();

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        Label lblPlayerName = new Label("Player name: ", uiSkin);
        lblPlayerName.setFontScale(1.5f);

        Label lblGameId = new Label("Game id: ", uiSkin);
        lblGameId.setFontScale(1.5f);

        Label vSpace = new Label("", uiSkin);

        inputPlayerName = new TextField("", uiSkin);
        inputGameId = new TextField("", uiSkin);

        final ImageButton btnJoinGame = new ImageButton(getButtonStyleFromName("join"));
        final ImageButton btnCreateGame = new ImageButton(getButtonStyleFromName("create"));
        final ImageButton btnBack = new ImageButton(getButtonStyleFromName("Back"));

        btnJoinGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!inputPlayerName.getText().isEmpty() && !inputGameId.getText().isEmpty()) {

                    gameController.getServerController().joinGame(inputPlayerName.getText(), inputGameId.getText());
                }
            }
        });

        btnCreateGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!inputPlayerName.getText().isEmpty()) {
                    gameController.getServerController().createGame(inputPlayerName.getText());
                }
            }
        });

        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMenuController().toMainMenu();
            }
        });

        multiplayerTable.add(lblPlayerName).align(Align.left).row();
        multiplayerTable.add(inputPlayerName).width(200).row();

        multiplayerTable.add(vSpace).row();

        multiplayerTable.add(lblGameId).align(Align.left).row();
        multiplayerTable.add(inputGameId).width(200).row();

        multiplayerTable.add(vSpace).row();

        multiplayerTable.add(btnJoinGame).row();
        multiplayerTable.add(btnCreateGame).row();
        multiplayerTable.add(btnBack).row();

        multiplayerTable.pack();

        return multiplayerTable;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        batch.setProjectionMatrix(super.getProjectionMatrix());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        stage.dispose();
    }

}
