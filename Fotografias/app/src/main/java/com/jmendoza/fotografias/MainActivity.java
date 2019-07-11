package com.jmendoza.fotografias;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnUpload)
    Button btnUpload;
    @BindView(R.id.imgPhoto)
    ImageView imgPhoto;
    @BindView(R.id.btnDelete)
    ImageButton btnDelete;
    @BindView(R.id.container)
    ConstraintLayout container;
    private TextView mTextMessage;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;

    private String mCurrentPhotoPath;
    private Uri mPhotoSelectedUri;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_gallery:
                    mTextMessage.setText(R.string.label_gallery);
                    //fromGallery();
                    checkPermissionToApp(Manifest.permission.READ_EXTERNAL_STORAGE, Constantes.RP_STORAGE);
                    return true;
                case R.id.navigation_camera:
                    mTextMessage.setText(R.string.label_camera);
                    //fromCamera();
                    //dispatchTakePictureIntent();
                    checkPermissionToApp(Manifest.permission.CAMERA, Constantes.RP_CAMERA);
                    return true;
            }
            return false;
        }
    };

    private void checkPermissionToApp(String permissionStr, int requestPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(this, permissionStr) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{permissionStr}, requestPermission);
                return;
            }
        }

        switch (requestPermission){
            case Constantes.RP_STORAGE:
                fromGallery();
                break;
             case Constantes.RP_CAMERA:
                 dispatchTakePictureIntent();
                 break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            switch (requestCode){
                case Constantes.RP_STORAGE:
                    fromGallery();
                    break;
                 case Constantes.RP_CAMERA:
                     dispatchTakePictureIntent();
                     break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        configFirebase();
        
        configPhotoProfile();

    }

    private void configPhotoProfile() {

        mStorageReference.child(Constantes.PATH_PROFILE).child(Constantes.MY_PHOTO).getDownloadUrl().addOnSuccessListener(uri -> {
            final RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(MainActivity.this)
                    .load(uri)
                    .apply(options)
                    .into(imgPhoto);
            btnDelete.setVisibility(View.VISIBLE);
        }).addOnFailureListener(e -> {
            btnDelete.setVisibility(View.GONE);
            Snackbar.make(container, R.string.main_message_error_not_found, Snackbar.LENGTH_LONG).show();
        });

        /*mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(MainActivity.this)
                        .load(dataSnapshot.getValue())
                        .apply(options)
                        .into(imgPhoto);
                btnDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                btnDelete.setVisibility(View.GONE);
                Snackbar.make(container, R.string.main_message_error_not_found, Snackbar.LENGTH_LONG).show();
            }
        });*/

    }

    private void configFirebase() {
        mStorageReference = FirebaseStorage.getInstance().getReference();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference().child(Constantes.PATH_PROFILE).child(Constantes.PATH_PHOTO_URL);
    }

    private void fromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constantes.RC_GALLERY);
    }

    private void fromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constantes.RC_CAMERA);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile;
            photoFile = createImageFile();

            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this, "com.jmendoza.fotografias", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, Constantes.RC_CAMERA);
            }
        }
    }

    private File createImageFile() {
        final String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HHmmss", Locale.ROOT).format(new Date());
        final String imageFileName = Constantes.MY_PHOTO + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
            mCurrentPhotoPath = image.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constantes.RC_GALLERY:
                    if (data != null) {
                        mPhotoSelectedUri = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mPhotoSelectedUri);
                            imgPhoto.setImageBitmap(bitmap);
                            btnDelete.setVisibility(View.GONE);
                            mTextMessage.setText(R.string.message_question_upload);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Constantes.RC_CAMERA:
                     /*Bundle extras = data.getExtras();
                     Bitmap bitmap = (Bitmap) extras.get("data");*/
                     mPhotoSelectedUri = addPicGallery();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mPhotoSelectedUri);
                        imgPhoto.setImageBitmap(bitmap);
                        btnDelete.setVisibility(View.GONE);
                        mTextMessage.setText(R.string.main_message_done);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    private Uri addPicGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        mCurrentPhotoPath = null;
        return contentUri;
    }

    @OnClick(R.id.btnUpload)
    public void onUploadPhoto() {
        StorageReference profileReference = mStorageReference.child(Constantes.PATH_PROFILE);

        StorageReference photoReference = profileReference.child(Constantes.MY_PHOTO);
        photoReference.putFile(mPhotoSelectedUri).addOnSuccessListener(taskSnapshot -> {
            Snackbar.make(container, R.string.main_message_load_success, Snackbar.LENGTH_LONG).show();
            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                savePhotoUrl(uri);
                btnDelete.setVisibility(View.VISIBLE);
                mTextMessage.setText(R.string.main_message_done);
            });
        }).addOnFailureListener(e -> Snackbar.make(container, R.string.main_message_upload_error, Snackbar.LENGTH_LONG).show());
    }

    private void savePhotoUrl(Uri downloadUri) {
        mDatabaseReference.setValue(downloadUri.toString());
    }

    @OnClick(R.id.btnDelete)
    public void onDeletePhoto() {
        mStorageReference.child(Constantes.PATH_PROFILE).child(Constantes.MY_PHOTO).delete().addOnSuccessListener(aVoid -> {
            mDatabaseReference.removeValue();
            Snackbar.make(container, R.string.main_message_delete_success, Snackbar.LENGTH_LONG).show();
            imgPhoto.setImageBitmap(null);
            btnDelete.setVisibility(View.GONE);
        }).addOnFailureListener(e -> {
            Snackbar.make(container, R.string.main_message_delete_error, Snackbar.LENGTH_LONG).show();
        });
    }
}
