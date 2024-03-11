package com.skloch.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// MENU SCREEN
// First thing the player sees, launches them into the actual game
public class MenuScreen implements Screen {
    final HustleGame game;
    private Stage menuStage;

    OrthographicCamera camera;

    private Viewport viewport;

    public MenuScreen(final HustleGame game) {
        this.game = game;
        this.game.menuScreen = this;
        // Create stage to draw UI on
        menuStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(menuStage);

        camera = new OrthographicCamera();
        viewport = new FitViewport(game.WIDTH, game.HEIGHT, camera);
        camera.setToOrtho(false, game.WIDTH, game.HEIGHT);

        // Make table to draw buttons and title
        Table table = new Table();
        table.setFillParent(true);
        menuStage.addActor(table);

        // Get fonts
        game.infoFont = game.skin.getFont("Button_white");
        game.smallinfoFont = game.skin.getFont("Button_white");
        game.smallinfoFont.getData().setScale(0.8f);

        // Creat the buttons and the title
        Label title = new Label("Heslington Hustle", game.skin, "title");
        TextButton startButton = new TextButton("New Game", game.skin);
        TextButton settingsButton = new TextButton("Settings", game.skin);
        TextButton creditsButton = new TextButton("Credits", game.skin);
        TextButton exitButton = new TextButton("Exit", game.skin);

        // Add everything to the table using row() to go to a new line
        int buttonWidth = 340;
        table.row().pad(80, 0, 10, 0);
        table.add(title).uniformX().padBottom(100);
        table.row();
        table.add(startButton).uniformX().width(buttonWidth).padBottom(10);
        table.row();
        table.add(settingsButton).uniformX().width(buttonWidth).padBottom(10);
        table.row();
        table.add(creditsButton).uniformX().width(buttonWidth).padBottom(30);
        table.row();
        table.add(exitButton).uniformX().width(buttonWidth);
        table.top();

        // Add listeners to the buttons so they do things when pressed
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new GameScreen(game));
            }
        }
        );

        // SETTINGS BUTTON
        Screen thisScreen = this;
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SettingsScreen(game, thisScreen));
            }
        });

        // CREDITS BUTTON
        creditsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new CreditScreen(game, thisScreen));
            }
        });

        exitButton.addListener(new ChangeListener() {
               @Override
               public void changed(ChangeEvent event, Actor actor) {
                   Gdx.app.exit();
               }
           }
        );

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.53f, 0.81f, 0.92f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Make the stage follow actions and draw itself
        menuStage.setViewport(viewport);
        menuStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        menuStage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    // Other required methods
    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(menuStage);

        // See the comment in the resume() function in GameScreen to see why this pointless line exists
        Gdx.input.setCursorPosition(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public void dispose() {
        menuStage.dispose();
    }

}
