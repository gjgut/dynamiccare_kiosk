package com.example.dynamiccare_kisok.Common.Component;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class DCActionButton {
    ImageButton button;
    boolean isPressed;
    boolean isPause = false;
    Drawable Pressed, UnPressed;
    Main main;

    public DCActionButton(Main main) {
        this.main = main;
    }

    public DCActionButton(Main main, ImageButton button, Drawable pressed) {
        this.main = main;
        setButton(button, pressed);
    }

    public boolean isPause() {
        return isPause;
    }


    public void setPause() {
        isPause = !isPause;
    }


    public void setPressed() {
        try {
            main.PlaySound(R.raw.normal_button);
            setPressedwithNoSound();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void setPressedwithNoSound() {
        try {
            isPressed = !isPressed;
            if (isPressed)
                this.button.setImageDrawable(Pressed);
            else
                this.button.setImageDrawable(UnPressed);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public ImageButton getButton() {
        return button;
    }

    public boolean IsPressed() {
        return isPressed;
    }

    public void setButton(ImageButton button, Drawable pressed) {
        try {
            this.button = button;
            this.Pressed = pressed;
            this.UnPressed = button.getDrawable();
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void Activate() {
        try {
            this.button.setEnabled(true);
            this.button.setColorFilter(Color.parseColor("#00000000"),
                    PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void Deactivate() {
        try {
            this.button.setEnabled(false);
            this.button.setColorFilter(Color.parseColor("#28FFFFFF"),
                    PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }
}
