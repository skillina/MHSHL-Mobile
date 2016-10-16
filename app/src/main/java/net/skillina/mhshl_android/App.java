package net.skillina.mhshl_android;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.NavigationView;

/**
 * Created by Alex on 7/29/2016.
 */
public class App extends Application {

    public static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        getBaseContext();
    }
}
