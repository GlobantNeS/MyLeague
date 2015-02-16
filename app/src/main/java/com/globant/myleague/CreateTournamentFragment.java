package com.globant.myleague;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTournamentFragment extends Fragment {

    private static final String [] PERIODICYTI = {"Daily","Weekly","Fortnightly","Monthly"};
    private static final int DAILY = 0;
    private static final int WEEKLY = 1;
    private static final int FORTNIGHTLY = 2;
    private static final int MONTHLY = 3;
    private static EditText mEditTextDatePicker;
    private static EditText mEditTextPeriod;

    public CreateTournamentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_tournament, container, false);
        mEditTextDatePicker = (EditText) rootView.findViewById(R.id.edit_text_date_picker);
        mEditTextPeriod =(EditText) rootView.findViewById(R.id.edit_text_period);

        mEditTextDatePicker.setKeyListener(null);
        mEditTextPeriod.setKeyListener(null);

        mEditTextDatePicker.setOnClickListener(viewsClickListener);
        mEditTextPeriod.setOnClickListener(viewsClickListener);

        return rootView;
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

                case R.id.button_add_team:
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



}
