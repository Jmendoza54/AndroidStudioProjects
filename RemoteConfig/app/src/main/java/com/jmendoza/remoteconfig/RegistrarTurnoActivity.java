package com.jmendoza.remoteconfig;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RegistrarTurnoActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.btnRequest)
    Button btnRequest;
    @BindView(R.id.fullscreen_content_controls)
    LinearLayout fullscreenContentControls;
    @BindView(R.id.ContentMain)
    FrameLayout ContentMain;

    private TextView mContentView;

    private FirebaseRemoteConfig mfirebaseRemoteConfig;

    private final Handler mHideHandler = new Handler();

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar_turno);
        ButterKnife.bind(this);

        mContentView = findViewById(R.id.fullscreen_content);

        configFirebaseRemoteConfig();

    }

    private void configFirebaseRemoteConfig() {
        mfirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build();
        mfirebaseRemoteConfig.setConfigSettings(configSettings);
        mfirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        configFetch();
    }

    private void configFetch() {
        mfirebaseRemoteConfig.fetch(0).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                mfirebaseRemoteConfig.activateFetched();
                Snackbar.make(fullscreenContentControls, R.string.registrarTurno_message_config_remote, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(fullscreenContentControls, R.string.registrarTurno_message_config_local, Snackbar.LENGTH_LONG).show();
            }
        });

        displayMainMessage();
    }

    private void displayMainMessage() {
        etName.setVisibility(mfirebaseRemoteConfig.getBoolean(Constantes.F_SHOW_NAME) ? View.VISIBLE : View.GONE);

        String messageRemote = mfirebaseRemoteConfig.getString(Constantes.F_MAIN_MESSAGE);
        messageRemote = messageRemote.replace("\\n", "\n");
        mContentView.setText(messageRemote);

        configColorsRemote();
    }

    private void configColorsRemote() {
        ContentMain.setBackgroundColor(Color.parseColor(mfirebaseRemoteConfig.getString(Constantes.F_COLOR_PRIMARY)));
        mContentView.setTextColor(Color.parseColor(mfirebaseRemoteConfig.getString(Constantes.F_COLOR_TEXT_MESSAGE)));
        btnRequest.setBackgroundColor(Color.parseColor(mfirebaseRemoteConfig.getString(Constantes.F_COLOR_BTN)));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mHideHandler.postDelayed(mHidePart2Runnable, 100);
    }


    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @OnClick(R.id.btnRequest)
    public void onViewClicked() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            }
        }

        fullscreenContentControls.requestFocus();

        etName.setText("");
        etPhone.setText("");
        mContentView.setText(R.string.registrarTurno_message_success);
    }
}
