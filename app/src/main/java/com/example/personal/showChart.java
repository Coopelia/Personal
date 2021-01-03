package com.example.personal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personal.util.DBOpenHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class showChart extends AppCompatActivity {
    private BarChart barChart;
    //y轴数据
    private List<BarEntry> list=new ArrayList<>();
    //数据的集合
    private BarDataSet dataSet1;
    //X轴数据名称
    private String[] lables;

    private DBOpenHelper dbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setTitle("支出汇总");
        barChart=findViewById(R.id.chart);
        dbOpenHelper = new DBOpenHelper(this);
        getData();
        show();
    }

    //获取数据
    private void getData()
    {
        lables = getResources().getStringArray(R.array.category_out);
        float[] values=new float[lables.length];
        String categoryName;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("user_out",null,null,null,null,null,null);
        if(cursor != null){
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                categoryName = cursor.getString(cursor.getColumnIndex("category"));
                for (int i =0;i<lables.length;i++)
                {
                    if(categoryName.equals(lables[i]))
                        values[i] += Float.parseFloat(cursor.getString(cursor.getColumnIndex("money")));
                }
            }
        }
        for (int i=0;i<lables.length;i++)
        {
            list.add(new BarEntry(i+1,values[i]));
        }
    }

    //可视化数据
    private  void show()
    {
        //设置数组数据
        dataSet1=new BarDataSet(list,"数据");
        //设置数组的颜色
        dataSet1.setColors(ColorTemplate.PASTEL_COLORS);
        //设置柱状图的字体大小
        dataSet1.setValueTextSize(20);

        //设置数据名称
        IAxisValueFormatter axisValueFormatter=new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value >= 0) {
                    return lables[(int) value % lables.length];
                } else {
                    return "";
                }
            }
        };

        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(dataSet1);
        BarData barData=new BarData(dataSets);
        barChart.setData(barData);
        barChart.getAxisLeft().setDrawLabels(true);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setValueFormatter(axisValueFormatter);
        barChart.getXAxis().setDrawLabels(true);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setTouchEnabled(false);//触摸
        barChart.setDragEnabled(false);//拖拽
        barChart.setScaleEnabled(false);//缩放
//        barChart.getXAxis().setLabelsToSkip(100);
        barChart.animateY(1000);
        barChart.setDrawBarShadow(false);
    }


}
