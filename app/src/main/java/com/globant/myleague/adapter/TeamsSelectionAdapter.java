package com.globant.myleague.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.tools.Tools;

import java.util.List;

public class TeamsSelectionAdapter extends ArrayAdapter<Teams> {

    private static final String LOG_TAG = TeamsSelectionAdapter.class.getSimpleName();
    Context mContext;
    public List<Teams> mTeams;
    ActionMode mActionMode;
    public TeamsSelectionAdapter(Context context, int resource, List<Teams> teams) {
        super(context, resource, teams);
        mContext = context;
        mTeams = teams;
    }

    public interface testActivityCall {
        public void CreateContextualActionBar();
    }

    private class ViewHolder {
        TextView mName;
        NetworkImageView imageView;
        CheckBox mChecked;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        ViewHolder holder = null;
        if(convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.fragment_cheked_teams_entry, parent, false);


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
            Log.d(LOG_TAG, "URL view: " + mTeams.get(position).getUrlimage());
            holder.mName.setText(mTeams.get(position).getName());
            Tools.loadImageFromInternet(mContext,holder.imageView, mTeams.get(position).getUrlimage());
            holder.mChecked.setChecked(false);
            holder.mChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton view, boolean isChecked) {

                        mTeams.get(position).setSelected(isChecked);

                }
            });
        }

        return rowView;
    }






}
