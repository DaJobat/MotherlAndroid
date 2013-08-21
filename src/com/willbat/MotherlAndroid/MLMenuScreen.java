package com.willbat.MotherlAndroid;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 02:25
 * To change this template use File | Settings | File Templates.
 */
public class MLMenuScreen implements Screen {

    MLCore game;
    SpriteBatch batch;
    BitmapFont font;

    //Constructor to allow us to reference the main Game class (MLCore)
    public MLMenuScreen(MLCore game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("consolas.fnt"),Gdx.files.internal("consolas_0.png"),false);
    }
    @Override
    public void render(float delta) {
//        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        Gdx.gl20.glClearColor(200.0F, 200.0F, 200.0F, 1.0F);
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
        font.setColor(0.0f,0.0f,1.0f,1.0f);
        String debugString = ("W: " + Gdx.graphics.getWidth() + ", H: " + Gdx.graphics.getHeight() + ", FPS: " + Gdx.graphics.getFramesPerSecond());
        font.draw(batch, debugString, 0,0);
    }
}
