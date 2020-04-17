package com.asukim.deletemultipleselectedlistview;

import java.util.List;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdapter extends ArrayAdapter<String> {

    Context myContext;
    LayoutInflater inflater;
    List<String> DataList;
    private SparseBooleanArray mSelectedItemsIds;

    //context 와 arrayList를 얻기위한 생성자
    public MyListViewAdapter(Context context, int resourceId, List<String> lists) {
        super(context, resourceId, lists);
        mSelectedItemsIds = new SparseBooleanArray();
        myContext = context;
        DataList = lists;
        inflater = LayoutInflater.from(context);
    }


    //아이템 컨테이너 클래스
    private class ViewHolder {
        TextView textView;
        ImageView imageView;
    }


    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item, null);

            //item.xml에서 textview를 선택.
            holder.textView = (TextView) view.findViewById(R.id.textView);

            //item.xml에서 imageView를 선택.
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //배열중에 해당 position값의 정보를 textView에 출력
        holder.textView.setText(DataList.get(position).toString());

        //배열중에 해당 position값의 정보를 imageView에 출력
        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        return view;
    }


    @Override
    public void remove(String object) {
        DataList.remove(object);
        notifyDataSetChanged();
    }


    //업데이트 또는 삭제 후 목록 가져 오기
    public List<String> getMyList() {
        return DataList;
    }


    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //선택 해제 후 선택 제거
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //선택시 체크된 된 항목
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }


    //선택한 항목 수 가져 오기
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }


    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}