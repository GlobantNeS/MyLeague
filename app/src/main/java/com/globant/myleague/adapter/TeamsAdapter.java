package com.globant.myleague.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.tools.Tools;

import java.util.List;

/**
 * Created by Juan on 16/02/2015.
 */
public class TeamsAdapter extends ArrayAdapter<Teams> {

    private static final String LOG_TAG = TeamsAdapter.class.getSimpleName();
    private Context mContext;
    private List<Teams> mTeams;

    public TeamsAdapter(Context context, int resource, List<Teams> teams) {
        super(context, resource, teams);
        mContext = context;
        mTeams = teams;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fragment_item_view_teams, parent, false);
        }

        if(rowView != null ) {
            TextView editTextName = (TextView) rowView.findViewById(R.id.text_view_team_name);
            NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.network_image_view_team);

            Log.d(LOG_TAG, "URL view: " + mTeams.get(position).getUrlimage());

            editTextName.setText(mTeams.get(position).getName());
            Tools.loadImageFromInternet(mContext, imageView, mTeams.get(position).getUrlimage());


        }

        return rowView;
    }
}
