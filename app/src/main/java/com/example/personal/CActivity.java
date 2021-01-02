package com.example.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.personal.model.UserInfo;
import com.example.personal.model.UserOut;
import com.example.personal.util.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "CActivity";

    private ListView listView;

    private List<UserOut> data;

    //自定义适配器
    private CListAdapter listAdapter;

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c_list);

        setTitle("我的支出");

        listView = (ListView)findViewById(R.id.list_view);
        dbOpenHelper = new DBOpenHelper(this);
        //取数据
        data=getData();
        //创建适配器对象
        listAdapter = new CListAdapter(this.data,this);
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
    public List<UserOut> getData(){
        List<UserOut> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("user_out",null,null,null,null,null,null);
        if(cursor != null){
            int i=1;
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                UserOut userOut = new UserOut();
                userOut.setId(i++);
                userOut.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                userOut.setMoney(cursor.getString(cursor.getColumnIndex("money")));
                userOut.setTime(cursor.getString(cursor.getColumnIndex("time")));
                list.add(userOut);
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
        UserOut userOut = data.get(position);
        Log.d(TAG, userOut.toString());
        Intent intent = getIntent();
        intent.setClass(this,CUpdateActivity.class);
        Bundle bundle = new Bundle();
        //把需要传的对象(必须要序列化)放入bundle
        bundle.putSerializable("userOut",userOut);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
