package com.example.dynamiccare_kisok.Common.Component;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class DCButton {
    ImageButton button;
    static DCButton PressedButton;
    static ImageView Body;
    boolean isPressed;
    Drawable Pressed, UnPressed, MappingBody;
    static Drawable DefaultBody;
    Main main;


    public DCButton(Main main) {
        this.main = main;
    }

    public DCButton(Main main, ImageButton button, Drawable pressed) {
        this.main = main;
        setButton(button, pressed);
    }

    public DCButton(Main main, ImageButton button, Drawable pressed, Drawable mappingBody) {
        this.main = main;
        setButton(button, pressed, mappingBody);
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

    public void Activate() {
        try {
            this.button.setEnabled(true);
            this.button.setColorFilter(Color.parseColor("#00000000"),
                    PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public static void setBody(ImageView body) {
        Body = body;
        DefaultBody = Body.getDrawable();
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

    public void setButton(ImageButton button, Drawable pressed, Drawable mappingBody) {
        try {
            this.button = button;
            this.Pressed = pressed;
            this.UnPressed = button.getDrawable();
            this.MappingBody = mappingBody;
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void UpdateBody() {
        try {
            if (PressedButton != null)
                Body.setImageDrawable(PressedButton.getMappingBody());
            else
                Body.setImageDrawable(DefaultBody);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public void ToggleButton() {
        try {
            if (PressedButton != null && PressedButton.button != button)
                PressedButton.setPressedWithNoSound();
            if (IsPressed())
                PressedButton = this;
            else
                PressedButton = null;
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

    public static void PressedOff() {
        PressedButton = null;
    }

    public void setPressed() {
        try {
            main.PlaySound(R.raw.normal_button);
            setPressedWithNoSound();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public void setPressedWithNoSound() {
        try {
            isPressed = !isPressed;
            ToggleButton();
            if (getMappingBody() != null)
                UpdateBody();
            if (isPressed)
                this.button.setImageDrawable(Pressed);
            else
                this.button.setImageDrawable(UnPressed);
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }

    public Drawable getMappingBody() {
        return MappingBody;
    }
}
