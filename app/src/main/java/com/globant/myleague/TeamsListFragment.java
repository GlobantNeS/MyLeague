package com.globant.myleague;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.globant.myleague.adapter.TeamsAdapter;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.services.MyLeagueService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
* Created by Juan on 16/02/2015.
*/
public class TeamsListFragment extends ListFragment {

    private static final String LOG_TAG = TeamsListFragment.class.getSimpleName();
    private TeamsAdapter mAdapter;
    MyLeagueService.ApiInterface mApiInterface;
    List<Teams> mTeams;
    
    public TeamsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teams_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Teams> teams = new ArrayList<>();
        teamsRequest();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void teamsRequest() {
        MyLeagueService myLeagueService = new MyLeagueService();
        mApiInterface = myLeagueService.generateServiceInterface();
        mTeams = new ArrayList<>();
        mApiInterface.getTeams(new Callback<List<Teams>>() {
            @Override
            public void success(List<Teams> teamsResponse, Response response) {
                if(response.getStatus() == 200) {
                    fillTeamList(teamsResponse);
                    prepareListWithAnAdapter(mTeams);
                } else Log.d(LOG_TAG, "Bad request");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "Failure request");
            }
        });
    }

    private void fillTeamList(List<Teams> teamsResponse) {
        Teams team;
        for(int i = 0; i < teamsResponse.size(); i++){
            team =new Teams();
            team.setName(teamsResponse.get(i).getName());
            team.setUrlimage(teamsResponse.get(i).getUrlimage());
            mTeams.add(team);
        }
    }

    private void prepareListWithAnAdapter(List<Teams> teams) {

        mAdapter = new TeamsAdapter(getActivity(), R.layout.fragment_item_view_teams, teams );
        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Teams teams = (Teams) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), "Name: " + teams.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
