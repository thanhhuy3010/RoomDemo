package com.example.demoroomdb.view.fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoroomdb.R;
import com.example.demoroomdb.model.Entity.Users;
import com.example.demoroomdb.utils.Defined;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    private static final String INSTANCE_VALUE = "argument";
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ImageView imgAvatar;
    TextView tvUsername;
    public static final int CAMERA_CODE = 200;
    public static final int GALLERY_CODE = 100;
    Uri imageUri = null;


    @Override
    protected int layoutResource() {return R.layout.fragment_profile;}

    public static ProfileFragment newInstance(String args) {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(INSTANCE_VALUE, args);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(layoutResource(), container, false);
        imgAvatar = view.findViewById(R.id.imv_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                tvUsername.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgAvatar.setOnClickListener(v -> {
            changeImage();
        });
    }

    private void changeImage() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an option");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            }
            if (which == 1) {
                openGallery();
            }
        });
        builder.create().show();
    }

    private void openGallery() {
        Log.d(TAG, "Click open Gallery");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_CODE);
    }

    private void openCamera() {
        Log.d(TAG, "Click open Camera");
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        values.put(MediaStore.Images.Media.TITLE, "Temp Desc");
        imageUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,CAMERA_CODE);
    }

    public ProfileFragment() {}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            String filepath = "Photos/" + "userprofile " + firebaseUser.getUid();
            storageReference = FirebaseStorage.getInstance().getReference(filepath);
            storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                Task<Uri> uriTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                uriTask.addOnSuccessListener(uri -> {
                    String imageURL =  uri.toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                            .child(firebaseUser.getUid());
                    HashMap<String, Object> imageMap = new HashMap<>();
                    imageMap.put(Defined.AVATAR_URL, imageURL);
                    databaseReference.updateChildren(imageMap);

                });

            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



}
