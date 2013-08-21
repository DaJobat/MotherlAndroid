package com.willbat.MotherlAndroid;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

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
    Circle[] circles = new Circle[20];

    //Constructor to allow us to reference the main Game class (MLCore)
    public MLMenuScreen(MLCore game){
        this.game = game;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("consolas.fnt"),Gdx.files.internal("consolas_0.png"),false);
    }
    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched())
        {
            game.setScreen(new MLGameScreen(game));
        }
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl20.glClearColor(100f / 255, 149f / 255, 237f / 255, 1.0F);
        batch.begin();
        renderDebug();
        batch.end();

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeType.Circle);
        shapeRenderer.setColor(1, 1, 0, 1);

        for(int i=0; i<20; i++){
            if(i<19 && circles[i+1] != null){
                circles[i] = circles[i+1];
            }else{
                circles[i] = new Circle(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            }
            shapeRenderer.circle(circles[i].x, circles[i].y, i+1);
        }

        shapeRenderer.end();
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

    private class Circle{
        public int x, y;

        public Circle(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
