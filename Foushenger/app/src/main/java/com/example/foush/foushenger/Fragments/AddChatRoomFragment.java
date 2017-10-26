package com.example.foush.foushenger.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foush.foushenger.MainActivity;
import com.example.foush.foushenger.R;
import com.example.foush.foushenger.models.ChatRoom;
import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by foush on 10/25/17.
 */

public class AddChatRoomFragment extends DialogFragment{

    public AddChatRoomFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         View v=inflater.inflate(R.layout.fragment_add_chat_room,container,false);

        Button btn_add_room=(Button)v.findViewById(R.id.btn_add_room);
        final EditText RoomName=(EditText)v.findViewById(R.id.et_room_name);
        final EditText RoomDesc=(EditText)v.findViewById(R.id.et_room_desc);

        btn_add_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatName=RoomName.getText().toString();
                String chatDesc=RoomDesc.getText().toString();
                ChatRoom room=new ChatRoom();
                if (chatName != null) {
                    room.room_name=chatName;
                    room.room_desc=chatDesc;

                    addChatRoom(room);
                }
                else {
                    Log.d(TAG, "onClick: one of the editText is empty or both");
                }
                


            }
        });
                return v;
    }


    private void addChatRoom(ChatRoom chatRoom){

        Log.i(TAG, "addChatRoom: function");
       
        API add_chat_room= ServiceGenerator.createService(API.class);
        Call<MainResponse> call=add_chat_room.addChatRoom(chatRoom);
        call.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().status=="1"){
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                    Intent goToMain=new Intent(getActivity(), MainActivity.class);
                    startActivity(goToMain);

                }else {
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "unkown failure", Toast.LENGTH_SHORT).show();
            }
        });
            }//End of addChatRoom() function




}
