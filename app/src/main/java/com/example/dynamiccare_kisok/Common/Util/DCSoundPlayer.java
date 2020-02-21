package com.example.dynamiccare_kisok.Common.Util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaActionSound;
import android.media.SoundPool;

import com.example.dynamiccare_kisok.Common.Component.DCButtonManager;
import com.example.dynamiccare_kisok.R;

import java.io.File;


public class DCSoundPlayer {
    public static enum integer {
        one,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine,
        ten,
        eleven,
        twelve,
        threeteen,
        fourteen,
        fifteen,
        sixteen,
        seventeen,
        eighteen,
        nineteen,
        twenty,
        twenty_one,
        twenty_two,
        twenty_three,
        twenty_four,
        twenty_five,
        twenty_six,
        twenty_seven,
        twenty_eight,
        twenty_nine,
        thirty,
        thirty_one,
        thirty_two,
        thirty_three,
        thirty_four,
        thirty_five,
        thirty_six,
        thirty_seven,
        thirty_eight,
        thirty_nine,
        fourty,
        fourty_one,
        fourty_two,
        fourty_three,
        fourty_four,
        fourty_five,
        fourty_six,
        fourty_seven,
        fourty_eight,
        fourty_nine,
        fifty
    }

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

    private int eight_sets_completed_english,
            five_sets_completed_english,
            four_sets_completed_english,
            nine_sets_completed_english,
            seven_sets_completed_english,
            six_sets_completed_english,
            ten_sets_completed_english;

    private int select_the_mode,
            select_the_mode_english,
            excercise_mode,
            excercise_mode_english,
            kinetic_excercise_mode,
            kinetic_excercise_mode_english,
            metric_log_mode,
            metric_log_mode_english,
            sotonic_log_mode,
            sotonic_log_mode_english;

    private int setup_is_complete_english,
            please_follow_the_directions_english,
            adjust_the_bar_or_wire_properly_english,
            please_do_your_best_in_measuring_english,
            do_not_stop_measuring_until_the_end_comment_is_made_english,
            the_measurement_wil_begin_shortly_english,
            the_measurement_starts_when_you_hear_the_beep_sound_english,
            the_measurement_is_going_to_stop_english,
            please_check_the_results_english;

    private int one,
            two,
            three,
            four,
            five,
            six,
            seven,
            eight,
            nine,
            ten,
            eleven,
            twelve,
            threeteen,
            fourteen,
            fifteen,
            sixteen,
            seventeen,
            eighteen,
            nineteen,
            twenty,
            twenty_one,
            twenty_two,
            twenty_three,
            twenty_four,
            twenty_five,
            twenty_six,
            twenty_seven,
            twenty_eight,
            twenty_nine,
            thirty,
            thirty_one,
            thirty_two,
            thirty_three,
            thirty_four,
            thirty_five,
            thirty_six,
            thirty_seven,
            thirty_eight,
            thirty_nine,
            fourty,
            fourty_one,
            fourty_two,
            fourty_three,
            fourty_four,
            fourty_five,
            fourty_six,
            fourty_seven,
            fourty_eight,
            fourty_nine,
            fifty;


