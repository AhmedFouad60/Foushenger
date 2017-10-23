package com.example.foush.foushenger.Utils;

import android.app.Activity;
import android.content.Intent;

import com.example.foush.foushenger.SignInActivity;
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
//config realm
    private Session(){
        RealmConfiguration realmConfiguration=new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm=Realm.getInstance(realmConfiguration);
    }
//session for login
    public void loginUser(final User user) {


        if (realm.where(User.class).findFirst() == null) {
            realm.executeTransaction(new Realm.Transaction() {

                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(user);

                }
            });
        }else {
            logout();
            loginUser(user);
        }
    }

    public void logout() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(User.class);
            }
        });
    }//End of logout


    public boolean isUerLoggedIn(){
        if(realm.where(User.class).findFirst()==null){
            return false;
        }else {
            return true;
        }
    }//End of isUserLoggedIn

    public User getUser(){
        return  realm.where(User.class).findFirst();
    }


    //logout and go to the SignActivity
    public void logoutAndGoToLogin(Activity activity){
        logout();
        activity.startActivity(new Intent(activity, SignInActivity.class));
        activity.finish();
    }









}
