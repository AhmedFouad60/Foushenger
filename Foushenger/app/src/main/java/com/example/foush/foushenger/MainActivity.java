package com.example.foush.foushenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foush.foushenger.Adapter.ChatRoomsAdapter;
import com.example.foush.foushenger.Utils.Session;
import com.example.foush.foushenger.models.ChatRoom;
import com.example.foush.foushenger.models.User;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button logout;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getChatRooms();

        User user= Session.getInstance().getUser();
/* Add fabbutton if the user is admin
        if(user != null){
           toolbarTitle.setText(getString(R.string.welcome)+user.username);
            toolbarDesc.setText(R.string.nice_to_meet_you);
            if(user.isAdmin){
                toolbarDesc.setText(R.string.nice_to_meet_you_admin);
                addCjarRoomFab.setvisibility(View.VISIBLE);
            }else {
                addCharRoomFab.setVisibility(View.GONE);
            }

        }
        logout=(Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance().logoutAndGoToLogin(MainActivity.this);
            }
        });

*/

    }

    private void getChatRooms(){

        API api= ServiceGenerator.createService(API.class);
        Call<List<ChatRoom>> call=api.getAllChatRooms();
        call.enqueue(new Callback<List<ChatRoom>>() {
            @Override
            public void onResponse(Call<List<ChatRoom>> call, Response<List<ChatRoom>> response) {
                Toast.makeText(MainActivity.this, "request all chatrooms", Toast.LENGTH_SHORT).show();
                List<ChatRoom>chatRooms=response.body();
                /*
                ChatRoomsAdapter adapter=new ChatRoomsAdapter(MainActivity.this,chatRooms);
                recyclerView.setAdapter(adapter);*/
                ChatRoomsAdapter adapter=new ChatRoomsAdapter(MainActivity.this,chatRooms);
                ListView listView = (ListView) findViewById(R.id.ListView);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ChatRoom>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "there is unknown error", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
