package com.globant.myleague.services;

import android.util.Base64;

import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.News;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by root on 16/02/15.
 */
public class NewsService {

    final static String MYLEAGUE_API_URL = "http://private-7bc2-myleague.apiary-mock.com";
    final static String ACCEPTED_DATA = "application/json";
    final static String ALL_MATCHES_ENDPOINT = "/matches";
    final static String NEWS_CURRENTLY_CLUBES_REGISTERED = "/news/clubs";
    final static String NEWS_TOURNAMENTS = "/news/tournaments";
    final static String ALL_NEWS_ENDPOINT = "/news";




    public interface ApiInterface {
        @GET(ALL_MATCHES_ENDPOINT)
        void getAllMatches(Callback<List<Matches>> callback);

        @GET(ALL_NEWS_ENDPOINT)
        void getAllNews(Callback<List<Matches>> callback);

        @GET(NEWS_CURRENTLY_CLUBES_REGISTERED)
        void getNewsAbaoutClubes(Callback<List<Matches>> callback);

        @GET(NEWS_TOURNAMENTS)
        void getnewsAboutTournaments(Callback<List<Matches>> callback);

      /*  @GET(PROJECT_TASKS_ENDPOINT)
        void getTasksForProjectId(@Path("id")String id, Callback<List<eee>> callback);*/
    }

    public NewsService() {

    }

    public ApiInterface generateServiceInterface() {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(MYLEAGUE_API_URL)
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", ACCEPTED_DATA);
                    }
                });
        RestAdapter restAdapter = builder.build();
        return restAdapter.create(ApiInterface.class);
    }
}
