package com.example.dynamiccare_kisok.Common.Component;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.R;

public class DCButton  {
    ImageButton button;
    static DCButton PressedButton;
    static ImageView Body;
    boolean isPressed;
    Drawable Pressed,UnPressed,MappingBody;
    static Drawable DefaultBody;
    Main main;

    public DCButton(){}
    public DCButton(Main main)
    {
        this.main = main;
    }
    public DCButton(Main main,ImageButton button,Drawable pressed)
    {
        this.main = main;
        setButton(button,pressed);
    }
    public DCButton(Main main,ImageButton button,Drawable pressed,Drawable mappingBody)
    {
        this.main = main;
        setButton(button, pressed, mappingBody);
    }
    public void Deactivate()
    {
        this.button.setEnabled(false);
        this.button.setColorFilter(Color.parseColor("#28FFFFFF"),
                PorterDuff.Mode.SRC_ATOP);
    }
    public void Activate()
    {
        this.button.setEnabled(true);
        this.button.setColorFilter(Color.parseColor("#00000000"),
                PorterDuff.Mode.SRC_ATOP);
    }
    public static void setBody(ImageView body)
    {
        Body = body;
        DefaultBody = Body.getDrawable();
    }

    public void setButton(ImageButton button,Drawable pressed) {
        this.button = button;
        this.Pressed = pressed;
        this.UnPressed = button.getDrawable();
    }

    public void setButton(ImageButton button,Drawable pressed,Drawable mappingBody) {
        this.button = button;
        this.Pressed = pressed;
        this.UnPressed = button.getDrawable();
        this.MappingBody = mappingBody;
    }

    public void UpdateBody()
    {
        if(PressedButton !=null)
            Body.setImageDrawable(PressedButton.getMappingBody());
        else
            Body.setImageDrawable(DefaultBody);
    }

    public void ToggleButton()
    {
        if(PressedButton != null && PressedButton.button != button)
            PressedButton.setPressed();
        if(IsPressed())
            PressedButton = this;
        else
            PressedButton = null;
    }

    public ImageButton getButton()
    {
        return button;
    }

    public boolean IsPressed()
    {
        return isPressed;
    }

    public static DCButton getPressedButton() {
        return PressedButton;
    }
    public static void PressedOff(){ PressedButton = null;}

    public void setPressed()
    {
        try {
            main.PlaySound(R.raw.normal_button);
            isPressed = !isPressed;
            ToggleButton();
            if(getMappingBody()!=null)
                UpdateBody();
            if (isPressed)
                this.button.setImageDrawable(Pressed);
            else
                this.button.setImageDrawable(UnPressed);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Drawable getMappingBody()
    {
        return MappingBody;
    }
}
