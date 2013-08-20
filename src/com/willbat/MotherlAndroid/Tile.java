package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.Texture;

/**
 * This class contains all the properties of a tile.
 */
public class Tile {
    private Texture texture;
    private int textureLocation;
    protected int mineLevel;
    protected boolean visible;

    public Tile(Tiletype type) {
        for (Tiletype properties : type.values()) {
            mineLevel = properties.mineLevel;
            textureLocation = properties.textureLocation;

        }
    }
}
