package com.globant.myleague;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.adapter.LinkAdapterTeam;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.TeamsInTournaments;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created the first version by kaineras on 17/02/15.
 */
public class AddMyTeamToTournamentFragment extends Fragment{

    final static String LOG_TAG = AddMyTeamToTournamentFragment.class.getSimpleName();
    final static String TOURNAMENT_ID = "TOURNAMENT_ID";
    ArrayAdapter<Teams> mAdapter;

    private View v;
    private Button btAddMyTeam;
    private MyLeagueService.ApiInterface mMyLeagueApiInterface;
    List<Teams> teamsList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_list_view_tournament, container, false);
        btAddMyTeam = (Button)v.findViewById(R.id.btAddMyTeam);
        MyLeagueService myleagueService= new MyLeagueService();
        mMyLeagueApiInterface = myleagueService.generateServiceInterface();
        mAdapter = new LinkAdapterTeam(getActivity(),R.layout.fragment_item_view_tournament,teamsList);
        String id=getArguments().getString(TOURNAMENT_ID);
        mMyLeagueApiInterface.getTeamsInTournament(id,new Callback<List<TeamsInTournaments>>() {
            @Override
            public void success(List<TeamsInTournaments> teamsInTournamentses, Response response) {
                for(TeamsInTournaments t:teamsInTournamentses)
                    loadDataTeam(t.getIdTeam());
                mAdapter.clear();
                mAdapter.addAll(teamsList);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return v;
    }

    private void loadDataTeam(String id) {
        MyLeagueService myLeagueService;
        myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
        mMyLeagueApiInterface.getTeam(id,new Callback<Teams>() {
            @Override
            public void success(Teams teams, Response response) {
                if(teams!=null) {
                    teamsList.add(teams);
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }
}
