package com.example.foush.foushenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.foush.foushenger.Adapter.MessagingAdapter;
import com.example.foush.foushenger.Utils.Session;
import com.example.foush.foushenger.models.MainResponse;
import com.example.foush.foushenger.models.Message;
import com.example.foush.foushenger.webservices.API;
import com.example.foush.foushenger.webservices.ServiceGenerator;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.recycler_chat)
    RecyclerView recyclerChat;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.img_attachment)
    ImageView imgAttachment;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.img_send)
    ImageView imgSend;
    @BindView(R.id.rllt_text_box)
    RelativeLayout rlltTextBox;
    @BindView(R.id.content_chat)
    LinearLayout contentChat;

    private int roomId = 0;
    private int userId = 0;
    private String roomName;
    private String username;
    private MessagingAdapter adapter;
    private List<Message> messages;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get room ID and room name
        roomId = getIntent().getExtras().getInt("room_id");
        roomName = getIntent().getExtras().getString("room_name");

        // set room name as toolbar title
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(roomName);
        // display back button in toolbar
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get user id and username from session
        userId = Session.getInstance().getUser().id;
        username = Session.getInstance().getUser().username;


        getMessages(roomId);

        //subscibe to the topic to send notification [ every room have id differ it from the other]
        FirebaseMessaging.getInstance().subscribeToTopic("room"+roomId);
        Log.e( "room topic is : ","room"+roomId );



















    }


    private BroadcastReceiver messageReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message=intent.getParcelableExtra("msg");
            if (message !=null){
                messages.add(message);
                adapter.notifyItemInserted(messages.size() -1);
                recyclerChat.scrollToPosition(messages.size()-1);
            }
        }
    };

    private void getMessages(int roomId) {

        //get the last 25 message

        final API api= ServiceGenerator.createService(API.class);
        retrofit2.Call<List<Message>> call=api.getMessages(roomId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messages=response.body();
                LinearLayoutManager layoutManager=new LinearLayoutManager(ChatActivity.this);
                recyclerChat.setLayoutManager(layoutManager);
                adapter=new MessagingAdapter(ChatActivity.this,messages);
                recyclerChat.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Toast.makeText(ChatActivity.this, "unknown Error", Toast.LENGTH_SHORT).show();

            }
        });



    }


    private void addMessage(Message message) {

        API api=ServiceGenerator.createService(API.class);
        Call<MainResponse> call=api.addMessage(message);
        call.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if(response.body().status=="0"){
                    Toast.makeText(ChatActivity.this, "Error while trying to send message", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });







    }







    /**
     * on click method using  butter knife library for img attachment and img send
     *
     * @param view
     */
    @OnClick({R.id.img_attachment, R.id.img_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_attachment:
                break;
            case R.id.img_send:
                // if msg editText is empty return don't do any thing
                if (etMessage.getText().toString().isEmpty()) return;

                // get msg from edit text
                String msg = etMessage.getText().toString();

                // create new message
                Message message = new Message();
                // set type to 1 (text message)
                message.setType("1");
                // set room id int
                message.setRoomId(String.valueOf(roomId));
                // set user id int
                message.setUserId(String.valueOf(userId));
                // set user name
                message.setUsername(username);
                // set message content
                message.setContent(msg);
                // add message to messages list
                messages.add(message);
                // notify adapter that there is new message in this position
                adapter.notifyItemInserted(messages.size() - 1);
                // scroll to last item in recycler
                recyclerChat.scrollToPosition(messages.size() - 1);
                // set message box empty
                etMessage.setText("");
                // sent message to server
                addMessage(message);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onPostResume();
        registerReceiver(messageReciver,new IntentFilter("UpdateChatActivity"));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageReciver);
    }
}
