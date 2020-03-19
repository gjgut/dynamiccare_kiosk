package com.example.dynamiccare_kisok.Fragment;

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

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCListViewAdapter;
import com.example.dynamiccare_kisok.Common.Component.DCListViewItem;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.ArmCurl;
import com.example.dynamiccare_kisok.Common.Excercise.ArmExtension;
import com.example.dynamiccare_kisok.Common.Excercise.BenchPress;
import com.example.dynamiccare_kisok.Common.Excercise.CarfRaise;
import com.example.dynamiccare_kisok.Common.Excercise.DeadLift;
import com.example.dynamiccare_kisok.Common.Excercise.Excercise;
import com.example.dynamiccare_kisok.Common.Excercise.LatPullDown;
import com.example.dynamiccare_kisok.Common.Excercise.ShoulderPress;
import com.example.dynamiccare_kisok.Common.Excercise.Squat;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Object.Workout;
import com.example.dynamiccare_kisok.R;

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
        this.WorkoutJson = WorkoutJson;

    }

    public SelectWorkOut(Main main) {
        super(main);

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
        View view = inflater.inflate(R.layout.fragment_select_workout, container, false);

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

            Workout workoutlist[];
            workoutlist = new Workout[]{
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new Squat(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new CarfRaise(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),

            };
            Workout programlist[] = new Workout[8];
            programlist = new Workout[]{
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new Squat(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new CarfRaise(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3),
                    new Workout(false,
                            false, new BenchPress(main),
                            30,
                            20,
                            3)
            };

            for (Workout work : workoutlist) {
                if (work == null)
                    break;
                worklayout.setVisibility(View.VISIBLE);
                adapter_workout.Fillitem(work);
            }

            for (Workout work : programlist) {
                if (work == null)
                    break;
                planlayout.setVisibility(View.VISIBLE);
                adapter_plan.Fillitem(work);
            }
            PaintListView();
            setListener();
            Pagging();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void PaintListView() {
        try {
            JSONArray programlistArray = null;
            JSONArray workoutlistArray = null;

            Workout workoutlist[] = new Workout[8];
            Workout programlist[] = new Workout[8];

            if (!WorkoutJson.get("programList").equals(null))
                programlistArray = WorkoutJson.getJSONArray("programList");
            if (!WorkoutJson.get("privateList").equals(null))
                workoutlistArray = WorkoutJson.getJSONArray("privateList");

            for (int i = 0; i < workoutlistArray.length(); i++) {
                JSONObject workout1 = workoutlistArray.getJSONObject(i);
                boolean isKinetic = false;
                Excercise excercise = new BenchPress(main);
                switch (workout1.get("plnVwCommonCode").toString().substring(0, 1)) {
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
                switch (workout1.get("plnVwCommonCode").toString().substring(2)) {
                    case "1":
                        isKinetic = false;
                        break;
                    default:
                        isKinetic = true;
                        break;
                }
                workoutlist[i] = new Workout(false,
                        isKinetic, excercise,
                        Integer.valueOf(workout1.get("plnVwWeight").toString()),
                        Integer.valueOf(workout1.get("plnVwCount").toString()),
                        Integer.valueOf(workout1.get("plnVwSet").toString()));
            }

            for (int i = 0; i < programlistArray.length(); i++) {
                JSONObject program = programlistArray.getJSONObject(i);
                boolean isKinetic = false;
                Excercise excercise = new BenchPress(main);
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
                programlist[i] = new Workout(false,
                        isKinetic, excercise,
                        Integer.valueOf(program.get("plnVwWeight").toString()),
                        Integer.valueOf(program.get("plnVwCount").toString()),
                        Integer.valueOf(program.get("plnVwSet").toString()));
            }

            for (Workout work : workoutlist) {
                if (work == null)
                    break;
                worklayout.setVisibility(View.VISIBLE);
                adapter_workout.Fillitem(work);
            }

            for (Workout work : programlist) {
                if (work == null)
                    break;
                planlayout.setVisibility(View.VISIBLE);
                adapter_plan.Fillitem(work);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        plan.setAdapter(adapter_plan);
        workout.setAdapter(adapter_workout);
        AdapterView.OnItemClickListener itemlistener = (parent, view, position, id) -> {
            try {
                DCListViewItem item = (DCListViewItem) parent.getItemAtPosition(position);
                if (item.getWorkout().getIsKinetic()) {
                    main.setisIsoKinetic(true);
                    main.ReplaceFragment(new ExcerciseMode(main, item.getWorkout(), true), true);
                } else {
                    main.setisIsoKinetic(false);
                    main.ReplaceFragment(new ExcerciseMode(main, item.getWorkout(), true), true);
                }
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
