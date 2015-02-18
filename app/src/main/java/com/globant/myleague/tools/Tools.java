package com.globant.myleague.tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.globant.myleague.R;

import java.util.HashMap;

/**
 * Created the first version by kaineras on 16/02/15.
 */
public class Tools {

    public Tools()
    {

    }

    public static void loadImageFromInternet(Context context,NetworkImageView nivComic, String url) {
        RequestQueue mRequestQueue;
        ImageLoader imageLoader;
        mRequestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(mRequestQueue,new BitmapLruCache(BitmapLruCache.getDefaultLruCacheSize()));
        nivComic.setImageUrl(url,imageLoader);
    }

    public void loadFragment(FragmentManager fm,Fragment f,int container,String namestack)
    {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.addToBackStack(namestack);
        fragmentTransaction.replace(container, f);
        fragmentTransaction.commit();
    }

    public HashMap<String,String> getPreferences(Context context) {
        HashMap<String,String> settings=new HashMap<String,String>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        settings.put("username",prefs.getString("username_settings",context.getString(R.string.default_username)));
        settings.put("id",prefs.getString("id_user_settings",context.getString(R.string.default_id)));
        return  settings;
    }
}
