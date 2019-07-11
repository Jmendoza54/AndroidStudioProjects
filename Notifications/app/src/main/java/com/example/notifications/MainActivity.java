package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextTitle)
    EditText editTextTitle;
    @BindView(R.id.editTextMessage)
    EditText getEditTextMessage;
    @BindView(R.id.switchImportance)
    Switch switchImportance;

    @BindString(R.string.switch_notification_on) String switchTextOn;
    @BindString(R.string.switch_notification_off) String switchTextOff;

    private boolean isHighImportance = false;

    private NotificationHandler notificationHandler;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Right After setContentView
        ButterKnife.bind(this);
        notificationHandler = new NotificationHandler(this);
    }

    @OnClick(R.id.buttonSend)
    public void click(){
        sendNotification();
    }

    @OnCheckedChanged(R.id.switchImportance)
    public void change(CompoundButton button, boolean isChecked){
        isHighImportance = isChecked;
        switchImportance.setText((isChecked) ? switchTextOn : switchTextOff);
    }

    private void sendNotification() {
        String title = editTextTitle.getText().toString();
        String message = getEditTextMessage.getText().toString();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){
            Notification.Builder nb = notificationHandler.createNotification(title, message, isHighImportance);
            notificationHandler.getManager().notify(++counter, nb.build());
            notificationHandler.publishNotificationSummaryGroup(isHighImportance);
        }

    }
}
