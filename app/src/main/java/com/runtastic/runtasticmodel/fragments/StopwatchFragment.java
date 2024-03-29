package com.runtastic.runtasticmodel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.runtastic.runtasticmodel.R;
import com.runtastic.runtasticmodel.helpers.Chronometer;

public class StopwatchFragment extends Fragment {


    private TextView mTvTime;
    private Button mBtnStart;
    private Button mBtnLap;
    private Button mBtnStop;
    private TextView splits;

    private int splitCtr = 1;

    private Chronometer mChronometer = new Chronometer(this);
    private Thread mThreadChrono = new Thread(mChronometer);

    public boolean haltDisplayForLap= false;
    private Fragment stopWatchFragment;

    View myView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.stopwatch_layout,container,false);



        stopWatchFragment = this;

        mTvTime = myView.findViewById(R.id.tv_time);
        mBtnStart = myView.findViewById(R.id.btn_start);
        mBtnLap = myView.findViewById(R.id.btn_lap);
        mBtnStop = myView.findViewById(R.id.btn_stop);
        splits = myView.findViewById(R.id.textView11);

        //btn_start click handler
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if chronometer has not been instantiated before...
                if(mChronometer == null){
                    //instantiated the chronometer
                    mChronometer = new Chronometer(stopWatchFragment);
                    //run the chronometer on a separate thread
                    mThreadChrono = new Thread(mChronometer);
                    mThreadChrono.start();

                    //start the chronometer
                   mChronometer.start();
                }

                if(!mChronometer.isRunning()){
                    mThreadChrono = new Thread(mChronometer);
                    mThreadChrono.start();
                    mChronometer.start();
                }

            }
        });

        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before
                if(mChronometer.isRunning()){
                    //stop the chronometer
                    //mChronometer.stop();
                    //stop the thread
                    //mThreadChrono.interrupt();
                    //mThreadChrono = null;
                    //kill the chrono class
                    mThreadChrono.interrupt();
                    mChronometer.stop();
                }else{
                    String resetTimeText= "00:00:00:000";
                    //TODO: Integer.toString(R.string.timerStart);
                    // fix this reference - it is returning an int and the parse is helping
                    updateTimerText(resetTimeText);
                    mChronometer.reset();
                    splits.setText("Laps");
                    splitCtr = 1;
                }

            }
        });

        mBtnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the chronometer had been instantiated before
                if(mChronometer.isRunning()){
                    //add lap data to textview.
                    String text = splits.getText().toString() + "\n" + splitCtr + " " +  mTvTime.getText().toString();
                    splits.setText(text);
                    splitCtr++;
                }
            }
        });
        return myView;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mThreadChrono.interrupt();
        mChronometer.stop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mThreadChrono = null;
        mChronometer = null;
    }


    public void updateTimerText(final String timeAsText) {

        if(!haltDisplayForLap){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvTime.setText(timeAsText);
                }
            });
        }

    }
}





