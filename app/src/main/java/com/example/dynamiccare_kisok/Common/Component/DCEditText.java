package com.example.dynamiccare_kisok.Common.Component;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.text.TextWatcher;

public class DCEditText implements TextWatcher {
    EditText source;
    public DCEditText(EditText editText)
    {
        source = editText;
        source.addTextChangedListener(this);
    }
    public void setSource(EditText editText)
    {
        source = editText;
        source.addTextChangedListener(this);
    }

    public EditText getSource() {
        return source;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(s.toString().length()>0)
            source.getBackground().setColorFilter(Color.parseColor("#3993de"),
                    PorterDuff.Mode.SRC_ATOP);
        else
            source.getBackground().setColorFilter(Color.parseColor("#33ffffff"),
                    PorterDuff.Mode.SRC_ATOP);
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}
