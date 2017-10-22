package com.example.foush.foushenger.Utils;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by foush on 10/22/17.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
