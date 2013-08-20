package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * This class contains all the properties of a tile.
 */
public class Tile {
    protected Sprite tileSprite;
    protected Texture texture;
    protected int mineLevel;
    protected boolean visible;
    protected int[] location;

    public Tile(Tiletype type, int x, int y) {
        // Constructor for each tile.
        texture = new Texture("res/tilesheettemplate.png");
        int[] texLocation =  getTexture(type.textureLocation);
        tileSprite = new Sprite(texture,texLocation[0], texLocation[1],32,32);
        location = new int[]{x,y};
        mineLevel = type.mineLevel;
    }

    private int[] getTexture(int location) {
        //result contains the x and y location of the texture in the texture sheet.
        int[] result = {location*32,0};
        // this gets a random texture from the correct column on the tilesheet
        int altTiles = ((texture.getHeight()/32) - 1);
        if (altTiles > 0)
        {
            Random tilepicker = new Random();
            result[1] = tilepicker.nextInt(altTiles)*32;
        }
        return result;
    }
}
