package com.asukim.deletemultipleselectedlistview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;

public class MainActivity extends Activity {
    List myList;
    ListView listView;
    MyListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = new ArrayList();

        //임시로 데이터 입력
        for (int i = 1; i < 30; i++) {
            myList.add("Item  " + i);
        }

        listView = (ListView) findViewById(R.id.listview);

        //adapter 생성후 layout이랑 배열 연결
        adapter = new MyListViewAdapter(this, R.layout.item, myList);
        listView.setAdapter(adapter);

        //다중 삭제를 위한 선택 모드
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.selectAll:
                        final int checkedCount = myList.size();

                        //항목을 이미 선택하거나 선택한 경우 제거하거나 선택 취소 후 다시 모두 선택
                        adapter.removeSelection();

                        for (int i = 0; i < checkedCount; i++) {
                            listView.setItemChecked(i, true);
                            //  listviewadapter.toggleSelection(i);
                        }
                        mode.setTitle(checkedCount + "  Selected");
                        return true;

                    case R.id.delete:
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("선택한 항목을 삭제하겠습니까?");
                        builder.setNegativeButton("No", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.setPositiveButton("Yes", new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SparseBooleanArray selected = adapter.getSelectedIds();

                                for (int i = (selected.size() - 1); i >= 0; i--) {
                                    if (selected.valueAt(i)) {
                                        String selecteditem = adapter.getItem(selected.keyAt(i));

                                        //다음에 선택한 항목 제거
                                        adapter.remove(selecteditem);
                                    }
                                }
                                mode.finish();
                                selected.clear();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.setIcon(R.mipmap.ic_launcher_round);// dialog  Icon
                        alert.setTitle("Confirmation"); // dialog  Title
                        alert.show();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                mode.setTitle(checkedCount + "  Selected");
                adapter.toggleSelection(position);
            }
        });
    }
}
