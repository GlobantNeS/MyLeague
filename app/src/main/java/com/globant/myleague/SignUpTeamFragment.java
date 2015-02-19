package com.globant.myleague;


import android.app.Activity;
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

import com.globant.myleague.pojo.Teams;
import com.globant.myleague.services.MyLeagueService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpTeamFragment extends Fragment {

    private static final String TEAM_KEY = "team";
    private static final String LOG_TAG = SignUpTeamFragment.class.getSimpleName();
    EditText etName;
    EditText etManager;
    EditText etEmail;
    EditText etPhone;
    Button btSaveTeam;
    ImageButton ibTakePicture;
    ImageView ivPictureTeam;
    Bitmap bMap;
    View view;
    public static Teams mTeam;

    MyLeagueService.ApiInterface mMyLeagueApiInterface;

    public static final int TAKE_PICTURE = 1;

    public SignUpTeamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up_team, container, false);
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
                MyLeagueService myleagueService = new MyLeagueService();
                mMyLeagueApiInterface = myleagueService.generateServiceInterface();
                Teams team = settingTeamToSave();
                savingTeamRequest(team);
            }
        });
    }

    private void savingTeamRequest(Teams team) {
        mMyLeagueApiInterface.setTeam(team, new Callback<Teams>() {
            @Override
            public void success(Teams team, Response response) {
                if (response.getStatus() == 201) {
                    mTeam = team;
                    Log.d(LOG_TAG, "Good Request");
                    setResultActivity();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null)
                    Log.e(LOG_TAG, "Fail Request: " + error.getUrl() + "  - - " + error.getMessage());
            }
        });
    }

    private Teams settingTeamToSave() {
        Teams team = new Teams();
        team.setName(etName.getText().toString());
        team.setManager(etManager.getText().toString());
        team.setEmail(etEmail.getText().toString());
        team.setPhone(etPhone.getText().toString());
        team.setUrl("");
        return team;
    }

    public void setResultActivity() {
        Activity activity = getActivity();
        Log.d(LOG_TAG, mTeam.getUrl());
        Intent intent = new Intent();
        intent.putExtra(TEAM_KEY, mTeam);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
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
