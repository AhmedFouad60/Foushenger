package com.example.foush.foushenger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foush.foushenger.Adapter.ChatRoomsAdapter;
import com.example.foush.foushenger.Fragments.AddChatRoomFragment;
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
    TextView logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView toolbarTitle=(TextView) findViewById(R.id.toolbar_title);

        TextView toolbarDesc=(TextView)findViewById(R.id.toolbar_desc);

        FloatingActionButton addCharRoomFab= (FloatingActionButton) findViewById(R.id.add_char_room_fab);








        //get all rooms to the user after entering this activity
        getChatRooms();

        User user= Session.getInstance().getUser();
// Add fab button if the user is admin and set his email to the top bar
        if(user != null){
           toolbarTitle.setText(getString(R.string.welcome)+user.username);
            toolbarDesc.setText(R.string.nice_to_meet_you);
            if(user.isAdmin){
                toolbarDesc.setText(R.string.nice_to_meet_you_admin);
                addCharRoomFab.setVisibility(View.VISIBLE);
            }else {
                addCharRoomFab.setVisibility(View.GONE);
            }

        }

        //add listener for the fab button

        addCharRoomFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.mainlayout,new AddChatRoomFragment(),"addChatRoom")
                        .commit();

            }
        });






        logout=(TextView)findViewById(R.id.toolbar_tv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance().logoutAndGoToLogin(MainActivity.this);
            }
        });








    }//End of onCreate

   public void getChatRooms(){

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
}//End of the Activity

/**notes
 * **how to make fragment?
 * first thing first :fragment is like the Activity  but we can put fragment within Activity thing that we
 * can't do with Activity within Activity
 *
 * 1-make class addchatrooms extends [fragment]
 * and @override at least onCreate and OnCreatView
 *
 * 2-go to his parent activity i.e(MainActivity) and use Fragment Actiivty to get it to this Activity
 *       getSupportFragmentManager()
                    .beginTransaction()
                    .add(new AddChatRoomFragment(),"addChatRoom")
                    .commit();
 *
 *
 * 3-create the layout for the fragment
 *
 *
 *
 *
 *
 * **/



 /*
        include the fragment of dialog to ask admin to add chat Rooms when clik on the fab button

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainlayout,new AddChatRoomFragment(),"addChatRoom")
                .commit();

        */