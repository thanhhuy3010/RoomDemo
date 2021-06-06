package com.example.demoroomdb.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoroomdb.model.Common.SharePreference.ConfigSharedPref;
import com.example.demoroomdb.model.Common.Logger.LoggerManager;
import com.example.demoroomdb.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private ConfigSharedPref configSharedPref;
    private LoggerManager logger;
    boolean isLoginValid;

    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger = LoggerManager.getInstance(getApplicationContext());
        configSharedPref = ConfigSharedPref.getInstance(getApplicationContext());
        logger.Debug(TAG, "onCreate app...");
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        initField();
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (configSharedPref.getBooleanData("KEY_LOGIN") == true) {
            startEmployeeActivity();
            finish();
        }
        loginHandler();
    }

    private void initField() {
        tvError = findViewById(R.id.tv_Error);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void loginHandler() {
        btnLogin.setOnClickListener(v -> validateDataLogin());
    }

    private void validateDataLogin() {
        if (!edtUsername.getText().toString().trim().matches("admin")
                && !edtPassword.getText().toString().trim().matches("123")) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(getResources().getString(R.string.dataError));
            isLoginValid = false;
        } else {
            isLoginValid = true;
            tvError.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),
                    "Redirecting...",Toast.LENGTH_SHORT).show();
            startEmployeeActivity();
            configSharedPref.saveStringData("username", edtUsername.getText().toString());
            configSharedPref.saveStringData("password", edtPassword.getText().toString());
            configSharedPref.saveBooleanData("KEY_LOGIN",isLoginValid);

        }
    }

    private void startEmployeeActivity() {
        Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (configSharedPref.isConfigAvailable()) {
            edtUsername.setText(configSharedPref.getStringData("username"));
            edtPassword.setText(configSharedPref.getStringData("password"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_CANCELED:
                finish();
                break;
        }
    }
}
