package com.heckyeahvince.weekdayimproved;

import android.widget.TextView;

/**
 * Created by cobalt on 10/4/15.
 */
public interface MyActivityInterface {

    // Displays the date picker and day
    public void pickDateDisplayDay();

    // Sets the TextView to message
    public void setText(String message);

    // Adds fade animation to the text
    public void fadeAnimation(TextView tv);

}
