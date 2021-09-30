package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MessagingActivity extends AppCompatActivity {
    FirebaseUser firebaseUser;
    TextView tvUsernameOnToolBar;
    String friendId, message, myId;
    Toolbar toolbar;
    EditText edtMessage;
    Button btnSend;
    final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        toolbar = findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvUsernameOnToolBar = findViewById(R.id.title);
        edtMessage = findViewById(R.id.edit_message);
        btnSend = findViewById(R.id.btn_send_message);
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
    }

    private void sendMessage(String myId, String friendId, String message) {
            HashMap<String, Object> sendingData = new HashMap<>();
            sendingData.put("sender", myId);
            sendingData.put("receiver", friendId);
            sendingData.put("message", message);
            dbRef.child("Chats").push().setValue(sendingData);

    }

}