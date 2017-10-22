package com.example.foush.foushenger.Utils;

import com.example.foush.foushenger.models.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by foush on 10/22/17.
 */

public class Session {

    private static Session instance;
    private Realm realm;

    public static Session getInstance(){
        if(instance ==null) {
            instance = new Session();
        }
        return instance;
    }

    private Session(){
        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm=Realm.getInstance(realmConfiguration);
    }

    public void loginUser(final User user){
        realm.executeTransaction(new Realm.Transaction(){


            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(user);

            }
        });
    }














}
