package com.example.dynamiccare_kisok.Common.Util;

import com.example.dynamiccare_kisok.Activity.Main;

public class DCSoundThread {
    Thread soundstream;
    Main main;

    public DCSoundThread(Main main) {
        this.main = main;
    }

    public void playstream(int[] stream) {
        if (soundstream != null)
            soundstream.interrupt();
        soundstream = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    for (int i : stream)
                    {
                        main.PlaySound(i);
                        Thread.sleep(3000);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        soundstream.start();
    }
}
