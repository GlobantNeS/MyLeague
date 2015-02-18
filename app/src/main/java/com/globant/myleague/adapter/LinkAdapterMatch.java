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
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.Tournaments;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class LinkAdapterMatch extends ArrayAdapter<Matches>{

    private List<Matches> matchesList;
    private Context ctx;
    MyLeagueService.ApiInterface mMyLeagueApiInterface;
    final static String LOG_TAG = SelectTournamentToMatchFragment.class.getSimpleName();

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
        final NetworkImageView nivLocal = (NetworkImageView)v.findViewById(R.id.nivLocal);
        final NetworkImageView nivVisitant = (NetworkImageView)v.findViewById(R.id.nivVisitant);
        final TextView tvLocalName = (TextView)v.findViewById(R.id.tvLocalName);
        final TextView tvVisitName = (TextView)v.findViewById(R.id.tvVisitantName);
        final TextView tvDateMatch = (TextView)v.findViewById(R.id.tvDateMatch);

        String idLocal=matchesList.get(position).getIdLocal();
        loadDataLocalTeam(nivLocal, tvLocalName, idLocal);

        String idVisit=matchesList.get(position).getIdVisit();
        loadDataVisit(nivVisitant, tvVisitName, idVisit);

        String dateMatch = matchesList.get(position).getDateMatch();
        tvDateMatch.setText(dateMatch);



        return v;
    }

    private void loadDataVisit(final NetworkImageView nivVisitant, final TextView tvVisitName, String idVisit) {
        MyLeagueService myLeagueService;
        myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
        mMyLeagueApiInterface.getTeam(idVisit,new Callback<Teams>() {
            @Override
            public void success(Teams teams, Response response) {
                if(teams!=null) {
                    tvVisitName.setText(teams.getName());
                    String url = teams.getUrl();
                    Tools.loadImageFromInternet(ctx, nivVisitant, url);
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }

    private void loadDataLocalTeam(final NetworkImageView nivLocal, final TextView tvLocalName, String idLocal) {
        MyLeagueService myLeagueService = new MyLeagueService();
        mMyLeagueApiInterface=myLeagueService.generateServiceInterface();
        mMyLeagueApiInterface.getTeam(idLocal,new Callback<Teams>() {
            @Override
            public void success(Teams teams, Response response) {
                if(teams!=null) {
                    tvLocalName.setText(teams.getName());
                    String url = teams.getUrl();
                    Tools.loadImageFromInternet(ctx, nivLocal, url);
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }
}
