package com.example.dynamiccare_kisok.Common.Util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.R;

import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DCSoundPlayer {

    private SoundPool soundPool;
    private int back_button,
            bee_measurement_begin,
            delay_sound,
            dont_stop_measurement_by_stop_sound,
            dynamic_care,
            effort_maximally_during_measurement,
            follow_instruction,
            home_button,
            measurement_begin_soon,
            measurement_complete_sound,
            mesuarement_completed,
            mesurement_will_begin_after_bee_sound,
            normal_button,
            power_log,
            ra,
            ready_get_set_go,
            ring, setting_is_completed,
            show_your_result,
            stopping_measurement,
            take_pose_and_place_bar_or_wire_to_right_position,
            thanks_for_your_effort;

    public void initSounds(Context context) {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        back_button = soundPool.load(context, R.raw.back_button, 1);
        bee_measurement_begin = soundPool.load(context, R.raw.bee_measurement_begin, 1);
        delay_sound = soundPool.load(context, R.raw.delay_sound, 1);
        dont_stop_measurement_by_stop_sound = soundPool.load(context, R.raw.dont_stop_measurement_by_stop_sound, 1);
        dynamic_care = soundPool.load(context, R.raw.dynamic_care, 1);
        effort_maximally_during_measurement = soundPool.load(context, R.raw.effort_maximally_during_measurement, 1);
        follow_instruction = soundPool.load(context, R.raw.follow_instruction, 1);
        home_button = soundPool.load(context, R.raw.home_button, 1);
        measurement_begin_soon = soundPool.load(context, R.raw.measurement_begin_soon, 1);
        measurement_complete_sound = soundPool.load(context, R.raw.measurement_complete_sound, 1);
        mesuarement_completed = soundPool.load(context, R.raw.mesuarement_completed, 1);
        mesurement_will_begin_after_bee_sound = soundPool.load(context, R.raw.mesurement_will_begin_after_bee_sound, 1);
        normal_button = soundPool.load(context, R.raw.normal_button, 1);
        power_log = soundPool.load(context, R.raw.power_log, 1);
        ra = soundPool.load(context, R.raw.ra, 1);
        ready_get_set_go = soundPool.load(context, R.raw.ready_get_set_go, 1);
        ring = soundPool.load(context, R.raw.ring, 1);
        setting_is_completed = soundPool.load(context, R.raw.setting_is_completed, 1);
        show_your_result = soundPool.load(context, R.raw.show_your_result, 1);
        stopping_measurement = soundPool.load(context, R.raw.stopping_measurement, 1);
        take_pose_and_place_bar_or_wire_to_right_position = soundPool.load(context, R.raw.take_pose_and_place_bar_or_wire_to_right_position, 1);
        thanks_for_your_effort = soundPool.load(context, R.raw.thanks_for_your_effort, 1);

    }

    public void play(int raw_id) {
        switch (raw_id) {

            case R.raw.back_button: {
                soundPool.play(back_button, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.bee_measurement_begin:
            {
                soundPool.play(bee_measurement_begin, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.delay_sound:
            {
                soundPool.play(delay_sound, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.dont_stop_measurement_by_stop_sound:
            {
                soundPool.play(dont_stop_measurement_by_stop_sound, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.dynamic_care:
            {
                soundPool.play(dynamic_care, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.effort_maximally_during_measurement:
            {
                soundPool.play(effort_maximally_during_measurement, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.follow_instruction:
            {
                soundPool.play(follow_instruction, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.home_button:
            {
                soundPool.play(home_button, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.measurement_begin_soon:
            {
                soundPool.play(measurement_begin_soon, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.measurement_complete_sound:
            {
                soundPool.play(measurement_complete_sound, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.mesuarement_completed:
            {
                soundPool.play(mesuarement_completed, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.mesurement_will_begin_after_bee_sound:
            {
                soundPool.play(mesurement_will_begin_after_bee_sound, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.normal_button:
            {
                soundPool.play(normal_button, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.power_log:
            {
                soundPool.play(power_log, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.ra:
            {
                soundPool.play(ra, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.ready_get_set_go:
            {
                soundPool.play(ready_get_set_go, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.ring:
            {
                soundPool.play(ring, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.setting_is_completed:
            {
                soundPool.play(setting_is_completed, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.show_your_result:
            {
                soundPool.play(show_your_result, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.stopping_measurement:
            {
                soundPool.play(stopping_measurement, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.take_pose_and_place_bar_or_wire_to_right_position:
            {
                soundPool.play(take_pose_and_place_bar_or_wire_to_right_position, 1, 1, 0, 0, 1);
                break;
            }
            case R.raw.thanks_for_your_effort:
            {
                soundPool.play(thanks_for_your_effort, 1, 1, 0, 0, 1);
                break;
            }
        }
    }

}
