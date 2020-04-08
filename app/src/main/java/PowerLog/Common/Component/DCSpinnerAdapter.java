package PowerLog.Common.Component;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import PowerLog.R;

import java.util.List;

public class DCSpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> data;
    List<Integer> number;
    LayoutInflater inflater;
    static TextView spinnerText;


    public DCSpinnerAdapter(Context context, List<Integer> number, List<String> data) {
        this.context = context;
        this.data = data;
        this.number = number;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DCSpinnerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static char getCurrentNumber() {
        return spinnerText.getText().toString().charAt(0);
    }


    @Override
    public int getCount() {
        if (data != null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.spinner_normal, parent, false);
            }

            spinnerText = (TextView) convertView.findViewById(R.id.spinnerText);
            if (data != null) {
                //데이터세팅
                String text = data.get(position);
                ((TextView) convertView.findViewById(R.id.spinnerText)).setText(text);
            }
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        try {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.spinner_drop_down, parent, false);
            }

            spinnerText = convertView.findViewById(R.id.spinnerText);
            //데이터세팅
            String text = data.get(position);
            spinnerText.setText(text);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }


        return convertView;
    }

    @Override
    public String getItem(int position) {
        Log.i("getItem", String.valueOf(position + 1));
        return String.valueOf(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}