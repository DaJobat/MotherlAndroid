package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created with IntelliJ IDEA.
 * User: Willeh
 * Date: 12/09/13
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class Player {

    private Sprite sprite;
    private Texture texture;
    public static final float MOV_SPEED = 200f; //Movement speed in pixels per tick or something
    private Vector2 screenPosition;
    private Vector3 absolutePosition;

    public Player()
    {
        screenPosition = new Vector2(0,0);
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
    }

    public void update()
    {
//        draw();
    }
    public void draw(SpriteBatch batch) {
        sprite.setX(screenPosition.x);
        sprite.setY(screenPosition.y);
        sprite.draw(batch);
    }
    public void moveTo(float delta, int x, int y){
        Vector2 current = screenPosition;
        Vector2 destination = new Vector2(x, y);
        Vector2 direction = destination.sub(current).nor();
        Vector2 newPosition = current.add(direction.mul(MOV_SPEED).mul(delta));
        screenPosition = newPosition;
    }
    public void getAbsolutePosition()
    {

    }
}
