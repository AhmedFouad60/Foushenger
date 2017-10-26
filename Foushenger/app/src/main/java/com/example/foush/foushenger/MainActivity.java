package com.example.foush.foushenger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foush.foushenger.Adapter.ChatRoomsAdapter;
import com.example.foush.foushenger.Fragments.AddChatRoomFragment;
import com.example.foush.foushenger.Utils.Session;
import com.example.foush.foushenger.models.ChatRoom;
import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.User;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView logout;
    RecyclerView ChatView;
    List<ChatRoom>chatRooms;
    ChatRoomsAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView toolbarTitle=(TextView) findViewById(R.id.toolbar_title);

        TextView toolbarDesc=(TextView)findViewById(R.id.toolbar_desc);

        FloatingActionButton addCharRoomFab= (FloatingActionButton) findViewById(R.id.add_char_room_fab);
        ChatView = (RecyclerView) findViewById(R.id.recycler_chat_rooms);
        ChatView.setLayoutManager(new LinearLayoutManager(MainActivity.this));







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

                //delete item when swap it to left
                ItemTouchHelper itemTouchHelper=new ItemTouchHelper(swapChatRoomCallback);
                itemTouchHelper.attachToRecyclerView(ChatView);


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
                chatRooms=response.body();
                /*
                ChatRoomsAdapter adapter=new ChatRoomsAdapter(MainActivity.this,chatRooms);
                recyclerView.setAdapter(adapter);*/
                 adapter=new ChatRoomsAdapter(MainActivity.this,chatRooms);

                ChatView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ChatRoom>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "there is unknown error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //implement delete_chat_room with swap the item to left

    ItemTouchHelper.SimpleCallback swapChatRoomCallback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }
        // to color the background of swiped item red color
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // if swiping now
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // get item that swiped
                View itemView = viewHolder.itemView;
                // create new pain
                Paint p = new Paint();
                // if swiping to left dx will < 0 so we do what we want
                if (dX <= 0) {
                    // set color for paint red color
                    p.setColor(Color.parseColor("#ED1220"));
                    // draw rectangle depending on the item view ends
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }



        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int postion = viewHolder.getAdapterPosition();
            int chatRoomID=Integer.parseInt( chatRooms.get(postion).id);

            //retrofit to delete this item
            API api=ServiceGenerator.createService(API.class);
            Call<MainResponse> call=api.deleteChatRoom(chatRoomID);
            call.enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if(response.body().status=="1"){
                        Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        chatRooms.remove(postion);
                        adapter.notifyItemRemoved(postion);


                    }else {
                        Toast.makeText(MainActivity.this,response.body().message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error" +t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });






        }
    };


















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