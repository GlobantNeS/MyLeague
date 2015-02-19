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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.adapter.LinkAdapterTeam;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.TeamsInTournaments;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String,String> settings;
    Tools tools = new Tools();
    String idTeam;
    String idTournament;

    private View v;
    private Button btAddMyTeam;
    private MyLeagueService.ApiInterface mMyLeagueApiInterface;
    List<Teams> teamsList = new ArrayList<>();
    private ListView listView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadSettings();
        loadIdTournament();
        v=inflater.inflate(R.layout.fragment_list_view_tournament, container, false);
        prepareButton();
        initializeService();
        fillListView();
        return v;
    }

    private void loadIdTournament() {
        idTournament=getArguments().getString(TOURNAMENT_ID);
    }

    private void fillListView() {
        mAdapter = new LinkAdapterTeam(getActivity(), R.layout.fragment_item_view_teams_in_tournament,teamsList);
        String id=getArguments().getString(TOURNAMENT_ID);
        mMyLeagueApiInterface.getAllTeamsInTournaments(new Callback<List<TeamsInTournaments>>() {
            @Override
            public void success(List<TeamsInTournaments> teamsInTournamentses, Response response) {
                mAdapter.clear();
                if(response.getStatus()==200) {
                    for (TeamsInTournaments t : teamsInTournamentses) {
                        if(t.getIdTournament().equals(idTournament))
                            loadDataTeam(t.getIdTeam());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        listView = (ListView)v.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);
    }

    private void initializeService() {
        MyLeagueService myleagueService= new MyLeagueService();
        mMyLeagueApiInterface = myleagueService.generateServiceInterface();
    }

    private void loadSettings() {
        settings=tools.getPreferences(getActivity());
        idTeam=settings.get("id");
    }

    private void prepareButton() {
        btAddMyTeam = (Button)v.findViewById(R.id.btAddMyTeam);
        btAddMyTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMyTeamToTournament(idTeam, idTournament);
            }
        });
    }

    private void loadDataTeam(String id) {
        MyLeagueService myLeagueService;
        myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
        mMyLeagueApiInterface.getTeam(id,new Callback<Teams>() {
            @Override
            public void success(Teams teams, Response response) {
                if(teams!=null) {
                    if(teams.getId().equals(idTeam))
                        btAddMyTeam.setEnabled(false);
                    teamsList.add(teams);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }

    private void addMyTeamToTournament(String id,String idTour) {
        MyLeagueService myLeagueService;
        myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
        TeamsInTournaments teamsInTournaments = new TeamsInTournaments();
        teamsInTournaments.setIdTeam(id);
        teamsInTournaments.setIdTournament(idTour);
        mMyLeagueApiInterface.addTeamToTournament(teamsInTournaments,new Callback<TeamsInTournaments>() {
            @Override
            public void success(TeamsInTournaments teamsInTournaments, Response response) {
                if(response.getStatus()==201) {
                    Toast.makeText(getActivity(), getString(R.string.text_sucessfull_add), Toast.LENGTH_LONG).show();
                    tools.loadFragment(getFragmentManager(),new PrincipalNewsFragment(), R.id.rightpane,"NEWS");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }
}
