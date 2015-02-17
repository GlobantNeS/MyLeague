package com.globant.myleague;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.globant.myleague.pojo.Tournaments;
import com.globant.myleague.services.MyLeagueService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CreateTournamentFragment extends Fragment {

    private static final String LOG_TAG = CreateTournamentFragment.class.getSimpleName();

    private static final String [] PERIODICYTI = {"Daily","Weekly","Fortnightly","Monthly"};
    private static final int DAILY = 0;
    private static final int WEEKLY = 1;
    private static final int FORTNIGHTLY = 2;
    private static final int MONTHLY = 3;

    private EditText mEditTextTournamentName;
    private static EditText mEditTextDatePicker;
    private static EditText mEditTextPeriod;
    private EditText mEditTextAward;
    private EditText mEditTextNumberOfTeams;
    private ImageView mImageViewTournamentLogo;
    private ImageButton mImageButtonSelectPicture;
    private Button mButtonAddTournament;

    MyLeagueService.ApiInterface mMyLeagueApiInterface;

    private enum FieldsToFill{
        TournamentName,
        DatePicker,
        Period,
        Award,
        NumberOfTeams
    }

    public EnumSet<FieldsToFill> mFieldsToFillEnumSet = EnumSet.noneOf(FieldsToFill.class);


    public class EditTextEvents implements TextWatcher {

        FieldsToFill mFieldToFill;
        public EditTextEvents( FieldsToFill fieldToFill){
            mFieldToFill = fieldToFill;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d(LOG_TAG, "Entrea watcher");
            if(TextUtils.isEmpty(s)){
                Log.d(LOG_TAG, "Es empty");
                mFieldsToFillEnumSet.remove(mFieldToFill);
            } else {
                mFieldsToFillEnumSet.add(mFieldToFill);
            }

            changeStatusButton();

        }
    }

    public void changeStatusButton(){
        if(mFieldsToFillEnumSet.containsAll(EnumSet.allOf(FieldsToFill.class))) {
            mButtonAddTournament.setEnabled(true);
        } else mButtonAddTournament.setEnabled(false);
    }


    public CreateTournamentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_tournament, container, false);
        prepareViews(rootView);
        setViewListeners();
        setViewsProperties();

        return rootView;
    }

    private void setViewsProperties() {
        mEditTextDatePicker.setKeyListener(null);
        mEditTextPeriod.setKeyListener(null);
        mButtonAddTournament.setEnabled(false);
    }

    private void setViewListeners() {
        mEditTextTournamentName.addTextChangedListener(new EditTextEvents(FieldsToFill.TournamentName));
        mEditTextDatePicker.addTextChangedListener(new EditTextEvents(FieldsToFill.DatePicker));
        mEditTextPeriod.addTextChangedListener(new EditTextEvents(FieldsToFill.Period));
        mEditTextAward.addTextChangedListener(new EditTextEvents(FieldsToFill.Award));
        mEditTextNumberOfTeams.addTextChangedListener(new EditTextEvents(FieldsToFill.NumberOfTeams));
        mEditTextDatePicker.setOnClickListener(viewsClickListener);
        mEditTextPeriod.setOnClickListener(viewsClickListener);
        mButtonAddTournament.setOnClickListener(viewsClickListener);
        mImageButtonSelectPicture.setOnClickListener(viewsClickListener);
    }

    private void prepareViews(View rootView) {
        mEditTextDatePicker = (EditText) rootView.findViewById(R.id.edit_text_date_picker);
        mEditTextPeriod =(EditText) rootView.findViewById(R.id.edit_text_period);
        mEditTextTournamentName = (EditText) rootView.findViewById(R.id.edit_text_name);
        mEditTextAward =(EditText) rootView.findViewById(R.id.edit_text_first_place_award);
        mEditTextNumberOfTeams = (EditText) rootView.findViewById(R.id.edit_text_number_of_teams);
        mImageViewTournamentLogo =(ImageView) rootView.findViewById(R.id.image_view_picture);
        mImageButtonSelectPicture = (ImageButton) rootView.findViewById(R.id.image_button_take_picture);
        mButtonAddTournament = (Button) rootView.findViewById(R.id.button_add_tournament);
    }

    private View.OnClickListener viewsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_text_date_picker:
                    showDatePickerDialog();
                break;

                case R.id.edit_text_period:
                    showPickerDialog();
                break;

                case R.id.button_add_tournament:
                    MyLeagueService myleagueService= new MyLeagueService();
                    mMyLeagueApiInterface = myleagueService.generateServiceInterface();
                    Tournaments tournament = new Tournaments();
                    tournament.setName(mEditTextTournamentName.getText().toString());
                    tournament.setDateini(mEditTextDatePicker.getText().toString());
                    tournament.setPeriod(mEditTextPeriod.getText().toString());
                    tournament.setNumTeams(mEditTextNumberOfTeams.getText().toString());
                    //TODO set URL image
