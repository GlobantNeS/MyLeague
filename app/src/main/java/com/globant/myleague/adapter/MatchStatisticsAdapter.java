package com.globant.myleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.tools.Tools;

import java.util.List;

/**
 * Created by root on 16/02/15.
 */
public class MatchStatisticsAdapter extends ArrayAdapter<Matches> {

    List<Matches> mlistMatches;
    Context mContext;
    public MatchStatisticsAdapter(Context context, List<Matches> listMatches){
        super(context, R.layout.item_statistics_match,listMatches);
        mlistMatches = listMatches;
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = reuseOrgenerateView(convertView, parent);
        displayMovieInRow(position,rowView);
        return rowView;
    }

    private View reuseOrgenerateView(View convertView, ViewGroup parent){
        View rowView;
        if(convertView != null){
            rowView = convertView;
        }
        else{
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_statistics_match,parent,false);
            ViewHolder viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }

        return  rowView;

    }

    private void displayMovieInRow(int position, View rowView){
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        viewHolder.textViewMatchDateTime.setText(mlistMatches.get(position).getDateMatch());
        viewHolder.textViewLocalTeamName.setText(mlistMatches.get(position).getNameLocal());
        viewHolder.textViewScoreLocal.setText(mlistMatches.get(position).getLocalScore());
        viewHolder.textViewYellowCardslocal.setText(mlistMatches.get(position).getLocalFaults());
        viewHolder.textViewRedCardsLocal.setText(mlistMatches.get(position).getLocalExp());

        viewHolder.textViewAwayTeamName.setText(mlistMatches.get(position).getNameVisit());
        viewHolder.textViewScoreAway.setText(mlistMatches.get(position).getVisitScore());
        viewHolder.textViewYellowCardsVisit.setText(mlistMatches.get(position).getVisitFaults());
        viewHolder.textViewRedCardsVisit.setText(mlistMatches.get(position).getVisitExp());



        Tools.loadImageFromInternet(mContext,viewHolder.imageViewLocal,mlistMatches.get(position).getUrlImgLocal());
        Tools.loadImageFromInternet(mContext,viewHolder.imageViewAway,mlistMatches.get(position).getUrlImgVisit());




    }

    public static class ViewHolder {
        public final TextView textViewLocalTeamName;
        public final TextView textViewAwayTeamName;
        public final TextView textViewRedCardsLocal;
        public final TextView textViewYellowCardslocal;
        public final TextView textViewRedCardsVisit;
        public final TextView textViewYellowCardsVisit;
        public final TextView textViewScoreLocal;
        public final TextView textViewScoreAway;
        public final TextView textViewMatchDateTime;
        public final NetworkImageView imageViewLocal;
        public final NetworkImageView imageViewAway;


        public ViewHolder (View view) {
            textViewLocalTeamName = (TextView) view.findViewById(R.id.text_view_name_local_ppal);
            textViewAwayTeamName = (TextView) view.findViewById(R.id.text_view_name_away_ppal);
            textViewRedCardsLocal = (TextView)view.findViewById(R.id.text_view_red_cards_local_ppal);
            textViewYellowCardslocal = (TextView)view.findViewById(R.id.text_view_yellow_cards_local_ppal);
            textViewRedCardsVisit = (TextView)view.findViewById(R.id.text_view_red_cards_visit_ppal);
            textViewYellowCardsVisit = (TextView)view.findViewById(R.id.text_view_yellow_cards_visit_ppal);
            textViewScoreLocal = (TextView)view.findViewById(R.id.text_view_score_local_ppal);
            textViewScoreAway = (TextView)view.findViewById(R.id.text_view_score_away_ppal);
            textViewMatchDateTime = (TextView)view.findViewById(R.id.text_view_match_date_ppal);
            imageViewLocal = (NetworkImageView)view.findViewById(R.id.image_view_local_ppal);
            imageViewAway = (NetworkImageView)view.findViewById(R.id.image_view_away_ppal);

        }
    }

}
