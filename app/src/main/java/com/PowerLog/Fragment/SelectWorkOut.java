package com.PowerLog.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.PowerLog.Activity.Main;
import com.PowerLog.Common.Component.DCListViewAdapter;
import com.PowerLog.Common.Component.DCListViewItem;
import com.PowerLog.Common.Component.DCfragment;
import com.PowerLog.Common.Excercise.ArmCurl;
import com.PowerLog.Common.Excercise.ArmExtension;
import com.PowerLog.Common.Excercise.BenchPress;
import com.PowerLog.Common.Excercise.CarfRaise;
import com.PowerLog.Common.Excercise.DeadLift;
import com.PowerLog.Common.Excercise.Excercise;
import com.PowerLog.Common.Excercise.LatPullDown;
import com.PowerLog.Common.Excercise.ShoulderPress;
import com.PowerLog.Common.Excercise.Squat;
import com.PowerLog.Common.Object.Workout;
import com.PowerLog.Common.Util.Commands;
import com.PowerLog.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectWorkOut extends DCfragment {
    TextView txt_today;
    ConstraintLayout planlayout, worklayout;
    ImageButton btn_workout_right, btn_workout_left, btn_plan_right, btn_plan_left;
    JSONObject WorkoutJson;
    ListView plan, workout;
    DCListViewAdapter adapter_plan, adapter_workout;
    int plan_page = 1, workout_page = 1;
    int plan_max = 1, workout_max = 1;


    public SelectWorkOut() {
        super();
    }

    public SelectWorkOut(Main main, JSONObject WorkoutJson) {
        super(main);
        if (main.getCurrentExcercise() != null)
            main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                    "0",
                    "0",
                    "0"));
        main.setCurrentExcercise(null);
        care.UpdateJson();
        this.WorkoutJson = WorkoutJson;
    }

    public SelectWorkOut(Main main) {
        super(main);
        try {
            if (main.getCurrentExcercise() != null)
                main.getusbService().write(Commands.ExcerciseStop(main.getCurrentExcercise().getMode(),
                        "0",
                        "0",
                        "0"));
            main.setCurrentExcercise(null);
            care.UpdateJson();
            this.WorkoutJson = (JSONObject) care.getCurrentUserJson().get("resultData");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plan_left:
                if (plan_page > 1)
                    plan_page--;

                if (plan_page != 1)
                    btn_plan_left.setVisibility(View.VISIBLE);
                else
                    btn_plan_left.setVisibility(View.INVISIBLE);

                if (plan_page == plan_max)
                    btn_plan_right.setVisibility(View.INVISIBLE);
                else
                    btn_plan_right.setVisibility(View.VISIBLE);

                adapter_plan.setPage(plan_page);
                adapter_plan.notifyDataSetChanged();
                break;
            case R.id.btn_plan_right:
                if (plan_page < 3)
                    plan_page++;

                if (plan_page != 1)
                    btn_plan_left.setVisibility(View.VISIBLE);
                else
                    btn_plan_left.setVisibility(View.INVISIBLE);

                if (plan_page == plan_max)
                    btn_plan_right.setVisibility(View.INVISIBLE);
                else
                    btn_plan_right.setVisibility(View.VISIBLE);
                adapter_plan.setPage(plan_page);
                adapter_plan.notifyDataSetChanged();
                break;
            case R.id.btn_workout_left:

                if (workout_page > 1)
                    workout_page--;

                if (workout_page != 1)
                    btn_workout_left.setVisibility(View.VISIBLE);
                else
                    btn_workout_left.setVisibility(View.INVISIBLE);

                if (workout_page == workout_max)
                    btn_workout_right.setVisibility(View.INVISIBLE);
                else
                    btn_workout_right.setVisibility(View.VISIBLE);
                adapter_workout.setPage(workout_page);
                adapter_workout.notifyDataSetChanged();
                break;
            case R.id.btn_workout_right:
                if (workout_page < workout_max)
                    workout_page++;

                if (workout_page != 1)
                    btn_workout_left.setVisibility(View.VISIBLE);
                else
                    btn_workout_left.setVisibility(View.INVISIBLE);

                if (workout_page == workout_max)
                    btn_workout_right.setVisibility(View.INVISIBLE);
                else
                    btn_workout_right.setVisibility(View.VISIBLE);
                adapter_workout.setPage(workout_page);
                adapter_workout.notifyDataSetChanged();
                break;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if (care.IsKiosk())
            view = inflater.inflate(R.layout.kiosk_fragment_select_workout, container, false);
        else
            view = inflater.inflate(R.layout.fragment_select_workout, container, false);

        try {
            Date currentTime = Calendar.getInstance().getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);


            worklayout = view.findViewById(R.id.Workout);
            planlayout = view.findViewById(R.id.Plan);

            txt_today = view.findViewById(R.id.txt_today);
            txt_today.setText(date_text);

            btn_plan_left = view.findViewById(R.id.btn_plan_left);
            btn_plan_right = view.findViewById(R.id.btn_plan_right);
            btn_workout_left = view.findViewById(R.id.btn_workout_left);
            btn_workout_right = view.findViewById(R.id.btn_workout_right);

            // 리스트뷰 참조 및 Adapter 달기
            plan = view.findViewById(R.id.list_plan);
            workout = view.findViewById(R.id.list_workout);

            adapter_plan = new DCListViewAdapter(R.layout.list_plan_item);
            adapter_workout = new DCListViewAdapter(R.layout.list_workout_item);
            care.UpdateJson();
            this.WorkoutJson = (JSONObject) care.getCurrentUserJson().get("resultData");
            PaintListView();
            setListener();
            Pagging();

            //            if (adapter_plan.getSize() == 0 && adapter_workout.getSize() == 0)
//                main.ReplaceFragment(new SelectMode(main), false);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private Workout[] FillWorkoutArray(String target, boolean isPlan) {
        try {
            if (!WorkoutJson.get(target).equals(null)) {
                Workout Arraylist[] = new Workout[8];
                if (!WorkoutJson.get(target).equals(null)) {
                    JSONArray Array = WorkoutJson.getJSONArray(target);
                    for (int i = 0; i < Array.length(); i++) {
                        JSONObject program = Array.getJSONObject(i);
                        boolean isKinetic;
                        Excercise excercise = null;
                        if (Boolean.valueOf(program.get("plnVwIsDone").toString()))
                            continue;
                        switch (program.get("plnVwCommonCode").toString().substring(0, 1)) {
                            case "A":
                                excercise = new BenchPress(main);
                                break;
                            case "B":
                                excercise = new Squat(main);
                                break;
                            case "C":
                                excercise = new DeadLift(main);
                                break;
                            case "D":
                                excercise = new ShoulderPress(main);
                                break;
                            case "E":
                                excercise = new CarfRaise(main);
                                break;
                            case "F":
                                excercise = new ArmCurl(main);
                                break;
                            case "G":
                                excercise = new ArmExtension(main);
                                break;
                            case "H":
                                excercise = new LatPullDown(main);
                                break;
                        }
                        switch (program.get("plnVwCommonCode").toString().substring(2)) {
                            case "1":
                                isKinetic = false;
                                break;
                            default:
                                isKinetic = true;
                                break;
                        }
                        Arraylist[i] = new Workout(false,
                                isKinetic, excercise,
                                Integer.valueOf(isKinetic ? program.get("plnVwLevel").toString() : program.get("plnVwWeight").toString()),
                                Integer.valueOf(program.get("plnVwCount").toString()),
                                Integer.valueOf(program.get("plnVwSet").toString()),
                                Integer.valueOf(program.get("plnVwRest").toString()),
                                program.get("plnVwId").toString());
                        if (Arraylist[i] == null)
                            continue;
                        if (isPlan) {
                            planlayout.setVisibility(View.VISIBLE);
                            adapter_plan.Fillitem(Arraylist[i]);
                        } else {
                            worklayout.setVisibility(View.VISIBLE);
                            adapter_workout.Fillitem(Arraylist[i]);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void PaintListView() {
        FillWorkoutArray("programList", true);
        FillWorkoutArray("privateList", false);
    }

    private void setListener() {
        plan.setAdapter(adapter_plan);
        workout.setAdapter(adapter_workout);
        AdapterView.OnItemClickListener itemlistener = (parent, view, position, id) -> {
            try {
                DCListViewItem item = (DCListViewItem) parent.getItemAtPosition(position);
                main.setisIsoKinetic(item.getWorkout().getIsKinetic());
                main.ReplaceFragment(new ExcerciseMode(main, this, item.getWorkout(), parent.getId() == R.id.list_plan), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        plan.setOnItemClickListener(itemlistener);
        workout.setOnItemClickListener(itemlistener);

        btn_workout_right.setOnClickListener(this);
        btn_workout_left.setOnClickListener(this);
        btn_plan_right.setOnClickListener(this);
        btn_plan_left.setOnClickListener(this);
    }

    private void Pagging() {
        if (adapter_plan.getSize() == 1 || adapter_plan.getSize() == 2 || adapter_plan.getSize() == 3)
            plan_max = 1;
        else if (adapter_plan.getSize() == 4 || adapter_plan.getSize() == 5 || adapter_plan.getSize() == 6)
            plan_max = 2;
        else
            plan_max = 3;

        if (adapter_workout.getSize() == 1 || adapter_workout.getSize() == 2 || adapter_workout.getSize() == 3)
            workout_max = 1;
        else if (adapter_workout.getSize() == 4 || adapter_workout.getSize() == 5 || adapter_workout.getSize() == 6)
            workout_max = 2;
        else
            workout_max = 3;


        if (workout_page != 1)
            btn_workout_left.setVisibility(View.VISIBLE);
        else
            btn_workout_left.setVisibility(View.INVISIBLE);

        if (workout_page == workout_max)
            btn_workout_right.setVisibility(View.INVISIBLE);
        else
            btn_workout_right.setVisibility(View.VISIBLE);

        if (plan_page != 1)
            btn_plan_left.setVisibility(View.VISIBLE);
        else
            btn_plan_left.setVisibility(View.INVISIBLE);

        if (plan_page == plan_max)
            btn_plan_right.setVisibility(View.INVISIBLE);
        else
            btn_plan_right.setVisibility(View.VISIBLE);

        adapter_plan.setPage(1);
        adapter_workout.setPage(1);
    }

    @Override
    public DCfragment getBackFragment() {
        return new SelectMode(main);
    }


    @Override
    public String getTitle() {
        return "플랜 및 일정 선택";
    }

    @Override
    public int isHomeVisible() {
        return 0;
    }

}
