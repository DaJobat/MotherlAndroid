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
    DIRT(0),
    SALT(0),
    COAL(1);
    public int mineLevel;

    Tiletype(int mineLevel) {
    this.mineLevel = mineLevel;
    }
}
