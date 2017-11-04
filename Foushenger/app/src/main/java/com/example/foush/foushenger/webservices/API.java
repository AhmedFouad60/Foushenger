package com.example.foush.foushenger.webservices;

import com.example.foush.foushenger.models.ChatRoom;
import com.example.foush.foushenger.models.LoginResponse;
import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.Message;
import com.example.foush.foushenger.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by foush on 10/21/17.
 */

public interface API {
    @POST("login-user.php")
    Call<LoginResponse> loginUser(@Body User user);
    @POST("register-user.php")
    Call<MainResponse> registerUser(@Body User user);

    @POST("add_chat_rooms.php")
    Call<MainResponse> addChatRoom(@Body ChatRoom chatRoom);
    @FormUrlEncoded
    @POST("delete_chat_room.php")
    Call<MainResponse>deleteChatRoom(@Field("id")int roomId);
    @POST("get-all-chat-rooms.php")
    Call<List<ChatRoom>>getAllChatRooms();
    //Get the messages
    @FormUrlEncoded
    @POST("get-messages.php")
    Call<List<Message>> getMessages( @Field("room_id") int RoomId);
    //add Message
    @POST("add-message.php")
    Call<MainResponse>addMessage(@Body Message message);

}
