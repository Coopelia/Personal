package com.example.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.personal.model.UserFA;
import com.example.personal.util.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class showFA extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "showFA";

    private ListView listView;

    private List<UserFA> data;

    //自定义适配器
    private FAListAdapter listAdapter;

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fa_list);

        setTitle("便签管理");

        listView = (ListView)findViewById(R.id.list_view);
        dbOpenHelper = new DBOpenHelper(this);
        //取数据
        data=getData();
        //创建适配器对象
        listAdapter = new FAListAdapter(this.data,this);
        //绑定适配器
        listView.setAdapter(listAdapter);
        //添加监听器
        listView.setOnItemClickListener(this);
    }

    /**
     * 重新显示界面
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart() called");
        //清空列表
        data.clear();
        //取列表数据
        data.addAll(getData());
        //通知适配器数据更新
        listAdapter.notifyDataSetChanged();

    }

    /**
     * 取列表数据
     */
    public List<UserFA> getData(){
        List<UserFA> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("user_fa",null,null,null,null,null,null);
        if(cursor != null){
            int i=1;
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                UserFA userFA = new UserFA();
                userFA.setId(i++);
                userFA.setContent(cursor.getString(cursor.getColumnIndex("content")));
                list.add(userFA);
            }
        }

        return list;
    }

    /**
     * 监听列表项点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Log.d(TAG, "onItemClick() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
        UserFA userFA = data.get(position);
        Log.d(TAG, userFA.toString());
        Intent intent = getIntent();
        intent.setClass(this,FAUpdateActivity.class);
        Bundle bundle = new Bundle();
        //把需要传的对象(必须要序列化)放入bundle
        bundle.putSerializable("userFA",userFA);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
