package com.globant.myleague;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.globant.myleague.adapter.TeamsSelectionAdapter;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.TeamsInTournaments;
import com.globant.myleague.services.MyLeagueService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CheckingTeamsListFragment extends ListFragment {

    private static final String LOG_TAG = CheckingTeamsListFragment.class.getSimpleName();
    private TeamsSelectionAdapter mAdapter;
    private MyLeagueService.ApiInterface mApiInterface;
    public List<Teams> mTeams;
    public Button buttonAddTemsTooutnament;

    AbsListView.MultiChoiceModeListener mMultiChoiceModeListener;;

    public ActionMode mActionMode;


    public CheckingTeamsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list_teams, container, false);
        buttonAddTemsTooutnament = (Button) rootView.findViewById(R.id.button_save_teams_tournament);
        buttonAddTemsTooutnament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List <Teams>teams = getCheckedItems();
                Log.d(LOG_TAG, "Al menos este: " + teams.size());
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        teamsRequest();
    }

    public List<Teams> getCheckedItems(){
        SparseBooleanArray checked = getListView().getCheckedItemPositions();
        Teams team;

        List<Teams> teams = mAdapter.mTeams;
        List<Teams> teamsToReturn = new ArrayList<>();
        for(int i=0;i<teams.size();i++){
            team  = teams.get(i);
            if(team.isSelected()){
                teamsToReturn.add(team);
            }
        }

        return teamsToReturn;
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

        mAdapter = new TeamsSelectionAdapter(getActivity(), R.layout.fragment_cheked_teams_entry, teams );
        setListAdapter(mAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        getListView().setMultiChoiceModeListener(mMultiChoiceModeListener);
        mAdapter.notifyDataSetChanged();
    }

    public void obtainTeamsInTournamentByIdRequest() {
        MyLeagueService myLeagueService = new MyLeagueService();

        mApiInterface = myLeagueService.generateServiceInterface();
        String id = getArguments().getString(TournamentsListFragment.TOURNAMENT_ID);
        mApiInterface.getTeamsInTournament(id, new Callback<List<TeamsInTournaments>>() {
            @Override
            public void success(List<TeamsInTournaments> teamsInTournamentses, Response response) {
                for (TeamsInTournaments t : teamsInTournamentses) {

                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG, "Que hace esto:" + v.getTag());

    }


}
