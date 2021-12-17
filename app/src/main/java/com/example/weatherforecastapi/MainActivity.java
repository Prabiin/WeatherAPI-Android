package com.example.weatherforecastapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    private TextView tv_weather_description, temperature, city, country, update_time, cloud, visibility, humidity, wind, sunrise, sunset,
            maxTemperature, minTemperature;
    private ImageView imageView, refresh;
    private RelativeLayout relativeLayout;
    private LinearLayout LLtop, LL;
    private ProgressBar prog_bar;

    private TextInputEditText editTxt;
    private TextInputLayout txtInputLayout;
    private CardView CV, five_day_forecast;

    private ImageView icon_one, icon_two, icon_three, icon_four, icon_five, icon_six;

    private TextView day_1, max_temp_one, min_temp_one, weather_summary_one,
            day_2, max_temp_two, min_temp_two, weather_summary_two,
            day_3, max_temp_three, min_temp_three, weather_summary_three,
            day_4, max_temp_four, min_temp_four, weather_summary_four,
            day_5, max_temp_five, min_temp_five, weather_summary_five,
            day_6, max_temp_six, min_temp_six, weather_summary_six;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);

        tv_weather_description = findViewById(R.id.tv_weather_description);
        temperature = findViewById(R.id.temperature);
        city = findViewById(R.id.tv_city_name);
        imageView = findViewById(R.id.imgView);
        country = findViewById(R.id.country_code);
        update_time = findViewById(R.id.txt_update);
        refresh = findViewById(R.id.refresh);
        maxTemperature = findViewById(R.id.txt_max_temp);
        minTemperature = findViewById(R.id.txt_min_temp);
        cloud = findViewById(R.id.txt_cloudiness);
        visibility = findViewById(R.id.txt_visibility);
        humidity = findViewById(R.id.txt_humidity);
        wind = findViewById(R.id.txt_wind);
        sunrise = findViewById(R.id.txt_sunrise);
        sunset = findViewById(R.id.txt_sunset);
        relativeLayout = findViewById(R.id.relative_layout);
        //search_button = findViewById(R.id.search_btn);
        prog_bar = findViewById(R.id.progressBar);
        CV = findViewById(R.id.today);
        five_day_forecast = findViewById(R.id.forecast);
        editTxt = findViewById(R.id.editText);
        LLtop = findViewById(R.id.top);
        LL = findViewById(R.id.top);
        txtInputLayout = findViewById(R.id.search_btn);

        day_1 = findViewById(R.id.day_one);
        day_2 = findViewById(R.id.day_two);
        day_3 = findViewById(R.id.day_three);
        day_4 = findViewById(R.id.day_four);
        day_5 = findViewById(R.id.day_five);
        day_6 = findViewById(R.id.day_six);


        icon_one = findViewById(R.id.icon1);
        icon_two = findViewById(R.id.icon2);
        icon_three = findViewById(R.id.icon3);
        icon_four = findViewById(R.id.icon4);
        icon_five = findViewById(R.id.icon5);
        icon_six = findViewById(R.id.icon6);

        max_temp_one = findViewById(R.id.max_temp1);
        min_temp_one = findViewById(R.id.min_temp1);
        weather_summary_one = findViewById(R.id.weather_summary1);

        max_temp_two = findViewById(R.id.max_temp2);
        min_temp_two = findViewById(R.id.min_temp2);
        weather_summary_two = findViewById(R.id.weather_summary2);

        max_temp_three = findViewById(R.id.max_temp3);
        min_temp_three = findViewById(R.id.min_temp3);
        weather_summary_three = findViewById(R.id.weather_summary3);

        max_temp_four = findViewById(R.id.max_temp4);
        min_temp_four = findViewById(R.id.min_temp4);
        weather_summary_four = findViewById(R.id.weather_summary4);

        max_temp_five = findViewById(R.id.max_temp5);
        min_temp_five = findViewById(R.id.min_temp5);
        weather_summary_five = findViewById(R.id.weather_summary5);

        max_temp_six = findViewById(R.id.max_temp6);
        min_temp_six = findViewById(R.id.min_temp6);
        weather_summary_six = findViewById(R.id.weather_summary6);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=kathmandu&appid=c16053c68dd00729de4c0dd32b00c986";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray array;
                        String condition = "", feels_like = "", min_temp = "", max_temp = "", country_code = "", city_name = "", icon = "", iconUrl, wind_speed = "", humidity_level = "",
                                visibility_level = "", cloudiness = "", sun_rise = "", sun_set = "", lon = "", lat = "";
                        try {
                            JSONObject coordinates = response.getJSONObject("coord");
                            lon =  coordinates.getString("lon");
                            lat = coordinates.getString("lat");
                            array = response.getJSONArray("weather");
                            JSONObject jsonObject = array.getJSONObject(0);
                            condition = jsonObject.getString("description");
                            icon = jsonObject.getString("icon");
                            JSONObject jsonObject1 = response.getJSONObject("main");
                            feels_like = jsonObject1.getString("feels_like");
                            min_temp = jsonObject1.getString("temp_min");
                            max_temp = jsonObject1.getString("temp_max");
                            JSONObject jsonObject2 = response.getJSONObject("sys");
                            country_code = jsonObject2.getString("country");
                            city_name = response.getString("name");
                            humidity_level = jsonObject1.getString("humidity");
                            visibility_level = response.getString("visibility");
                            JSONObject jsonObject3 = response.getJSONObject("clouds");
                            cloudiness = jsonObject3.getString("all");
                            JSONObject jsonObject4 = response.getJSONObject("wind");
                            wind_speed = jsonObject4.getString("speed");
                            sun_rise = jsonObject2.getString("sunrise");
                            sun_set = jsonObject2.getString("sunset");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String url1 = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=alerts,minutely,hourly&appid=c16053c68dd00729de4c0dd32b00c986";
                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        JSONArray array, array1, array2, array3, array4, array5, array6;
                                        String iconUrl1 = "", max_temp1 = "", min_temp1 = "", icon_code1 =  "", description1 = "", date1 = "",
                                                iconUrl2 = "", max_temp2 = "", min_temp2 = "", icon_code2 =  "", description2 = "", date2 = "",
                                                iconUrl3 = "", max_temp3 = "", min_temp3 = "", icon_code3 =  "", description3 = "", date3 = "",
                                                iconUrl4 = "", max_temp4 = "", min_temp4 = "", icon_code4 =  "", description4 = "", date4 = "",
                                                iconUrl5 = "", max_temp5 = "", min_temp5 = "", icon_code5 =  "", description5 = "", date5 = "",
                                                iconUrl6 = "", max_temp6 = "", min_temp6 = "", icon_code6 =  "", description6 = "", date6 = "";
                                        try {
                                            array = response.getJSONArray("daily");

                                            JSONObject jsonObject = array.getJSONObject(1);
                                            date1 = jsonObject.getString("dt");
                                            JSONObject jsonObject1 = jsonObject.getJSONObject("temp");
                                            max_temp1 = jsonObject1.getString("max");
                                            min_temp1 = jsonObject1.getString("min");
                                            array1 = jsonObject.getJSONArray("weather");
                                            JSONObject jsonObject2 = array1.getJSONObject(0);
                                            icon_code1 = jsonObject2.getString("icon");
                                            description1 = jsonObject2.getString("main");


                                            JSONObject jsonObject3 = array.getJSONObject(2);
                                            date2 = jsonObject3.getString("dt");
                                            JSONObject jsonObject4 = jsonObject.getJSONObject("temp");
                                            max_temp2 = jsonObject4.getString("max");
                                            min_temp2 = jsonObject4.getString("min");
                                            array2 = jsonObject3.getJSONArray("weather");
                                            JSONObject jsonObject5 = array2.getJSONObject(0);
                                            icon_code2 = jsonObject5.getString("icon");
                                            description2 = jsonObject5.getString("main");

                                            JSONObject jsonObject6 = array.getJSONObject(3);
                                            date3 = jsonObject6.getString("dt");
                                            JSONObject jsonObject7 = jsonObject.getJSONObject("temp");
                                            max_temp3 = jsonObject7.getString("max");
                                            min_temp3 = jsonObject7.getString("min");
                                            array3 = jsonObject6.getJSONArray("weather");
                                            JSONObject jsonObject8 = array3.getJSONObject(0);
                                            icon_code3 = jsonObject8.getString("icon");
                                            description3 = jsonObject8.getString("main");

                                            JSONObject jsonObject9 = array.getJSONObject(4);
                                            date4 = jsonObject9.getString("dt");
                                            JSONObject jsonObject10 = jsonObject.getJSONObject("temp");
                                            max_temp4 = jsonObject10.getString("max");
                                            min_temp4 = jsonObject10.getString("min");
                                            array4 = jsonObject9.getJSONArray("weather");
                                            JSONObject jsonObject11 = array4.getJSONObject(0);
                                            icon_code4 = jsonObject11.getString("icon");
                                            description4 = jsonObject11.getString("main");

                                            JSONObject jsonObject12 = array.getJSONObject(5);
                                            date5 = jsonObject12.getString("dt");
                                            JSONObject jsonObject13 = jsonObject.getJSONObject("temp");
                                            max_temp5 = jsonObject13.getString("max");
                                            min_temp5 = jsonObject13.getString("min");
                                            array5 = jsonObject12.getJSONArray("weather");
                                            JSONObject jsonObject14 = array5.getJSONObject(0);
                                            icon_code5 = jsonObject14.getString("icon");
                                            description5 = jsonObject14.getString("main");

                                            JSONObject jsonObject15 = array.getJSONObject(6);
                                            date6 = jsonObject15.getString("dt");
                                            JSONObject jsonObject16 = jsonObject.getJSONObject("temp");
                                            max_temp6 = jsonObject16.getString("max");
                                            min_temp6 = jsonObject16.getString("min");
                                            array6 = jsonObject15.getJSONArray("weather");
                                            JSONObject jsonObject17 = array6.getJSONObject(0);
                                            icon_code6 = jsonObject17.getString("icon");
                                            description6 = jsonObject17.getString("main");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        long longDate1 = Long.parseLong(date1);
                                        long unDate1 = longDate1 * (long) 1000;
                                        Date date_one = new Date(unDate1);
                                        SimpleDateFormat format1 = new SimpleDateFormat("LLL dd");
                                        format1.setTimeZone(TimeZone.getDefault());
                                        String formatted_date1 = format1.format(date_one);

                                        long longDate2 = Long.parseLong(date2);
                                        long unDate2 = longDate2 * (long) 1000;
                                        Date date_two = new Date(unDate2);
                                        SimpleDateFormat format2 = new SimpleDateFormat("LLL dd");
                                        format2.setTimeZone(TimeZone.getDefault());
                                        String formatted_date2 = format1.format(date_two);

                                        long longDate3 = Long.parseLong(date3);
                                        long unDate3 = longDate3 * (long) 1000;
                                        Date date_three = new Date(unDate3);
                                        SimpleDateFormat format3 = new SimpleDateFormat("LLL dd");
                                        format3.setTimeZone(TimeZone.getDefault());
                                        String formatted_date3 = format1.format(date_three);

                                        long longDate4 = Long.parseLong(date4);
                                        long unDate4 = longDate4 * (long) 1000;
                                        Date date_four = new Date(unDate4);
                                        SimpleDateFormat format4 = new SimpleDateFormat("LLL dd");
                                        format4.setTimeZone(TimeZone.getDefault());
                                        String formatted_date4 = format1.format(date_four);

                                        long longDate5 = Long.parseLong(date5);
                                        long unDate5 = longDate5 * (long) 1000;
                                        Date date_five = new Date(unDate5);
                                        SimpleDateFormat format5 = new SimpleDateFormat("LLL dd");
                                        format5.setTimeZone(TimeZone.getDefault());
                                        String formatted_date5 = format1.format(date_five);

                                        long longDate6 = Long.parseLong(date6);
                                        long unDate6 = longDate6 * (long) 1000;
                                        Date date_six = new Date(unDate6);
                                        SimpleDateFormat format6 = new SimpleDateFormat("LLL dd");
                                        format6.setTimeZone(TimeZone.getDefault());
                                        String formatted_date6 = format1.format(date_six);

                                        day_1.setText(formatted_date1);
                                        day_2.setText(formatted_date2);
                                        day_3.setText(formatted_date3);
                                        day_4.setText(formatted_date4);
                                        day_5.setText(formatted_date5);
                                        day_6.setText(formatted_date6);

                                        double kelvin_max1 = Double.parseDouble(max_temp1);
                                        int celsius_max1 = (int) (kelvin_max1 - 273.15);
                                        double kelvin_min1 = Double.parseDouble(min_temp1);
                                        int celsius_min1 = (int) (kelvin_min1 - 273.15);

                                        double kelvin_max2 = Double.parseDouble(max_temp2);
                                        int celsius_max2 = (int) (kelvin_max2 - 273.15);
                                        double kelvin_min2 = Double.parseDouble(min_temp2);
                                        int celsius_min2 = (int) (kelvin_min2 - 273.15);

                                        double kelvin_max3 = Double.parseDouble(max_temp3);
                                        int celsius_max3 = (int) (kelvin_max3 - 273.15);
                                        double kelvin_min3 = Double.parseDouble(min_temp3);
                                        int celsius_min3 = (int) (kelvin_min3 - 273.15);

                                        double kelvin_max4 = Double.parseDouble(max_temp4);
                                        int celsius_max4 = (int) (kelvin_max4 - 273.15);
                                        double kelvin_min4 = Double.parseDouble(min_temp4);
                                        int celsius_min4 = (int) (kelvin_min4 - 273.15);

                                        double kelvin_max5 = Double.parseDouble(max_temp5);
                                        int celsius_max5 = (int) (kelvin_max5 - 273.15);
                                        double kelvin_min5 = Double.parseDouble(min_temp5);
                                        int celsius_min5 = (int) (kelvin_min5 - 273.15);

                                        double kelvin_max6 = Double.parseDouble(max_temp6);
                                        int celsius_max6 = (int) (kelvin_max6 - 273.15);
                                        double kelvin_min6 = Double.parseDouble(min_temp6);
                                        int celsius_min6 = (int) (kelvin_min6 - 273.15);

                                        max_temp_one.setText(celsius_max1 + "°");
                                        min_temp_one.setText("/" + celsius_min1 + "°");
                                        max_temp_two.setText(celsius_max2 + "°");
                                        min_temp_two.setText("/" + celsius_min2 + "°");
                                        max_temp_three.setText(celsius_max3 + "°");
                                        min_temp_three.setText("/" + celsius_min3 + "°");
                                        max_temp_four.setText(celsius_max4 + "°");
                                        min_temp_four.setText("/" + celsius_min4 + "°");
                                        max_temp_five.setText(celsius_max5 + "°");
                                        min_temp_five.setText("/" + celsius_min5 + "°");
                                        max_temp_six.setText(celsius_max6 + "°");
                                        min_temp_six.setText("/" + celsius_min6 + "°");

                                        iconUrl1 = "https://openweathermap.org/img/wn/" + icon_code1 + "@2x.png";
                                        iconUrl2 = "https://openweathermap.org/img/wn/" + icon_code2 + "@2x.png";
                                        iconUrl3 = "https://openweathermap.org/img/wn/" + icon_code3 + "@2x.png";
                                        iconUrl4 = "https://openweathermap.org/img/wn/" + icon_code4 + "@2x.png";
                                        iconUrl5 = "https://openweathermap.org/img/wn/" + icon_code5 + "@2x.png";
                                        iconUrl6 = "https://openweathermap.org/img/wn/" + icon_code6 + "@2x.png";
                                        Picasso.get()
                                                .load(iconUrl1)
                                                .resize(150, 150)
                                                .into(icon_one);
                                        Picasso.get()
                                                .load(iconUrl2)
                                                .resize(150, 150)
                                                .into(icon_two);
                                        Picasso.get()
                                                .load(iconUrl3)
                                                .resize(150, 150)
                                                .into(icon_three);
                                        Picasso.get()
                                                .load(iconUrl4)
                                                .resize(150, 150)
                                                .into(icon_four);
                                        Picasso.get()
                                                .load(iconUrl5)
                                                .resize(150, 150)
                                                .into(icon_five);
                                        Picasso.get()
                                                .load(iconUrl6)
                                                .resize(150, 150)
                                                .into(icon_six);

                                        weather_summary_one.setText(description1);
                                        weather_summary_two.setText(description2);
                                        weather_summary_three.setText(description3);
                                        weather_summary_four.setText(description4);
                                        weather_summary_five.setText(description5);
                                        weather_summary_six.setText(description6);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(jsonObjectRequest1);
                        int visibility_int = (int)Double.parseDouble(visibility_level)/1000;

                        iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                        Picasso.get()
                                .load(iconUrl)
                                .resize(350, 350)
                                .into(imageView);

                        double kelvin1 = Double.parseDouble(feels_like);
                        int celsius1 = (int) (kelvin1 - 273.15);
                        temperature.setText("Feels " + celsius1 + "° C");

                        double kelvin2 = Double.parseDouble(max_temp);
                        int celsius2 = (int) (kelvin2 - 273.15);
                        maxTemperature.setText("Max Temp " + celsius2 + "° C");

                        double kelvin3 = Double.parseDouble(min_temp);
                        int celsius3 = (int) (kelvin3 - 273.15);
                        minTemperature.setText("Min Temp " + celsius3 + "° C");

                        tv_weather_description.setText(condition);
                        city.setText(city_name);
                        country.setText(country_code);

                        Date currentTime = Calendar.getInstance().getTime();
                        String time = DateFormat.getTimeInstance().format(currentTime);
                        update_time.setText("Updated as of " + time);

                        double speedDouble = Double.parseDouble(wind_speed);
                        int speedInt = (int) (speedDouble*36/10);
                        wind.setText("Wind  " + speedInt + " km/h");
                        humidity.setText("Humidity  " + humidity_level + "%");
                        visibility.setText("Visibility  " + visibility_int + " km");
                        cloud.setText("Cloudy  " + cloudiness + "%");

                        long riseTime = Long.parseLong(sun_rise);
                        long time1 = riseTime * (long) 1000;
                        Date date1 = new Date(time1);
                        SimpleDateFormat format1 = new SimpleDateFormat("H:mm");
                        format1.setTimeZone(TimeZone.getDefault());
                        String formatted_sunrise_time = format1.format(date1);
                        sunrise.setText("Sunrise  " + formatted_sunrise_time + "AM");

                        long setTime = Long.parseLong(sun_set);
                        long time2 = setTime * (long) 1000;
                        Date date2 = new Date(time2);
                        SimpleDateFormat format2 = new SimpleDateFormat("h:mm");
                        format2.setTimeZone(TimeZone.getDefault());
                        String formatted_sunset_time = format2.format(date2);
                        sunset.setText("Sunset  " + formatted_sunset_time + "PM");

                        prog_bar.setVisibility(View.INVISIBLE);
                        LLtop.setVisibility(View.VISIBLE);
                        LL.setVisibility(View.VISIBLE);
                        city.setVisibility(View.VISIBLE);
                        country.setVisibility(View.VISIBLE);
                        temperature.setVisibility(View.VISIBLE);
                        tv_weather_description.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        refresh.setVisibility(View.VISIBLE);
                        update_time.setVisibility(View.VISIBLE);
                        refresh.setVisibility(View.VISIBLE);
                        CV.setVisibility(View.VISIBLE);
                        five_day_forecast.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        txtInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searched_city = editTxt.getText().toString();
                if(searched_city.equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter city name", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + searched_city + "&appid=c16053c68dd00729de4c0dd32b00c986";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    JSONArray array;
                                    String condition = "", feels_like = "", country_code = "", city_name = "", icon = "", iconUrl, wind_speed = "", humidity_level = "",
                                            visibility_level = "", cloudiness = "", sun_rise = "", sun_set = "", lon = "", lat = "";
                                    try {
                                        JSONObject coordinates = response.getJSONObject("coord");
                                        lon =  coordinates.getString("lon");
                                        lat = coordinates.getString("lat");
                                        array = response.getJSONArray("weather");
                                        JSONObject jsonObject = array.getJSONObject(0);
                                        condition = jsonObject.getString("description");
                                        icon = jsonObject.getString("icon");
                                        JSONObject jsonObject1 = response.getJSONObject("main");
                                        feels_like = jsonObject1.getString("feels_like");
                                        JSONObject jsonObject2 = response.getJSONObject("sys");
                                        country_code = jsonObject2.getString("country");
                                        city_name = response.getString("name");
                                        humidity_level = jsonObject1.getString("humidity");
                                        visibility_level = response.getString("visibility");
                                        JSONObject jsonObject3 = response.getJSONObject("clouds");
                                        cloudiness = jsonObject3.getString("all");
                                        JSONObject jsonObject4 = response.getJSONObject("wind");
                                        wind_speed = jsonObject4.getString("speed");
                                        sun_rise = jsonObject2.getString("sunrise");
                                        sun_set = jsonObject2.getString("sunset");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    String url1 = "https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude=alerts,minutely,hourly&appid=c16053c68dd00729de4c0dd32b00c986";
                                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    JSONArray array, array1, array2, array3, array4, array5, array6;
                                                    String iconUrl1 = "", max_temp1 = "", min_temp1 = "", icon_code1 =  "", description1 = "", date1 = "",
                                                            iconUrl2 = "", max_temp2 = "", min_temp2 = "", icon_code2 =  "", description2 = "", date2 = "",
                                                            iconUrl3 = "", max_temp3 = "", min_temp3 = "", icon_code3 =  "", description3 = "", date3 = "",
                                                            iconUrl4 = "", max_temp4 = "", min_temp4 = "", icon_code4 =  "", description4 = "", date4 = "",
                                                            iconUrl5 = "", max_temp5 = "", min_temp5 = "", icon_code5 =  "", description5 = "", date5 = "",
                                                            iconUrl6 = "", max_temp6 = "", min_temp6 = "", icon_code6 =  "", description6 = "", date6 = "";
                                                    try {
                                                        array = response.getJSONArray("daily");

                                                        JSONObject jsonObject = array.getJSONObject(1);
                                                        date1 = jsonObject.getString("dt");
                                                        JSONObject jsonObject1 = jsonObject.getJSONObject("temp");
                                                        max_temp1 = jsonObject1.getString("max");
                                                        min_temp1 = jsonObject1.getString("min");
                                                        array1 = jsonObject.getJSONArray("weather");
                                                        JSONObject jsonObject2 = array1.getJSONObject(0);
                                                        icon_code1 = jsonObject2.getString("icon");
                                                        description1 = jsonObject2.getString("main");


                                                        JSONObject jsonObject3 = array.getJSONObject(2);
                                                        date2 = jsonObject3.getString("dt");
                                                        JSONObject jsonObject4 = jsonObject.getJSONObject("temp");
                                                        max_temp2 = jsonObject4.getString("max");
                                                        min_temp2 = jsonObject4.getString("min");
                                                        array2 = jsonObject3.getJSONArray("weather");
                                                        JSONObject jsonObject5 = array2.getJSONObject(0);
                                                        icon_code2 = jsonObject5.getString("icon");
                                                        description2 = jsonObject5.getString("main");

                                                        JSONObject jsonObject6 = array.getJSONObject(3);
                                                        date3 = jsonObject6.getString("dt");
                                                        JSONObject jsonObject7 = jsonObject.getJSONObject("temp");
                                                        max_temp3 = jsonObject7.getString("max");
                                                        min_temp3 = jsonObject7.getString("min");
                                                        array3 = jsonObject6.getJSONArray("weather");
                                                        JSONObject jsonObject8 = array3.getJSONObject(0);
                                                        icon_code3 = jsonObject8.getString("icon");
                                                        description3 = jsonObject8.getString("main");

                                                        JSONObject jsonObject9 = array.getJSONObject(4);
                                                        date4 = jsonObject9.getString("dt");
                                                        JSONObject jsonObject10 = jsonObject.getJSONObject("temp");
                                                        max_temp4 = jsonObject10.getString("max");
                                                        min_temp4 = jsonObject10.getString("min");
                                                        array4 = jsonObject9.getJSONArray("weather");
                                                        JSONObject jsonObject11 = array4.getJSONObject(0);
                                                        icon_code4 = jsonObject11.getString("icon");
                                                        description4 = jsonObject11.getString("main");

                                                        JSONObject jsonObject12 = array.getJSONObject(5);
                                                        date5 = jsonObject12.getString("dt");
                                                        JSONObject jsonObject13 = jsonObject.getJSONObject("temp");
                                                        max_temp5 = jsonObject13.getString("max");
                                                        min_temp5 = jsonObject13.getString("min");
                                                        array5 = jsonObject12.getJSONArray("weather");
                                                        JSONObject jsonObject14 = array5.getJSONObject(0);
                                                        icon_code5 = jsonObject14.getString("icon");
                                                        description5 = jsonObject14.getString("main");

                                                        JSONObject jsonObject15 = array.getJSONObject(6);
                                                        date6 = jsonObject15.getString("dt");
                                                        JSONObject jsonObject16 = jsonObject.getJSONObject("temp");
                                                        max_temp6 = jsonObject16.getString("max");
                                                        min_temp6 = jsonObject16.getString("min");
                                                        array6 = jsonObject15.getJSONArray("weather");
                                                        JSONObject jsonObject17 = array6.getJSONObject(0);
                                                        icon_code6 = jsonObject17.getString("icon");
                                                        description6 = jsonObject17.getString("main");
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    long longDate1 = Long.parseLong(date1);
                                                    long unDate1 = longDate1 * (long) 1000;
                                                    Date date_one = new Date(unDate1);
                                                    SimpleDateFormat format1 = new SimpleDateFormat("LLL dd");
                                                    format1.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date1 = format1.format(date_one);

                                                    long longDate2 = Long.parseLong(date2);
                                                    long unDate2 = longDate2 * (long) 1000;
                                                    Date date_two = new Date(unDate2);
                                                    SimpleDateFormat format2 = new SimpleDateFormat("LLL dd");
                                                    format2.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date2 = format1.format(date_two);

                                                    long longDate3 = Long.parseLong(date3);
                                                    long unDate3 = longDate3 * (long) 1000;
                                                    Date date_three = new Date(unDate3);
                                                    SimpleDateFormat format3 = new SimpleDateFormat("LLL dd");
                                                    format3.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date3 = format1.format(date_three);

                                                    long longDate4 = Long.parseLong(date4);
                                                    long unDate4 = longDate4 * (long) 1000;
                                                    Date date_four = new Date(unDate4);
                                                    SimpleDateFormat format4 = new SimpleDateFormat("LLL dd");
                                                    format4.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date4 = format1.format(date_four);

                                                    long longDate5 = Long.parseLong(date5);
                                                    long unDate5 = longDate5 * (long) 1000;
                                                    Date date_five = new Date(unDate5);
                                                    SimpleDateFormat format5 = new SimpleDateFormat("LLL dd");
                                                    format5.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date5 = format1.format(date_five);

                                                    long longDate6 = Long.parseLong(date6);
                                                    long unDate6 = longDate6 * (long) 1000;
                                                    Date date_six = new Date(unDate6);
                                                    SimpleDateFormat format6 = new SimpleDateFormat("LLL dd");
                                                    format6.setTimeZone(TimeZone.getDefault());
                                                    String formatted_date6 = format1.format(date_six);

                                                    day_1.setText(formatted_date1);
                                                    day_2.setText(formatted_date2);
                                                    day_3.setText(formatted_date3);
                                                    day_4.setText(formatted_date4);
                                                    day_5.setText(formatted_date5);
                                                    day_6.setText(formatted_date6);

                                                    double kelvin_max1 = Double.parseDouble(max_temp1);
                                                    int celsius_max1 = (int) (kelvin_max1 - 273.15);
                                                    double kelvin_min1 = Double.parseDouble(min_temp1);
                                                    int celsius_min1 = (int) (kelvin_min1 - 273.15);

                                                    double kelvin_max2 = Double.parseDouble(max_temp2);
                                                    int celsius_max2 = (int) (kelvin_max2 - 273.15);
                                                    double kelvin_min2 = Double.parseDouble(min_temp2);
                                                    int celsius_min2 = (int) (kelvin_min2 - 273.15);

                                                    double kelvin_max3 = Double.parseDouble(max_temp3);
                                                    int celsius_max3 = (int) (kelvin_max3 - 273.15);
                                                    double kelvin_min3 = Double.parseDouble(min_temp3);
                                                    int celsius_min3 = (int) (kelvin_min3 - 273.15);

                                                    double kelvin_max4 = Double.parseDouble(max_temp4);
                                                    int celsius_max4 = (int) (kelvin_max4 - 273.15);
                                                    double kelvin_min4 = Double.parseDouble(min_temp4);
                                                    int celsius_min4 = (int) (kelvin_min4 - 273.15);

                                                    double kelvin_max5 = Double.parseDouble(max_temp5);
                                                    int celsius_max5 = (int) (kelvin_max5 - 273.15);
                                                    double kelvin_min5 = Double.parseDouble(min_temp5);
                                                    int celsius_min5 = (int) (kelvin_min5 - 273.15);

                                                    double kelvin_max6 = Double.parseDouble(max_temp6);
                                                    int celsius_max6 = (int) (kelvin_max6 - 273.15);
                                                    double kelvin_min6 = Double.parseDouble(min_temp6);
                                                    int celsius_min6 = (int) (kelvin_min6 - 273.15);

                                                    max_temp_one.setText(celsius_max1 + "°");
                                                    min_temp_one.setText("/" + celsius_min1 + "°");
                                                    max_temp_two.setText(celsius_max2 + "°");
                                                    min_temp_two.setText("/" + celsius_min2 + "°");
                                                    max_temp_three.setText(celsius_max3 + "°");
                                                    min_temp_three.setText("/" + celsius_min3 + "°");
                                                    max_temp_four.setText(celsius_max4 + "°");
                                                    min_temp_four.setText("/" + celsius_min4 + "°");
                                                    max_temp_five.setText(celsius_max5 + "°");
                                                    min_temp_five.setText("/" + celsius_min5 + "°");
                                                    max_temp_six.setText(celsius_max6 + "°");
                                                    min_temp_six.setText("/" + celsius_min6 + "°");

                                                    iconUrl1 = "https://openweathermap.org/img/wn/" + icon_code1 + "@2x.png";
                                                    iconUrl2 = "https://openweathermap.org/img/wn/" + icon_code2 + "@2x.png";
                                                    iconUrl3 = "https://openweathermap.org/img/wn/" + icon_code3 + "@2x.png";
                                                    iconUrl4 = "https://openweathermap.org/img/wn/" + icon_code4 + "@2x.png";
                                                    iconUrl5 = "https://openweathermap.org/img/wn/" + icon_code5 + "@2x.png";
                                                    iconUrl6 = "https://openweathermap.org/img/wn/" + icon_code6 + "@2x.png";
                                                    Picasso.get()
                                                            .load(iconUrl1)
                                                            .resize(150, 150)
                                                            .into(icon_one);
                                                    Picasso.get()
                                                            .load(iconUrl2)
                                                            .resize(150, 150)
                                                            .into(icon_two);
                                                    Picasso.get()
                                                            .load(iconUrl3)
                                                            .resize(150, 150)
                                                            .into(icon_three);
                                                    Picasso.get()
                                                            .load(iconUrl4)
                                                            .resize(150, 150)
                                                            .into(icon_four);
                                                    Picasso.get()
                                                            .load(iconUrl5)
                                                            .resize(150, 150)
                                                            .into(icon_five);
                                                    Picasso.get()
                                                            .load(iconUrl6)
                                                            .resize(150, 150)
                                                            .into(icon_six);

                                                    weather_summary_one.setText(description1);
                                                    weather_summary_two.setText(description2);
                                                    weather_summary_three.setText(description3);
                                                    weather_summary_four.setText(description4);
                                                    weather_summary_five.setText(description5);
                                                    weather_summary_six.setText(description6);
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Location not found", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    queue.add(jsonObjectRequest1);

                                    int visibility_int = (int)Double.parseDouble(visibility_level)/1000;

                                    iconUrl = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
                                    Picasso.get()
                                            .load(iconUrl)
                                            .resize(350, 350)
                                            .into(imageView);

                                    double kelvin = Double.parseDouble(feels_like);
                                    int celsius = (int) (kelvin - 273.15);
                                    tv_weather_description.setText(condition);
                                    temperature.setText("Feels " + celsius + "° C");
                                    city.setText(city_name);
                                    country.setText(country_code);

                                    /*Picasso.get()
                                            .load(R.drawable.ic_round_refresh_24)
                                            .placeholder(R.drawable.ic_round_refresh_24)
                                            .resize(35, 35)
                                            .into(refresh);*/

                                    Date currentTime = Calendar.getInstance().getTime();
                                    String time = DateFormat.getTimeInstance().format(currentTime);
                                    update_time.setText("Updated as of " + time);

                                    double speedDouble = Double.parseDouble(wind_speed);
                                    int speedInt = (int) (speedDouble*36/10);
                                    wind.setText("Wind  " + speedInt + " km/h");
                                    humidity.setText("Humidity  " + humidity_level + "%");
                                    visibility.setText("Visibility  " + visibility_int + " km");
                                    cloud.setText("Cloudy  " + cloudiness + "%");

                                    long riseTime = Long.parseLong(sun_rise);
                                    long time1 = riseTime * (long) 1000;
                                    Date date1 = new Date(time1);
                                    SimpleDateFormat format1 = new SimpleDateFormat("H:mm");
                                    format1.setTimeZone(TimeZone.getDefault());
                                    String formatted_sunrise_time = format1.format(date1);
                                    sunrise.setText("Sunrise  " + formatted_sunrise_time + "AM");

                                    long setTime = Long.parseLong(sun_set);
                                    long time2 = setTime * (long) 1000;
                                    Date date2 = new Date(time2);
                                    SimpleDateFormat format2 = new SimpleDateFormat("h:mm");
                                    format2.setTimeZone(TimeZone.getDefault());
                                    String formatted_sunset_time = format2.format(date2);
                                    sunset.setText("Sunset  " + formatted_sunset_time + "PM");
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Invalid city name/ID", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(jsonObjectRequest);
                }
            }
        });
    }
}