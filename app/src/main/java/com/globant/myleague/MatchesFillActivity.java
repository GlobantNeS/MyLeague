package com.globant.myleague;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class MatchesFillActivity extends ActionBarActivity {

    final static String TOURNAMENT_ID = "TOURNAMENT_ID";
    final static String LOCAL_ID = "LOCAL_ID";
    final static String VISIT_ID = "VISIT_ID";
    final static String LOCAL_NAME = "LOCAL_NAME";
    final static String VISIT_NAME = "VISIT_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_fill);
        if (savedInstanceState == null) {
            MatchFillFragment matchFillFragment = new MatchFillFragment();
            String tournamentId = getIntent().getStringExtra(TOURNAMENT_ID);
            String localId = getIntent().getStringExtra(LOCAL_ID);
            String visitId = getIntent().getStringExtra(VISIT_ID);
            String localName = getIntent().getStringExtra(LOCAL_NAME);
            String visitName = getIntent().getStringExtra(VISIT_NAME);

            Bundle bundle = new Bundle();
            bundle.putString(TOURNAMENT_ID, tournamentId);
            bundle.putString(LOCAL_ID, localId);
            bundle.putString(VISIT_ID, visitId);
            bundle.putString(LOCAL_NAME, localName);
            bundle.putString(VISIT_NAME, visitName);
            matchFillFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, matchFillFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
