package com.example.dynamiccare_kisok.Common.Component;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.dynamiccare_kisok.Activity.Login;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

import com.example.dynamiccare_kisok.Fragment.*;

import java.sql.Time;

public class DCActionBar {

    private Main main;
    private ActionBar actionBar;
    private ImageButton Home,LogOut,TimeSet;
    private TextView title;

    public DCActionBar(Main _main, ActionBar _actionBar, String title){
        this.main = _main;
        this.actionBar = _actionBar;
        setActionBar();
        this.title.setText(title);
    }

    public void setTitle(String text)
    {
        this.title.setText(text);
    }

    public void setHome(int visibility)
    {
        Home.setVisibility(visibility);
    }

    public void setTimeButton(int visibility){TimeSet.setVisibility(visibility);}

    public void setActionBar(){
        try {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);

            View mCustomView = LayoutInflater.from(main)
                    .inflate(R.layout.custom_actionbar, null);
            actionBar.setCustomView(mCustomView);

            // Custom ActionBar 생성하면 양쪽에 공백이 생기는데 이 공백을 채우기 위해 아래 4줄 적용
            Toolbar parent = (Toolbar) mCustomView.getParent();
            parent.setContentInsetsAbsolute(0, 0);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(mCustomView, params);

            title = (TextView) mCustomView.findViewById(R.id.action_bar_title);
            Home = mCustomView.findViewById(R.id.action_bar_home);
            LogOut = mCustomView.findViewById(R.id.action_bar_logout);
            TimeSet = mCustomView.findViewById(R.id.action_bar_timeset);

            TimeSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    main.PlaySound(R.raw.back_button);
                    main.ReplaceFragment(new TimeSetting(main,main.getCurrentFragment()),true);
                    main.overridePendingTransition(R.anim.left_in,R.anim.right_out);
                }
            });

            Home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    main.PlaySound(R.raw.home_button);
                    main.ReplaceFragment(new SelectMode(main),true);
                    main.overridePendingTransition(R.anim.left_in,R.anim.right_out);
                }
            });
            LogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    main.PlaySound(R.raw.back_button);
                    Intent intent = new Intent(main, Login.class);
                    main.startActivity(intent);
                    main.overridePendingTransition(R.anim.left_in,R.anim.right_out);
                    main.finish();
                }
            });

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}