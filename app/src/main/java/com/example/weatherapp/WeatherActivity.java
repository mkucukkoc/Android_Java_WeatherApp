package com.example.weatherapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherActivity extends AppCompatActivity {
    private TextView cityWeather,temperatureWeather,condition,humidityWeather,maxTemperatureWeather,minTemperatureWeather,pressureWeather,windWeather;
    private ImageView imageViewWeather;
    private Button search;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityWeather=findViewById(R.id.textViewCityWeather);
        temperatureWeather=findViewById(R.id.temperatureWeather);
        condition=findViewById(R.id.textViewWeatherCondititon);
        humidityWeather=findViewById(R.id.humidityWeather);
        maxTemperatureWeather=findViewById(R.id.maxTempWeather);
        minTemperatureWeather=findViewById(R.id.minTempWeather);
        pressureWeather=findViewById(R.id.pressureWeather);
        windWeather=findViewById(R.id.windWeather);
        imageViewWeather=findViewById(R.id.imageViewWeather);
        search=findViewById(R.id.searchButtonWeather);
        editText=findViewById(R.id.editTextCityName);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String cityName=editText.getText().toString();
            getWeatherData(cityName);
            editText.setText("");
            }
        });

    }
    public void getWeatherData(String name)
    {
        WeatherAPI weatherAPI=RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call=weatherAPI.getWeatherWithCityName(name);

        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                if (response.isSuccessful())
                {
                    cityWeather.setText(response.body().getName()+" , "+response.body().getSys().getCountry());
                    temperatureWeather.setText(response.body().getMain().getTemp()+" °C");
                    condition.setText(response.body().getWeather().get(0).getDescription());
                    humidityWeather.setText(" : "+response.body().getMain().getHumidity()+"%");
                    maxTemperatureWeather.setText(" : "+response.body().getMain().getTempMax()+" °C");
                    minTemperatureWeather.setText(" : "+response.body().getMain().getTempMin()+" °C");
                    pressureWeather.setText(" : "+response.body().getMain().getPressure());
                    windWeather.setText(" : "+response.body().getWind().getSpeed());

                    String iconCode=response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/"+iconCode+"@2x.png").placeholder(R.drawable.sun1).into(imageViewWeather);

                }
                else
                {
                    Toast.makeText(WeatherActivity.this,"City not found,please try again...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });
    }
}