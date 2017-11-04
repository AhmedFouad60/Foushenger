package com.example.foush.foushenger.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foush.foushenger.R;
import com.example.foush.foushenger.Utils.Session;
import com.example.foush.foushenger.models.Message;
import com.example.foush.foushenger.models.MessageType;
import com.example.foush.foushenger.webservices.Urls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by foush on 10/29/17.
 */

public class MessagingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> messages;
    private Context context;
public MessagingAdapter(Context context,List<Message> messages){
    this.messages=messages;
    this.context=context;

}



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * check the type of view and return holder
         */
        if (viewType == MessageType.SENT_TEXT) {
            return new SentTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sent_message_text, parent, false));
        } else if (viewType == MessageType.SENT_IMAGE) {
            return new SentImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sent_message_img, parent, false));
        } else if (viewType == MessageType.RECEVED_TEXT) {
            return new ReceivedTextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_received_message_text, parent, false));
        } else if (viewType == MessageType.RECEVED_IMAGE) {
            return new ReceivedImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_received_message_img, parent, false));
        }


        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder mHolder, int position) {
        int type = getItemViewType(position);
        Message message = messages.get(position);
        /**
         * check message type and init holder to user it and set data in the right place for every view
         */
        if (type == MessageType.SENT_TEXT) {
            SentTextHolder holder = (SentTextHolder) mHolder;
            holder.tvTime.setText(message.getTime());
            holder.tvMessageContent.setText(message.getContent());

        } else if (type == MessageType.SENT_IMAGE) {
            SentImageHolder holder = (SentImageHolder) mHolder;
            holder.tvTime.setText(message.getTime());
            Glide.with(context).load(Urls.IMAGES_URL + message.getContent()).into(holder.imgMsg);


        } else if (type == MessageType.RECEVED_TEXT) {
            ReceivedTextHolder holder = (ReceivedTextHolder) mHolder;
            holder.tvTime.setText(message.getTime());
            holder.tvUsername.setText(message.getUsername());
            holder.tvMessageContent.setText(message.getContent());


        } else if (type == MessageType.RECEVED_IMAGE) {
            ReceivedImageHolder holder = (ReceivedImageHolder) mHolder;
            holder.tvTime.setText(message.getTime());
            holder.tvUsername.setText(message.getUsername());
            Glide.with(context).load(Urls.IMAGES_URL + message.getContent()).into(holder.imgMsg);

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {

        /**
         * Check the user id  to know  if he is the [sender or receiver]
         * Then check if it's Text or Image
         * */

        int userID= Session.getInstance().getUser().id;
        Message message=messages.get(position);
        if(userID==Integer.parseInt(message.getUserId())){
            //this is the sender
                if(message.getType().equals("1")){
                    //text message

                    return MessageType.SENT_TEXT;


                }else if(message.getType().equals("2")){
                    //image message
                    return MessageType.SENT_IMAGE;
                }


        }else {
            //this is the receiver
            if(message.getType().equals("1")){
                //text message

                return MessageType.RECEVED_TEXT;


            }else if(message.getType().equals("2")){
                //image message
                return MessageType.RECEVED_IMAGE;
            }
        }








        return super.getItemViewType(position);
    }

    // sent message holders
    class SentMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;

        public SentMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // sent message with type text
    class SentTextHolder extends SentMessageHolder {
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;

        public SentTextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    // sent message with type image
    class SentImageHolder extends SentMessageHolder {
        @BindView(R.id.img_msg)
        ImageView imgMsg;

        public SentImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // received message holders
    class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // received message with type text
    class ReceivedTextHolder extends ReceivedMessageHolder {
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;

        public ReceivedTextHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // received message with type image

    class ReceivedImageHolder extends ReceivedMessageHolder {
        @BindView(R.id.img_msg)
        ImageView imgMsg;

        public ReceivedImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
