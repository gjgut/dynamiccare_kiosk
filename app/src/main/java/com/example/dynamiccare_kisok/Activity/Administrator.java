package com.example.dynamiccare_kisok.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dynamiccare_kisok.Common.Component.DCActivity;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Util.ACKListener;
import com.example.dynamiccare_kisok.Common.Util.DCSoundPlayer;
import com.example.dynamiccare_kisok.Common.Util.DCSoundThread;
import com.example.dynamiccare_kisok.Common.Util.UsbService;
import com.example.dynamiccare_kisok.Fragment.Administrator.Authentification;
import com.example.dynamiccare_kisok.Fragment.DetailResult;
import com.example.dynamiccare_kisok.Fragment.ExcerciseMode;
import com.example.dynamiccare_kisok.Fragment.SelectMode;
import com.example.dynamiccare_kisok.R;

public class Administrator extends DCActivity implements View.OnClickListener {
    DCfragment currentFragment;
    ImageButton btn_back;
    FragmentManager fragmentManager;
    DCSoundThread dcSoundThread;
    DCSoundPlayer dcSoundPlayer;
    Handler handler;


    public void PlaySound(int soundId) {
        dcSoundPlayer.playwithNoInterrept(soundId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        try {
            ViewMapping();

            ReplaceFragment(new Authentification(this), true);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Override
    public void onClick(View v) {
        try {
            dcSoundPlayer.play(R.raw.back_button);
            boolean flag = v.getId() != R.id.btn_back;
            isNextFragment(flag);
        }
        catch (Exception e) {
            Log.i("Error", e.toString());
        }
    }

    @Override
    protected void ViewMapping() {
        handler = new Handler();
        dcSoundPlayer = care.getDcSoundPlayer();
        dcSoundThread = new DCSoundThread(this, dcSoundPlayer);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

    }

    @Override
    protected void setListener() {

    }

    public void ReplaceFragment(DCfragment fragment, boolean isRight) {
        try {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            currentFragment = fragment;
            if (isRight) {
                fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.right_out);
            }
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commit();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void isNextFragment(boolean isNext) {
        if(currentFragment.getBackFragment()==null)
        {
            startActivity(new Intent(this, Login.class));
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
            return;
        }
        if (isNext)
            ReplaceFragment(currentFragment.getNextFragment(), true);
        else
            ReplaceFragment(currentFragment.getBackFragment(), false);
    }

}
