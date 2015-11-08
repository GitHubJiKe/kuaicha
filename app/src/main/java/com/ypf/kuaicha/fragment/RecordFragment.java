
package com.ypf.kuaicha.fragment;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.ypf.kuaicha.R;
import com.ypf.kuaicha.activity.DetialActivity;
import com.ypf.kuaicha.adapter.MyRecordFragmentAdapter;
import com.ypf.kuaicha.entity.List;
import com.ypf.kuaicha.entity.Result;
import com.ypf.kuaicha.myMenu.MyMenu;
import com.ypf.kuaicha.util.Tools;

import java.util.ArrayList;

public class RecordFragment extends Fragment {
	private ListView listview;
	private ArrayList<Result>inflist;
	private MyRecordFragmentAdapter adapter;
	private MyMenu menu;
    private TextView set;
    private TextView update;
    private TextView back;
    private TextView about;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.activity_record, null);
		menu= (MyMenu) view.findViewById(R.id.mymenu);
        set=(TextView)menu.findViewById(R.id.set);
        update=(TextView)menu.findViewById(R.id.updete);
        back=(TextView)menu.findViewById(R.id.back);
        about=(TextView)menu.findViewById(R.id.about);
        listview= (ListView) view.findViewById(R.id.record);
        getData();
        setOnListener();
		return view;
	}
	@Override
	public void onResume() {
		getData();
		setOnListener();
		super.onResume();
	}

    /**
     * 为了解决ListView在ScrollView中只能显示一行数据的问题
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter adapter= listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (adapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

	private void setOnListener() {
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showToast("设置",getActivity());
            }
        }); update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showToast("更新",getActivity());
            }
        }); back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showToast("反馈",getActivity());
            }
        }); about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.showToast("关于",getActivity());
            }
        });
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                Builder dialog = new Builder(getActivity());
                dialog.setPositiveButton("删除", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbUtils db = DbUtils.create(getActivity());
                        try {
                            Result result = inflist.get(position);
                            db.delete(result);
                            getData();
                            adapter.notifyDataSetChanged();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.create().show();
                return false;
            }
        });
		
		listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Result result = inflist.get(position);
                java.util.List<List> list = result.getList();
                Intent intent = new Intent(getActivity(), DetialActivity.class);
                intent.putExtra("list", (ArrayList<List>) list);
                startActivity(intent);
            }
        });
	}
	private void setAdapter() {
		adapter=new MyRecordFragmentAdapter(getActivity(), inflist, R.layout.record_item);
		listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listview);
	}
	private void getData() {
		inflist=new ArrayList<Result>();
		DbUtils db=DbUtils.create(getActivity());
		try {
            inflist= (ArrayList<Result>) db.findAll(Result.class);
			if (inflist!=null) {
				setAdapter();
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
}
