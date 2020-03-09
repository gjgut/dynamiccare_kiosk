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
    boolean isPause = true;
    Drawable Pressed, UnPressed;
    Main main;

    public boolean isPause() {
        return isPause;
    }


    public void setPause() {
        isPause = !isPause;
    }

    public DCActionButton() {
    }

    public DCActionButton(Main main) {
        this.main = main;
    }

    public DCActionButton(Main main, ImageButton button, Drawable pressed) {
        this.main = main;
        setButton(button, pressed);
    }

    public void setPressed() {
        try {
            main.PlaySound(R.raw.normal_button);
            isPressed = !isPressed;
            if (isPressed)
                this.button.setImageDrawable(Pressed);
            else
                this.button.setImageDrawable(UnPressed);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void setPressedwithNoSound() {
        isPressed = !isPressed;
        if (isPressed)
            this.button.setImageDrawable(Pressed);
        else
            this.button.setImageDrawable(UnPressed);
    }

    public ImageButton getButton() {
        return button;
    }

    public boolean IsPressed() {
        return isPressed;
    }

    public void setButton(ImageButton button, Drawable pressed) {
        this.button = button;
        this.Pressed = pressed;
        this.UnPressed = button.getDrawable();
    }

    public void Activate() {
        this.button.setEnabled(true);
        this.button.setColorFilter(Color.parseColor("#00000000"),
                PorterDuff.Mode.SRC_ATOP);
    }

    public void Deactivate() {
        this.button.setEnabled(false);
        this.button.setColorFilter(Color.parseColor("#28FFFFFF"),
                PorterDuff.Mode.SRC_ATOP);
    }
}
