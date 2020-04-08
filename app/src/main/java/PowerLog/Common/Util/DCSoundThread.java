package PowerLog.Common.Util;

import android.util.Log;

import PowerLog.Activity.Administrator;
import PowerLog.Activity.Main;
import PowerLog.R;

public class DCSoundThread {
    Thread soundstream;
    DCSoundPlayer dcSoundPlayer;
    Administrator admin;
    Main main;

    public DCSoundThread(Main main) {
        this.main = main;
        dcSoundPlayer = main.getDcSoundPlayer();
    }
    public DCSoundThread(Administrator admin,DCSoundPlayer player) {
        this.admin = admin;
        dcSoundPlayer = player;
    }

    public void stopstream()
    {
        if (soundstream != null)
        soundstream.interrupt();
        dcSoundPlayer.stop();
    }

    public void playstream(int[] stream) {
        try {
            if (soundstream != null)
            {
                soundstream.interrupt();
                Log.i("Sound","Sound interrupted");
            }
            soundstream = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i : stream) {
                            dcSoundPlayer.play(i);
                            Thread.sleep(getSleeptime(i));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            soundstream.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSleeptime(int raw_id) {
        try{
        switch (raw_id) {
            case R.raw.back_button:
            case R.raw.bee_measurement_begin:
            case R.raw.eight:
            case R.raw.eighteen:
            case R.raw.eleven:
            case R.raw.fifteen:
            case R.raw.fifty:
            case R.raw.five:
            case R.raw.four:
            case R.raw.fourteen:
            case R.raw.fourty:
            case R.raw.fourty_one:
            case R.raw.nine:
            case R.raw.nineteen:
            case R.raw.normal_button:
            case R.raw.seven:
            case R.raw.seventeen:
            case R.raw.six:
            case R.raw.sixteen:
            case R.raw.ten:
            case R.raw.thirty:
            case R.raw.thirty_eight:
            case R.raw.threeteen:
            case R.raw.twelve:
            case R.raw.twenty:
            case R.raw.twenty_eight:
                return 1000;
            case R.raw.delay_sound:
            case R.raw.dynamic_care:
            case R.raw.excercise_is_going_to_stop:
            case R.raw.excercise_mode:
            case R.raw.excercise_mode_english:
            case R.raw.follow_instruction:
            case R.raw.fourty_eight:
            case R.raw.fourty_five:
            case R.raw.fourty_four:
            case R.raw.fourty_nine:
            case R.raw.fourty_seven:
            case R.raw.fourty_six:
            case R.raw.fourty_three:
            case R.raw.fourty_two:
            case R.raw.home_button:
            case R.raw.measurement_complete_sound:
            case R.raw.mesuarement_completed:
            case R.raw.next_set_will_start_soon_english:
            case R.raw.one:
            case R.raw.one_set_complete_english:
            case R.raw.please_check_the_results_english:
            case R.raw.please_do_your_best_in_measuring_english:
            case R.raw.power_log:
            case R.raw.ra:
            case R.raw.ring:
            case R.raw.select_the_mode:
            case R.raw.setting_is_completed:
            case R.raw.setup_is_complete_english:
            case R.raw.show_your_result:
            case R.raw.sotonic_log_mode_english:
            case R.raw.start_excercise_english:
            case R.raw.stopping_measurement:
            case R.raw.take_a_break:
            case R.raw.take_a_break_english:
            case R.raw.thanks_for_your_effort:
            case R.raw.thank_you_for_your_efforts:
            case R.raw.thank_you_for_your_efforts_english:
            case R.raw.the_measurement_is_going_to_stop_english:
            case R.raw.thirty_five:
            case R.raw.thirty_four:
            case R.raw.thirty_nine:
            case R.raw.thirty_one:
            case R.raw.thirty_seven:
            case R.raw.thirty_six:
            case R.raw.thirty_three:
            case R.raw.thirty_two:
            case R.raw.three:
            case R.raw.three_set_complete_english:
            case R.raw.twenty_five:
            case R.raw.twenty_four:
            case R.raw.twenty_nine:
            case R.raw.twenty_one:
            case R.raw.twenty_seven:
            case R.raw.twenty_six:
            case R.raw.twenty_three:
            case R.raw.twenty_two:
            case R.raw.two:
            case R.raw.two_set_complete_english:
                return 2000;
            case R.raw.eight_sets_completed_english:
            case R.raw.excercise_is_going_to_stop_english:
            case R.raw.five_sets_completed_english:
            case R.raw.four_sets_completed_english:
            case R.raw.kinetic_excercise_mode:
            case R.raw.kinetic_excercise_mode_english:
            case R.raw.measurement_begin_soon:
            case R.raw.mesurement_will_begin_after_bee_sound:
            case R.raw.metric_log_mode:
            case R.raw.metric_log_mode_english:
            case R.raw.nine_sets_completed_english:
            case R.raw.one_set_complete:
            case R.raw.please_follow_the_directions_english:
            case R.raw.raise_the_bar_english:
            case R.raw.select_the_mode_english:
            case R.raw.seven_sets_completed_english:
            case R.raw.six_sets_completed_english:
            case R.raw.sotonic_log_mode:
            case R.raw.start_excercise:
            case R.raw.take_down_the_bar_english:
            case R.raw.ten_sets_completed_english:
            case R.raw.the_measurement_wil_begin_shortly_english:
            case R.raw.three_set_complete:
            case R.raw.two_set_complete:
                return 3000;
            case R.raw.dont_stop_measurement_by_stop_sound:
            case R.raw.do_not_stop_measuring_until_the_end_comment_is_made_english:
            case R.raw.effort_maximally_during_measurement:
            case R.raw.next_set_will_start_soon:
            case R.raw.raise_the_bar:
            case R.raw.ready_get_set_go:
            case R.raw.take_down_the_bar:
            case R.raw.the_measurement_starts_when_you_hear_the_beep_sound_english:
                return 4000;
            case R.raw.adjust_the_bar_or_wire_properly_english:
                return 5000;
            case R.raw.take_pose_and_place_bar_or_wire_to_right_position:
                return 7000;

        }
        }catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

}
