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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dynamiccare_kisok.Activity.Administrator;
import com.example.dynamiccare_kisok.Activity.Main;
import com.example.dynamiccare_kisok.Common.Component.DCfragment;
import com.example.dynamiccare_kisok.Common.Excercise.BenchPress;
import com.example.dynamiccare_kisok.Common.Excercise.Squat;
import com.example.dynamiccare_kisok.Common.Object.ACK;
import com.example.dynamiccare_kisok.Common.Object.Workout;
import com.example.dynamiccare_kisok.R;

import java.lang.invoke.WrongMethodTypeException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelectWorkOut extends DCfragment {
    TextView txt_today;
    ConstraintLayout planlayout, worklayout;
    ImageButton btn_workout_right, btn_workout_left, btn_plan_right, btn_plan_left;
    int plan_page,workout_page;

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
            String date_text = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(currentTime);


            worklayout = (ConstraintLayout) view.findViewById(R.id.Workout);
            planlayout = (ConstraintLayout) view.findViewById(R.id.Plan);

            txt_today = view.findViewById(R.id.txt_today);
            txt_today.setText(date_text);
            ListView plan, workout;
            ListViewAdapter adapter_plan, adapter_workout;

            Workout workoutlist[] = {
                    new Workout(true, false, new Squat(main), 20, 20, 3),
                    new Workout(true, false, new Squat(main), 20, 20, 3),
                    new Workout(true, false, new Squat(main), 20, 20, 3),
                    new Workout(true, false, new BenchPress(main), 20, 20, 3),
                    new Workout(true, false, new Squat(main), 20, 20, 3),
                    new Workout(false, false, new Squat(main), 20, 20, 3),
                    new Workout(false, false, new Squat(main), 20, 20, 3),
                    new Workout(true, false, new Squat(main), 20, 20, 3),
            };

            btn_plan_left = view.findViewById(R.id.btn_plan_left);
            btn_plan_right = view.findViewById(R.id.btn_plan_right);
            btn_workout_left = view.findViewById(R.id.btn_workout_left);
            btn_workout_right = view.findViewById(R.id.btn_workout_right);


            // 리스트뷰 참조 및 Adapter 달기
            plan = view.findViewById(R.id.list_plan);
            workout = view.findViewById(R.id.list_workout);

            adapter_plan = new ListViewAdapter(R.layout.list_plan_item);
            adapter_workout = new ListViewAdapter(R.layout.list_workout_item);

            plan.setAdapter(adapter_plan);
            workout.setAdapter(adapter_workout);
            AdapterView.OnItemClickListener itemlistener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);
                        main.ReplaceFragment(new ExcerciseMode(main, item.getWorkout()), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            plan.setOnItemClickListener(itemlistener);
            workout.setOnItemClickListener(itemlistener);


            for (Workout work : workoutlist) {
                if (work.isWorkout()) {
                    worklayout.setVisibility(View.VISIBLE);
                    adapter_workout.Fillitem(work);
                } else {
                    planlayout.setVisibility(View.VISIBLE);
                    adapter_plan.Fillitem(work);
                }
            }

            adapter_plan.setPage(1);
            adapter_workout.setPage(1);


            btn_workout_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(workout_page<3)
                        workout_page++;
                    adapter_workout.setPage(workout_page);
                    adapter_workout.notifyDataSetChanged();
                }
            });
            btn_workout_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(workout_page>1)
                        workout_page--;
                    adapter_workout.setPage(workout_page);
                    adapter_workout.notifyDataSetChanged();
                }
            });

            btn_plan_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(plan_page<3)
                        plan_page++;
                    adapter_plan.setPage(plan_page);
                    adapter_plan.notifyDataSetChanged();
                }
            });
            btn_plan_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(plan_page>1)
                        plan_page--;
                    adapter_plan.setPage(plan_page);
                    adapter_plan.notifyDataSetChanged();
                }
            });

        } catch (Exception e) {
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
    Workout workout;

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

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
    private ArrayList<ListViewItem> itemContainer = new ArrayList<ListViewItem>();


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter(int layout) {
        ListViewItem = layout;
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(ListViewItem, parent, false);
        }

        TextView title = convertView.findViewById(R.id.item_exc_title);
        TextView content = convertView.findViewById(R.id.item_exc_content);

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

    public void Fillitem(Workout workout) {
        ListViewItem item = new ListViewItem();
        item.setWorkout(workout);


        item.setExc_title(item.getWorkout().getExcercise().getSimpleName() + " " + item.getWorkout().getExcercise().getMuscleName());
        item.setExc_content(item.getWorkout().isKinetic() + ", " + item.getWorkout().getWeight() + "kg " + item.getWorkout().getReps() + "회 " + item.getWorkout().getSet() + "세트");

        itemContainer.add(item);
    }

    public void NextPage() {
        listViewItemList.clear();

    }

    public void setPage(int page) {
        try {
            listViewItemList.clear();
            switch (page) {
                case 1:
                    listViewItemList.add(itemContainer.get(0));
                    listViewItemList.add(itemContainer.get(1));
                    listViewItemList.add(itemContainer.get(2));
                    break;
                case 2:
                    listViewItemList.add(itemContainer.get(3));
                    listViewItemList.add(itemContainer.get(4));
                    listViewItemList.add(itemContainer.get(5));
                    break;
                case 3:
                    listViewItemList.add(itemContainer.get(6));
                    listViewItemList.add(itemContainer.get(7));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Workout workout) {
        ListViewItem item = new ListViewItem();
        item.setWorkout(workout);


        item.setExc_title(item.getWorkout().getExcercise().getSimpleName() + " " + item.getWorkout().getExcercise().getMuscleName());
        item.setExc_content(item.getWorkout().isKinetic() + ", " + item.getWorkout().getWeight() + "kg " + item.getWorkout().getReps() + "회 " + item.getWorkout().getSet() + "세트");

        listViewItemList.add(item);
    }
}
