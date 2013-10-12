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

    private ExtendedCamera camera;
    private Sprite sprite;
    private Texture texture;

    public static final float MAX_SPEED = 3f; //Das maximum speed
    public static final float THRUSTER_POWER = 0.02f; //How strong the players movement thrusters are

    public static final float centreX = Gdx.graphics.getWidth()/2;
    public static final float centreY = Gdx.graphics.getHeight()/2;

    private float zoomLevel;
    private Vector2 chunkLocation; // this is the current chunk of the player
    private Vector2 tileLocation; // this is the current tile the player is on in the chunk
    private Vector2 position;
    private Vector2 velocity = new Vector2(0,0);
    private Vector2 movementThisFrame = new Vector2(0,0);
    private Rectangle boundingRectangle; // this should be replaced with a bounding polygon when art is finalised

    public Player(ExtendedCamera camera, float zoomLevel)
    {
        this.camera = camera;
        this.zoomLevel = zoomLevel;
        position = new Vector2(0, 0);
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
        boundingRectangle = sprite.getBoundingRectangle();
    }

    public void update(SpriteBatch batch, float delta)
    {
        handleInput(delta);
        move(delta);
        draw(batch);
    }

    public void draw(SpriteBatch batch) {
        sprite.setX(position.x);
        sprite.setY(position.y);

        updateCamera();

        sprite.draw(batch);
    }

    public void updateCamera(){

        float cameraX = (position.x + texture.getWidth()/2) - camera.position.x;
        float cameraY = (position.y + texture.getHeight()/2) - camera.position.y;

        if(position.x + texture.getWidth()/2 < Gdx.graphics.getWidth()/2 * zoomLevel){
            cameraX = ((Gdx.graphics.getWidth()/2)*zoomLevel) - camera.position.x;
        }

        if(position.y + texture.getHeight()/2 > 0){
            cameraY = 0 - camera.position.y;
        }

        camera.translate(cameraX, cameraY, 0);
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

    public Vector2 getPositionOnScreen(){
        float x = centreX + ((position.x + texture.getWidth()/2) - camera.position.x)/zoomLevel;
        float y = centreY + ((position.y + texture.getHeight()/2) - camera.position.y)/zoomLevel;

        return new Vector2(x, y);
    }

    public void moveTo(int x, int y, float delta){
        Vector2 destination = new Vector2(x, y);
        Vector2 centre = getPositionOnScreen();
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
            position.x = 0;
            velocity.x = 0;
        }

        if(position.y > ((Gdx.graphics.getHeight()/2)*zoomLevel) - texture.getHeight()){
            position.y = ((Gdx.graphics.getHeight()/2)*zoomLevel) - texture.getHeight();
            velocity.y = 0;
        }
        else if(position.y < -200){
            position.y = -200;
            velocity.y = 0;
        }
    }
}
