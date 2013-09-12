package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

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
    private int x; //Where the player is at, yo.
    private int y;
    public static final float MOV_SPEED = 10f; //Movement speed in pixels per tick or something
    private Vector2 vector;

    public Player()
    {
        x=0;
        y=0;
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
    }

    public void draw(SpriteBatch batch) {
        sprite.setX(x);
        sprite.setY(y);
        sprite.draw(batch);
    }
    public void moveTo(int x, int y){
        Vector2 current = new Vector2(this.x, this.y);
        Vector2 destination = new Vector2(x, y);
        Vector2 direction = destination.sub(current).nor();
        Vector2 newPosition = current.add(direction.mul(MOV_SPEED));
        this.x = (int) newPosition.x;
        this.y = (int) newPosition.y;
    }
}
