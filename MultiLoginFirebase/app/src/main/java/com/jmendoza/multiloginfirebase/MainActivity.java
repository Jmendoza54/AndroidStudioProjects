package com.jmendoza.multiloginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.imgPhotoProfile)
    CircleImageView imgPhotoProfile;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvProvider)
    TextView tvProvider;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                onSetDataUser(user.getDisplayName(), user.getEmail(), user.getProviderData() != null ? user.getProviderData().get(1).getProviderId() : Constantes.PROVEEDOR_DESCONOCIDO);
                loadImage(user.getPhotoUrl());
            } else {

                onSignedOutCleanUp();

                AuthUI.IdpConfig facebookIDP = new AuthUI.IdpConfig.FacebookBuilder()
                        .setPermissions(Arrays.asList("user_friends", "user_gender"))
                        .build();
                AuthUI.IdpConfig googleIdp = new AuthUI.IdpConfig.GoogleBuilder()
                        .build();

                AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                        .Builder(R.layout.custom_view_login)
                        .setEmailButtonId(R.id.btnEmail)
                        .setFacebookButtonId(R.id.btnFB)
                        .setGoogleButtonId(R.id.btnGoogle)
                        .setTosAndPrivacyPolicyId(R.id.tvPolicy)
                        .build();

                startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setTosAndPrivacyPolicyUrls(
                                "https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional",
                                "https://www.udemy.com/user/alain-nicolas-tello")
                        .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(), facebookIDP, googleIdp))
                        .setTheme(R.style.GreenTheme)
                        .setLogo(R.drawable.img_multi_login)
                        .setAuthMethodPickerLayout(customLayout)
                        .build(), Constantes.RC_SIGN_IN);
            }
        };


       /* try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                PackageInfo info = null;
                try {
                    info = getPackageManager().getPackageInfo(
                            "com.jmendoza.multiloginfirebase",
                            PackageManager.GET_SIGNING_CERTIFICATES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                for (Signature signature : info.signingInfo.getApkContentsSigners()) {//.signatures) {
                    MessageDigest md = null;
                    try {
                        md = MessageDigest.getInstance("SHA");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } else {
                PackageInfo info = null;
                try {
                    info = getPackageManager().getPackageInfo(
                            "com.jmendoza.multiloginfirebase",
                            PackageManager.GET_SIGNATURES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                for (Signature signature : info.signatures) {
                    MessageDigest md = null;
                    try {
                        md = MessageDigest.getInstance("SHA");
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    md.update(signature.toByteArray());
                    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
        }catch (NoSuchFieldError e){

        }*/
    }

    private void onSignedOutCleanUp() {
        onSetDataUser("", "", "");
    }

    private void onSetDataUser(String displayName, String email, String provider) {
        tvUserName.setText(displayName);
        tvEmail.setText(email);


        int drawableRes;

        switch (provider) {
            case Constantes.PASSWORD_FIREBASE:
                drawableRes = R.drawable.ic_firebase;
                break;
            case Constantes.FACEBOOK:
                drawableRes = R.drawable.ic_fb;
                break;
            case Constantes.GOOGLE:
                drawableRes = R.drawable.ic_google;
                break;
            default:
                drawableRes = R.drawable.ic_desconocido;
                provider = Constantes.PROVEEDOR_DESCONOCIDO;
                break;
        }

        tvProvider.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableRes, 0, 0, 0);
        tvProvider.setText(provider);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constantes.RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Algo salio mal, intente de nuevo", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == Constantes.RC_FROM_GALLERY && resultCode == RESULT_OK){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference reference = storage.getReference().child(Constantes.PATH_PROFILE).child(Constantes.MY_PHOTO_AUTH);
            Uri selectedImageUri = data.getData();

            if(selectedImageUri != null){
                reference.putFile(selectedImageUri).addOnSuccessListener(taskSnapshot -> {
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user != null) {
                            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                            user.updateProfile(request).addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    loadImage(user.getPhotoUrl());
                                }
                            });
                        }
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error " + e, Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    private void loadImage(Uri photoUrl) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(MainActivity.this)
                .load(photoUrl)
                .apply(options)
                .into(imgPhotoProfile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sign_up:
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @OnClick(R.id.imgPhotoProfile)
    public void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, Constantes.RC_FROM_GALLERY);

    }
}
