package com.globant.myleague;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by root on 16/02/15.
 */
public class SettingsPrincipalActivity extends Activity{

    public static class AppSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.principal_settings);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_principal_settings);
        getFragmentManager().beginTransaction().
                add(R.id.frame_principal_settings, new AppSettingsFragment()).
                commit();
    }
}
