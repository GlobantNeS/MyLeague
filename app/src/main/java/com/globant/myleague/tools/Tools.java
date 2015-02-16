package com.globant.myleague.tools;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

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
}
