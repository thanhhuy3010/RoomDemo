package com.example.demoroomdb.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.utils.Defined;
import com.example.demoroomdb.view.MessagingActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
//    private List<Employee> itemColumn; TODO close Room DB

    private List<Users> itemColumn;
    private LayoutInflater mInflater;
    private Context context;
    private String friendId = null;
    private boolean isChat;

//    public ListAdapter(Context context, List<Employee> mFullName) {
//        this.context = context;
//        mInflater = LayoutInflater.from(context);
//        this.itemColumn = mFullName;
//    }

    public ListAdapter(Context context, List<Users> usersList, boolean isChat) {
        this.context = context;
        this.itemColumn = usersList;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(context).inflate(R.layout.data_item, parent,false);
        return new ListViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
//        Employee mCurrent = itemColumn.get(position);

        Users mCurrent = itemColumn.get(position);
        friendId = mCurrent.getId();
        holder.tvItemFullname.setText(mCurrent.getUsername());

        if (isChat) {
            if (mCurrent.getStatus().equals(Defined.ACCOUNT_STATUS_ONLINE)) {
                holder.imvOnline.setVisibility(View.VISIBLE);
                holder.imvOffline.setVisibility(View.GONE);
            } else {
                holder.imvOnline.setVisibility(View.GONE);
                holder.imvOffline.setVisibility(View.VISIBLE);
            }
        } else {
            holder.imvOnline.setVisibility(View.GONE);
            holder.imvOffline.setVisibility(View.GONE);
        }
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
        public ImageView imvOnline, imvOffline;
        final ListAdapter mAdapter;

        /**
         * default constructor
         * @param itemView
         * @param adapter
         */
        public ListViewHolder(@NonNull View itemView, ListAdapter adapter) {
            super(itemView);
            tvItemFullname = itemView.findViewById(R.id.tv_fullName);
            imvOnline = itemView.findViewById(R.id.tv_status_online);
            imvOffline = itemView.findViewById(R.id.tv_status_offline);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Users users = itemColumn.get(getAdapterPosition());
            friendId = users.getId();
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


