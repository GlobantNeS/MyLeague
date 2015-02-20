package com.globant.myleague;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.tools.Tools;

public class MatchResultFragment extends Fragment {

    private static final String LOG_TAG = MatchResultFragment.class.getSimpleName();
    private TextView textViewMatchDate;
    private TextView textViewLocalName;
    private TextView textViewVisitantName;
    private TextView textViewLocalRedCards;
    private TextView textViewLocalYellowCards;
    private TextView textViewLocalScore;
    private TextView textViewVisitantRedCards;
    private TextView textViewVisitantYellowCards;
    private TextView textViewVisitantScore;
    private NetworkImageView imageViewLocalLogo;
    private NetworkImageView imageViewVisitantLogo;
    private Matches mMatch;

    public MatchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_result, container, false);

        if(getArguments()!= null) {
            mMatch = getArguments().getParcelable(PrincipalNewsFragment.KEY_MATCH);
            prepareView(rootView);
            Log.d(LOG_TAG, "Nombre Local:" + mMatch.getNameLocal());
            textViewMatchDate.setText(mMatch.getDateMatch());
            textViewLocalName.setText(mMatch.getNameLocal());
            textViewLocalYellowCards.setText(mMatch.getLocalFaults());
            textViewLocalRedCards.setText(mMatch.getLocalExp());
            textViewLocalScore.setText(mMatch.getLocalScore());
            textViewVisitantName.setText(mMatch.getNameVisit());
            textViewVisitantYellowCards.setText(mMatch.getVisitFaults());
            textViewVisitantRedCards.setText(mMatch.getVisitExp());
            textViewVisitantScore.setText(mMatch.getVisitScore());
            Tools.loadImageFromInternet(getActivity(), imageViewLocalLogo, mMatch.getUrlImgLocal());
            Tools.loadImageFromInternet(getActivity(),imageViewVisitantLogo, mMatch.getUrlImgVisit());

        }


        return rootView;
    }

    private void prepareView(View rootView) {
        textViewMatchDate = (TextView) rootView.findViewById(R.id.text_view_match_date);
        textViewLocalName = (TextView) rootView.findViewById(R.id.text_view_local_name);
        textViewVisitantName = (TextView) rootView.findViewById(R.id.text_view_visitant_name);
        textViewLocalRedCards = (TextView) rootView.findViewById(R.id.text_view_local_red_cards);
        textViewLocalYellowCards = (TextView) rootView.findViewById(R.id.text_view_local_yellow_cards);
        textViewLocalScore = (TextView) rootView.findViewById(R.id.text_view_score_local_ppal);
        textViewVisitantRedCards = (TextView) rootView.findViewById(R.id.text_view_visitant_red_cards);
        textViewVisitantYellowCards = (TextView) rootView.findViewById(R.id.text_view_visitant_yellow_cards);
        textViewVisitantScore = (TextView) rootView.findViewById(R.id.text_view_score_away_ppal);
        imageViewLocalLogo = (NetworkImageView) rootView.findViewById(R.id.image_view_local);
        imageViewVisitantLogo = (NetworkImageView) rootView.findViewById(R.id.image_view_visitant);
    }


}
