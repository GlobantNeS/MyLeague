package com.globant.myleague;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.globant.myleague.adapter.LinkAdapterMatch;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.services.MyLeagueService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class SelectMatchToFillFragment extends ListFragment {
    MyLeagueService.ApiInterface mMyLeagueApiInterface;
    ArrayAdapter<Matches> mAdapter;

    final static String LOG_TAG = SelectTournamentToMatchFragment.class.getSimpleName();
    final static String TOURNAMENT_ID = "TOURNAMENT_ID";

    public SelectMatchToFillFragment()
    {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MyLeagueService myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Matches> matchesList= new ArrayList<>();
        mAdapter = new LinkAdapterMatch(getActivity(),R.layout.fragment_item_view_game,matchesList);
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Matches selectedMatch = (Matches)mAdapter.getItem(position);
                String selectedTournamentId = selectedMatch.getId();
                //Intent intent = new Intent(getActivity(), MatchesListActivity.class);
                //intent.putExtra(MatchesListActivity.TOURNAMENT_ID, selectedTournamentId);
                //startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        String id=getArguments().getString(TOURNAMENT_ID);
        mMyLeagueApiInterface.getMatchesForTournament(id,new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> matcheses, Response response) {
                if(response.getStatus()==200)
                {
                    mAdapter.clear();
                    mAdapter.addAll(matcheses);
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Log.e(LOG_TAG, "Project retrieval status problem: " + response.getReason());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }
}