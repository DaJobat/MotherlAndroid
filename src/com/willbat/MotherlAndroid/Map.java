package com.willbat.MotherlAndroid;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class contains the map of tiles that is the rendered game world
 * TODO: This will also contain the code used to generate the world and distribute minerals
 * TODO: add conditional constructors for loading maps
 * TODO: Maps could be stored as JSON objects, but it might make more sense just to store arrays of numbers and use the tiletype enum
 */
public class Map
{
    /**
     * The chunks array contains the 9 chunks that are nearest the player at any one time.
     */
    Chunk[] chunks;
    String mapName;
    FileHandle directory;
    FileHandle indexFile;
    FileHandle mapFile;
    BufferedReader indexFileReader;
    BufferedReader mapFileReader;

    public Map(String mapName)
    {
        //first, check if map already exists and setup file structure
        this.mapName = mapName;
        manageFiles();
        chunks = new Chunk[9];
        int k = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                chunks[k] = new Chunk(new Vector2(i,j));
                k++;
            }
        }
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera)
    {
        for (Chunk chunk : chunks)
        {
            chunk.draw(batch, camera);
        }
    }

    private void manageFiles()
    {
        directory = new FileHandle(Gdx.files.getExternalStoragePath().concat("/MotherlAndroid/" + mapName));
        if (!directory.exists() || !directory.isDirectory())
        {
            directory.mkdirs();
        }
        indexFile = new FileHandle(directory.path().concat("/"+mapName+".mlindex"));
        try
        {
            indexFile.file().createNewFile();
        }
        catch (IOException e)
        {
            Log.d(e.toString(), "Cannot create index file");
        }
        mapFile = new FileHandle(directory.path().concat("/"+mapName+".mlmap"));
        try
        {
            mapFile.file().createNewFile();
        }
        catch (IOException e)
        {
            Log.d(e.toString(), "Cannot create map file");
        }
    }

    //Chunk class starts here.
    private class Chunk
    {
        Json json = new Json();
        protected Vector2 size;
        public Vector2 chunkPosition;
        int chunkLengthInFile = 0;
        int chunkLineInFile = 0;
        boolean changedSinceLastSaved = false;
        Tile[][] tiles;

        public Chunk(Vector2 chunkPosition)
        {
            this.chunkPosition = chunkPosition;
            size = new Vector2(20, 20);
            if (checkChunkExists()) // if chunk already exists, load from file
            {
                loadChunk();
            }
            else
            {
                generateChunk();
            }
        }

        private void generateChunk()
        {
            tiles = new Tile[(int)size.y][(int)size.x];
            int rowNumber = 0;
            for (Tile[] row : tiles)
            {
                for (int j = 0; j<row.length; j++)
                {
                    row[j] = new Tile(chunkPosition, new Vector2(j, rowNumber));
                }
                rowNumber++;
            }
        }

        private boolean loadChunk()
        {
            boolean success = false;
            mapFileReader = mapFile.reader(8192);
            String currentLine;
            int i = 0;
            try
            {
                currentLine = mapFileReader.readLine();
                tiles = new Tile[(int)size.y][(int)size.x];
                outsideloop:
                for (Tile[] row : tiles)
                {
                    for (int j = 0; j<row.length; j++)
                    {
                        row[j] = json.fromJson(Tile.class, currentLine);
                        currentLine = mapFileReader.readLine();
                        if (currentLine ==null)
                        {
                            break outsideloop;
                        }
                    }
                }
            }
            catch (IOException e)
            {
                Log.d(e.toString(), "Map file could not be read on load");
            }
            return success;
        }

        private boolean saveChunk()
        {
            boolean success = false;

            if (checkChunkExists() && changedSinceLastSaved)// check if chunk already exists in file
            {
                mapFileReader = mapFile.reader(8192);
                FileHandle tempFile = new FileHandle(Gdx.files.getExternalStoragePath().concat("/MotherlAndroid/tempfile"));
                if (tempFile.exists())
                {
                    tempFile.delete();
                }
                PrintWriter pw = new PrintWriter(tempFile.writer(true));
                String currentLine;
                try
                {
                    currentLine = mapFileReader.readLine();
                    int i = 0;
                    while (currentLine!=null)
                    {
                        if (i < chunkLineInFile && i >= chunkLineInFile+chunkLengthInFile) // if the current line is either earlier than the start of the chunk data, or after the end of the chunk data
                        {
                            pw.println(currentLine);
                        }
                        currentLine = mapFileReader.readLine();
                        i++;
                    }
                    chunkLineInFile = i;
                    //this removes the old chunk data, now we append the new chunk data to the end of the file
                    int j = 0;
                    for(Tile[] row : tiles)
                    {
                        for(Tile tile : row)
                        {
                            // write tile as json to a new line
                            pw.println(json.toJson(tile));
                            j++;
                        }
                    }
                    pw.close();
                    mapFileReader.close();
                    chunkLengthInFile = j;
                    Gdx.files.external(tempFile.path()).moveTo(mapFile);
                    tempFile.delete();
                }
                catch (IOException e)
                {
                    Log.d(e.toString(), "Could not read line in map file");
                }
                //finally, need to rewrite the index file with the new chunk data
                try
                {
                    indexFileReader = indexFile.reader(8192);
                    tempFile = new FileHandle(Gdx.files.getExternalStoragePath().concat("/MotherlAndroid/tempindexfile"));
                    if (tempFile.exists())
                    {
                        tempFile.delete();
                    }
                    pw = new PrintWriter(tempFile.writer(true));
                    currentLine = indexFileReader.readLine();
                    while (currentLine != null)
                    {
                        if (!currentLine.startsWith(chunkPosition.x+":"+chunkPosition.y))
                        {
                            pw.println(currentLine);
                        }
                    }
                    pw.println(chunkPosition.x+":"+chunkPosition.y+"|"+chunkLineInFile+"|"+chunkLengthInFile);
                    pw.close();
                    indexFileReader.close();
                    Gdx.files.external(tempFile.path()).moveTo(indexFile);
                    tempFile.delete();
                }
                catch (IOException e)
                {
                    Log.d(e.toString(), "Could not read line in index file");
                }
            }
            else if (!checkChunkExists())
            {
                // insert chunk data at end of file and put new chunk in index file
                for(Tile[] row : tiles)
                {
                    for(Tile tile : row)
                    {
                        if (Gdx.files.isExternalStorageAvailable())
                        {
                            mapFile.writeString(json.toJson(tile), true);
                        }
                    }
                }
            }
            return success;
        }

        private boolean checkChunkExists()
        {
            indexFileReader = indexFile.reader(8192);
            String currentIndex;
            try
            {
                currentIndex = indexFileReader.readLine();
                while (currentIndex != null)
                {
                    //iterate through line to check if chunk exists, and if so get data on location etc
                    /**
                     * Splits contains the chunk number, chunk location in file and chunk length in file
                     */
                    String[] splits = currentIndex.split("|");
                    if (splits.length == 3)
                    {
                        String[] cPos = splits[0].split(":");
                        if (chunkPosition == new Vector2(Integer.parseInt(cPos[0]), Integer.parseInt(cPos[1])))
                        {
                            chunkLineInFile = Integer.parseInt(splits[1]);
                            chunkLengthInFile = Integer.parseInt(splits[2]);
                            return true;
                        }
                    }
                    indexFileReader.readLine();
                }

            }
            catch (IOException e)
            {
                Log.d(e.toString(), "File finished reading");
                return false;
            }
            finally
            {
                if (indexFileReader != null)
                {
                    try
                    {
                        indexFileReader.close();
                    }
                    catch (IOException e)
                    {
                        Log.d(e.toString(), "Reader failed to close");
                    }
                }
            }
            return false;
        }

        public void draw(SpriteBatch batch, ExtendedCamera camera)
        {
            for (Tile[] row : tiles)
            {
                for (Tile tile : row)
                {
                    tile.isDrawn = false;
                    if (tile != null && tile.visible)
                    {
                        if (camera.frustum.boundsInFrustum(tile.boundingBox))
                        {
                            tile.draw(batch);
                            tile.isDrawn = true;
                        }
                    }
                }
            }
        }
    }
}