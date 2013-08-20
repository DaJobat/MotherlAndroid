package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.Texture;

/**
 * This class contains all the properties of a tile.
 */
public class Tile {
    protected Texture texture;
    protected int mineLevel;
    protected boolean visible;

    public Tile(Tiletype type) {
        getProperties(type);
    }

    public void getProperties(Tiletype type) {
        switch (type) {
            case DIRT:

                break;
            case SALT:
                break;
        }
    }
}
