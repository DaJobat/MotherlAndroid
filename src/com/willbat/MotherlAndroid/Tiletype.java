package com.willbat.MotherlAndroid;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public enum Tiletype {
    // Tiletype is formatted in the following manner:
    // NAME (Texture, Mine Level,)
    DIRT(0,0),
    SALT(1,0),
    COAL(2,1);
    // This refers to the column of the tilesheet that corresponds to this material
    public int textureLocation;
    // This refers to the level of upgrade needed for the drill
    public int mineLevel;

    Tiletype(int mineLevel, int textureLocation) {
        this.textureLocation = textureLocation;
        this.mineLevel = mineLevel;
    }
}
