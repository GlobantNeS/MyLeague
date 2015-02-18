package com.globant.myleague.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.tools.Tools;

import java.util.List;

public class TeamsSelectionAdapter extends ArrayAdapter<Teams> {

    private static final String LOG_TAG = TeamsSelectionAdapter.class.getSimpleName();
    Context mContext;
    List<Teams> mTeams;
    public TeamsSelectionAdapter(Context context, int resource, List<Teams> teams) {
        super(context, resource, teams);
        mContext = context;
        mTeams = teams;
    }

    private class ViewHolder {
        TextView mName;
        NetworkImageView imageView;
        CheckBox mChecked;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        ViewHolder holder = null;
        if(convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fragment_tournament_list, parent, false);


        }

        if(rowView != null) {
            holder = new ViewHolder();
            holder.mName = (TextView) rowView.findViewById(R.id.text_view_team_name);
            holder.mChecked = (CheckBox) rowView.findViewById(R.id.checkbox_team);
            holder.imageView = (NetworkImageView) rowView.findViewById(R.id.network_image_view_team);
            rowView.setTag(holder);
//            TextView editTextName = (TextView) rowView.findViewById(R.id.text_view_team_name);
//            NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.network_image_view_team);
//            CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkbox_team);
            Log.d(LOG_TAG, "URL view: " + mTeams.get(position).getUrl());
            holder.mChecked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "Cheking");
                }
            });
            holder.mName.setText(mTeams.get(position).getName());
            Tools.loadImageFromInternet(mContext, holder.imageView, mTeams.get(position).getUrl());

        }

        return rowView;
    }
}
