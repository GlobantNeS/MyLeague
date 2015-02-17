package com.globant.myleague;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.globant.myleague.pojo.Matches;
import com.globant.myleague.services.MyLeagueService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created the first version by kaineras on 17/02/15.
 */
public class MatchFillFragment extends Fragment {

    private View v;
    private String idTournament;
    private String idLocal;
    private String idVisit;
    private String localName;
    private String visitName;

    final static String TOURNAMENT_ID = "TOURNAMENT_ID";
    final static String LOCAL_ID = "LOCAL_ID";
    final static String VISIT_ID = "VISIT_ID";
    final static String LOCAL_NAME = "LOCAL_NAME";
    final static String VISIT_NAME = "VISIT_NAME";

    private EditText etLocalGoals;
    private EditText etLocalFaults;
    private EditText etLocalExp;
    private EditText etVisitGoals;
    private EditText etVisitFaults;
    private EditText etVisitExp;

    private Button btSaveGame;

    private Matches matches;

    MyLeagueService.ApiInterface mMyLeagueApiInterface;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_stadistics_game, container, false);
        TextView tvLocalName = (TextView)v.findViewById(R.id.tvLocalName);
        TextView tvVisitName = (TextView)v.findViewById(R.id.tvVisitName);


        idTournament=getArguments().getString(TOURNAMENT_ID);
        idLocal=getArguments().getString(LOCAL_ID);
        idVisit=getArguments().getString(VISIT_ID);

        localName=getArguments().getString(LOCAL_NAME);
        visitName=getArguments().getString(VISIT_NAME);


        tvLocalName.setText(localName);
        tvVisitName.setText(visitName);
        prepareText();
        prepareButton();
        return v;
    }

    private void prepareButton() {
        btSaveGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLeagueService myleagueService= new MyLeagueService();
                mMyLeagueApiInterface = myleagueService.generateServiceInterface();
                matches = new Matches();
                setMatches();
                mMyLeagueApiInterface.updateMatch(matches,new Callback<Matches>() {
                    @Override
                    public void success(Matches matches, Response response) {
                        //response.getStatus();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            private void setMatches() {
                matches.setId(idTournament);
                matches.setIdLocal(idLocal);
                matches.setIdVisit(idVisit);
                matches.setNameLocal(localName);
                matches.setNameVisit(visitName);
                matches.setLocalExp(etLocalExp.getText().toString());
                matches.setLocalFaults(etLocalFaults.getText().toString());
                matches.setLocalScore(etLocalGoals.getText().toString());
                matches.setVisitExp(etVisitExp.getText().toString());
                matches.setVisitFaults(etVisitFaults.getText().toString());
                matches.setVisitScore(etVisitGoals.getText().toString());
            }
        });
    }

    private void prepareText() {
        prepareComponents();
        TextWatcher MyListener = new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( !etLocalGoals.getText().toString().isEmpty() &&
                    !etLocalFaults.getText().toString().isEmpty() &&
                    !etLocalExp.getText().toString().isEmpty() &&
                    !etVisitGoals.getText().toString().isEmpty() &&
                    !etVisitFaults.getText().toString().isEmpty() &&
                    !etVisitExp.getText().toString().isEmpty())
                    btSaveGame.setEnabled(true);
                else
                    btSaveGame.setEnabled(false);
            }
        };
        etLocalGoals.addTextChangedListener(MyListener);
        etLocalFaults.addTextChangedListener(MyListener);
        etLocalExp.addTextChangedListener(MyListener);
        etVisitGoals.addTextChangedListener(MyListener);
        etVisitFaults.addTextChangedListener(MyListener);
        etVisitExp.addTextChangedListener(MyListener);
    }

    private void prepareComponents() {
        btSaveGame = (Button)v.findViewById(R.id.btSaveGame);
        etLocalGoals = (EditText)v.findViewById(R.id.etLocalGoals);
        etLocalFaults= (EditText)v.findViewById(R.id.etLocalFaults);
        etLocalExp= (EditText)v.findViewById(R.id.etLocalExp);
        etVisitGoals= (EditText)v.findViewById(R.id.etVisitsGoals);
        etVisitFaults= (EditText)v.findViewById(R.id.etVisitFaults);
        etVisitExp= (EditText)v.findViewById(R.id.etVisitExp);

    }
}
