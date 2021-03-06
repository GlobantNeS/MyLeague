package com.globant.myleague;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.globant.myleague.pojo.Clubs;
import com.globant.myleague.pojo.Teams;
import com.globant.myleague.services.MyLeagueService;
import com.globant.myleague.tools.Tools;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignUpTeamFragment extends Fragment {

    EditText etName;
    EditText etManager;
    EditText etEmail;
    EditText etPhone;
    Button btSaveTeam;
    ImageButton ibTakePicture;
    ImageView ivPictureTeam;
    Bitmap bMap;
    View view;
    private static final String BACK ="BACK";

    MyLeagueService.ApiInterface mMyLeagueApiInterface;

    public static final int TAKE_PICTURE = 1;
    final static String LOG_TAG = SignUpTeamFragment.class.getSimpleName();

    public SignUpTeamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up_team, container, false);
        getActivity().setTitle(getString(R.string.text_title_new_team));
        prepareText();
        prepareButton();
        return view;
    }

    private void prepareButton() {
        ibTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_PICTURE);
            }
        });

        btSaveTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLeagueService myleagueService= new MyLeagueService();
                mMyLeagueApiInterface = myleagueService.generateServiceInterface();
                final Teams team=new Teams();
                team.setName(etName.getText().toString());
                team.setManager(etManager.getText().toString());
                team.setEmail(etEmail.getText().toString());
                team.setPhone(etPhone.getText().toString());
                team.setUrlimage("");
                team.setUrlimage("");
                mMyLeagueApiInterface.setTeam(team, new Callback<Teams>() {
                    @Override
                    public void success(Teams teams, Response response) {
                        if (response.getStatus() == 201) {
                            Tools tools=new Tools();
                            tools.setIdUser(getActivity(),teams.getId());
                            Toast.makeText(getActivity(),"Added Team Ok",Toast.LENGTH_LONG).show();
                            createNewTeam(tools);
                        }
                    }

                    private void createNewTeam(Tools tools) {
                        Clubs clubs = new Clubs();
                        clubs.setIdNews("1");
                        clubs.setTitleNews("A new club has been registered "+team.getName()+" has been registered");
                        saveClub(clubs);
                    }

                    public void saveClub(Clubs clubs){
                        MyLeagueService myleagueService= new MyLeagueService();
                        mMyLeagueApiInterface = myleagueService.generateServiceInterface();
                        mMyLeagueApiInterface.setClubs(clubs,new Callback<Clubs>() {
                            @Override
                            public void success(Clubs clubs, Response response) {
                                Tools tools = new Tools();
                                if(getArguments()!=null)
                                    tools.loadFragment(getFragmentManager(),new TeamsListFragment(), R.id.rightpane,"NEWS");
                                else
                                    tools.loadFragment(getFragmentManager(),new PrincipalNewsFragment(), R.id.rightpane,"TEAMS");
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.d(LOG_TAG, "failure request: " + error.getUrl());
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.w(LOG_TAG, "ERROR: downloading " + error.getBody());
                    }
                });
            }
        });
    }

    public void prepareText() {

        prepareComponents();

        TextWatcher MyListener = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                if (!etName.getText().toString().isEmpty() &&
                        !etManager.getText().toString().isEmpty() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches() &&
                        Patterns.PHONE.matcher(etPhone.getText()).matches())
                    btSaveTeam.setEnabled(true);
                else
                    btSaveTeam.setEnabled(false);

            }
        };

        etName.addTextChangedListener(MyListener);
        etManager.addTextChangedListener(MyListener);
        etEmail.addTextChangedListener(MyListener);
        etPhone.addTextChangedListener(MyListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == getActivity().RESULT_OK) {
            bMap = (Bitmap) data.getExtras().get("data");
            ivPictureTeam.setImageBitmap(bMap);
        }
    }

    private void prepareComponents() {
        btSaveTeam = (Button) view.findViewById(R.id.btSave);
        etName = (EditText) view.findViewById(R.id.etName);
        etManager = (EditText) view.findViewById(R.id.etManager);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        ibTakePicture = (ImageButton)view.findViewById(R.id.ibTakePicture);
        ivPictureTeam = (ImageView)view.findViewById(R.id.ivPictureTeam);
    }
}
