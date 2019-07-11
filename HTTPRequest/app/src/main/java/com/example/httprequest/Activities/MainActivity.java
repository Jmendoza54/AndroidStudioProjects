package com.example.httprequest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.httprequest.API.API;
import com.example.httprequest.API.APIServices.WeatherService;
import com.example.httprequest.Models.City;
import com.example.httprequest.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText EditTextsearch;
    private TextView textViewcity;
    private TextView textViewDescription;
    private TextView textViewTemp;
    private ImageView img;
    private Button btn;

    private WeatherService service;
    private Call<City> cityCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        service = API.getApi().create(WeatherService.class);
        btn.setOnClickListener(this);

    }

    private void setUI() {
        EditTextsearch = findViewById(R.id.editTextSearch);
        textViewcity = findViewById(R.id.textViewCity);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTemp = findViewById(R.id.textViewTemperature);
        img = findViewById(R.id.imageViewIcon);
        btn = findViewById(R.id.buttonSearch);
    }

    @Override
    public void onClick(View view) {
        String city = EditTextsearch.getText().toString();
        if (city != "") {
            cityCall = service.getCity(city, API.APPKEY, "metric", "es");
            cityCall.enqueue(new Callback<City>() {
                @Override
                public void onResponse(Call<City> call, Response<City> response) {
                    City city = response.body();
                    setResult(city);
                }

                @Override
                public void onFailure(Call<City> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error" + t, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setResult(City city) {
        textViewcity.setText(city.getName() + ", " + city.getCountry());
        textViewDescription.setText(city.getDescription());
        textViewTemp.setText(city.getTemperature() + "ºC");
        Picasso.get().load(API.BASE_ICONS + city.getIcon() + API.EXTENSION_ICONS).into(img);
    }
}
