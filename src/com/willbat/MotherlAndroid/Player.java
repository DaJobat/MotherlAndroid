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
    public static final float MOV_SPEED = 0.2f; //Movement speed in pixels per tick or something
    private float centreX = Gdx.graphics.getWidth()/2;
    private float centreY = Gdx.graphics.getHeight()/2;
    private Vector2 position;

    public Player()
    {
        position = new Vector2(0, Gdx.graphics.getHeight());
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
    }

    public void update()
    {
//        draw();
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera) {
        sprite.setX(position.x);
        sprite.setY(position.y);

        camera.translate(position.x - camera.position.x, position.y - camera.position.y, 0);

        sprite.draw(batch);
    }

    public void move(float delta, int x, int y){
        Vector2 destination = new Vector2(x, y);
        Vector2 centre = new Vector2(centreX, centreY);
        Vector2 direction = destination.sub(centre);
        Vector2 moveAmount = direction.mul(MOV_SPEED).mul(delta);
        position = position.add(moveAmount);
    }

    public void getAbsolutePosition()
    {

    }
}
