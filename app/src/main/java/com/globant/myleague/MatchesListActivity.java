package com.globant.myleague;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class MatchesListActivity extends ActionBarActivity {

    final static String TOURNAMENT_ID = "TOURNAMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        if (savedInstanceState == null) {
            SelectMatchToFillFragment selectMatchToFill = new SelectMatchToFillFragment();
            String tournamentId = getIntent().getStringExtra(TOURNAMENT_ID);
            Bundle bundle = new Bundle();
            bundle.putString(TOURNAMENT_ID, tournamentId);
            selectMatchToFill.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, selectMatchToFill)
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
