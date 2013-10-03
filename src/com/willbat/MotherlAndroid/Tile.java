package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.Random;

/**
 * This class contains all the properties of a tile.
 */
public class Tile
{
    protected Sprite tileSprite;
    protected int mineLevel;
    protected boolean visible;
    private int layer;
    private Vector3 absolutePosition;
    public boolean collides;
    public boolean isDrawn;
    public BoundingBox boundingBox;
    Tiletype type;

    public Tile(Tiletype tiletype, int x, int y)
    {
        // Constructor for each tile.
        visible = true;
        type = tiletype;
        Texture texture = new Texture(Gdx.files.internal("tilesheet.png"));
        int[] texLocation =  getTexture(texture, type.textureLocation);
        tileSprite = new Sprite(texture, texLocation[0], texLocation[1], 32, 32);
        tileSprite.setX(x * tileSprite.getWidth());
        tileSprite.setY(-y * tileSprite.getHeight() + (Gdx.graphics.getHeight() - tileSprite.getHeight()));
        setBoundingBox();
        mineLevel = type.mineLevel;
        this.collides = type.collides;
        this.visible = type.visible;
        isDrawn = false;
    }

    private int[] getTexture(Texture texture, int textureLocation)
    {
        //result contains the x and y location of the texture in the texture sheet.
        int[] result = {0,0};
        result[0] = textureLocation*32;
        // this gets a random texture from the correct column on the tilesheet
        int altTiles = texture.getHeight()/32;
        if (altTiles > 0)
        {
            Random tilepicker = new Random();
            result[1] = tilepicker.nextInt(altTiles)*32;
        }
        return result;
    }

    public void draw(SpriteBatch batch) {
        tileSprite.draw(batch);
    }

    private void setBoundingBox()
    {
        Rectangle rectangle = tileSprite.getBoundingRectangle();
        boundingBox = new BoundingBox(new Vector3(rectangle.getX(), rectangle.getY(), 0), new Vector3(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(), 0));
    }
}