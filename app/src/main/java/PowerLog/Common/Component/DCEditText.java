package PowerLog.Common.Component;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.TextView;

public class DCEditText implements TextWatcher {
    EditText source;
    TextView reject;
    public DCEditText(EditText editText) {
        source = editText;
        source.addTextChangedListener(this);
    }

    public DCEditText(EditText editText,TextView reject) {
        source = editText;
        source.addTextChangedListener(this);
        this.reject = reject;
    }

    public void setSource(EditText editText) {
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
        try {
            reject.setVisibility(View.INVISIBLE);
            if (s.toString().length() > 0)
                source.getBackground().setColorFilter(Color.parseColor("#3993de"),
                        PorterDuff.Mode.SRC_ATOP);
            else
                source.getBackground().setColorFilter(Color.parseColor("#33ffffff"),
                        PorterDuff.Mode.SRC_ATOP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
