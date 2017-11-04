package com.example.foush.foushenger.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by foush on 10/28/17.
 */

public class Message implements Parcelable{

    @SerializedName("id")
    private String id;
    @SerializedName("room_id")
    private String room_id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("type")
    private String type;
    @SerializedName("content")
    private String content;

    @SerializedName("timestamp")
    private String timestamp;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return room_id;
    }

    public void setRoomId(String roomId) {
        this.room_id= roomId;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Method convert time stamp to hourly time ;
     *
     * @return time like 07:20 AM from time 2017 8 1 20:12
     */
    public String getTime() {
        if (timestamp == null) {
            return "now";
        } else {
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

            Date date = null;
            try {
                date = serverFormat.parse(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeZone timeZone = TimeZone.getDefault();
            int rawOffset = timeZone.getRawOffset();
            long local = 0;
            if (date != null) {
                local = date.getTime() + rawOffset;
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(local);
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            return format.format(calendar.getTime());
        }


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(room_id);
        dest.writeString(user_id);
        dest.writeString(user_name);
        dest.writeString(type);
        dest.writeString(content);
        dest.writeString(timestamp);
    }
}
