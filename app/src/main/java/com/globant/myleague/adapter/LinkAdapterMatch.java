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
import com.globant.myleague.pojo.Tournaments;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.List;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class LinkAdapterMatch extends ArrayAdapter<Matches>{

    private List<Matches> matchesList;
    private Context ctx;
    Tools tools = new Tools();

    public LinkAdapterMatch(Context context, int resource, List<Matches> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.matchesList = objects;
    }

    @Override
    public int getCount() {
        return matchesList.size();
    }

    @Override
    public Matches getItem(int position) {
        return matchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return matchesList.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if ( v == null)
        {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.fragment_item_view_game, null);
        }
        NetworkImageView nivLocal = (NetworkImageView)v.findViewById(R.id.nivLocal);
        NetworkImageView nivVisitant = (NetworkImageView)v.findViewById(R.id.nivVisitant);
        TextView tvLocalName = (TextView)v.findViewById(R.id.tvLocalName);
        TextView tvVisitName = (TextView)v.findViewById(R.id.tvVisitName);





        return v;
    }
}
