package com.globant.myleague;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.globant.myleague.adapter.TeamsSelectionAdapter;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.TeamsInTournaments;
import com.globant.myleague.services.MyLeagueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CheckingTeamsListFragment extends ListFragment {

    private static final String LOG_TAG = CheckingTeamsListFragment.class.getSimpleName();
    private TeamsSelectionAdapter mAdapter;
    private MyLeagueService.ApiInterface mApiInterface;
    public List<Teams> mTeams;
    public Button buttonAddTeamsTournament;
    public static List<TeamsInTournaments> mTeamsInTournaments;
    public Set<String> teamIds;
    public Set<String> tournamentIds;

    AbsListView.MultiChoiceModeListener mMultiChoiceModeListener;;

    public ActionMode mActionMode;


    public CheckingTeamsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list_teams, container, false);
        buttonAddTeamsTournament = (Button) rootView.findViewById(R.id.button_save_teams_tournament);
        buttonAddTeamsTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Teams> teams = getCheckedItems();
                if (teams.size() > 0) {
                    Log.d(LOG_TAG, "Al menos este: " + teams.size());
                } else
                    Toast.makeText(getActivity(), "Select at least one team", Toast.LENGTH_SHORT).show();
            }
        });
        buttonAddTeamsTournament.setEnabled(false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        obtainTeamsInTournamentByIdRequest();
    }

    private void removeTeamsInTournament(List<Teams> responseTeams) {
        preparingTeamIdsInTournament();
        for(int index = 0 ; index < responseTeams.size(); index++) {
            if(!tournamentIds.contains(responseTeams.get(index).getId())){
                mTeams.add(responseTeams.get(index));
            }
        }
    }

    private void preparingTeamIdsInTournament() {
        tournamentIds = new TreeSet<>();
        for(int i = 0; i < mTeamsInTournaments.size(); i++) {
            tournamentIds.add(mTeamsInTournaments.get(i).getIdTeam());
        }
    }


    private void teamsRequest() {
        MyLeagueService myLeagueService = new MyLeagueService();
        mApiInterface = myLeagueService.generateServiceInterface();
        mTeams = new ArrayList<>();
        mApiInterface.getTeams(new Callback<List<Teams>>() {
            @Override
            public void success(List<Teams> teamsResponse, Response response) {
                if(response.getStatus() == 200) {
                    removeTeamsInTournament(teamsResponse);
                    prepareListWithAnAdapter(mTeams);
                } else Log.d(LOG_TAG, "Bad request");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "Failure request");
            }
        });
    }


    private void prepareListWithAnAdapter(List<Teams> teams) {
        mAdapter = new TeamsSelectionAdapter(getActivity(), R.layout.fragment_cheked_teams_entry, teams );
        setListAdapter(mAdapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        getListView().setMultiChoiceModeListener(mMultiChoiceModeListener);
        mAdapter.notifyDataSetChanged();
    }

    public List<Teams> getCheckedItems(){
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

    public void obtainTeamsInTournamentByIdRequest() {
        MyLeagueService myLeagueService = new MyLeagueService();
        mApiInterface = myLeagueService.generateServiceInterface();
        String id = getArguments().getString(TournamentsListFragment.TOURNAMENT_ID);
        mApiInterface.getTeamsInTournament(id, new Callback<List<TeamsInTournaments>>() {
            @Override
            public void success(List<TeamsInTournaments> teamsInTournaments, Response response) {
                if(response.getStatus() == 200) {
                    Log.d(LOG_TAG, "goodRequest");
                    if (teamsInTournaments != null) {
                        Log.d(LOG_TAG, "teams in: " + teamsInTournaments.size());
                        mTeamsInTournaments = teamsInTournaments;
                    } else  Log.d(LOG_TAG, "Empty ids");
                } else Log.d(LOG_TAG, "Bad Request");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "Failure request");
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(LOG_TAG, "Que hace esto:" + v.getTag());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu__sync_check_teams, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handle = false;

        switch (item.getItemId()) {
            case R.id.menu_sync_teams:
                teamsRequest();
                buttonAddTeamsTournament.setEnabled(true);
                handle = true;
            break;
        }

        if(!handle) {
            handle = super.onOptionsItemSelected(item);
        }

        return handle;
    }
}
