package com.globant.myleague;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.globant.myleague.tools.PagerEnabledSlidingPaneLayout;
import com.globant.myleague.tools.Tools;

import java.util.HashMap;


public class ManagerScreenActivity extends ActionBarActivity implements menuFragment.OptionsMenuListener{


    final static String MYLEAGUE = "MYLEAGUE";
    final static int REQUEST_CODE_SETTINGS =0;

    PagerEnabledSlidingPaneLayout slidingPaneLayout;
    Tools tools=new Tools();
    HashMap<String,String> settings;
    ConnectivityManager connMgr;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeCheckNetwork();
        setContentView(R.layout.activity_manager_screens);
        prepareToolbar();
        prepareSlide();
        settings=tools.getPreferences(this);
        if(settings.get("username").equals(getString(R.string.default_username)))
            callSettings();
        else
            if(settings.get("id").equals("-1"))
                tools.loadFragment(getSupportFragmentManager(),new SignUpTeamFragment(),R.id.rightpane,"SIGN UP");
        checkConnection();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SETTINGS)
        {
            settings=tools.getPreferences(this);
            if(settings.get("username").equals(MYLEAGUE))
                tools.setIdUser(this,"0");
            else
                if(settings.get("id").equals("-1"))
                    tools.loadFragment(getSupportFragmentManager(),new SignUpTeamFragment(),R.id.rightpane,"SIGN UP");
        }
    }



    private void checkConnection() {
        if (networkInfo != null && networkInfo.isConnected()) {
            PrincipalNewsFragment fragment=new PrincipalNewsFragment();
            tools.loadFragment(getSupportFragmentManager(),fragment, R.id.rightpane,"NEWS");
        } else {
            createAlert(getString(R.string.text_check_connection));
        }
    }

    private void initializeCheckNetwork() {
        connMgr = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
    }

    public void createAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.text_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void prepareSlide() {
        slidingPaneLayout=(PagerEnabledSlidingPaneLayout)findViewById(R.id.sp);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {
                openPane();
            }

            @Override
            public void onPanelClosed(View panel) {
                closePane();
            }
        });
    }

    private void prepareToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_news_principal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_image_dehaze);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        boolean handler;

        switch (id)
        {
            case R.id.action_settings:
                callSettings();
                handler=true;
                break;
            case android.R.id.home:
                if(slidingPaneLayout.isOpen()) {
                    closePane();
                }
                else
                {
                    openPane();
                }
                handler=true;
                break;
            default:
                handler=super.onOptionsItemSelected(item);
                break;
        }
        return handler;
    }

    private void callSettings() {
        Intent intent = new Intent(ManagerScreenActivity.this,SettingsMainActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    }

    private void openPane() {
        slidingPaneLayout.openPane();
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.arrow_down_float);
    }

    private void closePane() {
        slidingPaneLayout.closePane();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_image_dehaze);
    }

    @Override
    public void OptionsMenuListener(String optionMenu) {
        switch (optionMenu) {
            case "NEWS":
                checkConnection();
                break;
            case "SIGN UP TEAM":
                if (networkInfo != null && networkInfo.isConnected()) {
                    SignUpTeamFragment fragment=new SignUpTeamFragment();
                    tools.loadFragment(getSupportFragmentManager(),fragment, R.id.rightpane,"SIGN UP TEAM");
                } else {
                    createAlert(getString(R.string.text_check_connection));
                }
                break;
            case "ABOUT":
                if (networkInfo != null && networkInfo.isConnected()) {
                    //tools.loadFragment(getSupportFragmentManager(), new AboutFragment(), R.id.rightpane, "ABOUT");
                } else {
                    createAlert(getString(R.string.text_check_connection));
                }

                break;
            case "CONTACT":
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"myleague@hotmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.text_subject_email));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.text_body_mail));
                startActivity(Intent.createChooser(intent, getString(R.string.text_send_email)));
                break;
            default:
                break;
        }
    }

}
