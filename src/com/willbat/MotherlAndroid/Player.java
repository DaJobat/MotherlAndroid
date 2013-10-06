package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
    private Vector2 absolutePosition; // this is the players position in the world
    private Vector2 position;
    private Vector2 velocity = new Vector2(0,0);
    private Vector2 movementThisFrame = new Vector2(0,0);
    private Rectangle boundingRectangle; // this should be replaced with a bounding polygon when art is finalised

    public Player()
    {
        position = new Vector2(0, Gdx.graphics.getHeight());
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
        boundingRectangle = sprite.getBoundingRectangle();
    }

    public void update(SpriteBatch batch, ExtendedCamera camera, float delta)
    {
        handleInput(delta);
        move(delta);
        draw(batch, camera);
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera) {
        sprite.setX(position.x);
        sprite.setY(position.y);

        camera.translate((position.x + texture.getWidth()/2) - camera.position.x, (position.y + texture.getHeight()/2) - camera.position.y, 0);

        sprite.draw(batch);
    }

    public void handleInput(float delta) {
        boolean firstTouch = Gdx.input.isTouched(0);
        boolean secondTouch = Gdx.input.isTouched(1);
        boolean thirdTouch = Gdx.input.isTouched(2);
        movementThisFrame.x = 0;
        movementThisFrame.y = 0;

        if (firstTouch && secondTouch && thirdTouch)
        {

        }
        else if (firstTouch && secondTouch)
        {

        }
        else if (firstTouch)
        {
            moveTo(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), delta);
        }
    }

    public void moveTo(int x, int y, float delta){
        Vector2 destination = new Vector2(x, y);
        Vector2 centre = new Vector2(centreX, centreY);
        Vector2 direction = destination.sub(centre);
        Vector2 moveAmount = direction.mul(THRUSTER_POWER).mul(delta);
        movementThisFrame = moveAmount;
    }

    public void move(float delta)
    {
        // need to put collision in here so that the game can know whether to move the player or not.
        if(velocity.len() > MAX_SPEED){
            velocity.div(velocity.len() / MAX_SPEED);
        }

        velocity.mul(0.99f);//Air resistance type deal, slows stuff down

        velocity.add(0, -2f * delta); //Bit of gravity y'all
        velocity.add(movementThisFrame);
        position = position.add(velocity);

        if(position.x < 0){
            position.add(-position.x, 0);
        }

        if(position.y < 0){
            position.add(0, -position.y);
        }
    }
    public void getAbsolutePosition()
    {

    }
}
