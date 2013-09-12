package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This class contains the map of tiles that is the rendered game world
 * TODO: This will also contain the code used to generate the world and distribute minerals
 * TODO: add conditional constructors for loading maps
 * TODO: Maps could be stored as JSON objects, but it might make more sense just to store arrays of numbers and use the tiletype enum
 */
public class Map
{
    Tile[][] tiles;

    public Map(int width, int height)
    {
        tiles = new Tile[height][width];
        generateMap();
    }

    public Map(String filename)
    {

    }

    private void generateMap()
    {
        int rowNumber = 0;
        for (Tile[] row : tiles)
        {
            int columnNumber = 0;
            for (Tile tile : row)
            {
                if (rowNumber < 2)
                {
                    row[columnNumber] = new Tile(Tiletype.AIR, columnNumber, rowNumber);
                }
                else if (rowNumber > 2 && rowNumber < 5)
                {
                    row[columnNumber] = new Tile(Tiletype.DIRT, columnNumber, rowNumber);
                }
                else
                {
                    row[columnNumber] = new Tile(Tiletype.COAL, columnNumber, rowNumber);
                }
                columnNumber++;
            }
            rowNumber++;
        }
    }

    public void draw(SpriteBatch batch)
    {
        for (Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                if (tile != null)
                {
                    tile.draw(batch);
                }
            }
        }
    }
}