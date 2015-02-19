package com.globant.myleague.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.globant.myleague.R;
import com.globant.myleague.pojo.GeneralNews;
import com.globant.myleague.pojo.Matches;
import com.globant.myleague.pojo.News;
import com.globant.myleague.tools.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/02/15.
 */
public class NewsAdapter extends ArrayAdapter<Matches> {

    private final static String LOG_TAG=NewsAdapter.class.getSimpleName();
    List<Matches> mListNews;
    Context mContext;
    public NewsAdapter(Context context, List<Matches> listNews){

        super(context, R.layout.item_news,listNews);
        mListNews = listNews;
        mContext = context;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;
        if(mListNews.get(position).getIdNews().equals("2")) {
            rowView = reuseOrgenerateViewMatch(convertView, parent);
            displayMatchInRow(position, rowView);
        }
        else if(mListNews.get(position).getIdNews().equals("1")) {
            rowView = reuseOrgenerateViewNews(convertView, parent);
            displayNewsInRow(position, rowView);
        }

        return rowView;
    }

    private View reuseOrgenerateViewNews(View convertView, ViewGroup parent){
        View rowView;
        if(convertView != null && !(convertView.getTag() instanceof MatchStatisticsAdapter.ViewHolder)){
              rowView = convertView;
        }
        else{
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_news,parent,false);
            ViewHolder viewHolder = new ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }

        return  rowView;

    }

    private View reuseOrgenerateViewMatch(View convertView, ViewGroup parent){
        View rowView;
        if(convertView != null && !(convertView.getTag() instanceof ViewHolder)){
             rowView = convertView;
        }
        else{
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_statistics_match,parent,false);
            MatchStatisticsAdapter.ViewHolder viewHolder = new MatchStatisticsAdapter.ViewHolder(rowView);
            rowView.setTag(viewHolder);

        }

        return  rowView;

    }

    private void displayNewsInRow(int position, View rowView){

            ViewHolder viewHolder = (ViewHolder) rowView.getTag();
             News generalNews =  mListNews.get(position);
            viewHolder.textViewTitleNews.setText(generalNews.getTitleNews());
            viewHolder.textViewDescriptionNews.setText(generalNews.getDescriptionNews());
            Tools.loadImageFromInternet(mContext, viewHolder.imageViewPictureNews,generalNews.getUrlPicture());

    }

    private void displayMatchInRow(int position, View rowView){



           Matches match =  mListNews.get(position);
           MatchStatisticsAdapter.ViewHolder  viewHolderMatch = (MatchStatisticsAdapter.ViewHolder) rowView.getTag();

            Log.e(LOG_TAG,"Position="+position+"| "+match.toString());

            viewHolderMatch.textViewMatchDateTime.setText(match.getDateMatch());
            viewHolderMatch.textViewLocalTeamName.setText(match.getNameLocal());
            viewHolderMatch.textViewScoreLocal.setText(match.getLocalScore());
            viewHolderMatch.textViewYellowCardslocal.setText(match.getLocalFaults());
            viewHolderMatch.textViewRedCardsLocal.setText(match.getLocalExp());

            viewHolderMatch.textViewAwayTeamName.setText(match.getNameVisit());
            viewHolderMatch.textViewScoreAway.setText(match.getVisitScore());
            viewHolderMatch.textViewYellowCardsVisit.setText(match.getVisitFaults());
            viewHolderMatch.textViewRedCardsVisit.setText(match.getVisitExp());

            Tools.loadImageFromInternet(mContext,viewHolderMatch.imageViewLocal,match.getUrlImgLocal());
            Tools.loadImageFromInternet(mContext,viewHolderMatch.imageViewAway,match.getUrlImgVisit());

    }

    public class ViewHolder {
        public final TextView textViewTitleNews;
        public final TextView textViewDescriptionNews;
        public final NetworkImageView imageViewPictureNews;

        public ViewHolder (View view) {
            textViewTitleNews = (TextView)view.findViewById(R.id.text_view_title_news);
            textViewDescriptionNews = (TextView)view.findViewById(R.id.text_view_news_description);
            imageViewPictureNews = (NetworkImageView)view.findViewById(R.id.image_view_news);

        }
    }




}
