package com.jmendoza.firebase_notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String SP_TOPICS = "sharedPreferencesTopics";

    @BindView(R.id.spinnerTopic)
    Spinner spinnerTopic;
    @BindView(R.id.tvTopics)
    TextView tvTopics;

    private Set<String> mTopicsSet;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Metodo para obtener token
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult ->
                Log.d("TokenId", instanceIdResult.getToken()));
        
        configSharedPreferences();
    }

    private void configSharedPreferences() {
        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
        mTopicsSet = mSharedPreferences.getStringSet(SP_TOPICS, new HashSet<>());
        showTopics();
    }

    private void showTopics() {
        tvTopics.setText(mTopicsSet.toString());
    }

    @OnClick({R.id.btnSuscribir, R.id.btnDesuscribir})
    public void onViewClicked(View view) {

        String topic = getResources().getStringArray(R.array.topicsValues)[spinnerTopic.getSelectedItemPosition()];

        switch (view.getId()) {
            case R.id.btnSuscribir:
                if (!mTopicsSet.contains(topic)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(topic);
                    mTopicsSet.add(topic);
                    saveSharedPreferences();
                }
                break;
            case R.id.btnDesuscribir:
                if (mTopicsSet.contains(topic)) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
                    mTopicsSet.remove(topic);
                    saveSharedPreferences();
                }
                break;
        }
    }

    private void saveSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.putStringSet(SP_TOPICS,mTopicsSet);
        editor.apply();

        showTopics();
    }
}
