package com.globant.myleague;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globant.myleague.pojo.Matches;

public class MatchResultFragment extends Fragment {

    private TextView textViewLocalName;
    private TextView textViewVisitantName;
    private TextView textViewLocalRedCards;
    private TextView textViewLocalYellowCards;
    private TextView textViewVisitantRedCards;
    private TextView textViewVisitantYellowCards;
    private ImageView imageViewLocalLogo;
    private ImageView imageViewVisitantLogo;
    private Matches match;

    public MatchResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_match_result, container, false);

        if(getArguments()!= null){
            match = getArguments().getParcelable(PrincipalNewsFragment.KEY_MATCH);
            prepareView(rootView);
            textViewLocalName.setText(match.getNameLocal());
            textViewLocalYellowCards.setText(match.getLocalFaults());
            textViewLocalRedCards.setText(match.getLocalExp());
            textViewVisitantName.setText(match.getNameVisit());
            textViewVisitantYellowCards.setText(match.getVisitFaults());
            textViewVisitantRedCards.setText(match.getVisitExp());

        }


        return rootView;
    }

    private void prepareView(View rootView) {
        textViewLocalName = (TextView) rootView.findViewById(R.id.text_view_local_name);
        textViewVisitantName = (TextView) rootView.findViewById(R.id.text_view_visitant_name);
        textViewLocalRedCards = (TextView) rootView.findViewById(R.id.text_view_local_red_cards);
        textViewLocalYellowCards = (TextView) rootView.findViewById(R.id.text_view_local_yellow_cards);
        textViewVisitantRedCards = (TextView) rootView.findViewById(R.id.text_view_visitant_red_cards);
        textViewVisitantYellowCards = (TextView) rootView.findViewById(R.id.text_view_visitant_yellow_cards);
        imageViewLocalLogo = (ImageView) rootView.findViewById(R.id.image_view_local);
        imageViewVisitantLogo = (ImageView) rootView.findViewById(R.id.image_view_visitant);
    }


}
