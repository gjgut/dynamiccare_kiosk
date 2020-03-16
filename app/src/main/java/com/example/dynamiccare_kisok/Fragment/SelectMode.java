package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.DynamicCare;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Dialog.LoadPlan;
import com.example.dynamiccare_kisok.R;

import org.json.JSONObject;

public class SelectMode extends DCfragment {
    ImageButton selectExec, isokinetic, isometronic, isotonic;
    LoadPlan loadPlandialog;

    public SelectMode(Main main) {
        super(main);
        Main.setCurrentExcercise(null);
        DCButton.PressedOff();
    }

    @Override
    public void onClick(View v) {
        try {
            main.getusbService().write(Commands.Home(true));
            switch (v.getId()) {
                case R.id.btn_select_exec: {
                    try {
                    DynamicCare care = (DynamicCare) main.getApplication();
//                    JSONObject jsonObject = care.getCurrentUserJson();
//                    JSONObject resultData = (JSONObject) jsonObject.get("resultData");
//                        main.getusbService().write(Commands.Home(true).getBytes());
//                        if (!resultData.get("programList").toString().equals("null") && !resultData.get("privateList").toString().equals("null")) {
                            loadPlandialog = new LoadPlan(main,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loadPlandialog.dismiss();
                                            ((Main) getActivity()).ReplaceFragment(new SelectWorkOut(main, null), true);
                                            main.getusbService().write(Commands.ExcerciseMode(false));
                                        }
                                    },
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            main.setisIsoKinetic(false);
                                            ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                                            main.getusbService().write(Commands.ExcerciseMode(false));
                                            loadPlandialog.dismiss();
                                        }
                                    });
                            loadPlandialog.show();
//                        } else {
//                            main.setisIsoKinetic(false);
//                            ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
//                            main.getusbService().write(Commands.ExcerciseMode(false));
//                        }
                    } catch (NullPointerException e) {
                        main.setisIsoKinetic(false);
                        ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                        main.getusbService().write(Commands.ExcerciseMode(false));
                    }
                    break;
                }
                case R.id.btn_select_exec_isokinetic: {
                    try {
                    DynamicCare care = (DynamicCare) main.getApplication();
                    JSONObject jsonObject = care.getCurrentUserJson();
                    JSONObject resultData = (JSONObject) jsonObject.get("resultData");
                        if (!resultData.get("programList").toString().equals("null") && !resultData.get("privateList").toString().equals("null")) {
                            loadPlandialog = new LoadPlan(main,
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            loadPlandialog.dismiss();
                                            ((Main) getActivity()).ReplaceFragment(new SelectWorkOut(main, resultData), true);
                                            main.getusbService().write(Commands.ExcerciseMode(true));
                                        }
                                    },
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            main.setisIsoKinetic(true);
                                            ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                                            main.getusbService().write(Commands.ExcerciseMode(true));
                                            loadPlandialog.dismiss();
                                        }
                                    });
                            loadPlandialog.show();
                        } else {
                            main.setisIsoKinetic(true);
                            ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                            main.getusbService().write(Commands.ExcerciseMode(true));
                        }
                    } catch (NullPointerException e) {
                        main.setisIsoKinetic(true);
                        ((Main) getActivity()).ReplaceFragment(new ExcerciseMode(main), true);
                        main.getusbService().write(Commands.ExcerciseMode(true));
                    }
                    break;
                }
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_mode, container, false);
        try{
        main.PlaySound(new int[]{R.raw.select_the_mode, R.raw.select_the_mode_english});
        selectExec = view.findViewById(R.id.btn_select_exec);
        isokinetic = view.findViewById(R.id.btn_select_exec_isokinetic);
        isometronic = view.findViewById(R.id.btn_sel_mes_isometric);
        isotonic = view.findViewById(R.id.btn_sel_mes_isotonic);

        selectExec.setOnClickListener(this);
        isokinetic.setOnClickListener(this);
        isometronic.setOnClickListener(this);
        isotonic.setOnClickListener(this);

        main.getusbService().write(Commands.Home(true));
        }catch (Exception e)
        {
            Log.i("Error",e.toString());
        }

        return view;
    }

//    public JSONObject isTherePlan() {
//        try {
//            DynamicCare care = (DynamicCare) main.getApplication();
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.accumulate("uid", care.getCurrentUser());
//            String json = jsonObject.toString();
//            return new HttpUtil().execute(json).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

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
