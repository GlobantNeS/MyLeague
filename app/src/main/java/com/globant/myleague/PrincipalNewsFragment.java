package com.globant.myleague;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.globant.myleague.adapter.MatchNewsAdapter;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.services.MatchService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PrincipalNewsFragment extends ListFragment {

    private final static String LOG_TAG= PrincipalNewsFragment.class.getSimpleName();

    MatchService.ApiInterface mMatchServiceInterface;
    ArrayAdapter<Matches> mAdapter;
    public PrincipalNewsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MatchService matchService = new MatchService();
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
        prepareListview();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getAllMatches();
    }

    private void prepareListview(){
        List<Matches> listMatches = new ArrayList<>();
        mAdapter = new MatchNewsAdapter(getActivity(),listMatches);
        setListAdapter(mAdapter);
    }

    private void getAllMatches(){
        mMatchServiceInterface.getAllMatches(new Callback<List<Matches>>() {
            @Override
            public void success(List<Matches> listMatches, Response response) {
                if(response.getStatus()==200){
                    mAdapter.clear();
                    mAdapter.addAll(listMatches);
                    mAdapter.notifyDataSetChanged();
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

}
