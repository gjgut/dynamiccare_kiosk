package com.PowerLog.Common.Component;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.PowerLog.Activity.Login;
import com.PowerLog.Activity.Main;
import com.PowerLog.R;

import com.PowerLog.Fragment.*;

public class DCActionBar implements View.OnClickListener {

    private Main main;
    private ActionBar actionBar;
    private ImageButton Home, LogOut, TimeSet;
    private TextView title;

    public DCActionBar(Main _main, ActionBar _actionBar, String title) {
        this.main = _main;
        this.actionBar = _actionBar;
        setActionBar();
        this.title.setText(title);
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }

    public void setHome(int visibility) {
        Home.setVisibility(visibility);
    }

    public void setTimeButton(int visibility) {
        TimeSet.setVisibility(visibility);
    }

    public void setActionBar() {
        try {
            ViewMapping();
            setListener();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.action_bar_home:
                    main.PlaySound(R.raw.home_button);
                    main.ReplaceFragment(new SelectMode(main), false);
                    main.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    break;
                case R.id.action_bar_logout:
                    main.PlaySound(R.raw.back_button);
                    main.ChangeActivity(Login.class);
                    break;
                case R.id.action_bar_timeset:
                    main.PlaySound(R.raw.back_button);
                    main.ReplaceFragment(new TimeSetting(main, main.getCurrentFragment()), true);
                    main.overridePendingTransition(R.anim.left_in, R.anim.right_out);
                    main.getCurrentFragment().timer.cancel();
                    break;
            }
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    private void setListener() {
        TimeSet.setOnClickListener(this);
        Home.setOnClickListener(this);
        LogOut.setOnClickListener(this);

    }

    private void ViewMapping() {
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        View mCustomView = LayoutInflater.from(main).inflate(R.layout.kiosk_custom_actionbar, null);
        actionBar.setCustomView(mCustomView);
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);

        title = mCustomView.findViewById(R.id.action_bar_title);
        Home = mCustomView.findViewById(R.id.action_bar_home);
        LogOut = mCustomView.findViewById(R.id.action_bar_logout);
        TimeSet = mCustomView.findViewById(R.id.action_bar_timeset);
    }
}