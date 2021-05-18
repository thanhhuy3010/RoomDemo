package com.example.demoroomdb.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demoroomdb.model.Entity.Employee;
import com.example.demoroomdb.R;
import com.example.demoroomdb.ViewModel.EmployeeViewModel;

public class DialogAddContact extends DialogFragment implements View.OnClickListener {
    /**
     * Declare variable in dialog
     */
    public final String TAG = DialogAddContact.class.getSimpleName();
    private static final String TITLE_INSTANCE = "title";
    private EditText txtFullName, txtUserName, txtPhone, txtRole, txtAge,txtGender;
    private Button btnAdd, btnCancel;
    EmployeeViewModel employeeViewModel;
    public DialogAddContact() {}

    /**
     *
     * @param title
     * @return
     */
    public static DialogAddContact newInstance(String title) {
        DialogAddContact frag = new DialogAddContact();
        Bundle args = new Bundle();
        args.putString(TITLE_INSTANCE, title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeViewModel = new ViewModelProvider(this).get(EmployeeViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        return getActivity().getLayoutInflater().inflate(R.layout.add_dialog_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreate");
        initField(view);
        String title = getArguments().getString(TITLE_INSTANCE);
        getDialog().setTitle(title);
        txtFullName.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnAdd.setOnClickListener(this);
    }
    private void initField(View view) {
        txtFullName = view.findViewById(R.id.txt_your_name);
        txtUserName = view.findViewById(R.id.txt_username);
        txtPhone = view.findViewById(R.id.txt_phone);
        txtRole = view.findViewById(R.id.txt_role);
        txtAge = view.findViewById(R.id.txt_age);
        txtGender = view.findViewById(R.id.txt_gender);
        btnAdd = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (txtFullName.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Data is incorrect please check it before add",Toast.LENGTH_SHORT).show();
                } else {
                    employeeViewModel.insert(new Employee(txtUserName.getText().toString(),txtFullName.getText().toString(),
                            txtPhone.getText().toString(),txtRole.getText().toString(),0,txtGender.getText().toString()));
                    dismiss();
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}
