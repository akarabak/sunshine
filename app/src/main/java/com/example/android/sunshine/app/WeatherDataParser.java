package com.example.android.sunshine.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dexel on 10/20/2016.
 */

public class WeatherDataParser {
    public WeatherDataParser(){

    }
    private final String LOG_TAG = WeatherDataParser.class.getSimpleName();

    public static double getMaxTemperatureForDay(String weatherJson, int dayIndex) throws JSONException {
        return new JSONObject(weatherJson).getJSONArray("list").getJSONObject(dayIndex).getJSONObject("temp").getDouble("max");
    }



}
