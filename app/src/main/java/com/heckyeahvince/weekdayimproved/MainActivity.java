package com.heckyeahvince.weekdayimproved;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements MyActivityInterface {

    public final static String DEBUG_TAG = "MainActivity";



    // DatePickerFragment for getting the date
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private DatePickerDialog dpd;
        private int count;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Initialize count
            count = 0;

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.setTitle("Input the date you want to check: "); // Override default Title
            dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "CHECK DAY", dpd); // Override Button text

            return dpd;
        }



        // When the date is set, compute the day using Zeller's Congruence 
        public void onDateSet(DatePicker view, int year, int month, int day) {

            if(count % 2 == 0) { // BUG: onDateSet() executes twice

                if (view.isShown()) {
                    dpd.updateDate(year, month, day);
                }

                // Increment month since it started at zero
                month++;

                // Adjust the month for the Zeller's Congruence formula
                if (month == 1 || month == 2) {
                    month += 12;
                    year--;
                }

                int temp_year = ((year / 10) % 10) * 10 + year % 10;
                int century = (int) (year / 100.0);

                // Debugging logs
                Log.d(DEBUG_TAG, "month = " + month);
                Log.d(DEBUG_TAG, "day = " + day);
                Log.d(DEBUG_TAG, "temp_year = " + temp_year + ", " + "century = " + century);

                // Compute Zeller's Congruence
                int day_out = (day + (int) (26 * (month + 1) / 10.0) + temp_year + (int) (temp_year / 4.0) + (int) (century / 4.0) + 5 * century) % 7;

                // Debugging log
                Log.d(DEBUG_TAG, "day_out = " + day_out);

                // Set and Display the day in TextView
                setDayMessage(day_out);
            }
            count++;
        }


		// Sets the message for the TextView depending on day_out
        public void setDayMessage(int day) {
            MyActivityInterface mai = (MyActivityInterface) getActivity();
            String day_text;
            switch (day) {
                case 0:
                    day_text = "It's Saturday!";
                    break;
                case 1:
                    day_text = "It's Sunday!";
                    break;
                case 2:
                    day_text = "It's Monday!";
                    break;
                case 3:
                    day_text = "It's Tuesday!";
                    break;
                case 4:
                    day_text = "It's Wednesday!";
                    break;
                case 5:
                    day_text = "It's Thursday!";
                    break;
                case 6:
                    day_text = "It's Friday!";
                    break;
                default:
                    day_text = "Invalid day!";
            }

            mai.setText(day_text);

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickDateDisplayDay(); // mkc
    }


    public void pickDateDisplayDay() {
        DialogFragment df = new DatePickerFragment();
        df.show(getFragmentManager(), "datePicker");
    }


    public void setText(String message){
        TextView tv_out = (TextView) findViewById(R.id.textViewOut);
        tv_out.setText(message);
        fadeAnimation(tv_out);
        tv_out.setVisibility(View.INVISIBLE);
    }

    public void fadeAnimation(TextView tv){

        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade);
        tv.startAnimation(fade);

        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                pickDateDisplayDay();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

        });
    }
}
