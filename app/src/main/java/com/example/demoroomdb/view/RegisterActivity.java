package com.example.demoroomdb.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoroomdb.R;
import com.example.demoroomdb.utils.Defined;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText edtUsername, editEmail, edtPassword;
    Button btnRegister;
//    private final String ID = "id";
//    private final String USERNAME = "username";
//    private final String EMAIL = "email";
//    private final String STATUS = "status";
    ConstraintLayout constraintLayout;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        constraintLayout = findViewById(R.id.register_layout);
        initField();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initField() {
        editEmail = findViewById(R.id.edt_register_email);
        edtUsername = findViewById(R.id.edt_register_username);
        edtPassword = findViewById(R.id.edt_register_password);
        btnRegister = findViewById(R.id.btn_registered);
    }

    private void register(final String username, String password,final String email) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userId = firebaseUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                if (firebaseUser != null) {

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put(Defined.ID, userId);
                    hashMap.put(Defined.USERNAME, username);
                    hashMap.put(Defined.EMAIL, email);
                    hashMap.put(Defined.STATUS, Defined.ACCOUNT_STATUS_OFFLINE);

                    databaseReference.setValue(hashMap).addOnCompleteListener( RegisterActivity.this,taskComplete -> {
                        if (taskComplete.isSuccessful()) {
                            Log.d(TAG, "Add successfully: " + username);
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        } else {
                            Log.d(TAG, "Error db" + taskComplete.getException());
                            Toast.makeText(this,"Failed to add user ! Please check it again",Toast.LENGTH_LONG).show();
                        }
                    });
                }


            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
            }
        });
    }

    public void onRegisterUser(View view) {
        String usName, password, email;
        usName = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        email = editEmail.getText().toString().trim();

        if (usName.isEmpty()) {
            edtUsername.setError("Username is required !");
            edtUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please input valid email !");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edtPassword.setError(" password is incorrectly !");
            edtPassword.requestFocus();
            return;
        }
        register(usName,password, email);


    }
}