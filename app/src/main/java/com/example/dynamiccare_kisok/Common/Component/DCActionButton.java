package com.example.dynamiccare_kisok.Common.Component;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

public class DCActionButton {
    ImageButton button;
    boolean isPressed;
    Drawable Pressed,UnPressed;

    public DCActionButton(){}

    public DCActionButton(ImageButton button, Drawable pressed)
    {
        setButton(button,pressed);
    }

    public void setPressed()
    {
        isPressed = !isPressed;
        if(isPressed)
            this.button.setImageDrawable(Pressed);
        else
            this.button.setImageDrawable(UnPressed);
    }
    public ImageButton getButton()
    {
        return button;
    }

    public void setButton(ImageButton button,Drawable pressed) {
        this.button = button;
        this.Pressed = pressed;
        this.UnPressed = button.getDrawable();
    }
    public void Activate()
    {
        this.button.setClickable(true);
        this.button.setColorFilter(Color.parseColor("#00000000"),
                PorterDuff.Mode.SRC_ATOP);
    }
    public void Deactivate()
    {
        this.button.setClickable(false);
        this.button.setColorFilter(Color.parseColor("#28FFFFFF"),
                PorterDuff.Mode.SRC_ATOP);
    }
}
