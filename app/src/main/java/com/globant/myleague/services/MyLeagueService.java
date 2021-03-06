package com.globant.myleague.services;

import com.globant.myleague.pojo.Clubs;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.News;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.pojo.TeamsInTournaments;
import com.globant.myleague.pojo.Tournaments;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public class MyLeagueService {

    final static String API_URL = "http://10.0.2.2:3000";
    final static String TOURNAMENTS_ENDPOINT = "/tournaments.json";
    final static String TEAMS_ENDPOINT = "/teams.json";
    final static String TEAM_ENDPOINT = "/teams/{id}.json";
    final static String TOURNAMENTS_ID_ENDPOINT = "/tournaments/{id}.json";
    final static String MATCHES_ENDPOINT = "/matches.json";
    final static String MATCHES_TOURNAMENT_ENDPOINT = "/matches/{id}.json";
    final static String TEAMS_TOURNAMENT_ID_ENDPOINT = "/teamsintournaments/{id}.json";
    final static String TEAMS_TOURNAMENT_ENDPOINT = "/teamsintournaments.json";
    final static String NEWS_CURRENTLY_CLUBS_REGISTERED = "/clubs.json";
    final static String NEWS_TOURNAMENTS = "/news_tournaments.json";
    final static String ALL_NEWS_ENDPOINT = "/news.json";
    final static String ALL_CLUBS_ENDPOINT = "/clubs.json";

    /*final static String API_URL = "http://private-a479a-myleague.apiary-mock.com";
    final static String TOURNAMENTS_ENDPOINT = "/tournaments";
    final static String TEAMS_ENDPOINT = "/teams";
    final static String TEAM_ENDPOINT = "/teams/{id}";
    final static String TEAM_CREATE_ENDPOINT = "/team";
    final static String TOURNAMENTS_ID_ENDPOINT = "/tournaments/{id}";
    final static String MATCHES_ENDPOINT = "/matches";
    final static String MATCHES_TOURNAMENT_ENDPOINT = "/matches/{id}";
    final static String TEAMS_TOURNAMENT_ID_ENDPOINT = "/teamsintournament/{id}";
    final static String TEAMS_TOURNAMENT_ENDPOINT = "/teamsintournament";
    final static String ALL_MATCHES_ENDPOINT = "/matches";
    final static String NEWS_CURRENTLY_CLUBES_REGISTERED = "/news/clubs";
    final static String NEWS_TOURNAMENTS = "/news/tournaments";
    final static String ALL_NEWS_ENDPOINT = "/news";*/

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

        @POST(ALL_NEWS_ENDPOINT)
        void setNews(@Body News news,Callback<News> callback);

        @POST(ALL_NEWS_ENDPOINT)
        void setClubs(@Body Clubs clubs,Callback<Clubs> callback);

        @POST(MATCHES_ENDPOINT)
        void setMatch(@Body Matches matches,Callback<Matches> callback);

        @POST(TOURNAMENTS_ENDPOINT)
        void setTournament(@Body Tournaments tournament, Callback<Tournaments> callback);

        @POST(TOURNAMENTS_ENDPOINT)
        void addTeamsToTournament(@Body List <TeamsInTournaments> teamsInTournaments, Callback<List<TeamsInTournaments>> callback);

        @PATCH(MATCHES_TOURNAMENT_ENDPOINT)
        void updateMatch(@Path("id") String id,@Body Matches matches,Callback<Matches> callback);

        @POST(TEAMS_TOURNAMENT_ENDPOINT)
        void addTeamToTournament(@Body TeamsInTournaments teamsInTournaments,Callback<TeamsInTournaments> callback);

        @GET(TEAMS_TOURNAMENT_ENDPOINT)
        void getAllTeamsInTournaments(Callback<List<TeamsInTournaments>> callback);

        @GET(TEAMS_TOURNAMENT_ID_ENDPOINT)
        void getTeamsInTournament(@Path("id") String id,Callback<List<TeamsInTournaments>> callback);

        @GET(ALL_NEWS_ENDPOINT)
        void getAllNews(Callback<List<Matches>> callback);

        @GET(NEWS_CURRENTLY_CLUBS_REGISTERED)
        void getNewsAboutClubs(Callback<List<Matches>> callback);

        @GET(NEWS_TOURNAMENTS)
        void getNewsAboutTournaments(Callback<List<Matches>> callback);
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
