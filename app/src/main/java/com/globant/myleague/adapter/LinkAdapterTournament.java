package com.globant.myleague.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.Tournaments;
import com.globant.myleague.tools.Tools;

import java.util.List;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class LinkAdapterTournament extends ArrayAdapter<Tournaments>{

    private List<Tournaments> tournamentList;
    private Context ctx;
    Tools tools = new Tools();

    public LinkAdapterTournament(Context context, int resource, List<Tournaments> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.tournamentList = objects;
    }

    @Override
    public int getCount() {
        return tournamentList.size();
    }

    @Override
    public Tournaments getItem(int position) {
        return tournamentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return tournamentList.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if ( v == null)
        {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.fragment_item_view_tournament, null);
        }
        NetworkImageView nivTournament = (NetworkImageView)v.findViewById(R.id.nivTournament);
        TextView tvTournamentName = (TextView)v.findViewById(R.id.tvTournamentName);

        String name=tournamentList.get(position).toString();
        String url=tournamentList.get(position).getUrl_image();

        tvTournamentName.setText(name);
        Tools.loadImageFromInternet(ctx,nivTournament, url);

        return v;
    }

}
