package com.example.demoroomdb.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessViewHolder> {
    Context context;
    List<Chats> messageList;
    public static final int MESSAGE_RIGHT = 0;
    public static final int MESSAGE_LEFT = 1;


    public MessageAdapter(@NonNull Context context, List<Chats> list) {
        this.context = context;
        this.messageList = list;
    }

    @NonNull
    @Override
    public MessageAdapter.MessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate((viewType == MESSAGE_RIGHT) ? R.layout.chat_item_right : R.layout.chat_item_left,parent,false);

        return new MessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessViewHolder holder, int position) {
        Chats chats = messageList.get(position);
        holder.messageText.setText(chats.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ImageView imageView;

        public MessViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.show_message);
            imageView = itemView.findViewById(R.id.chat_image);
        }

    }

    @Override
    public int getItemViewType(int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (messageList.get(position).getSender().equals(user.getUid())) {
            return MESSAGE_RIGHT;
        } else return MESSAGE_LEFT;
    }
}