//                    tournament.setUrl("");

                    mMyLeagueApiInterface.setTournament(tournament, new Callback<Tournaments>(){

                        @Override
                        public void success(Tournaments tournaments, Response response) {
                            if(response.getStatus() == 200) {
                                Toast.makeText(getActivity(), "TODO: Add Tournament", Toast.LENGTH_SHORT).show();
                            } else Log.d(LOG_TAG, "bad request: " + response.getUrl());
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d(LOG_TAG, "failure request: " + error.getUrl());
                        }
                    });
                break;

                case R.id.image_button_take_picture:   Intent intent =new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                                        startActivityForResult(intent, SignUpTeamFragment.TAKE_PICTURE);

                break;
            }
        }
    };

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private Calendar currentCalendar;
        private int year;
        private int month;
        private int day;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            prepareCalendar();
            return getDatePickerDialog(year, month, day);
        }

        private void prepareCalendar() {
            currentCalendar = Calendar.getInstance();
            year = currentCalendar.get(Calendar.YEAR);
            month = currentCalendar.get(Calendar.MONTH);
            day = currentCalendar.get(Calendar.DAY_OF_MONTH);
        }

        private DatePickerDialog getDatePickerDialog(int year, int month, int day) {
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int yearSelected, int monthSelected, int daySelected) {
            prepareCalendar();
            Date dateSelected = getSelectedDate(yearSelected, monthSelected, daySelected);
            Date currentDate = this.currentCalendar.getTime();

            if(!dateSelected.before(currentDate)) {
                prepareDate(yearSelected, monthSelected, daySelected);
            } else advertiseWrongDate();

        }

        private Date getSelectedDate(int yearSelected, int monthSelected, int daySelected) {
            Calendar createdCalendar = Calendar.getInstance();
            createdCalendar.set(yearSelected, monthSelected, daySelected);
            return createdCalendar.getTime();
        }

        private void advertiseWrongDate() {
            new AlertDialog.Builder(getActivity()).
                    setTitle("Date").
                    setMessage("Select date larger or equals than the current date ").
                    setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    }).show();
        }

        private void prepareDate(int year, int month, int day) {
            Date date = getSelectedDate(year, month, day);
            String formattedDate = new SimpleDateFormat("dd-MM-yyy").format(date);
            setDateToEditText(formattedDate);
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public static void setDateToEditText (String date) {
        mEditTextDatePicker.setText(date);
    }


    public static class PeriodicityDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.periodicity)
                    .setItems(R.array.periods, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          switch (which) {

                              case DAILY: mEditTextPeriod.setText(PERIODICYTI[which]);
                              break;

                              case WEEKLY: mEditTextPeriod.setText(PERIODICYTI[which]);
                              break;

                              case FORTNIGHTLY: mEditTextPeriod.setText(PERIODICYTI[which]);
                              break;

                              case MONTHLY: mEditTextPeriod.setText(PERIODICYTI[which]);
                              break;
                          }
                        }
                    });
            return builder.create();
        }
    }

    public void showPickerDialog() {
        PeriodicityDialog newFragment = new PeriodicityDialog();
        newFragment.show(getActivity().getSupportFragmentManager(),getTag());
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SignUpTeamFragment.TAKE_PICTURE && resultCode == getActivity().RESULT_OK) {
            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
            mImageViewTournamentLogo.setImageBitmap(bitmapImage);
        }
    }

}
