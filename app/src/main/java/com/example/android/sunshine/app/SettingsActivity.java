package com.example.android.sunshine.app;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * Created by dexel on 10/30/2016.
 */

public class SettingsActivity extends PreferenceActivity
    implements Preference.OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object value){
        String settingName = value.toString();

        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(settingName);
            if (index >= 0){
                preference.setSummary("summary");
            }
        }
        else{
            preference.setSummary(settingName);
        }

        return true;
    }

}
