package com.example.seccion_01;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamera;
    private final int PHONE_CALL_CODE = 100;
    private final int IMG_CAMERA = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imgBtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);

        //boton para llamada
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_number = editTextPhone.getText().toString();
                if(p_number != null && !p_number.isEmpty()){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                    }else{
                        OlderVersions(p_number);
                    }
                }
            }

            private void OlderVersions(String p_number){
                //Intent para llamar
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + p_number));
                if(checkPermissions(Manifest.permission.CALL_PHONE)){
                    startActivity(intentCall);
                }else{
                    Toast.makeText(ThirdActivity.this, "You declined the accesss", Toast.LENGTH_LONG).show();
                }

            }


        });

        //boton para web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();
                String email = "jmendoza@advanz.mx";
                if(url != null && !url.isEmpty()){

                    //Intent para web
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));

                    //Intent para Contactos
                    Intent intentCon = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));

                    //Intent Email Rapido
                    Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));

                    //Intent Mail Completo
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Titulo del mail");
                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hola este es un correo desde mi aplicacion");
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"jair_rams@hotmail.com","jair.yayis1@gmail.com"});
                    //startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));

                    //Intent Tel 2 sin permisos
                    Intent intenttel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:12345"));

                    startActivity(intentMail);
                }
            }
        });

        imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent camara
                Intent intCam =  new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intCam, IMG_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case IMG_CAMERA:
                if(requestCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this,"result" + result, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"There was an error with the picture", Toast.LENGTH_LONG).show();
                }
                break;

             default:
                 super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];

                if(permission.equals(Manifest.permission.CALL_PHONE)){
                    if(result == PackageManager.PERMISSION_GRANTED){
                        String phone_number = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_number));
                        startActivity(intentCall);
                    }else{
                        Toast.makeText(ThirdActivity.this, "You declined the accesss", Toast.LENGTH_LONG).show();
                    }
                }

                break;

             default:
                 super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                 break;
        }


    }

    private boolean checkPermissions(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return  result == PackageManager.PERMISSION_GRANTED;
    }
}
