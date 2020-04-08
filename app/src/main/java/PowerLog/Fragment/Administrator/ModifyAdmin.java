package PowerLog.Fragment.Administrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import PowerLog.Activity.Administrator;
import PowerLog.Common.Component.DCEditText;
import PowerLog.Common.Component.DCfragment;
import PowerLog.R;

public class ModifyAdmin extends DCfragment {

    ImageButton back, newpwvisible;
    Button ok;
    DCEditText prev, New, correct;
    TextView reject;
    boolean isVisible;

    public ModifyAdmin() {
        super();
    }

    public ModifyAdmin(Administrator admin) {
        super(admin);
    }

    @Override
    public DCfragment getBackFragment() {
        return new Authentification(admin);
    }

    @Override
    public int isHomeVisible() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        try{
        switch (v.getId()) {
            case R.id.btn_set_admin_ok:
                admin.PlaySound(R.raw.normal_button);
                if (prev.getSource().getText().toString().equals(care.getAdminPassword())) {
                    care.setAdminPassword(New.getSource().getText().toString());
                    if (New.getSource().getText().toString().equals(correct.getSource().getText().toString()))
                        admin.ReplaceFragment(new Authentification(admin), false);
                }
                else if(!New.getSource().getText().toString().equals(correct.getSource().getText().toString()))
                {
                    reject.setVisibility(View.VISIBLE);
                    reject.setText("새로운 비밀번호를 다시 입력해주십시오.");
                }
                else if(!prev.getSource().getText().toString().equals(care.getAdminPassword()))
                {
                    reject.setVisibility(View.VISIBLE);
                    reject.setText("관리자 비밀번호가 틀립니다.");
                }
                break;
            case R.id.btn_newpw_visible:
                admin.PlaySound(R.raw.normal_button);
                if (isVisible) {
                    newpwvisible.setImageDrawable(admin.getDrawable(R.drawable.ic_visibility_off));
                    New.getSource().setTransformationMethod(PasswordTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible = false;
                } else {
                    newpwvisible.setImageDrawable(admin.getDrawable(R.drawable.ic_visibility_on));
                    New.getSource().setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    New.getSource().setSelection(New.getSource().length());
                    isVisible = true;
                }
                break;
        }
        }catch (Exception e)
        {
            Log.i("Error",e.toString());
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_set_admin, container, false);
        super.onCreate(savedInstanceState);
        try {

            back = view.findViewById(R.id.btn_back);
            newpwvisible = view.findViewById(R.id.btn_newpw_visible);
            ok = view.findViewById(R.id.btn_set_admin_ok);

            reject = view.findViewById(R.id.password_reject);
            reject.setVisibility(View.INVISIBLE);

            prev = new DCEditText(view.findViewById(R.id.et_prevpw),reject);
            New = new DCEditText(view.findViewById(R.id.et_newpw),reject);
            correct = new DCEditText(view.findViewById(R.id.et_pwconfirm),reject);

            ok.setOnClickListener(this);
            newpwvisible.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
