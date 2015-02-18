package com.globant.myleague;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;


public class TeamsListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_list);
        if (savedInstanceState == null) {
            if(getIntent().getExtras() == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new TeamsListFragment())
                        .commit();
            } else {
                CheckingTeamsListFragment checkingTeamsListFragment = new CheckingTeamsListFragment();
                Bundle bundle = new Bundle();
                String tournamentId = getIntent().getExtras().getString(TournamentsListFragment.TOURNAMENT_ID);
                bundle.putString(TournamentsListFragment.TOURNAMENT_ID,tournamentId);
                checkingTeamsListFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, checkingTeamsListFragment).commit();


            }
        }
    }

}
