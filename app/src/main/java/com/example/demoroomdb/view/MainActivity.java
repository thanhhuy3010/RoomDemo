package com.example.demoroomdb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoroomdb.model.Common.SharePreference.ConfigSharedPref;
import com.example.demoroomdb.model.Common.Logger.LoggerManager;
import com.example.demoroomdb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private ConfigSharedPref configSharedPref;
    private LoggerManager logger;
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvSignup;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initField();

        mFirebaseAuth = FirebaseAuth.getInstance();
//        getSupportActionBar().hide();

        FirebaseUser firebaseAuthCurrentUserUser = mFirebaseAuth.getCurrentUser();

        if (firebaseAuthCurrentUserUser != null) {
            Log.d(TAG, "Firebase user account : " + firebaseAuthCurrentUserUser.getEmail());
            startEmployeeActivity();
            finish();
        }

        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loginHandler();
        registeredAccount();
    }

    private void initField() {
        edtUsername = findViewById(R.id.edit_mail);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        tvSignup = findViewById(R.id.tv_register_account);
    }

    private void loginHandler() {
        btnLogin.setOnClickListener(v -> validateDataLogin());
    }

    private void registeredAccount() { tvSignup.setOnClickListener( view -> startRegisteredActivity()); }

    private void validateDataLogin() {
        if (edtUsername.getText().toString().trim().isEmpty()) {
            edtUsername.setError("Username is required");
            edtUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(edtUsername.getText()).matches()) {
            edtUsername.setError("Please input valid email !");
            edtUsername.requestFocus();
            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(edtUsername.getText().toString().trim()
                , edtPassword.getText().toString().trim()).addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        startEmployeeActivity();
                    } else {
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.failed_login),Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void startEmployeeActivity() {
        Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        intent = null;
        finish();
    }

    private void startRegisteredActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
        intent = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

}
