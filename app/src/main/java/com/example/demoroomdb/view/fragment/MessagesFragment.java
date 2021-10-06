package com.example.demoroomdb.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.ChatList;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.view.adapter.ListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class MessagesFragment extends BaseFragment {

    public static final String TAG = MessagesFragment.class.getSimpleName();

    private static final String INSTANCE_VALUE = "argument";

    /**
     * Calling recyclerview instance.
     */
    private RecyclerView mRecyclerView;

    /**
     * Create instance Firebase User to reference to Real Time database - Firebase
     */
    private FirebaseUser firebaseUser;

    /**
     * Create employee linked list to observer data.
     */
    private List<ChatList> chatLists = new LinkedList<>();
    private List<Users> usersList = new LinkedList<>();

    private com.example.demoroomdb.view.adapter.ListAdapter mAdapter;

    @Override
    protected int layoutResource() { return R.layout.fragment_messages; }

    public MessagesFragment() {}

    public static MessagesFragment newInstance(String args) {
        MessagesFragment messagesFragment = new MessagesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INSTANCE_VALUE, args);
        messagesFragment.setArguments(bundle);
        return messagesFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running to Messages Fragment");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_view_messages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        displayChatList();
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView,
//                                  @NonNull RecyclerView.ViewHolder viewHolder,
//                                  @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
////                int position = viewHolder.getAdapterPosition();
//            }
//        });
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void displayChatList() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatLists")
                .child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatLists.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    ChatList chatList = dataSnapshot.getValue(ChatList.class);
                    chatLists.add(chatList);
                }
                displayUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void displayUser( ) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);

                    for (ChatList chatList: chatLists) {
                        if (users.getId().equals(chatList.getId())) {
                            usersList.add(users);
                        }
                    }
                    mAdapter = new ListAdapter(getContext(), usersList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
