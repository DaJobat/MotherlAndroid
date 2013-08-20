package com.willbat.MotherlAndroid;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Jobat
 * Date: 20/08/13
 * Time: 02:10
 * To change this template use File | Settings | File Templates.
 */
public class ActivityMain extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration appConfig = new AndroidApplicationConfiguration();
        appConfig.useGL20 = true;
        appConfig.useWakelock = true;

        initialize(new MLCore(), appConfig);
    }
}
