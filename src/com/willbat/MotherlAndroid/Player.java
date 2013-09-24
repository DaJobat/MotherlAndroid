package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
    public static final float MAX_SPEED = 3f; //Das maximum speed
    public static final float THRUSTER_POWER = 0.02f; //How strong the players movement thrusters are
    private float centreX = Gdx.graphics.getWidth()/2;
    private float centreY = Gdx.graphics.getHeight()/2;
    private Vector2 position;
    private Vector2 velocity = new Vector2(0,0);

    public Player()
    {
        position = new Vector2(0, Gdx.graphics.getHeight());
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
    }

    public void update(SpriteBatch batch, ExtendedCamera camera, float delta)
    {
        if(velocity.len() > MAX_SPEED){
            velocity.div(velocity.len() / MAX_SPEED);
        }

        velocity.mul(0.99f);//Air resistance type deal, slows stuff down

        velocity.add(0, -2f * delta); //Bit of gravity y'all
        position = position.add(velocity);

        if(position.x < 0){
            position.add(-position.x, 0);
        }

        if(position.y < 0){
            position.add(0, -position.y);
        }
        draw(batch, camera);
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera) {
        sprite.setX(position.x);
        sprite.setY(position.y);

        camera.translate((position.x + texture.getWidth()/2) - camera.position.x, (position.y + texture.getHeight()/2) - camera.position.y, 0);

        sprite.draw(batch);
    }

    public void move(int x, int y, float delta){
        Vector2 destination = new Vector2(x, y);
        Vector2 centre = new Vector2(centreX, centreY);
        Vector2 direction = destination.sub(centre);
        Vector2 moveAmount = direction.mul(THRUSTER_POWER).mul(delta);
        velocity.add(moveAmount);
    }

    public void getAbsolutePosition()
    {

    }
}
