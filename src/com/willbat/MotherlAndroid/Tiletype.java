package com.willbat.MotherlAndroid;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public enum Tiletype
{
    // Tiletype is formatted in the following manner:
    // NAME (Texture, Mine Level, collides, mapLevel, visible)
    AIR(0,0,false,2, false),
    DIRT(1,0,true,2, true),
    SALT(2,0,true,2, true),
    COAL(3,1,true,2, true);
    // This refers to the column of the tilesheet that corresponds to this material
    public int textureLocation;
    // This refers to the level of upgrade needed for the drill
    public int mineLevel;
    // This says whether the tile is solid or not
    public boolean collides;
    //This sets the layer of the map the tile should belong to. 0 is background, 1 is middle, 2 is foreground
    public int mapLevel;
    //this sets whether the tile is visible or not
    public boolean visible;

    Tiletype(int textureLocation, int mineLevel, boolean collides, int mapLevel, boolean visible)
    {
        this.textureLocation = textureLocation;
        this.mineLevel = mineLevel;
        this.collides = collides;
        this.mapLevel = mapLevel;
        this.visible = visible;
    }
}
