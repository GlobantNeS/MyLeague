package com.globant.myleague;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.globant.myleague.pojo.Matches;

import java.util.List;

/**
 * Created by root on 16/02/15.
 */
public class MatchNewsAdapter extends ArrayAdapter<Matches> {

    List<Matches> mlistMatches;
    public MatchNewsAdapter(Context context, List<Matches> listMatches){
        super(context, R.layout.item_statistics_match,listMatches);
        mlistMatches = listMatches;

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
        viewHolder.textViewLocalTeamName.setText(mlistMatches.get(position).getNameLocal());
        viewHolder.textViewAwayTeamName.setText(mlistMatches.get(position).getNameVisit());

    }

    public class ViewHolder {
        public final TextView textViewLocalTeamName;
        public final TextView textViewAwayTeamName;


        public ViewHolder (View view) {
            textViewLocalTeamName = (TextView) view.findViewById(R.id.text_view_name_local_club);
            textViewAwayTeamName = (TextView) view.findViewById(R.id.text_view_name_away_club);
           // text_view_red_cards_for_match
            //        text_view_yellow_cards_for_match
        }
    }

}
