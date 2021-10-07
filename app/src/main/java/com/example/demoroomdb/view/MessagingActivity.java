package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Chats;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.utils.Defined;
import com.example.demoroomdb.utils.Utils;
import com.example.demoroomdb.view.adapter.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MessagingActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    TextView tvUsernameOnToolBar;
    String friendId, message, myId;
    Toolbar toolbar;
    EditText edtMessage;
    Button btnSend;
    List<Chats> chatsList;
    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        toolbar = findViewById(R.id.toolbar_message);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        utils = Utils.getInstance(this);

        tvUsernameOnToolBar = findViewById(R.id.title);
        edtMessage = findViewById(R.id.edit_message);
        btnSend = findViewById(R.id.btn_send_message);

        recyclerView = findViewById(R.id.recycler_view_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        friendId = getIntent().getStringExtra("friendId");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myId = firebaseUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(friendId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                tvUsernameOnToolBar.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        readMessage(myId, friendId);


        edtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().length() > 0) {
                    btnSend.setEnabled(true);
                } else{
                    btnSend.setEnabled(false);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnSend.setOnClickListener( event -> {
            message = edtMessage.getText().toString().trim();
            sendMessage(myId, friendId, message);
            edtMessage.getText().clear();
        });
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(MessagingActivity.this, EmployeeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        utils.status(Defined.ACCOUNT_STATUS_ONLINE);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        utils.status(Defined.ACCOUNT_STATUS_OFFLINE);

    }

    private void readMessage(String myId, String friendId) {
        chatsList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Chats chats = ds.getValue(Chats.class);
                    if ((chats.getSender().equals(myId) && chats.getReceiver().equals(friendId)) ||
                            (chats.getSender().equals(friendId) && chats.getReceiver().equals(myId))) {
                        chatsList.add(chats);
                    }
                    messageAdapter = new MessageAdapter(MessagingActivity.this, chatsList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void sendMessage(String myId, String friendId, String message) {
            HashMap<String, Object> sendingData = new HashMap<>();
            sendingData.put("sender", myId);
            sendingData.put("receiver", friendId);
            sendingData.put("message", message);
            dbRef.child("Chats").push().setValue(sendingData);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChatLists").child(myId).child(friendId);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        reference.child("id").setValue(friendId);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

}