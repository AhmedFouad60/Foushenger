package com.example.foush.foushenger.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by foush on 10/21/17.
 */

public class User extends RealmObject {
    //"username":"ahmed","password":"123456","email":"ahmedfouad@yahoo.com}
    //serializeName is the key in the json
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;

}
