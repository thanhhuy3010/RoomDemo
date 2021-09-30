package com.example.demoroomdb.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.model.Entity.Chats;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessViewHolder> {
    Context context;
    List<Chats> messageList;
    public MessageAdapter(@NonNull Context context, List<Chats> list) {
        this.context = context;
        this.messageList = list;
    }

    @NonNull
    @Override
    public MessageAdapter.MessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MessViewHolder extends RecyclerView.ViewHolder {

        public MessViewHolder(@NonNull View itemView, MessageAdapter messageAdapter) {
            super(itemView);

        }
    }
}
