package com.PowerLog.Common.Component;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.PowerLog.Common.Object.Workout;
import com.PowerLog.R;

import java.util.ArrayList;

public class DCListViewAdapter extends BaseAdapter {
    private int ListViewItem;
    private ArrayList<com.PowerLog.Common.Component.DCListViewItem> itemContainer = new ArrayList<DCListViewItem>();


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<DCListViewItem> listViewItemList = new ArrayList<DCListViewItem>();

    // ListViewAdapter의 생성자
    public DCListViewAdapter(int layout) {
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
        try {
            final int pos = position;
            final Context context = parent.getContext();

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(ListViewItem, parent, false);
            }

            TextView title = convertView.findViewById(R.id.item_exc_title);
            TextView content = convertView.findViewById(R.id.item_exc_content);

            DCListViewItem DCListViewItem = listViewItemList.get(position);


            title.setText(DCListViewItem.getExc_title());
            content.setText(DCListViewItem.getExc_content());
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }


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
        try {
            DCListViewItem item = new DCListViewItem();
            item.setWorkout(workout);


            item.setExc_title(item.getWorkout().getExcercise().getSimpleName() + " " + item.getWorkout().getExcercise().getMuscleName());
            item.setExc_content(item.getWorkout().isKinetic() + ", " + (item.getWorkout().getIsKinetic()?item.getWorkout().getLevel()+" 레벨 ":item.getWorkout().getWeight() + "kg ") + item.getWorkout().getReps() + "회 " + item.getWorkout().getSet() + "세트");

            itemContainer.add(item);
        } catch (Exception e) {
            Log.i("Error", e.toString());
        }

    }

    public int getSize() {
        return itemContainer.size();
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
        DCListViewItem item = new DCListViewItem();
        item.setWorkout(workout);


        item.setExc_title(item.getWorkout().getExcercise().getSimpleName() + " " + item.getWorkout().getExcercise().getMuscleName());
        item.setExc_content(item.getWorkout().isKinetic() + ", " + item.getWorkout().getWeight() + "kg " + item.getWorkout().getReps() + "회 " + item.getWorkout().getSet() + "세트");

        listViewItemList.add(item);
    }
}
