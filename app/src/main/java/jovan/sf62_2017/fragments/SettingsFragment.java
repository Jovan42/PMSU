package jovan.sf62_2017.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

import jovan.sf62_2017.R;
import jovan.sf62_2017.SettingsActivity;

/**
 * Created by jovan on 05-May-18.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        String posts_sort = "Date", comments_sort = "Date";
        try {
           SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
           posts_sort = sharedPreferences.getString("posts_sort", "Date");
           comments_sort =  sharedPreferences.getString("comments_sort", "Date");
        } catch (Exception e) {
            throw e;
        }

        findPreference("posts_sort").setSummary(posts_sort);
        findPreference("comments_sort").setSummary(comments_sort);
        findPreference("posts_sort").setOnPreferenceChangeListener(listener);
        findPreference("comments_sort").setOnPreferenceChangeListener(listener);

        Preference date = findPreference("date");
        date.setOnPreferenceClickListener(clickListener);
        date.setOnPreferenceChangeListener(listener);

    }


    private Preference.OnPreferenceClickListener clickListener = new Preference.OnPreferenceClickListener() {

        @Override
        public boolean onPreferenceClick(final Preference preference) {
            DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    preference.setSummary(day + "/" + month + "/" + year);
                }
            };
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog( preference.getContext() ,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialog.show();
            return true;
        }
    };
    private Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference instanceof ListPreference) {
                findPreference(preference.getKey()).setSummary(newValue.toString());
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                //sharedPreferences.edit().putString(preference.getKey(),newValue.toString()).commit();

                //preference.setSummary(value);
                return true;
            } else {
                Log.d("TAG", newValue.toString());
            }
            return true;
        }
    };

}
