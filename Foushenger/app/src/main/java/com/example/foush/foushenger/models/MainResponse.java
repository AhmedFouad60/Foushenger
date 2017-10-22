package com.example.foush.foushenger.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by foush on 10/21/17.
 */

public class MainResponse {
    //{"status":0,"message":"fail"}
    //{"status":1,"message":"welcome"}
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
}
