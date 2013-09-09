package com.willbat.MotherlAndroid;

import com.badlogic.gdx.Game;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 02:17
 * To change this template use File | Settings | File Templates.
 */
public class MLCore extends Game {
    @Override
    public void create() {
        //To change body of implemented methods use File | Settings | File Templates.
        setScreen(new MLTileTestScreen(this));
    }
}