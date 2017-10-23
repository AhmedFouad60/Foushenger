package com.example.foush.foushenger.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by foush on 10/23/17.
 */

public class ChatRoom {
    @SerializedName("id")
    public String id;
    @SerializedName("room_name")
    public String room_name;
    @SerializedName("room_desc")
    public String room_desc;
}
