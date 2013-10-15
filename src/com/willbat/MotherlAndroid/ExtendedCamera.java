package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 12/09/13
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class ExtendedCamera extends OrthographicCamera
{
    public ExtendedCamera(float viewportWidth, float viewportHeight)
    {
        super(viewportWidth, viewportHeight);
    }

    @Override
    public void update()
    {
        checkBounds();
        super.update();
    }

    private void checkBounds()
    {
        //TODO: Complete this method, make it work with bounding boxes
    }

    public Vector2[] getCurrentTile()
    {
        //this method gives the current chunk and current tile of the camera (for use instead of frustum culling)
        float x = (position.x - position.x%32)/32;
        float y = (position.y - position.y%32)/32;
        y = y * -1;
        Vector2[] result = new Vector2[2];
        Vector2 currentTile = new Vector2(x%MLGameScreen.chunkSize.x,y%MLGameScreen.chunkSize.y); //depends on chunk size
        Vector2 currentChunk = new Vector2((x-currentTile.x)/MLGameScreen.chunkSize.x,(y-currentTile.y)/MLGameScreen.chunkSize.y);
        result[0] = currentChunk;
        result[1] = currentTile;
        return result;
    }

    public Vector2[] getNorthernTile()
    {
        //This method gets the current most northern tile visible by the camera
        Vector2[] currPos = getCurrentTile();
        if (currPos[1].y - MLGameScreen.chunkSize.y/2 < 0)
        {
            currPos[0].y = currPos[0].y - 1;
            currPos[1].y = MLGameScreen.chunkSize.y + (currPos[1].y - MLGameScreen.chunkSize.y/2);
            return currPos;
        }
        else
        {
            currPos[1].y = currPos[1].y - MLGameScreen.chunkSize.y/2;
            return currPos;
        }

    }

    public Vector2[] getSouthernTile()
    {
        //This method gets the current most southern tile visible by the camera
        Vector2[] currPos = getCurrentTile();
        if (currPos[1].y + MLGameScreen.chunkSize.y/2 > MLGameScreen.chunkSize.y - 1)
        {
            currPos[0].y = currPos[0].y + 1;
            currPos[1].y = (currPos[1].y + MLGameScreen.chunkSize.y/2)%MLGameScreen.chunkSize.y;
            return currPos;
        }
        else
        {
            currPos[1].y = currPos[1].y + MLGameScreen.chunkSize.y/2;
            return currPos;
        }
    }

    public Vector2[] getEasternTile()
    {
        //This method gets the current most Eastern tile visible by the camera
        Vector2[] currPos = getCurrentTile();
        if (currPos[1].x + MLGameScreen.chunkSize.x/2 > MLGameScreen.chunkSize.x - 1)
        {
            currPos[0].x = currPos[0].x + 1;
            currPos[1].x = (currPos[1].x + MLGameScreen.chunkSize.x/2)%MLGameScreen.chunkSize.x;
            return currPos;
        }
        else
        {
            currPos[1].x = currPos[1].x + MLGameScreen.chunkSize.x/2;
            return currPos;
        }
    }

    public Vector2[] getWesternTile()
    {
        //This method gets the current most Western tile visible by the camera
        Vector2[] currPos = getCurrentTile();
        if (currPos[1].x - MLGameScreen.chunkSize.x/2 < 0)
        {
            currPos[0].x = currPos[0].x - 1;
            currPos[1].x = MLGameScreen.chunkSize.x + (currPos[1].x - MLGameScreen.chunkSize.x/2);
            return currPos;
        }
        else
        {
            currPos[1].x = currPos[1].x - MLGameScreen.chunkSize.x/2;
            return currPos;
        }
    }
}
