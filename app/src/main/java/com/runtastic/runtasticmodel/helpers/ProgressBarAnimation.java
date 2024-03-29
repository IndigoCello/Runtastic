package com.runtastic.runtasticmodel.helpers;

/********************************************
 * RuntasticProgressBar.java
 * ProgressBarAnimation example from https://stackoverflow.com/questions/8035682/animate-progressbar-update-in-android
 * To give a smooth progress bar animation even if nothing really being loaded.
 */

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class ProgressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;

    public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }

}