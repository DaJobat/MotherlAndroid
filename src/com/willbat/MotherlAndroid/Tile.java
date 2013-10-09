package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import java.util.Random;

/**
 * This class contains all the properties of a tile.
 */
public class Tile
{
    protected Sprite tileSprite;
    protected boolean visible;
    public Vector2 chunk;
    public Vector3 positionInChunk;
    public boolean isDrawn;
    public Rectangle boundingRectangle;
    public BoundingBox boundingBox;
    public Tiletype type;

    /**
     *
     * @param chunk Vector2 containing the position of the chunk in the world.
     * @param positionInChunk Vector2 containing the position of the tile in the chunk.
     */
    public Tile(Vector2 chunk, Vector2 positionInChunk)
    {
        // Constructor for each tile.
        this.chunk = chunk;
        this.positionInChunk = new Vector3(positionInChunk.x, positionInChunk.y, 2);

        setTileType(); // sets tile type based on location in world

        tileSprite.setX(this.positionInChunk.x * tileSprite.getWidth());
        tileSprite.setY(-this.positionInChunk.y * tileSprite.getHeight() + (Gdx.graphics.getHeight() - tileSprite.getHeight()));
        setBoundingBox();
        boundingRectangle = tileSprite.getBoundingRectangle();
        isDrawn = false;
    }

    private void setTileType()
    {
        // in here, use the chunklocation and positioninchunk to decide what the tile can be. at present, make it all dirt for testing
        type = Tiletype.DIRT;

        Texture texture = new Texture(Gdx.files.internal("tilesheet.png"));
        int[] texLocation =  getTexture(texture, type.textureLocation);
        tileSprite = new Sprite(texture, texLocation[0], texLocation[1], 32, 32);
        visible = type.visible;
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

    public void draw(SpriteBatch batch)
    {
        tileSprite.draw(batch);
    }

    private void setBoundingBox()
    {
        Rectangle rectangle = tileSprite.getBoundingRectangle();
        boundingBox = new BoundingBox(new Vector3(rectangle.getX(), rectangle.getY(), 0), new Vector3(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(), 0));
    }
}