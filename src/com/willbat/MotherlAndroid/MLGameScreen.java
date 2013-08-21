package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 21:45
 * To change this template use File | Settings | File Templates.
 */
public class MLGameScreen implements Screen {

    MLCore game;
    Texture gameBtnTexture;
    SpriteBatch batch;
    BitmapFont font;

    //Constructor to allow us to reference the main Game class (MLCore)
    public MLGameScreen(MLCore game){
        this.game = game;
        gameBtnTexture = new Texture(Gdx.files.internal("gameButton.png"));
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("consolas.fnt"),Gdx.files.internal("consolas_0.png"),false);
    }

    @Override
    public void render(float delta) {
        //To change body of implemented methods use File | Settings | File Templates.
        if (Gdx.input.justTouched())
        {
            game.setScreen(new MLMenuScreen(game));
        }
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(100f / 255, 149f / 255, 237f / 255, 1.0F);

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeType.Circle);
        shapeRenderer.setColor(1, 1, 0, 1);
        shapeRenderer.circle(Gdx.input.getX(), Gdx.input.getY(), 20);
        shapeRenderer.end();
        batch.begin();
        renderDebug();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void show() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dispose() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void renderDebug() {
        font.setColor(0.0f,0.0f,0.0f,1.0f);
        CharSequence debugString = ("W: " + Gdx.graphics.getWidth() + ", H: " + Gdx.graphics.getHeight() + ", FPS: " + Gdx.graphics.getFramesPerSecond());
        font.draw(batch, debugString, 0,Gdx.graphics.getHeight());
    }
}
