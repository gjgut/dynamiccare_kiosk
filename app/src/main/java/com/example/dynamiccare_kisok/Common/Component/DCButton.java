package com.example.dynamiccare_kisok.Common.Component;

import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DCButton  {
    ImageButton button;
    static DCButton PressedButton;
    static ImageView Body;
    boolean isPressed;
    Drawable Pressed,UnPressed,MappingBody;
    static Drawable DefaultBody;

    public DCButton(){}
    public DCButton(ImageButton button,Drawable pressed)
    {
        setButton(button,pressed);
    }
    public DCButton(ImageButton button,Drawable pressed,Drawable mappingBody)
    {
        setButton(button, pressed, mappingBody);
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
        if(isPressed())
            PressedButton = this;
        else
            PressedButton = null;
    }

    public ImageButton getButton()
    {
        return button;
    }

    public boolean isPressed()
    {
        return isPressed;
    }

    public static DCButton getPressedButton() {
        return PressedButton;
    }
    public static void PressedOff(){ PressedButton = null;}

    public void setPressed()
    {
        isPressed = !isPressed;
        ToggleButton();
        UpdateBody();
        if(isPressed)
            this.button.setImageDrawable(Pressed);
        else
            this.button.setImageDrawable(UnPressed);
    }

    public Drawable getMappingBody()
    {
        return MappingBody;
    }
}
