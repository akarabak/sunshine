package com.example.android.sunshine.app;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(R.id.action_refresh == item.getItemId()){
            FetchWeatherTask refresh = new FetchWeatherTask();
            refresh.execute("91205");
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override //this runs when
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //This makes onCreateOptionsMenu from this fragment to be called by main activity, and
        //thus visible
        setHasOptionsMenu(true);
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
        download.execute("91205");



        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, Void> {
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params){

            if (params.length == 0){
                return null;
            }

            HttpURLConnection urlConnection = null;
            String forecastJson = null;


            try {
                URL url = new URL(Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily").buildUpon()
                    .appendQueryParameter("q", params[0])
                    .appendQueryParameter("appid", "c09942ecfc5be500fbbe8f1921763374")
                    .appendQueryParameter("cnt", "7")
                    .appendQueryParameter("units", "metric")
                    .appendQueryParameter("mode", "json")
                    .build().toString());

//                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043" +
//                        "&mode=json&units=metric&cnt=7&appid=c09942ecfc5be500fbbe8f1921763374");

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
            //return forecastJson;
            return null;
        }
    }
}
