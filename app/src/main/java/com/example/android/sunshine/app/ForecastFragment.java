package com.example.android.sunshine.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dexel on 10/13/2016.
 */


public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> listAdapter;

    public ForecastFragment() {
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_refresh){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> forecast = new ArrayList<>(Arrays.asList("Monday",
                "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));

        listAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, forecast);

        ListView weatherList = (ListView) rootView.findViewById(R.id.listview_forecast);

        weatherList.setAdapter(listAdapter);

        FetchWeatherTask download = new FetchWeatherTask();
        download.execute();

        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<URL, Integer, String>{
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected String doInBackground(URL[] urls){
            HttpURLConnection urlConnection = null;
            String forecastJson = null;


            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043" +
                        "&mode=json&units=metric&cnt=7&appid=c09942ecfc5be500fbbe8f1921763374");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                if (stream == null){
                    return null;
                }
                forecastJson = stream.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            }
            finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return forecastJson;
        }
    }
}
