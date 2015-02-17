package com.globant.myleague.services;

import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.Tournaments;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public class MyLeagueService {
    final static String API_URL = "http://private-a479a-myleague.apiary-mock.com";
    final static String ACCEPTED_DATA = "application/json";
    final static String TOURNAMENTS_ENDPOINT = "/tournaments";
    final static String TEAMS_ENDPOINT = "/teams";
    final static String TEAM_ENDPOINT = "/teams/{id}";
    final static String TOURNAMENTS_ID_ENDPOINT = "/tournaments/{id}";
    final static String MATCHES_ENDPOINT = "/matches";
    final static String MATCHES_TOURNAMENT_ENDPOINT = "/matches/{id}";

    public interface ApiInterface {


        @GET(TOURNAMENTS_ENDPOINT)
        void getTournaments(Callback<List<Tournaments>> callback);

        @GET(TEAMS_ENDPOINT)
        void getTeams(Callback<List<Teams>> callback);

        @GET(TEAM_ENDPOINT)
        void getTeam(@Path("id") String id,Callback<Teams> callback);

        @GET(MATCHES_ENDPOINT)
        void getMatches(Callback<List<Matches>> callback);

        @GET(MATCHES_TOURNAMENT_ENDPOINT)
        void getMatchesForTournament(@Path("id") String id,Callback<List<Matches>> callback);

        @POST(TEAMS_ENDPOINT)
        void setTeam(@Body Teams team,Callback<Teams> callback);

        @POST(TOURNAMENTS_ENDPOINT)
        void setTournament(@Body Tournaments tournament, Callback<Tournaments> callback);
    }

    public MyLeagueService() {
    }

    public ApiInterface generateServiceInterface() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(API_URL)
                .setClient(new OkClient(new OkHttpClient()));
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(ApiInterface.class);
    }
}
