package com.example.personal;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.personal.model.UserFA;

import java.util.List;

public class FAListAdapter extends BaseAdapter{

    private static final String TAG = "FAListAdapter";

    //需要显示的数据
    private List<UserFA> list;

    private LayoutInflater layoutInflater;

    public FAListAdapter(List<UserFA> list, Context context){
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 每一行需要显示的view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");

        if(convertView == null){
            //通过布局文件list_item来取它的View对象
            convertView = layoutInflater.inflate(R.layout.falist_item,null);
        }
        //从list_item取id是textView的组件
        TextView idTextView = (TextView)convertView.findViewById(R.id.textView);
        TextView contentTextView = (TextView)convertView.findViewById(R.id.textView5);

        //取第一行需要显示的数据
        UserFA userFA = list.get(position);
        String content = userFA.getContent();

        //给每一个textview赋值(赋给需要显示的数据)
        idTextView.setText(String.valueOf(userFA.getId()));
        contentTextView.setText(content);

        return convertView;
    }
}
