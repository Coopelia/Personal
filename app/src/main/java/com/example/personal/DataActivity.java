package com.example.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DataActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonZhichu, buttonShouru, buttonBianqian;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        setTitle("数据管理");

        buttonZhichu = (Button)findViewById(R.id.buttonZhichu);
        buttonShouru = (Button)findViewById(R.id.buttonShouru);
        buttonBianqian = (Button)findViewById(R.id.buttonBianqian);

        buttonZhichu.setOnClickListener(this);
        buttonShouru.setOnClickListener(this);
        buttonBianqian.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonZhichu:
                intent = new Intent(this, showChart.class);
                startActivity(intent);
                break;
            case R.id.buttonShouru:
                intent = new Intent(this, showIncome.class);
                startActivity(intent);
                break;
            case R.id.buttonBianqian:
                intent = new Intent(this,showFA.class);
                startActivity(intent);
                break;
        }
    }
}
