package com.example.foush.foushenger.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foush.foushenger.R;
import com.example.foush.foushenger.models.ChatRoom;

import java.util.List;

/**
 * Created by foush on 10/23/17.
 */

public class ChatRoomsAdapter extends ArrayAdapter<ChatRoom> {

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
}