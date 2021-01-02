package com.example.personal;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.personal.model.UserFA;
import com.example.personal.util.DBOpenHelper;

import java.util.Calendar;


public class FAUpdateActivity  extends Activity implements View.OnClickListener{
    private static final String TAG = "FAUpdateActivity";

    private EditText editText;

    private Button button,button1;

    private String content;

    private int id;

    private DBOpenHelper dbOpenHelper;

    private UserFA userFA;

    private Intent intent;

    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faupdate);

        editText = (EditText)findViewById(R.id.editText);//money


        button = (Button)findViewById(R.id.button5);
        button1 = (Button)findViewById(R.id.button6);


        button.setOnClickListener(this);
        button1.setOnClickListener(this);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                this,R.array.category_in,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userFA = (UserFA)bundle.get("userFA");
        //Toast.makeText(this, studentInfo.toString(),Toast.LENGTH_SHORT).show();
        editText.setText( userFA.getContent());

        dbOpenHelper = new DBOpenHelper(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button5:
                //主键
                id = userFA.getId();
                content = editText.getText().toString();
                db = dbOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("content",content);
                //第一个参数:表名
                //第二个参数:需要修改的值
                //第三个参数：修改的条件
                //第四个参数:修改的条件的值的数组
                db.update("user_fa",contentValues,"_id=?",new String[]{String.valueOf(id)});
                Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button6:
                id = userFA.getId();
                db = dbOpenHelper.getWritableDatabase();
                db.delete("user_fa","_id=?",new String[]{String.valueOf(id)}  );
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
