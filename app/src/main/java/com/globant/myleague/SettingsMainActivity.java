package com.globant.myleague;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by KaineraS on 16/02/15.
 */
public class SettingsMainActivity extends Activity{

    public static class AppSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main_settings);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);
        getFragmentManager().beginTransaction().
                add(R.id.frame_main_settings, new AppSettingsFragment()).
                commit();
    }
}
