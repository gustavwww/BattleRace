package com.backendboys.battlerace.view.screens;


import com.backendboys.battlerace.controller.MenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * The OptionMenu in our game. Is in view when it's set as the current screen for our application
 */
class OptionsMenu extends AbstractMenuScreen implements IScreen {

    private final SpriteBatch batch;
    private Stage stage;
    private final static Music music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
    private boolean musicSliderUpdating = false;

    /**
     * Constructor
     *
     * @param menuController The controller is needed currently to check setting's
     */
    OptionsMenu(MenuController menuController) {
        super(menuController);


        batch = new SpriteBatch();
    }

    /**
     * Shows the OptionsMenu and calls the method that add the buttons to the menu
     */
    @Override
    public void show() {
        super.show();

        stage = new Stage(getViewport(), batch);
        stage.addActor(createOptionsTable());

        Gdx.input.setInputProcessor(stage);

    }

    private Table createOptionsTable() {
        Table optionsTable = new Table();
        optionsTable.setFillParent(true);
        optionsTable.center();

        final ImageButton soundButton = new ImageButton(getButtonStyleFromName("Soundon"));
        if (music.isPlaying()) {
            soundButton.setStyle(getButtonStyleFromName("Soundon"));
        } else {
            soundButton.setStyle(getButtonStyleFromName("Soundoff"));
        }

        ImageButton backToMainMenuButton = new ImageButton(getButtonStyleFromName("Back"));

        backToMainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getMenuController().toMainMenu();
            }
        });

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (music.isPlaying()) {
                    playMenuMusic(false);
                    soundButton.setStyle(getButtonStyleFromName("Soundoff"));

                } else {
                    playMenuMusic(true);
                    soundButton.setStyle(getButtonStyleFromName("Soundon"));
                }


            }
        });

        Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Slider musicSlider = new Slider(0, 100, 1f, false, uiSkin);
        musicSlider.setValue(music.getVolume() * 100);
        addSliderListener(musicSlider);
        optionsTable.add(soundButton).row();
        optionsTable.add(musicSlider).row();
        optionsTable.add(backToMainMenuButton).row();

        return optionsTable;
    }

    private void addSliderListener(final Slider slider) {
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (slider.isDragging() && !musicSliderUpdating) {
                    musicSliderUpdating = true;
                    music.setVolume(slider.getPercent());
                    musicSliderUpdating = false;
                }
            }
        });
    }


    /**
     * @param play Should background music be played or not.
     */
    private void playMenuMusic(boolean play) {
        if (play) {
            if (!music.isPlaying()) {
                music.play();
                music.setLooping(true);
            }
            return;
        }

        if (music.isPlaying()) {
            music.stop();
        }
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
