package com.globant.myleague;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.globant.myleague.adapter.MatchStatisticsAdapter;
import com.globant.myleague.adapter.NewsAdapter;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.News;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrincipalNewsFragment extends ListFragment {

    private final static String LOG_TAG= PrincipalNewsFragment.class.getSimpleName();
    public final static String KEY_NEWS_PREFERENCES="key_filter_news_preferences";
    public final static String KEY_DEFAULT_PREFERENCES="default_preferences";
    public final int RESULT_SETTINGS=1;

    MyLeagueService.ApiInterface mMatchServiceInterface;
    ArrayAdapter<Matches> mAdapterListMatches;
    ArrayAdapter<Matches> mAdapterListNews;
    public PrincipalNewsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MyLeagueService matchService = new MyLeagueService();
        mMatchServiceInterface = matchService.generateServiceInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_principal_news, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String preferences = getNewsSettings();
        switch(preferences){
            case "Show Matches":
                prepareListviewMatches();
                break;
            case "Show news soccer teams":
                prepareListviewNews();
                break;
            case "Show new tournaments":
                prepareListviewNews();
                break;
            case "Show all news":
                prepareListviewNews();
                break;
            default:  prepareListviewMatches();
                      prepareListviewNews();

        }


        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showViewByItem(position);
            }
        });
    }

    private void showViewByItem(int position) {
        if(getListView().getAdapter() instanceof NewsAdapter){
            Log.w(LOG_TAG, "Clic en lista New General");
            News news = mAdapterListNews.getItem(position);
            if(news.getIdNews().equals("2")){
                Log.w(LOG_TAG, "Click en Partido");
                startMatchResultActivity();
            }else if(news.getIdNews().equals("1")){
                Log.w(LOG_TAG, "Click en News");
            }
        }
        if(getListView().getAdapter() instanceof MatchStatisticsAdapter){
            Log.w(LOG_TAG, "Click en Partido" );
            startMatchResultActivity();
        }
    }

    private void startMatchResultActivity() {
        Intent intent = new Intent(getActivity(), MatchResultActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        String preferences = getNewsSettings();
        switch(preferences){
            case "Show Matches":
                getNewsAboutMatches();
                break;
            case "Show news soccer teams":
                getNewsAboutClubesOnly();
                break;
            case "Show new tournaments":
                getNewsAboutTournamentsOnly();
                break;
            case "Show all news":
                getAllNews();
                break;
            default:  getAllNews();
        }

    }

    private void prepareListviewMatches(){
        List<Matches> listMatches = new ArrayList<>();
        mAdapterListMatches = new MatchStatisticsAdapter(getActivity(),listMatches);
        setListAdapter(mAdapterListMatches);
    }

    private void prepareListviewNews(){
        List<Matches> listNews = new ArrayList<>();
        mAdapterListNews  = new NewsAdapter(getActivity(),listNews);
        setListAdapter(mAdapterListNews);
    }

    private void getNewsAboutMatches(){
        mMatchServiceInterface.getMatches(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> listMatches, Response response) {
                if(response.getStatus()==200){
                    Log.d(LOG_TAG,String.valueOf(listMatches.size()));
                    mAdapterListMatches.clear();
                    mAdapterListMatches.addAll(listMatches);
                    mAdapterListMatches.notifyDataSetChanged();
                }else{
                    Log.e(LOG_TAG, "Matches retrieval status problem: " + response.getReason());

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }
    private void getAllNews(){
        mMatchServiceInterface.getAllNews(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> listNews, Response response) {
                if (response.getStatus() == 200) {
                    parseList(listNews);
                    mAdapterListNews.clear();
                    mAdapterListNews.addAll(listNews);
                    mAdapterListNews.notifyDataSetChanged();
                } else {
                    Log.e(LOG_TAG, "Matches retrieval status problem: " + response.getReason());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }

    private void getNewsAboutTournamentsOnly(){
        mMatchServiceInterface.getNewsAboutTournaments(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> listNews, Response response) {
                if (response.getStatus() == 200) {
                    parseList(listNews);
                    mAdapterListNews.clear();
                    mAdapterListNews.addAll(listNews);
                    mAdapterListNews.notifyDataSetChanged();
                } else {
                    Log.e(LOG_TAG, "Matches retrieval status problem: " + response.getReason());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }

    private void getNewsAboutClubesOnly(){
        mMatchServiceInterface.getNewsAboutClubs(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> listNews, Response response) {
                if (response.getStatus() == 200) {
                    parseList(listNews);
                    mAdapterListNews.clear();
                    mAdapterListNews.addAll(listNews);
                    mAdapterListNews.notifyDataSetChanged();
                } else {
                    Log.e(LOG_TAG, "Matches retrieval status problem: " + response.getReason());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
            }
        });
    }

    public static void parseList(List<Matches>listNews){
        Log.i(LOG_TAG,"===================List.size="+listNews.size()+"============================");
        for(int i=0; i< listNews.size(); i++){
            Log.i(LOG_TAG,"idnews :"+listNews.get(i).getIdNews());
            if(listNews.get(i).getIdNews().equals("2")) {
                Log.i(LOG_TAG,"Es Noticia de Partido :"+listNews.get(i).toString());
            }
            else if(listNews.get(i).getIdNews().equals("1")) {
                Log.i(LOG_TAG,"Es Noticia de Gral :"+listNews.get(i).getTitleNews());
            }
            Log.i(LOG_TAG,"===================position="+i+"============================");
        }
    }

    private String getNewsSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String idNewsOption =  sharedPreferences.getString(KEY_NEWS_PREFERENCES,KEY_DEFAULT_PREFERENCES) ;
        getActivity().setTitle(idNewsOption.equals("default_preferences")?"Show all News":idNewsOption);
        Log.i(LOG_TAG,"^^^ Value settings=:"+sharedPreferences.getString(KEY_NEWS_PREFERENCES,"default") );
        return idNewsOption;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.menu_principal_news, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter_news) {

            Intent intent = new Intent(getActivity(), SettingsPrincipalActivity.class);
            startActivityForResult(intent, RESULT_SETTINGS);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
