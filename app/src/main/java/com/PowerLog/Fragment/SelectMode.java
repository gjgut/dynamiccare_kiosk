package com.PowerLog.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.PowerLog.Common.Component.DCButton;
import com.PowerLog.Common.Component.DCButtonManager;
import com.PowerLog.Common.Component.DCfragment;
import com.PowerLog.Activity.Main;
import com.PowerLog.Common.Util.Commands;
import com.PowerLog.Dialog.LoadPlan;
import com.PowerLog.R;

import org.json.JSONObject;

public class SelectMode extends DCfragment {
    ImageButton selectExec, isokinetic, isometronic, isotonic;
    LoadPlan loadPlandialog;

    public SelectMode(Main main) {
        super(main);
        if(DCButtonManager.getDCState()== DCButtonManager.State.StartSetting)

        DCButton.PressedOff();
    }

    @Override
    public void onClick(View v) {
        try {
            care.UpdateJson();
            switch (v.getId()) {
                case R.id.btn_select_exec: {
                    goExcMode(false);
                    break;
                }
                case R.id.btn_select_exec_isokinetic:
                    goExcMode(true);
                    break;

                case R.id.btn_sel_mes_isometric: {
                    main.setIsIsoTonic(false);
                    ((Main) getActivity()).ReplaceFragment(new Explain(main), true);
                    main.getusbService().write(Commands.MeasureMode(false));
                    break;
                }
                case R.id.btn_sel_mes_isotonic: {
                    main.setIsIsoTonic(true);
                    ((Main) getActivity()).ReplaceFragment(new Explain(main), true);
                    main.getusbService().write(Commands.MeasureMode(true));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(main, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void goExcMode(boolean isKinetic) {
        try {
            JSONObject resultData = (JSONObject) care.getCurrentUserJson().get("resultData");
            if (resultData.toString().contains("\"plnVwIsDone\":\"false\"") && (!resultData.get("programList").toString().equals("null") || !resultData.get("privateList").toString().equals("null"))) {
                loadPlandialog = new LoadPlan(main,
                        v1 -> {
                            loadPlandialog.dismiss();
                            main.ReplaceFragment(new SelectWorkOut(main, resultData), true);
                            main.getusbService().write(Commands.ExcerciseMode(isKinetic));
                        },
                        v12 -> {
                            main.setisIsoKinetic(isKinetic);
                            main.ReplaceFragment(new ExcerciseMode(main), true);
                            main.getusbService().write(Commands.ExcerciseMode(isKinetic));
                            loadPlandialog.dismiss();
                        });
                loadPlandialog.show();
            } else {
                main.setisIsoKinetic(isKinetic);
                main.ReplaceFragment(new ExcerciseMode(main), true);
                main.getusbService().write(Commands.ExcerciseMode(isKinetic));
            }
        } catch (NullPointerException e) {
            main.setisIsoKinetic(isKinetic);
            main.ReplaceFragment(new ExcerciseMode(main), true);
            main.getusbService().write(Commands.ExcerciseMode(isKinetic));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view;
        if (care.IsKiosk())
            view = inflater.inflate(R.layout.kiosk_fragment_select_mode, container, false);
        else
            view = inflater.inflate(R.layout.fragment_select_mode, container, false);
        try {
            main.PlaySound(new int[]{R.raw.select_the_mode, R.raw.select_the_mode_english});
            selectExec = view.findViewById(R.id.btn_select_exec);
            isokinetic = view.findViewById(R.id.btn_select_exec_isokinetic);
            isometronic = view.findViewById(R.id.btn_sel_mes_isometric);
            isotonic = view.findViewById(R.id.btn_sel_mes_isotonic);

            selectExec.setOnClickListener(this);
            isokinetic.setOnClickListener(this);
            isometronic.setOnClickListener(this);
            isotonic.setOnClickListener(this);
//            main.getusbService().write(Commands.Home(true));
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        care.UpdateJson();
    }

    @Override
    public String getTitle() {
        return "모드 선택";
    }

    @Override
    public int isHomeVisible() {
        return View.INVISIBLE;
    }

    @Override
    public DCfragment getBackFragment() {
        return null;
    }

    @Override
    public DCfragment getNextFragment() {
        return null;
    }
}
