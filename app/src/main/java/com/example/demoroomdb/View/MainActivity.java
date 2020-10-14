package com.example.demoroomdb.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoroomdb.Common.SharePreference.ConfigSharedPref;
import com.example.demoroomdb.Common.Logger.LoggerManager;
import com.example.demoroomdb.Entity.Employee;
import com.example.demoroomdb.R;
import com.example.demoroomdb.ViewModel.EmployeeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private ConfigSharedPref configSharedPref;
    private LoggerManager logger;
    boolean isUsernameValid, isPasswordValid;

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
        tvError = findViewById(R.id.tv_Error);
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void loginHandler() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDataLogin();
            }
        });
    }

    private void validateDataLogin() {
        if (!edtUsername.getText().toString().matches("admin") ) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setError(getResources().getString(R.string.userNameError));
            isUsernameValid = false;
        } else {
            isUsernameValid = true;
            tvError.setVisibility(View.INVISIBLE);
        }

        if (!edtPassword.getText().toString().matches("123") ) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setError(getResources().getString(R.string.passwordError));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
            tvError.setVisibility(View.INVISIBLE);
        }
        if (isUsernameValid && isPasswordValid) {
            Toast.makeText(getApplicationContext(),
                    "Redirecting...",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
            intent.putExtra("username", edtUsername.getText().toString());
            configSharedPref.saveStringData("username", edtUsername.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (configSharedPref.isConfigAvailable()) {
            edtUsername.setText(configSharedPref.getStringData("username"));
        }
    }
}
