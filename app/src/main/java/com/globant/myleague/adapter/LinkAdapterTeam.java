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
import com.globant.myleague.SelectTournamentToMatchFragment;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class LinkAdapterTeam extends ArrayAdapter<Teams>{

    private List<Teams> teamsList;
    private Context ctx;
    final static String LOG_TAG = SelectTournamentToMatchFragment.class.getSimpleName();

    public LinkAdapterTeam(Context context, int resource, List<Teams> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.teamsList = objects;
    }

    @Override
    public int getCount() {
        return teamsList.size();
    }

    @Override
    public Teams getItem(int position) {
        return teamsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return teamsList.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if ( v == null)
        {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.fragment_item_view_teams_in_tournament, null);
        }
        final NetworkImageView nivTeam = (NetworkImageView)v.findViewById(R.id.nivTeam);
        final TextView tvTeamName = (TextView)v.findViewById(R.id.tvTeamName);


        String name= teamsList.get(position).getName();
        String url = teamsList.get(position).getUrl();


        tvTeamName.setText(name);
        Tools.loadImageFromInternet(ctx,nivTeam, url);
        return v;
    }
}
