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
import java.util.HashMap;

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
    HashMap<String, Chunk> chunkMap;
    String mapName;
    FileHandle directory;
    FileHandle indexFile;
    FileHandle mapFile;
    BufferedReader indexFileReader;
    BufferedReader mapFileReader;
    Vector2 tilesOnScreen;

    public Map(String mapName, Vector2 tilesOnScreen)
    {
        //first, check if map already exists and setup file structure
        this.mapName = mapName;
        this.tilesOnScreen = tilesOnScreen;
        manageFiles();
        chunkMap = new HashMap<String, Chunk>(9);
        chunkMap.put("northwest", new Chunk(new Vector2(0,0)));
        chunkMap.put("north", new Chunk(new Vector2(1,0)));
        chunkMap.put("northeast", new Chunk(new Vector2(2,0)));
        chunkMap.put("west", new Chunk(new Vector2(0,1)));
        chunkMap.put("middle", new Chunk(new Vector2(1,1)));
        chunkMap.put("east", new Chunk(new Vector2(2,1)));
        chunkMap.put("southwest", new Chunk(new Vector2(0,2)));
        chunkMap.put("south", new Chunk(new Vector2(1,2)));
        chunkMap.put("southeast", new Chunk(new Vector2(2,2)));
    }

    public void draw(SpriteBatch batch, ExtendedCamera camera)
    {
        // when drawing tiles, first we want to check if any of the corner chunks need to be drawn, then the chunks north south east and west
        Vector2[] currentLocation = camera.getCurrentTile();
        Vector2[] northernTile = camera.getNorthernTile();
        Vector2[] southernTile = camera.getSouthernTile();
        Vector2[] westernTile = camera.getWesternTile();
        Vector2[] easternTile = camera.getEasternTile();

        if (currentLocation[0].x > westernTile[0].x && currentLocation[0].y > northernTile[0].y) // if northwest chunk is visible, draw that chunk and the north, west and middle chunks
        {
            chunkMap.get("northwest").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x, MLGameScreen.chunkSize.y));
            chunkMap.get("north").draw(batch, camera,
                    new Vector2(0, northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
            chunkMap.get("west").draw(batch, camera,
                    new Vector2(westernTile[1].x,0),
                    new Vector2(MLGameScreen.chunkSize.x, southernTile[1].y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(0, 0),
                    new Vector2(easternTile[1].x, southernTile[1].y));
        }
        else if (currentLocation[0].x < easternTile[0].x && currentLocation[0].y > northernTile[0].y) // if northeast chunk is visible, draw that chunk and the north, east and middle chunks
        {
            chunkMap.get("northeast").draw(batch, camera,
                    new Vector2(0, northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
            chunkMap.get("north").draw(batch, camera,
                    new Vector2(westernTile[1].x,northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x,MLGameScreen.chunkSize.y));
            chunkMap.get("east").draw(batch, camera,
                    new Vector2(0,0),
                    new Vector2(easternTile[1].x,southernTile[1].y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(westernTile[1].x,0),
                    new Vector2(MLGameScreen.chunkSize.x,southernTile[1].y));
        }
        else if (currentLocation[0].x < easternTile[0].x && currentLocation[0].y < southernTile[0].y) // if southeast chunk is visible, draw that chunk and the south, east and middle chunks
        {
            chunkMap.get("southeast").draw(batch, camera,
                    new Vector2(0, 0),
                    new Vector2(easternTile[1].x, southernTile[1].y));
            chunkMap.get("south").draw(batch, camera,
                    new Vector2(westernTile[1].x, 0),
                    new Vector2(MLGameScreen.chunkSize.x, southernTile[1].y));
            chunkMap.get("east").draw(batch, camera,
                    new Vector2(0,northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x, MLGameScreen.chunkSize.y));
        }
        else if (currentLocation[0].x > westernTile[0].x && currentLocation[0].y < southernTile[0].y) // if southwest chunk is visible, draw that chunk and the south, west and middle chunks
        {
            chunkMap.get("southwest").draw(batch, camera,
                    new Vector2(westernTile[1].x, 0),
                    new Vector2(MLGameScreen.chunkSize.x, southernTile[1].y));
            chunkMap.get("south").draw(batch, camera,
                    new Vector2(0, 0),
                    new Vector2(easternTile[1].x, southernTile[1].y));
            chunkMap.get("west").draw(batch, camera,
                    new Vector2(westernTile[1].x,northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x, MLGameScreen.chunkSize.y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(0, northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
        }
        else if (currentLocation[0].x > westernTile[0].x) // west chunk visible
        {
            chunkMap.get("west").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x, southernTile[1].y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(0, northernTile[1].y),
                    new Vector2(easternTile[1].x, southernTile[1].y));
        }
        else if (currentLocation[0].y > northernTile[0].y) // north chunk visible
        {
            chunkMap.get("north").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(westernTile[1].x, 0),
                    new Vector2(easternTile[1].x, southernTile[1].y));
        }
        else if (currentLocation[0].x < easternTile[0].x) // east chunk visible
        {
            chunkMap.get("east").draw(batch, camera,
                    new Vector2(0, northernTile[1].y),
                    new Vector2(easternTile[1].x, southernTile[1].y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(MLGameScreen.chunkSize.x, southernTile[1].y));
        }
        else if (currentLocation[0].y < southernTile[0].y) // south chunk visible
        {
            chunkMap.get("south").draw(batch, camera,
                    new Vector2(westernTile[1].x, 0),
                    new Vector2(easternTile[1].x, southernTile[1].y));
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(westernTile[1].x, northernTile[1].y),
                    new Vector2(easternTile[1].x, MLGameScreen.chunkSize.y));
        }
        else // only middle visible
        {
            chunkMap.get("middle").draw(batch, camera,
                    new Vector2(easternTile[1].x, northernTile[1].y),
                    new Vector2(westernTile[1].x, southernTile[1].y));
        }
    }

    public void update(SpriteBatch batch, ExtendedCamera camera)
    {
        manageChunks(camera);
        draw(batch, camera);
    }

    public void manageChunks(ExtendedCamera camera)
    {
        Vector2[] currPos = camera.getCurrentTile();
        if (chunkMap.get("middle").chunkPosition != currPos[0]) //if middle chunk is not at the centre
        {
            if (currPos[0].y > 0 && currPos[0].y < chunkMap.get("middle").chunkPosition.y) // if moved north
            {
                chunkMap.remove("southwest");
                chunkMap.remove("south");
                chunkMap.remove("southeast");
                chunkMap.put("southwest", chunkMap.get("west"));
                chunkMap.put("south", chunkMap.get("middle"));
                chunkMap.put("southeast", chunkMap.get("east"));
                chunkMap.remove("west");
                chunkMap.remove("middle");
                chunkMap.remove("east");
                chunkMap.put("west", chunkMap.get("northwest"));
                chunkMap.put("middle", chunkMap.get("north"));
                chunkMap.put("east", chunkMap.get("northeast"));
                chunkMap.remove("northwest");
                chunkMap.remove("north");
                chunkMap.remove("northeast");
                chunkMap.put("northwest", new Chunk(currPos[0].add(-1,-1)));
                chunkMap.put("north", new Chunk(currPos[0].add(0,-1)));
                chunkMap.put("northeast", new Chunk(currPos[0].add(1,-1)));
            }
            if (currPos[0].x > chunkMap.get("middle").chunkPosition.x) // if moved east
            {
                chunkMap.remove("northwest");
                chunkMap.remove("west");
                chunkMap.remove("southwest");
                chunkMap.put("northwest", chunkMap.get("north"));
                chunkMap.put("west", chunkMap.get("middle"));
                chunkMap.put("southwest", chunkMap.get("south"));
                chunkMap.remove("north");
                chunkMap.remove("middle");
                chunkMap.remove("south");
                chunkMap.put("north", chunkMap.get("northeast"));
                chunkMap.put("middle", chunkMap.get("east"));
                chunkMap.put("south", chunkMap.get("southeast"));
                chunkMap.remove("northeast");
                chunkMap.remove("east");
                chunkMap.remove("southeast");
                chunkMap.put("northeast", new Chunk(currPos[0].add(1,-1)));
                chunkMap.put("east", new Chunk(currPos[0].add(1,0)));
                chunkMap.put("southeast", new Chunk(currPos[0].add(1,1)));
            }
            if (currPos[0].y > chunkMap.get("middle").chunkPosition.y) // if moved south
            {
                chunkMap.remove("northwest");
                chunkMap.remove("north");
                chunkMap.remove("northeast");
                chunkMap.put("northwest", chunkMap.get("west"));
                chunkMap.put("north", chunkMap.get("middle"));
                chunkMap.put("northeast", chunkMap.get("east"));
                chunkMap.remove("west");
                chunkMap.remove("middle");
                chunkMap.remove("east");
                chunkMap.put("west", chunkMap.get("southwest"));
                chunkMap.put("middle", chunkMap.get("south"));
                chunkMap.put("east", chunkMap.get("southeast"));
                chunkMap.remove("southwest");
                chunkMap.remove("south");
                chunkMap.remove("southeast");
                chunkMap.put("southwest", new Chunk(currPos[0].add(-1,1)));
                chunkMap.put("south", new Chunk(currPos[0].add(0,1)));
                chunkMap.put("southeast", new Chunk(currPos[0].add(1,1)));
            }
            if (currPos[0].x > 0 && currPos[0].x < chunkMap.get("middle").chunkPosition.x) // if moved west
            {

                chunkMap.remove("northeast");
                chunkMap.remove("east");
                chunkMap.remove("southeast");
                chunkMap.put("northeast", chunkMap.get("north"));
                chunkMap.put("east", chunkMap.get("middle"));
                chunkMap.put("southeast", chunkMap.get("south"));
                chunkMap.remove("north");
                chunkMap.remove("middle");
                chunkMap.remove("south");
                chunkMap.put("north", chunkMap.get("northwest"));
                chunkMap.put("middle", chunkMap.get("west"));
                chunkMap.put("south", chunkMap.get("southwest"));
                chunkMap.remove("northwest");
                chunkMap.remove("west");
                chunkMap.remove("southwest");
                chunkMap.put("northwest", new Chunk(currPos[0].add(-1,-1)));
                chunkMap.put("west", new Chunk(currPos[0].add(-1,0)));
                chunkMap.put("southwest", new Chunk(currPos[0].add(-1,1)));
            }
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
            size = MLGameScreen.chunkSize;
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

        public void draw(SpriteBatch batch, ExtendedCamera camera, Vector2 minimumXYTile, Vector2 maximumXYTile)
        {
            //TODO: make this code use the vectors provided for drawing
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