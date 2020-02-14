package com.example.dynamiccare_kisok.Common.Util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.R;


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

    private int one_set_complete,
            one_set_complete_english,
            two_set_complete,
            two_set_complete_english,
            three_set_complete,
            three_set_complete_english,
            excercise_is_going_to_stop,
            excercise_is_going_to_stop_english,
            next_set_will_start_soon,
            next_set_will_start_soon_english,
            raise_the_bar,
            raise_the_bar_english,
            start_excercise,
            start_excercise_english,
            take_a_break,
            take_a_break_english,
            take_down_the_bar,
            take_down_the_bar_english,
            thank_you_for_your_efforts,
            thank_you_for_your_efforts_english;



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

        one_set_complete = soundPool.load(context, R.raw.one_set_complete, 1);
        one_set_complete_english = soundPool.load(context, R.raw.one_set_complete_english, 1);
        two_set_complete = soundPool.load(context, R.raw.two_set_complete, 1);
        two_set_complete_english = soundPool.load(context, R.raw.two_set_complete_english, 1);
        three_set_complete = soundPool.load(context, R.raw.three_set_complete, 1);
        three_set_complete_english = soundPool.load(context, R.raw.three_set_complete_english, 1);
        excercise_is_going_to_stop = soundPool.load(context, R.raw.excercise_is_going_to_stop, 1);
        excercise_is_going_to_stop_english = soundPool.load(context, R.raw.excercise_is_going_to_stop_english, 1);
        next_set_will_start_soon = soundPool.load(context, R.raw.next_set_will_start_soon, 1);
        next_set_will_start_soon_english = soundPool.load(context, R.raw.next_set_will_start_soon_english, 1);
        raise_the_bar = soundPool.load(context, R.raw.raise_the_bar, 1);
        raise_the_bar_english = soundPool.load(context, R.raw.raise_the_bar_english, 1);
        start_excercise = soundPool.load(context, R.raw.start_excercise, 1);
        start_excercise_english = soundPool.load(context, R.raw.start_excercise_english, 1);
        take_a_break = soundPool.load(context, R.raw.take_a_break, 1);
        take_a_break_english = soundPool.load(context, R.raw.take_a_break_english, 1);
        take_down_the_bar = soundPool.load(context, R.raw.take_down_the_bar, 1);
        take_down_the_bar_english = soundPool.load(context, R.raw.take_down_the_bar_english, 1);
        thank_you_for_your_efforts = soundPool.load(context, R.raw.thank_you_for_your_efforts, 1);
        thank_you_for_your_efforts_english = soundPool.load(context, R.raw.thank_you_for_your_efforts_english, 1);

    }

    public void play(int raw_id) {
        switch (raw_id) {
            case R.raw.back_button:
                soundPool.play(back_button, 1, 1, 0, 0, 1);
                break;
            case R.raw.bee_measurement_begin:
                soundPool.play(bee_measurement_begin, 1, 1, 0, 0, 1);
                break;
            case R.raw.delay_sound:
                soundPool.play(delay_sound, 1, 1, 0, 0, 1);
                break;
            case R.raw.dont_stop_measurement_by_stop_sound:
                soundPool.play(dont_stop_measurement_by_stop_sound, 1, 1, 0, 0, 1);
                break;
            case R.raw.dynamic_care:
                soundPool.play(dynamic_care, 1, 1, 0, 0, 1);
                break;
            case R.raw.effort_maximally_during_measurement:
                soundPool.play(effort_maximally_during_measurement, 1, 1, 0, 0, 1);
                break;
            case R.raw.follow_instruction:
                soundPool.play(follow_instruction, 1, 1, 0, 0, 1);
                break;
            case R.raw.home_button:
                soundPool.play(home_button, 1, 1, 0, 0, 1);
                break;
            case R.raw.measurement_begin_soon:
                soundPool.play(measurement_begin_soon, 1, 1, 0, 0, 1);
                break;
            case R.raw.measurement_complete_sound:
                soundPool.play(measurement_complete_sound, 1, 1, 0, 0, 1);
                break;
            case R.raw.mesuarement_completed:
                soundPool.play(mesuarement_completed, 1, 1, 0, 0, 1);
                break;
            case R.raw.mesurement_will_begin_after_bee_sound:
                soundPool.play(mesurement_will_begin_after_bee_sound, 1, 1, 0, 0, 1);
                break;
            case R.raw.normal_button:
                soundPool.play(normal_button, 1, 1, 0, 0, 1);
                break;
            case R.raw.power_log:
                soundPool.play(power_log, 1, 1, 0, 0, 1);
                break;
            case R.raw.ra:
                soundPool.play(ra, 1, 1, 0, 0, 1);
                break;
            case R.raw.ready_get_set_go:
                soundPool.play(ready_get_set_go, 1, 1, 0, 0, 1);
                break;
            case R.raw.ring:
                soundPool.play(ring, 1, 1, 0, 0, 1);
                break;
            case R.raw.setting_is_completed:
                soundPool.play(setting_is_completed, 1, 1, 0, 0, 1);
                break;
            case R.raw.show_your_result:
                soundPool.play(show_your_result, 1, 1, 0, 0, 1);
                break;
            case R.raw.stopping_measurement:
                soundPool.play(stopping_measurement, 1, 1, 0, 0, 1);
                break;
            case R.raw.take_pose_and_place_bar_or_wire_to_right_position:
                soundPool.play(take_pose_and_place_bar_or_wire_to_right_position, 1, 1, 0, 0, 1);
                break;
            case R.raw.thanks_for_your_effort:
                soundPool.play(thanks_for_your_effort, 1, 1, 0, 0, 1);
                break;

            case R.raw.one_set_complete:
                soundPool.play( one_set_complete, 1, 1, 0, 0, 1);
                break;
            case R.raw.one_set_complete_english:
                soundPool.play(one_set_complete_english, 1, 1, 0, 0, 1);
                break;
            case R.raw.two_set_complete:
                soundPool.play(two_set_complete , 1, 1, 0, 0, 1);
                break;
            case R.raw.two_set_complete_english:
                soundPool.play(two_set_complete_english , 1, 1, 0, 0, 1);
                break;
            case R.raw.three_set_complete:
                soundPool.play(three_set_complete , 1, 1, 0, 0, 1);
                break;
            case R.raw.three_set_complete_english:
                soundPool.play( three_set_complete_english, 1, 1, 0, 0, 1);
                break;
            case R.raw.excercise_is_going_to_stop:
                soundPool.play(excercise_is_going_to_stop , 1, 1, 0, 0, 1);
                break;
            case R.raw.excercise_is_going_to_stop_english:
                soundPool.play(excercise_is_going_to_stop_english , 1, 1, 0, 0, 1);
                break;
            case R.raw.next_set_will_start_soon:
                soundPool.play(next_set_will_start_soon , 1, 1, 0, 0, 1);
                break;
            case R.raw.next_set_will_start_soon_english:
                soundPool.play( next_set_will_start_soon_english, 1, 1, 0, 0, 1);
                break;
            case R.raw.raise_the_bar:
                soundPool.play(raise_the_bar , 1, 1, 0, 0, 1);
                break;
            case R.raw.raise_the_bar_english:
                soundPool.play(raise_the_bar_english , 1, 1, 0, 0, 1);
                break;
            case R.raw.start_excercise:
                soundPool.play( start_excercise, 1, 1, 0, 0, 1);
                break;
            case R.raw.start_excercise_english:
                soundPool.play(start_excercise_english , 1, 1, 0, 0, 1);
                break;
            case R.raw.take_a_break:
                soundPool.play(take_a_break , 1, 1, 0, 0, 1);
                break;
            case R.raw.take_a_break_english:
                soundPool.play( take_a_break_english, 1, 1, 0, 0, 1);
                break;
            case R.raw.take_down_the_bar:
                soundPool.play( take_down_the_bar, 1, 1, 0, 0, 1);
                break;
            case R.raw.take_down_the_bar_english:
                soundPool.play(take_down_the_bar_english , 1, 1, 0, 0, 1);
                break;
            case R.raw.thank_you_for_your_efforts:
                soundPool.play(thank_you_for_your_efforts , 1, 1, 0, 0, 1);
                break;
            case R.raw.thank_you_for_your_efforts_english:
                soundPool.play(thank_you_for_your_efforts_english, 1, 1, 0, 0, 1);
                break;
        }
    }

}
