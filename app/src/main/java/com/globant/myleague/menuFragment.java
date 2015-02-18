package com.globant.myleague;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.globant.myleague.adapter.LinkAdapterMenu;
import com.globant.myleague.pojo.OptionsMenu;
import com.globant.myleague.tools.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class menuFragment extends Fragment {


    HashMap<String,String> settings;
    Tools tools = new Tools();

    private List<OptionsMenu> optionList = new ArrayList<>();

    public menuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings=tools.getPreferences(getActivity());
        init();
    }

    @Override
    public void onAttach(Activity activity) {
        if (! (activity instanceof OptionsMenuListener) )
            throw new ClassCastException();
        super.onAttach(activity);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.menu_left_layout, container, false);
        ListView lView = (ListView) v.findViewById(R.id.menuList);
        LinkAdapterMenu la = new LinkAdapterMenu(optionList, getActivity());
        lView.setAdapter(la);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ( (OptionsMenuListener) getActivity()).OptionsMenuListener( optionList.get(position).name);
            }
        });
        setHasOptionsMenu(true);
        return v;
    }

    private void init() {
        optionList.add(new OptionsMenu(getString(R.string.text_news),"NEWS",R.drawable.ic_action_action_language));
        if(settings.get("id").equals("0"))
            optionList.add(new OptionsMenu(getString(R.string.text_create_tournament),"CREATE TOURNAMENT",R.drawable.ic_action_social_person));
        if(settings.get("id").equals("0"))
            optionList.add(new OptionsMenu(getString(R.string.text_fill_statistics),"FILL STATISTICS",R.drawable.ic_action_social_person));
        if(settings.get("id").equals("-1") || settings.get("id").equals("0"))
            optionList.add(new OptionsMenu(getString(R.string.text_sign_up_team),"SIGN UP TEAM",R.drawable.ic_action_social_person));
        if(settings.get("id").equals("0"))
            optionList.add(new OptionsMenu(getString(R.string.text_add_teams_to_tournament),"ADD TEAMS TO TOURNAMENT",R.drawable.ic_action_communication_email));
        if(!settings.get("id").equals("0"))
            optionList.add(new OptionsMenu(getString(R.string.text_add_my_team_to_tournament),"ADD MY TEAM TO TOURNAMENT",R.drawable.ic_action_communication_email));
        optionList.add(new OptionsMenu(getString(R.string.text_view_teams),"VIEW TEAMS",R.drawable.ic_action_communication_email));
        optionList.add(new OptionsMenu(getString(R.string.text_contact),"CONTACT",R.drawable.ic_action_communication_email));
    }

    public interface OptionsMenuListener {
        public void OptionsMenuListener(String optionMenu);
    }
}
