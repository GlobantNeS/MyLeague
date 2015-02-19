package com.globant.myleague;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MatchResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MatchResultFragment())
                    .commit();
        }
    }

}
