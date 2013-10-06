package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * This class contains the map of tiles that is the rendered game world
 * TODO: This will also contain the code used to generate the world and distribute minerals
 * TODO: add conditional constructors for loading maps
 * TODO: Maps could be stored as JSON objects, but it might make more sense just to store arrays of numbers and use the tiletype enum
 */
public class Map
{
    Tile[][][] tiles;
    Chunk currentChunk;
    Random random = new Random();

    public Map(int width, int height)
    {
        tiles = new Tile[3][height][width];
        generateMap();
    }

    public Map(String filename)
    {

    }

    public void update(Vector3 playerPosition)
    {
//        if (playerPosition.y )
//        {
//
//        }
    }

    private void generateMap()
    {
        //int layerNumber = 0;
        for (Tile[][] columnRow : tiles)
        {
            int rowNumber = 0;
            for (Tile[] row : columnRow)
            {
                int columnNumber = 0;
                for (Tile tile : row)
                {
                    if (rowNumber <= 2)
                    {
                        row[columnNumber] = new Tile(Tiletype.AIR, columnNumber, rowNumber);
                    }
                    else if (rowNumber > 2 && rowNumber < 5)
                    {
                        row[columnNumber] = new Tile(Tiletype.DIRT, columnNumber, rowNumber);
                    }
                    else
                    {
                        if (random.nextInt(100)> 90)
                        {
                            row[columnNumber] = new Tile(Tiletype.SALT, columnNumber, rowNumber);
                        }
                        else if (random.nextInt(100)> 95)
                        {
                            row[columnNumber] = new Tile(Tiletype.COAL,columnNumber,rowNumber);
                        }
                        else
                        {
                            row[columnNumber] = new Tile(Tiletype.DIRT, columnNumber, rowNumber);
                        }
                    }
                    columnNumber++;
                }
                rowNumber++;
            }
            //layerNumber++;
        }
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera)
    {
        int layerNumber = 0;
        for (Tile[][] columnRow : tiles)
        {
            int columnNumber = 0;
            for (Tile[] row : columnRow)
            {
                int rowNumber = 0;
                for (Tile tile : row)
                {
                    tile.isDrawn = false;
                    if (layerNumber == 0)
                    {
                        if (tile != null && !tiles[layerNumber+2][columnNumber][rowNumber].visible && !tiles[layerNumber+1][columnNumber][rowNumber].visible && tile.visible)
                        {
                            if (camera.frustum.boundsInFrustum(tile.boundingBox))
                            {
                                tile.draw(batch);
                                tile.isDrawn = true;
                            }
                        }
                    }
                    else if (layerNumber == 1)
                    {
                        if (tile != null && !tiles[layerNumber+1][columnNumber][rowNumber].visible && tile.visible)
                        {
                            if (camera.frustum.boundsInFrustum(tile.boundingBox))
                            {
                                tile.draw(batch);
                                tile.isDrawn = true;
                            }
                        }
                    }
                    else if (layerNumber == 2)
                    {
                        if (tile != null && tile.visible)
                        {
                            if (camera.frustum.boundsInFrustum(tile.boundingBox))
                            {
                                tile.draw(batch);
                                tile.isDrawn = true;
                            }
                        }
                    }
                    rowNumber++;
                }
                columnNumber++;
            }
            layerNumber++;
        }
    }

    private class Chunk
    {
        protected Vector2 size;
        public Vector2 chunkLocation;

        public Chunk(Vector2 chunkLocation, boolean generate)
        {
            this.chunkLocation = chunkLocation;
            size = new Vector2(30,20);
            if (generate)
            {
                //generate chunk if it is not being loaded from a file
            }
            else
            {
                //load chunk from file.

            }
        }
    }
}