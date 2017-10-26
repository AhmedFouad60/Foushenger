package com.example.foush.foushenger.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foush.foushenger.R;
import com.example.foush.foushenger.models.ChatRoom;

import java.util.List;

/**
 * Created by foush on 10/23/17.
 */

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.chatRoomHolder> {

    TextView chatTittle, chatDesc;
    ImageView imageView;


    //define list and content
    private List<ChatRoom>chatRooms;
    private Context context;

    public ChatRoomsAdapter(Context context,List<ChatRoom>rooms){
        this.chatRooms=rooms;
        this.context=context;
    }




    @Override
    public chatRoomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new chatRoomHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat_room, parent, false));
    }

    @Override
    public void onBindViewHolder(chatRoomHolder holder, int position) {

        final ChatRoom chatRoom=chatRooms.get(position);

        // Populate the data into the template view using the data object
        chatTittle.setText(chatRoom.room_name);
        chatDesc.setText(chatRoom.room_desc);

    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }
    class chatRoomHolder extends RecyclerView.ViewHolder{

        public chatRoomHolder(View itemView) {
            super(itemView);
            //get the all UI in the row_chat_room.xml
             chatTittle= (TextView) itemView.findViewById(R.id.tv_title);
             chatDesc= (TextView) itemView.findViewById(R.id.tv_desc);
             imageView=(ImageView)itemView.findViewById(R.id.img_group_icon) ;




        }
    }





/*
    public ChatRoomsAdapter(Context context, List<ChatRoom> chats) {
        super(context, 0, chats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ChatRoom chatRoom = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_chat_room, parent, false);
        }
        // Lookup view for data population
        TextView chatTittle= (TextView) convertView.findViewById(R.id.tv_title);
        TextView chatDesc= (TextView) convertView.findViewById(R.id.tv_desc);
        ImageView imageView=(ImageView)convertView.findViewById(R.id.img_group_icon) ;

        // Populate the data into the template view using the data object
        chatTittle.setText(chatRoom.room_name);
        chatDesc.setText(chatRoom.room_desc);

        // Return the completed view to render on screen
        return convertView;
    }
    */
}