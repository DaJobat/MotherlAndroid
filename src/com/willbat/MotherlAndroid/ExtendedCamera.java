package com.willbat.MotherlAndroid;

import com.badlogic.gdx.graphics.OrthographicCamera;

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
}
