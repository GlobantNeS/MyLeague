package com.globant.myleague.tools;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import com.globant.myleague.ManagerScreenActivity;
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
        if(namestack.equals("YES"))
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

    public void setIdUser(Context context,String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(prefs.getString("id_user_settings",context.getString(R.string.default_id)).equals("-1")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("id_user_settings", value);
            editor.commit();
            Intent mRestartActivity = new Intent(context, ManagerScreenActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mRestartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }
    }
}
