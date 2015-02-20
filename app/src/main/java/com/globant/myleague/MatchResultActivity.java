package com.globant.myleague;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class MatchResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        if (savedInstanceState == null) {
            if (getIntent().getExtras() != null) {
                MatchResultFragment matchResultFragment = new MatchResultFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(PrincipalNewsFragment.KEY_MATCH,
                        getIntent().getExtras().getParcelable(PrincipalNewsFragment.KEY_MATCH));
                matchResultFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, matchResultFragment)
                        .commit();
            } else {
                Toast.makeText(getApplicationContext(), R.string.toast_no_match, Toast.LENGTH_SHORT);

            }
        }
    }

}