    public void initSounds(Context context) {
        try {
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

            eight_sets_completed_english = soundPool.load(context, R.raw.eight_sets_completed_english, 1);
            five_sets_completed_english = soundPool.load(context, R.raw.five_sets_completed_english, 1);
            four_sets_completed_english = soundPool.load(context, R.raw.four_sets_completed_english, 1);
            nine_sets_completed_english = soundPool.load(context, R.raw.nine_sets_completed_english, 1);
            seven_sets_completed_english = soundPool.load(context, R.raw.seven_sets_completed_english, 1);
            six_sets_completed_english = soundPool.load(context, R.raw.six_sets_completed_english, 1);
            ten_sets_completed_english = soundPool.load(context, R.raw.ten_sets_completed_english, 1);

            select_the_mode = soundPool.load(context, R.raw.select_the_mode, 1);
            select_the_mode_english = soundPool.load(context, R.raw.select_the_mode_english, 1);
            excercise_mode = soundPool.load(context, R.raw.excercise_mode, 1);
            excercise_mode_english = soundPool.load(context, R.raw.excercise_mode_english, 1);
            kinetic_excercise_mode = soundPool.load(context, R.raw.kinetic_excercise_mode, 1);
            kinetic_excercise_mode_english = soundPool.load(context, R.raw.kinetic_excercise_mode_english, 1);
            metric_log_mode = soundPool.load(context, R.raw.metric_log_mode, 1);
            metric_log_mode_english = soundPool.load(context, R.raw.metric_log_mode_english, 1);
            sotonic_log_mode = soundPool.load(context, R.raw.sotonic_log_mode, 1);
            sotonic_log_mode_english = soundPool.load(context, R.raw.sotonic_log_mode_english, 1);

            setup_is_complete_english = soundPool.load(context, R.raw.setup_is_complete_english, 1);
            please_follow_the_directions_english = soundPool.load(context, R.raw.please_follow_the_directions_english, 1);
            adjust_the_bar_or_wire_properly_english = soundPool.load(context, R.raw.adjust_the_bar_or_wire_properly_english, 1);
            please_do_your_best_in_measuring_english = soundPool.load(context, R.raw.please_do_your_best_in_measuring_english, 1);
            do_not_stop_measuring_until_the_end_comment_is_made_english = soundPool.load(context, R.raw.do_not_stop_measuring_until_the_end_comment_is_made_english, 1);
            the_measurement_wil_begin_shortly_english = soundPool.load(context, R.raw.the_measurement_wil_begin_shortly_english, 1);
            the_measurement_starts_when_you_hear_the_beep_sound_english = soundPool.load(context, R.raw.the_measurement_starts_when_you_hear_the_beep_sound_english, 1);
            the_measurement_is_going_to_stop_english = soundPool.load(context, R.raw.the_measurement_is_going_to_stop_english, 1);
            please_check_the_results_english = soundPool.load(context, R.raw.please_check_the_results_english, 1);

            one = soundPool.load(context, R.raw.one, 1);
            two = soundPool.load(context, R.raw.two, 1);
            three = soundPool.load(context, R.raw.three, 1);
            four = soundPool.load(context, R.raw.four, 1);
            five = soundPool.load(context, R.raw.five, 1);
            six = soundPool.load(context, R.raw.six, 1);
            seven = soundPool.load(context, R.raw.seven, 1);
            eight = soundPool.load(context, R.raw.eight, 1);
            nine = soundPool.load(context, R.raw.nine, 1);
            ten = soundPool.load(context, R.raw.ten, 1);
            eleven = soundPool.load(context, R.raw.eleven, 1);
            twelve = soundPool.load(context, R.raw.twelve, 1);
            threeteen = soundPool.load(context, R.raw.threeteen, 1);
            fourteen = soundPool.load(context, R.raw.fourteen, 1);
            fifteen = soundPool.load(context, R.raw.fifteen, 1);
            sixteen = soundPool.load(context, R.raw.sixteen, 1);
            seventeen = soundPool.load(context, R.raw.seventeen, 1);
            eighteen = soundPool.load(context, R.raw.eighteen, 1);
            nineteen = soundPool.load(context, R.raw.nineteen, 1);
            twenty = soundPool.load(context, R.raw.twenty, 1);
            twenty_one = soundPool.load(context, R.raw.twenty_one, 1);
            twenty_two = soundPool.load(context, R.raw.twenty_two, 1);
            twenty_three = soundPool.load(context, R.raw.twenty_three, 1);
            twenty_four = soundPool.load(context, R.raw.twenty_four, 1);
            twenty_five = soundPool.load(context, R.raw.twenty_five, 1);
            twenty_six = soundPool.load(context, R.raw.twenty_six, 1);
            twenty_seven = soundPool.load(context, R.raw.twenty_seven, 1);
            twenty_eight = soundPool.load(context, R.raw.twenty_eight, 1);
            twenty_nine = soundPool.load(context, R.raw.twenty_nine, 1);
            thirty = soundPool.load(context, R.raw.thirty, 1);
            thirty_one = soundPool.load(context, R.raw.thirty_one, 1);
            thirty_two = soundPool.load(context, R.raw.thirty_two, 1);
            thirty_three = soundPool.load(context, R.raw.thirty_three, 1);
            thirty_four = soundPool.load(context, R.raw.thirty_four, 1);
            thirty_five = soundPool.load(context, R.raw.thirty_five, 1);
            thirty_six = soundPool.load(context, R.raw.thirty_six, 1);
            thirty_seven = soundPool.load(context, R.raw.thirty_seven, 1);
            thirty_eight = soundPool.load(context, R.raw.thirty_eight, 1);
            thirty_nine = soundPool.load(context, R.raw.thirty_nine, 1);
            fourty = soundPool.load(context, R.raw.fourty, 1);
            fourty_one = soundPool.load(context, R.raw.fourty_one, 1);
            fourty_two = soundPool.load(context, R.raw.fourty_two, 1);
            fourty_three = soundPool.load(context, R.raw.fourty_three, 1);
            fourty_four = soundPool.load(context, R.raw.fourty_four, 1);
            fourty_five = soundPool.load(context, R.raw.fourty_five, 1);
            fourty_six = soundPool.load(context, R.raw.fourty_six, 1);
            fourty_seven = soundPool.load(context, R.raw.fourty_seven, 1);
            fourty_eight = soundPool.load(context, R.raw.fourty_eight, 1);
            fourty_nine = soundPool.load(context, R.raw.fourty_nine, 1);
            fifty = soundPool.load(context, R.raw.fifty, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(int raw_id) {
        try {
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
                    soundPool.play(one_set_complete, 1, 1, 0, 0, 1);
                    break;
                case R.raw.one_set_complete_english:
                    soundPool.play(one_set_complete_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.two_set_complete:
                    soundPool.play(two_set_complete, 1, 1, 0, 0, 1);
                    break;
                case R.raw.two_set_complete_english:
                    soundPool.play(two_set_complete_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.three_set_complete:
                    soundPool.play(three_set_complete, 1, 1, 0, 0, 1);
                    break;
                case R.raw.three_set_complete_english:
                    soundPool.play(three_set_complete_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.excercise_is_going_to_stop:
                    soundPool.play(excercise_is_going_to_stop, 1, 1, 0, 0, 1);
                    break;
                case R.raw.excercise_is_going_to_stop_english:
                    soundPool.play(excercise_is_going_to_stop_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.next_set_will_start_soon:
                    soundPool.play(next_set_will_start_soon, 1, 1, 0, 0, 1);
                    break;
                case R.raw.next_set_will_start_soon_english:
                    soundPool.play(next_set_will_start_soon_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.raise_the_bar:
                    soundPool.play(raise_the_bar, 1, 1, 0, 0, 1);
                    break;
                case R.raw.raise_the_bar_english:
                    soundPool.play(raise_the_bar_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.start_excercise:
                    soundPool.play(start_excercise, 1, 1, 0, 0, 1);
                    break;
                case R.raw.start_excercise_english:
                    soundPool.play(start_excercise_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.take_a_break:
                    soundPool.play(take_a_break, 1, 1, 0, 0, 1);
                    break;
                case R.raw.take_a_break_english:
                    soundPool.play(take_a_break_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.take_down_the_bar:
                    soundPool.play(take_down_the_bar, 1, 1, 0, 0, 1);
                    break;
                case R.raw.take_down_the_bar_english:
                    soundPool.play(take_down_the_bar_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thank_you_for_your_efforts:
                    soundPool.play(thank_you_for_your_efforts, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thank_you_for_your_efforts_english:
                    soundPool.play(thank_you_for_your_efforts_english, 1, 1, 0, 0, 1);
                    break;

                case R.raw.eight_sets_completed_english:
                    soundPool.play(eight_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.five_sets_completed_english:
                    soundPool.play(five_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.four_sets_completed_english:
                    soundPool.play(four_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.nine_sets_completed_english:
                    soundPool.play(nine_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.seven_sets_completed_english:
                    soundPool.play(seven_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.six_sets_completed_english:
                    soundPool.play(six_sets_completed_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.ten_sets_completed_english:
                    soundPool.play(ten_sets_completed_english, 1, 1, 0, 0, 1);
                    break;

                case R.raw.select_the_mode:
                    soundPool.play(select_the_mode, 1, 1, 0, 0, 1);
                    break;
                case R.raw.select_the_mode_english:
                    soundPool.play(select_the_mode_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.excercise_mode:
                    soundPool.play(excercise_mode, 1, 1, 0, 0, 1);
                    break;
                case R.raw.excercise_mode_english:
                    soundPool.play(excercise_mode_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.kinetic_excercise_mode:
                    soundPool.play(kinetic_excercise_mode, 1, 1, 0, 0, 1);
                    break;
                case R.raw.kinetic_excercise_mode_english:
                    soundPool.play(kinetic_excercise_mode_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.metric_log_mode:
                    soundPool.play(metric_log_mode, 1, 1, 0, 0, 1);
                    break;
                case R.raw.metric_log_mode_english:
                    soundPool.play(metric_log_mode_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.sotonic_log_mode:
                    soundPool.play(sotonic_log_mode, 1, 1, 0, 0, 1);
                    break;
                case R.raw.sotonic_log_mode_english:
                    soundPool.play(sotonic_log_mode_english, 1, 1, 0, 0, 1);
                    break;

                case R.raw.setup_is_complete_english:
                    soundPool.play(setup_is_complete_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.please_follow_the_directions_english:
                    soundPool.play(please_follow_the_directions_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.adjust_the_bar_or_wire_properly_english:
                    soundPool.play(adjust_the_bar_or_wire_properly_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.please_do_your_best_in_measuring_english:
                    soundPool.play(please_do_your_best_in_measuring_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.do_not_stop_measuring_until_the_end_comment_is_made_english:
                    soundPool.play(do_not_stop_measuring_until_the_end_comment_is_made_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.the_measurement_wil_begin_shortly_english:
                    soundPool.play(the_measurement_wil_begin_shortly_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.the_measurement_starts_when_you_hear_the_beep_sound_english:
                    soundPool.play(the_measurement_starts_when_you_hear_the_beep_sound_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.the_measurement_is_going_to_stop_english:
                    soundPool.play(the_measurement_is_going_to_stop_english, 1, 1, 0, 0, 1);
                    break;
                case R.raw.please_check_the_results_english:
                    soundPool.play(please_check_the_results_english, 1, 1, 0, 0, 1);
                    break;

                case R.raw.one:
                    soundPool.play(one, 1, 1, 0, 0, 1);
                    break;
                case R.raw.two:
                    soundPool.play(two, 1, 1, 0, 0, 1);
                    break;
                case R.raw.three:
                    soundPool.play(three, 1, 1, 0, 0, 1);
                    break;
                case R.raw.four:
                    soundPool.play(four, 1, 1, 0, 0, 1);
                    break;
                case R.raw.five:
                    soundPool.play(five, 1, 1, 0, 0, 1);
                    break;
                case R.raw.six:
                    soundPool.play(six, 1, 1, 0, 0, 1);
                    break;
                case R.raw.seven:
                    soundPool.play(seven, 1, 1, 0, 0, 1);
                    break;
                case R.raw.eight:
                    soundPool.play(eight, 1, 1, 0, 0, 1);
                    break;
                case R.raw.nine:
                    soundPool.play(nine, 1, 1, 0, 0, 1);
                    break;
                case R.raw.ten:
                    soundPool.play(ten, 1, 1, 0, 0, 1);
                    break;
                case R.raw.eleven:
                    soundPool.play(eleven, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twelve:
                    soundPool.play(twelve, 1, 1, 0, 0, 1);
                    break;
                case R.raw.threeteen:
                    soundPool.play(threeteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourteen:
                    soundPool.play(fourteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fifteen:
                    soundPool.play(fifteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.sixteen:
                    soundPool.play(sixteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.seventeen:
                    soundPool.play(seventeen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.eighteen:
                    soundPool.play(eighteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.nineteen:
                    soundPool.play(nineteen, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty:
                    soundPool.play(twenty, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_one:
                    soundPool.play(twenty_one, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_two:
                    soundPool.play(twenty_two, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_three:
                    soundPool.play(twenty_three, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_four:
                    soundPool.play(twenty_four, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_five:
                    soundPool.play(twenty_five, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_six:
                    soundPool.play(twenty_six, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_seven:
                    soundPool.play(twenty_seven, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_eight:
                    soundPool.play(twenty_eight, 1, 1, 0, 0, 1);
                    break;
                case R.raw.twenty_nine:
                    soundPool.play(twenty_nine, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty:
                    soundPool.play(thirty, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_one:
                    soundPool.play(thirty_one, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_two:
                    soundPool.play(thirty_two, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_three:
                    soundPool.play(thirty_three, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_four:
                    soundPool.play(thirty_three, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_five:
                    soundPool.play(thirty_five, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_six:
                    soundPool.play(thirty_six, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_seven:
                    soundPool.play(thirty_seven, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_eight:
                    soundPool.play(thirty_eight, 1, 1, 0, 0, 1);
                    break;
                case R.raw.thirty_nine:
                    soundPool.play(thirty_nine, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty:
                    soundPool.play(fourty, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_one:
                    soundPool.play(fourty_one, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_two:
                    soundPool.play(fourty_two, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_three:
                    soundPool.play(fourty_three, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_four:
                    soundPool.play(fourty_four, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_five:
                    soundPool.play(fourty_five, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_six:
                    soundPool.play(fourty_six, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_seven:
                    soundPool.play(fourty_seven, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_eight:
                    soundPool.play(fourty_eight, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fourty_nine:
                    soundPool.play(fourty_nine, 1, 1, 0, 0, 1);
                    break;
                case R.raw.fifty:
                    soundPool.play(fifty, 1, 1, 0, 0, 1);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
