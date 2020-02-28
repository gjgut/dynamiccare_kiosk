package com.example.dynamiccare_kisok.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Util.ACK;
import com.example.dynamiccare_kisok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelectWorkOut extends DCfragment {
    TextView txt_today;

    public SelectWorkOut() {
        super();
    }

    public SelectWorkOut(Main main) {
        super(main);
    }

    public SelectWorkOut(Administrator admin) {
        super(admin);
    }

    @Override
    public void onClick(View v) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_workout, container, false);

        try {

            Date currentTime = Calendar.getInstance().getTime();
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);

            txt_today = view.findViewById(R.id.txt_today);
            txt_today.setText(date_text);
//        ListView plan, workout;
//        ListViewAdapter adapter_plan, adapter_workout;
//
//        // 리스트뷰 참조 및 Adapter 달기
//        plan = (ListView) view.findViewById(R.id.list_plan);
//        workout = view.findViewById(R.id.list_workout);
//
//        adapter_plan = new ListViewAdapter(R.layout.list_plan_item);
//        adapter_workout = new ListViewAdapter(R.layout.list_workout_item);
//
//        plan.setAdapter(adapter_plan);
//        workout.setAdapter(adapter_workout);
//
//        AdapterView.OnItemClickListener listener= new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(main,"Hello", Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        plan.setOnItemClickListener(listener);
//        workout.setOnItemClickListener(listener);
//
//        adapter_plan.addItem("스쿼트", "20kg 3회");
//        adapter_plan.addItem("벤치프레스", "20kg 3회");
//        adapter_plan.addItem("데드리프트", "20kg 3회");
//
//        adapter_workout.addItem("스쿼트", "20kg 3회");
//        adapter_workout.addItem("벤치프레스", "20kg 3회");
//        adapter_workout.addItem("데드리프트", "20kg 3회");

            ListView plan, workout;
            ListViewAdapter adapter_plan, adapter_workout;

            // 리스트뷰 참조 및 Adapter 달기
            plan = view.findViewById(R.id.list_plan);
            workout = view.findViewById(R.id.list_workout);

            adapter_plan = new ListViewAdapter(R.layout.list_plan_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Hello","Hello");
//                    Toast.makeText(main, "Hello", Toast.LENGTH_SHORT).show();
                    main.ReplaceFragment(new ExcerciseMode(main),true);
                }
            });

            plan.setAdapter(adapter_plan);
            plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(main, "Hello", Toast.LENGTH_SHORT).show();
                }
            });

            adapter_plan.addItem("스쿼트", "20kg 3회");
            adapter_plan.addItem("벤치프레스", "20kg 3회");
            adapter_plan.addItem("데드리프트", "20kg 3회");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public DCfragment getBackFragment() {
        return null;
    }

    @Override
    public DCfragment getNextFragment() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int isHomeVisible() {
        return 0;
    }

    @Override
    public void HandleACK(ACK ack) {
        super.HandleACK(ack);
    }

}

class ListViewItem {
    String exc_title, exc_content;

    public String getExc_title() {
        return exc_title;
    }

    public void setExc_title(String exc_title) {
        this.exc_title = exc_title;
    }

    public String getExc_content() {
        return exc_content;
    }

    public void setExc_content(String exc_content) {
        this.exc_content = exc_content;
    }
}

class ListViewAdapter extends BaseAdapter {
    private int ListViewItem;
    View.OnClickListener buttonlistener;
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter(int layout,View.OnClickListener buttonlistener) {
        ListViewItem = layout;
        this.buttonlistener = buttonlistener;
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(ListViewItem, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView title = convertView.findViewById(R.id.item_exc_title);
        TextView content = convertView.findViewById(R.id.item_exc_content);
        ImageButton button = convertView.findViewById(R.id.item_btn_ok);

        button.setOnClickListener(buttonlistener);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        title.setText(listViewItem.getExc_title());
        content.setText(listViewItem.getExc_content());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String content) {
        ListViewItem item = new ListViewItem();

        item.setExc_title(title);
        item.setExc_content(content);

        listViewItemList.add(item);
    }
}
