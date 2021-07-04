package com.example.demoroomdb.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.model.Entity.Employee;
import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
//    private List<Employee> itemColumn; TODO close Room DB

    private List<Users> itemColumn;
    private LayoutInflater mInflater;
    private Context context;
    private String friendId = null;

//    public ListAdapter(Context context, List<Employee> mFullName) {
//        this.context = context;
//        mInflater = LayoutInflater.from(context);
//        this.itemColumn = mFullName;
//    }

    public ListAdapter(Context context, List<Users> usersList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.itemColumn = usersList;
    }

    /**
     * This method is similar to the onCreate() method. It inflates the item layout, and returns a ViewHolder with the layout and the adapter.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.data_item, parent,false);
        return new ListAdapter.ListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
//        Employee mCurrent = itemColumn.get(position);

        Users mCurrent = itemColumn.get(position);
        friendId = mCurrent.getId();
        holder.tvItemFullname.setText(mCurrent.getUsername());
    }

    @Override
    public int getItemCount() {
        return itemColumn.size();
    }

//    public Employee getPosition(int position) {
//        // Use that to access the affected item in mWordList.
//        return itemColumn.get(position);
//    }

    public Users getPosition(int position) {
        // Use that to access the affected item in mWordList.
        return itemColumn.get(position);
    }

    // inner class
    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvItemFullname;
        final ListAdapter mAdapter;

        /**
         * default constructor
         * @param itemView
         * @param adapter
         */
        public ListViewHolder(@NonNull View itemView, ListAdapter adapter) {
            super(itemView);
            tvItemFullname = itemView.findViewById(R.id.tv_fullName);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            // Employee element = itemColumn.get(mPosition);
            Users element = itemColumn.get(mPosition);
            // Change the word in the mWordList.
            Toast.makeText(context,"Click: " + element.getUsername(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MessagingActivity.class);
            intent.putExtra("friendId", friendId);
            context.startActivity(intent);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void setEmployee(List<Users> users) {
        this.itemColumn = users;
        notifyDataSetChanged();
    }

}


