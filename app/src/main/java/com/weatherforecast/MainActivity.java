package com.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText cityName;
    private Button getForecast;
    private GridView forecastGrid;
    private TextView processText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = findViewById(R.id.city_name);
        getForecast = findViewById(R.id.btn_forecast);
        forecastGrid = findViewById(R.id.forecast_result);
        processText = findViewById(R.id.process_text);

        getForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = cityName.getText().toString().trim();
                if(city.equals("")){
                    Toast.makeText(MainActivity.this, R.string.empty_city,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String key = "your_api_key_to_weather_api";
                    String pattern = "https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=7";
                    String api = String.format(pattern, key, city);
                    new ApiDataParser().execute(api);
                }
            }
        });
    }

    private void setForecastDays(ArrayList<DayInfo> days){
        processText.setText("");
        DayGVAdapter adapter = new DayGVAdapter(this, days);
        forecastGrid.setAdapter(adapter);
    }

    @SuppressLint("StaticFieldLeak")
    private class ApiDataParser extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            processText.setText(R.string.load_message);
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append('\n');
                }
                return buffer.toString();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                if(connection != null){
                    connection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if (result == null) {
                processText.setText(R.string.error_message);
            }
            else {
                try {
                    JSONObject json = new JSONObject(result);
                    ArrayList<DayInfo> days = new ArrayList<DayInfo>();
                    JSONArray forecast = json.getJSONObject("forecast")
                            .getJSONArray("forecastday");

                    for (int i=0; i<forecast.length(); i++){
                        DayInfo dayInfo = getDayData(forecast.getJSONObject(i));
                        if(dayInfo != null){
                            days.add(dayInfo);
                        }
                    }

                    setForecastDays(days);
                }
                catch (JSONException ex){
                    ex.printStackTrace();
                }
            }
        }

        private DayInfo getDayData(JSONObject json){
            try {
                String date = json.getString("date");
                JSONObject day = json.getJSONObject("day");
                double maxTemp = day.getDouble("maxtemp_c");
                double minTemp = day.getDouble("mintemp_c");
                double avgTemp = day.getDouble("avgtemp_c");
                String condition = day.getJSONObject("condition")
                        .getString("text");
                return new DayInfo(date, maxTemp, minTemp, avgTemp, condition);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}