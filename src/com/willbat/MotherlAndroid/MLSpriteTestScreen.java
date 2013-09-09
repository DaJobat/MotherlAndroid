package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 22/08/13
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
public class MLSpriteTestScreen implements Screen {
    MLCore game;
    BitmapFont font;
    SpriteBatch batch;
    Sprite sprite;

    public MLSpriteTestScreen(MLCore game) {
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("consolas.fnt"),Gdx.files.internal("consolas_0.png"),false);
        sprite = new Sprite(new Texture(Gdx.files.internal("tilesheettemplate.png")), 0, 0, 32, 64);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        sprite.setX(Gdx.graphics.getWidth()/2);
        sprite.setY(Gdx.graphics.getHeight()/2);
        sprite.setScale(3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(100f / 255, 149f / 255, 237f / 255, 1.0F);
        batch.begin();
        renderDebug();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

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

    }

    @Override
    public void dispose() {

    }

    public void renderDebug() {
        font.setColor(0.0f,0.0f,0.0f,1.0f);
        CharSequence debugString = ("W: " + Gdx.graphics.getWidth() + ", H: " + Gdx.graphics.getHeight() + ", FPS: " + Gdx.graphics.getFramesPerSecond());
        font.draw(batch, debugString, 0,Gdx.graphics.getHeight());
    }
}