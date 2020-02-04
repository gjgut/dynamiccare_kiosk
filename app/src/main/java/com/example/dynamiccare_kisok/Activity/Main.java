package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Common.Component.DCActionBar;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Fragment.DetailResult;
import com.example.dynamiccare_kisok.Fragment.ExcerciseMode;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.R;

public class Main extends AppCompatActivity implements View.OnClickListener {
    DCfragment currentFragment;
    ImageButton btn_back, btn_next;
    DCActionBar customActionBar;
    static ConstraintLayout bottombar;
    FragmentManager fragmentManager;
    static boolean isIsoKinetic,isIsoTonic;
    static Excercise currentExcercise;
    public static ConstraintLayout getBottombar()
    {
        return bottombar;
    }

    public static void setisIsoKinetic(boolean value){
        isIsoKinetic = value;
    }
    public static void setIsIsoTonic(boolean value)
    {
        isIsoTonic = value;
    }
    public static boolean getisIsoKinetic()
    {
        return isIsoKinetic;
    }
    public static boolean getisIsoTonic()
    {
        return  isIsoTonic;
    }
    public static Excercise getCurrentExcercise()
    {
        return currentExcercise;
    }
    public static void setCurrentExcercise(Excercise excercise){currentExcercise = excercise;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customActionBar = new DCActionBar(this, getSupportActionBar(), "메인");
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        bottombar = findViewById(R.id.Bottom);

        btn_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);

        ReplaceFragment(new SelectMode(this));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                ReplaceFragment(currentFragment.getBackFragment(),false);
                break;
            }
            case R.id.btn_next: {
                ReplaceFragment(currentFragment.getNextFragment(),true);
                break;
            }
        }
    }

    public void ReplaceFragment(DCfragment fragment,boolean isRight)
    {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if(isRight && fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
        }
        else if(!isRight && fragment.getClass() != SelectMode.class)
        {
            bottombar.setVisibility(View.VISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }
        else
        {
            bottombar.setVisibility(View.INVISIBLE);
            fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
        }


        if(currentFragment.getClass() == ExcerciseMode.class  || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);

        switch(fragment.getClass().getSimpleName())
        {
            case "Explain":
            {
                btn_next.setVisibility(View.INVISIBLE);
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_instruct));
                break;
            }
            case "Instruction":
            {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_lograedy));
                break;
            }
            case "GraphResult":
            {
                btn_next.setImageDrawable(getResources().getDrawable(R.drawable.btn_logdetail));
                break;
            }
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();
    }

    public void ReplaceFragment(DCfragment fragment)
    {

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        currentFragment = fragment;

        if(fragment.getClass() != SelectMode.class) {
            bottombar.setVisibility(View.VISIBLE);
        }
        else
        {
            bottombar.setVisibility(View.INVISIBLE);
        }

        customActionBar.setHome(fragment.isHomeVisible());
        customActionBar.setTitle(fragment.getTitle());
        if(currentFragment.getClass() == ExcerciseMode.class  || currentFragment.getClass() == DetailResult.class)
            btn_next.setVisibility(View.INVISIBLE);
        else
            btn_next.setVisibility(View.VISIBLE);
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();
    }
}
