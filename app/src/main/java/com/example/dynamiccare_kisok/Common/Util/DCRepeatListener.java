package com.example.dynamiccare_kisok.Common.Util;

import android.os.Handler;

import android.view.MotionEvent;

import android.view.View;

import android.view.View.OnClickListener;

import android.view.View.OnTouchListener;


public class DCRepeatListener implements OnTouchListener {


    private Handler handler = new Handler();

    private int initialInterval;
    private int normalInterval;
    private OnClickListener clickListener;


    private Runnable handlerRunnable = new Runnable() {

        @Override

        public void run() {

            handler.postDelayed(this, normalInterval);

            clickListener.onClick(downView);

        }

    };


    private View downView;
    public DCRepeatListener(int initialInterval, int normalInterval,
                            OnClickListener clickListener) {
        if (clickListener == null)
            throw new IllegalArgumentException("null runnable");
        if (initialInterval < 0 || normalInterval < 0)
            throw new IllegalArgumentException("negative interval");
        this.initialInterval = initialInterval;
        this.normalInterval = normalInterval;
        this.clickListener = clickListener;

    }


    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, initialInterval);
                downView = view;
                clickListener.onClick(view);
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(handlerRunnable);
                downView = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                downView = null;
                break;
        }
        return false;
    }
}
