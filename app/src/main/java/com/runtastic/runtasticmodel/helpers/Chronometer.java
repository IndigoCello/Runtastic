package com.runtastic.runtasticmodel.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import com.runtastic.runtasticmodel.fragments.StopwatchFragment;
import com.runtastic.runtasticmodel.runtasticmodel;
import java.util.Locale;

public class Chronometer implements Runnable {

    //some constants for milliseconds to hours,minutes,and seconds conversion
    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLS_TO_HOURS = 3600000;

    Fragment stopWatchFragment;
    long mStartTime;

    boolean mIsRunning;

    public Chronometer(Fragment fragment) {
        stopWatchFragment = fragment;
    }

    public Chronometer(Fragment fragment, long startTime) {
        this(fragment);
        mStartTime = startTime;
    }


    public void start() {
        if (mStartTime == 0) {
            mStartTime = System.currentTimeMillis();
        }

        mIsRunning = true;
    }

    public void stop() {
        mIsRunning = false;
    }

    public void reset() {
        mStartTime = System.currentTimeMillis();
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public long getStartTime() {
        return mStartTime;
    }

    @Override
    public void run() {

        while (mIsRunning) {

            //here we calculate the different of starting time and current time
            long since = System.currentTimeMillis() - mStartTime;


            //converts the resulted time difference into hours, minutes, seconds, milliseconds
            int seconds = (int) (since / 1000) % 60;
            int minutes = (int) ((since / (MILLIS_TO_MINUTES)) % 60);
            int hours = (int) ((since / (MILLS_TO_HOURS)) % 24);
            int millis = (int) since % 1000;//the last 3 digits of milliseconds

            try {
                ((StopwatchFragment) stopWatchFragment).updateTimerText(String.format("%02d:%02d:%02d:%03d"
                        , hours, minutes, seconds, millis));
            } catch (Exception e) {
                //TODO: bad fix!!!! replace with test of stopwatchFragment
            }

        }

    }

}
