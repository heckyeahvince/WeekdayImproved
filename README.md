# WeekDay App using Date Picker Fragment (with basic Text Fade IN/OUT Animation)

weekdayimproved-melvincabatuan created by Classroom for GitHub

This assignment illustrates the basic application of Date Picker Fragment. It also introduces simple animation in Android.

## Problem:

Design and implement an Android application that determines the day of the week (Sunday through Saturday) given any date entered by the user. You could use this program, for example, to determine what day of the week you were born or what day of the week an upcoming exam is. Use Zeller's congruence algorithm to calculate the day of the week. The output day should be printed in a TextView with fade in and fade out animation. Input the date using the Date Picker Fragment.

## Formula:

```Java
day_out = (day + (int)(26 *(month + 1)/10.0) + year + (int)(year/4.0) + (int)(century/4.0) + 5 * century ) % 7;
```

where:

day_out - is the calculated day of the week (0..6) 

day     - is the day entered by the user.

month   - is the adjusted month (January and February are 13 and 14, not 1 and 2)

year    - is the last two digits of the adjusted year (January and February are in the previous year). For example: if year is 2003, then year would be 03

century - is the century of the adjusted year (January and February are in the previous year). For example, if year is 2003, then century is 20

## Accept

To accept the assignment, click the following URL:

https://classroom.github.com/assignment-invitations/402224e6245bbc7726d3a5431a12247c

## Sample Solution:

https://github.com/DeLaSalleUniversity-Manila/weekdayimproved-melvincabatuan

## Keypoints:

In the MyActivityInterface.java (for interfacing Activity to Fragments):

```Java
public interface MyActivityInterface {

    // Displays the date picker and day
    public void pickDateDisplayDay();

    // Sets the TextView to message
    public void setText(String message);

    // Adds fade animation to the text
    public void fadeAnimation(TextView tv);

}
```

The Date Picker Fragment is implemented as follows:

```Java
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
```


The animation xml for fade in/out:

```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:shareInterpolator="false">
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:duration="2500">
    </alpha>
    <alpha
        android:fromAlpha="1.0"
        android:toAlpha="0.0"
        android:startOffset="2500"
        android:duration="2500">
    </alpha>
</set>
```

The animation method as defined in MainActivity.java;

```java
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
```


## Submission Procedure with Git: 

```shell
$ cd /path/to/your/android/app/
$ git init
$ git add –all
$ git commit -m "your message, e.x. Assignment 1 submission"
$ git remote add origin <Assignment link copied from assignment github, e.x. https://github.com/DeLaSalleUniversity-Manila/secondactivityassignment-melvincabatuan.git>
$ git push -u origin master
<then Enter Username and Password>
```

Sample:

https://gist.github.com/melvincabatuan/defd8992ecdce9c73d7e

## Screenshots:

![alt tag](https://github.com/DeLaSalleUniversity-Manila/weekdayimproved-melvincabatuan/blob/master/device-2015-10-04-082749.png)

![alt tag](https://github.com/DeLaSalleUniversity-Manila/weekdayimproved-melvincabatuan/blob/master/device-2015-10-04-082812.png)

![alt tag](https://github.com/DeLaSalleUniversity-Manila/weekdayimproved-melvincabatuan/blob/master/device-2015-10-04-082834.png)

![alt tag](https://github.com/DeLaSalleUniversity-Manila/weekdayimproved-melvincabatuan/blob/master/device-2015-10-04-082918.png)


"*Example teaches better than precept. It is the best modeler of the character of men and women. To set a lofty example is the richest bequest a man can leave behind him.*" - Samuel Smiles
