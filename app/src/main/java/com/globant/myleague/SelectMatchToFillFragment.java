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
import com.globant.myleague.tools.Tools;

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
    final static String ID_DB = "ID_DB";
    final static String TOURNAMENT_ID = "TOURNAMENT_ID";
    final static String LOCAL_ID = "LOCAL_ID";
    final static String VISIT_ID = "VISIT_ID";
    final static String LOCAL_NAME = "LOCAL_NAME";
    final static String VISIT_NAME = "VISIT_NAME";

    Tools tools = new Tools();

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
                Matches selectedMatch = mAdapter.getItem(position);
                String selectedTournamentId = selectedMatch.getId();
                String IdLocal = selectedMatch.getIdLocal();
                String IdVisit = selectedMatch.getIdVisit();
                String nameLocal = selectedMatch.getNameLocal();
                String nameVisit = selectedMatch.getNameVisit();
                String idDB = selectedMatch.getIdDB();

                Bundle bundle = new Bundle();
                bundle.putString(TOURNAMENT_ID, selectedTournamentId);
                bundle.putString(LOCAL_ID, IdLocal);
                bundle.putString(VISIT_ID, IdVisit);
                bundle.putString(LOCAL_NAME, nameLocal);
                bundle.putString(VISIT_NAME, nameVisit);
                bundle.putString(ID_DB, idDB);
                MatchFillFragment matchFillFragment = new MatchFillFragment();
                matchFillFragment.setArguments(bundle);
                tools.loadFragment(getFragmentManager(),matchFillFragment,R.id.rightpane,"YES");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        final String id=getArguments().getString(TOURNAMENT_ID);
        mMyLeagueApiInterface.getMatches(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> matcheses, Response response) {
                List<Matches> matches = new ArrayList<Matches>();
                for(Matches matchesTemp:matcheses)
                    if(matchesTemp.getId().equals(id))
                        matches.add(matchesTemp);
                if(response.getStatus()==200)
                {
                    mAdapter.clear();
                    mAdapter.addAll(matches);
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