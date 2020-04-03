package com.example.dynamiccare_kisok.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCActionButton;
import com.example.dynamiccare_kisok.Common.Component.DCButton;
import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.Common.Component.DCEditText;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Object.MeasureResult;
import com.example.dynamiccare_kisok.Common.Util.Commands;
import com.example.dynamiccare_kisok.Common.Util.DCHttp;
import com.example.dynamiccare_kisok.Dialog.NormalAlert;
import com.example.dynamiccare_kisok.R;

import org.json.JSONObject;

public class GraphResult extends DCfragment implements View.OnTouchListener, View.OnFocusChangeListener {

    DCButton Low, Mid, High;
    DCActionButton Up, Down;
    DCActionButton ready, go;
    ProgressBar power;
    MeasureResult resCalculator;
    DCEditText edt_time, edt_weight;
    DCButtonManager dcButtonManager;
    boolean isSend = true;


    public GraphResult(Main main) {
        super(main);
        try {
            if (dcButtonManager.getDCState() != DCButtonManager.State.MeasureClear &&
                    dcButtonManager.getDCState() != DCButtonManager.State.MeasureReady &&
                    dcButtonManager.getDCState() != DCButtonManager.State.Measuring)
                main.PlaySound(
                        new int[]{R.raw.power_log,
                                R.raw.setting_is_completed,
                                R.raw.follow_instruction,
                                R.raw.take_pose_and_place_bar_or_wire_to_right_position,
                                R.raw.effort_maximally_during_measurement,
                                R.raw.dont_stop_measurement_by_stop_sound,
                                R.raw.measurement_begin_soon,
                                R.raw.please_follow_the_directions_english,
                                R.raw.adjust_the_bar_or_wire_properly_english,
                                R.raw.please_do_your_best_in_measuring_english,
                                R.raw.do_not_stop_measuring_until_the_end_comment_is_made_english,
                                R.raw.the_measurement_wil_begin_shortly_english});
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }


    public void setPropertiesFocusable(boolean value) {
        try {
            edt_time.getSource().setEnabled(value);
            edt_weight.getSource().setEnabled(value);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }


    public void setBottomBar(boolean isShow) {
        try {
            if (!isShow)
                Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.INVISIBLE);
            else
                Main.getBottombar().findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void setViews(View v) {
        try {
            Up = new DCActionButton(main, v.findViewById(R.id.btn_up), null);
            Down = new DCActionButton(main, v.findViewById(R.id.btn_down), null);

            Low = new DCButton(main, v.findViewById(R.id.btn_low), getResources().getDrawable(R.drawable.pressed_btn_low));
            Mid = new DCButton(main, v.findViewById(R.id.btn_mid), getResources().getDrawable(R.drawable.pressed_btn_mid));
            High = new DCButton(main, v.findViewById(R.id.btn_high), getResources().getDrawable(R.drawable.pressed_btn_high));

            Low.setPressedWithNoSound();
            if (Low.IsPressed())
                main.getusbService().write(Commands.MeasureLevelCheck("L"));

            ready = new DCActionButton(main, v.findViewById(R.id.btn_ready), getResources().getDrawable(R.drawable.pressed_btn_ready));
            go = new DCActionButton(main, v.findViewById(R.id.btn_start), getResources().getDrawable(R.drawable.pressed_btn_start));

            power = v.findViewById(R.id.progressBar_power);

            edt_time = new DCEditText(v.findViewById(R.id.edt_time));
            edt_weight = new DCEditText(v.findViewById(R.id.edt_weight));

//            edt_time.getSource().setText(main.getMeasureTime());
//            edt_weight.getSource().setText(main.getMeasureWeight());

            setListener();

            dcButtonManager = new DCButtonManager(go, ready, Up, Down);
            dcButtonManager.setDCState(dcButtonManager.getDCState());
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }
    }

    private void setListener() {
        edt_time.getSource().setOnFocusChangeListener(this);
        edt_weight.getSource().setOnFocusChangeListener(this);


        Low.getButton().setOnClickListener(this);
        Mid.getButton().setOnClickListener(this);
        High.getButton().setOnClickListener(this);

        Up.getButton().setOnTouchListener(this);
        Down.getButton().setOnTouchListener(this);
        ready.getButton().setOnClickListener(this);
        go.getButton().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        go.setPressedwithNoSound();
                        break;
                    case MotionEvent.ACTION_UP:
                        go.setPressed();
                        go.setPause();
                        if (go.isPause()) {
                            DCButtonManager.setDCState(DCButtonManager.State.Measuring);
                            isSend = true;
                            setBottomBar(false);
                            if (resCalculator != null)
                                main.getusbService().write("$CSP0#".getBytes());
                            main.getusbService().write(Commands.MeasureStart(edt_weight.getSource().getText().toString(), edt_time.getSource().getText().toString()));
                            resCalculator = new MeasureResult();

                            go.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_stop));
                            go.setButton(go.getButton(), getResources().getDrawable(R.drawable.pressed_btn_stop));
                        } else {
                            DCButtonManager.setDCState(DCButtonManager.State.StartSetting);
                            isSend = false;
                            if (timer != null)
                                timer.cancel();
                            main.getusbService().write("$CSP0#".getBytes());
                            main.getusbService().write(Commands.Home(true));
                            main.PlaySound(new int[]{R.raw.stopping_measurement, R.raw.thank_you_for_your_efforts, R.raw.the_measurement_is_going_to_stop_english, R.raw.thank_you_for_your_efforts_english});

                            go.Deactivate();
                            go.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_start));
                            go.setButton(go.getButton(), getResources().getDrawable(R.drawable.pressed_btn_start));

                            if (ready.IsPressed())
                                ready.setPressedwithNoSound();
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void SendResult() {
        Log.i("Send", "LogSend!!");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("commonCode", main.getCurrentExcercise().getDBCode() + ((main.getisIsoTonic()) ? "02" : "01"));
            jsonObject.accumulate("device", main.getCare().getDeviceID().toString());
            jsonObject.accumulate("email", care.getCurrentUserJson().get("email"));
            jsonObject.accumulate("start", resCalculator.getStart() == 0 ? 0 : resCalculator.getStart() / 1000);
            jsonObject.accumulate("max", resCalculator.getMax() == 0 ? 0 : resCalculator.getMax() / 1000);
            jsonObject.accumulate("min", resCalculator.getMin() == 0 ? 0 : resCalculator.getMin() / 1000);
            jsonObject.accumulate("average", resCalculator.getAverage() == 0 ? 0 : resCalculator.getAverage() / 1000);

            new DCHttp().SendResult(jsonObject.toString());
            new NormalAlert(main, "결과를 전송하였습니다.", true).show();
        } catch (Exception e) {
//            new NormalAlert(main, e.toString() + "exc:" + main.getCurrentExcercise(), true).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.edt_weight) {
            if (!hasFocus)
                if (Integer.valueOf(edt_time.getSource().getText().toString()) > 500)
                    new NormalAlert(main, "입력할 수 있는 무게 범위를 초과하였습니다.").show();
                else
                    main.setMeasureWeight(Integer.valueOf(edt_weight.getSource().getText().toString()));
        } else if (v.getId() == R.id.edt_time) {
            if (!hasFocus) {
                if (Integer.valueOf(edt_time.getSource().getText().toString()) > 999)
                    new NormalAlert(main, "입력할 수 있는 시간 범위를 초과하였습니다.").show();
                else
                    main.setMeasureTime(Integer.valueOf(edt_time.getSource().getText().toString()));
            }

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (v.getId() == R.id.btn_up) {
                        Up.getButton().setImageDrawable(getResources().getDrawable(R.drawable.pressed_btn_up));
                        timer = new CountDownTimer(1000000, 1) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                main.getusbService().write(Commands.Position("U"));
                                Log.i("Position", "U");
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        timer.start();
                    } else {
                        Down.getButton().setImageDrawable(getResources().getDrawable(R.drawable.pressed_btn_down));
                        timer = new CountDownTimer(1000000, 1) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                main.getusbService().write(Commands.Position("D"));
                                Log.i("Position", "D");
                            }

                            @Override
                            public void onFinish() {

                            }
                        };
                        timer.start();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (v.getId() == R.id.btn_up) {
                        Up.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_up));
                        timer.cancel();
                        Log.i("Sent Command", "stop");
                    } else {
                        Down.getButton().setImageDrawable(getResources().getDrawable(R.drawable.btn_down));
                        timer.cancel();
                        Log.i("Sent Command", "stop");
                    }
                    break;
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.btn_ready:
                    if (!ready.IsPressed()) {
                        ready.setPressed();
                        DCButtonManager.setDCState(DCButtonManager.State.MeasureReady);
                        setPropertiesFocusable(false);
                        main.PlaySound(new int[]{R.raw.mesurement_will_begin_after_bee_sound, R.raw.the_measurement_starts_when_you_hear_the_beep_sound_english});
                        main.getusbService().write(Commands.MeasureReady(String.valueOf(main.getMeasureWeight()), String.valueOf(main.getMeasureTime())));
                        go.Activate();
                    }
                    break;
                case R.id.btn_low: {
                    if (DCButton.getPressedButton() != Low)
                        Low.setPressed();
                    if (Low.IsPressed())
                        main.getusbService().write(Commands.MeasureLevelCheck("L"));
                    break;
                }
                case R.id.btn_mid: {

                    if (DCButton.getPressedButton() != Mid)
                        Mid.setPressed();
                    if (Mid.IsPressed())
                        main.getusbService().write(Commands.MeasureLevelCheck("M"));
                    break;
                }

                case R.id.btn_high: {

                    if (DCButton.getPressedButton() != High)
                        High.setPressed();
                    if (High.IsPressed())
                        main.getusbService().write(Commands.MeasureLevelCheck("H"));
                    break;
                }

            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_result_graph, container, false);
            setViews(view);
            setBottomBar(false);
            return view;
        } catch (Exception e) {
//            Toast.makeText(main, e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
    }


    @Override
    public void HandleACK(ACK ack) {
        try {
            Log.i("ParseACK", ack.getCommandCode());
            switch (ack.getCommandCode()) {
                case "AME":
                    if (Integer.parseInt(ack.getTime()) < 1000) {
                        resCalculator.putBase(Integer.parseInt(ack.getmTension()));
                        if (Integer.parseInt(ack.getmTension()) < resCalculator.getMax())
                            break;
                        power.setProgress(Integer.parseInt(ack.getmTension()));
                    } else {
                        resCalculator.putNumber(Integer.parseInt(ack.getmTension()));
                        if (Integer.parseInt(ack.getmTension()) < resCalculator.getMax())
                            break;
                        power.setProgress(Integer.parseInt(ack.getmTension()));
                    }
                    break;
                case "ASP":
                    if (DCButtonManager.getDCState() == DCButtonManager.State.Clear)
                        break;
                    Log.i("Measure ended:", "");
                    DCButtonManager.setDCState(DCButtonManager.State.StartSetting);
                    setBottomBar(false);
                    main.PlaySound(new int[]{R.raw.measurement_complete_sound,
                            R.raw.stopping_measurement,
                            R.raw.thank_you_for_your_efforts,
                            R.raw.show_your_result,
                            R.raw.the_measurement_is_going_to_stop_english,
                            R.raw.thank_you_for_your_efforts_english,
                            R.raw.please_check_the_results_english,
                            R.raw.dynamic_care});
                    setBottomBar(true);
                    if (isSend)
                        SendResult();
                    break;
                case "PCA":
//                case "AHM":
                    DCButtonManager.setDCState(DCButtonManager.State.Setted);
                    if (ready.IsPressed())
                        ready.setPressedwithNoSound();
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        if (main.getisIsoTonic())
            return "등척성 측정 모드";
        else
            return "등장성 측정 모드";
    }

    @Override
    public int isHomeVisible() {
        return View.VISIBLE;
    }

    @Override
    public DCfragment getBackFragment() {
        return new Instruction(main);
    }

    @Override
    public DCfragment getNextFragment() {
        return new DetailResult(main, resCalculator.getStart(), resCalculator.getMax(), resCalculator.getMin(), resCalculator.getAverage());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (timer != null)
            timer.cancel();
        if (main.getCurrentFragment().getClass() != TimeSetting.class &&
                main.getCurrentFragment().getClass() != DetailResult.class) {
            if (DCButtonManager.getDCState() == DCButtonManager.State.MeasureReady ||
                    DCButtonManager.getDCState() == DCButtonManager.State.Measuring) {
                main.getusbService().write(Commands.ExcerciseStop("00",
                        "0",
                        "0",
                        "0"));
                main.getusbService().write(Commands.Home(false));
                main.getusbService().write(Commands.Home(true));
            } else {
                main.getusbService().write(Commands.ExcerciseStop("00",
                        "0",
                        "0",
                        "0"));
                main.getusbService().write(Commands.Home(true));
            }
        }

    }
}
