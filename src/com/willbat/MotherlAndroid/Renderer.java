package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 10/09/13
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */
public class Renderer {

    BitmapFont font;
    SpriteBatch debugBatch;
    public Renderer()
    {
        debugBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("consolas.fnt"),Gdx.files.internal("consolas_0.png"),false);
    }
    public void render(SpriteBatch batch, OrthographicCamera camera)
    {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
        debugBatch.begin();
        renderDebug();
        debugBatch.end();
    }

    public void renderDebug() {
        font.setColor(0.0f,0.0f,0.0f,1.0f);
        CharSequence debugString = ("W: " + Gdx.graphics.getWidth() + ", H: " + Gdx.graphics.getHeight() + ", FPS: " + Gdx.graphics.getFramesPerSecond());
        font.draw(debugBatch, debugString, 0,Gdx.graphics.getHeight());
    }
}
