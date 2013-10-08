package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.util.Random;

/**
 * This class contains the map of tiles that is the rendered game world
 * TODO: This will also contain the code used to generate the world and distribute minerals
 * TODO: add conditional constructors for loading maps
 * TODO: Maps could be stored as JSON objects, but it might make more sense just to store arrays of numbers and use the tiletype enum
 */
public class Map
{
    /**
     * The chunks variable contains the 9 chunks that are nearest the player at any one time.
     */
    Chunk[] chunks;
    Random random = new Random();

    public Map()
    {
        for (Chunk chunk : chunks)
        {
            chunk = new Chunk(new Vector2(0,0));
        }
    }

    public Map(String filename)
    {

    }

    public void draw(SpriteBatch batch, ExtendedCamera camera)
    {
        for (Chunk chunk : chunks)
        {
            chunk.draw(batch, camera);
        }
    }

    private class Chunk
    {
        Json json = new Json();
        protected Vector2 size;
        public Vector2 chunkPosition;
        private boolean exists;
        Tile[][][] tiles;

        public Chunk(Vector2 chunkPosition)
        {

            size = new Vector2(20,20);
            if (exists) // if chunk already exists, load from file
            {
                loadChunk();
            }
            else
            {

                generateChunk(20, 20);
            }
        }

        private void generateChunk(int width, int height)
        {
            tiles = new Tile[3][height][width];
            for (Tile[][] columnRow : tiles)
            {
                int rowNumber = 0;
                for (Tile[] row : columnRow)
                {
                    int columnNumber = 0;
                    for (Tile tile : row)
                    {
                        row[columnNumber] = new Tile(new Vector2(columnNumber, rowNumber), chunkPosition);
                    }
                    columnNumber++;
                }
                rowNumber++;
            }
        }

        private boolean loadChunk()
        {
            boolean success = false;

            return success;
        }

        private boolean saveChunk()
        {
            boolean success = false;
            FileHandle handle = Gdx.files.external("chunk.data");
            if (true)// check if chunk already exists in file
            {
                // overwrite chunk data, don't remove flag for chunk
                for (Tile[][] columnRow : tiles)
                {
                    for(Tile[] row : columnRow)
                    {
                        for(Tile tile : row)
                        {
                            if (Gdx.files.isExternalStorageAvailable())
                            {
                                //handle.writeString(json.prettyPrint(tile), true); // use this if need legibility in files.
                                handle.writeString(json.toJson(tile), true);
                            }
                        }
                    }
                }
                // add end flag to chunk
            }
            else
            {
                // insert chunk data in correct area of file
                for (Tile[][] columnRow : tiles)
                {
                    for(Tile[] row : columnRow)
                    {
                        for(Tile tile : row)
                        {
                            if (Gdx.files.isExternalStorageAvailable())
                            {
                                //handle.writeString(json.prettyPrint(tile), true); // use this if need legibility in files.
                                handle.writeString(json.toJson(tile), true);
                            }
                        }
                    }
                }
                // add end flag to chunk
            }
            return success;
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
    }
}