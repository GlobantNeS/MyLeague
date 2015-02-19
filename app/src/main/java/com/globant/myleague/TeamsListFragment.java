package com.globant.myleague;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class TeamsListFragment extends ListFragment {

    private static final String LOG_TAG = TeamsListFragment.class.getSimpleName();
    private static final int ADD_TEAM_REQUEST = 0;
    private TeamsAdapter mAdapter;
    MyLeagueService.ApiInterface mApiInterface;
    List<Teams> mTeams;
    
    public TeamsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
            teamsRequest();
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
            team.setUrl(teamsResponse.get(i).getUrl());
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_teams_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handle = false;

        switch (item.getItemId()) {
            case R.id.action_add_team:  Intent intent = new Intent(getActivity(), CreateTeamActivity.class);
                                        startActivityForResult(intent, ADD_TEAM_REQUEST);
                                        handle = true;

            break;

            case R.id.action_settings: handle = true;
            break;
        }

        if(!handle) {
            handle = super.onOptionsItemSelected(item);
        }
        return handle;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ADD_TEAM_REQUEST:
                Teams team;
                if(resultCode == getActivity().RESULT_OK){
                    team = (Teams)data.getExtras().getParcelable("team");
                    mAdapter.add(team);
                } else Toast.makeText(getActivity(), R.string.toast_no_teams_found, Toast.LENGTH_SHORT).show();

                break;
        }
    }
}
