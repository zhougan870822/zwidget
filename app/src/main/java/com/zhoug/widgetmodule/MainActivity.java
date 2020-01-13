package com.zhoug.widgetmodule;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.zhoug.widgets.dialog.BaseDialog;
import com.zhoug.widgets.dialog.list.ListDialog;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

    }

    private void findViews() {
        mBtn1 = findViewById(R.id.btn1);
        mBtn1.setOnClickListener(v->{
            ListDialog<String> listDialog=new ListDialog<String>(this,R.style.widget_dialog_full) {
                @Override
                public String getText(String itemData) {
                    return itemData;
                }
            };
            listDialog.setDefWindowAnimations();
            listDialog.setData(Arrays.asList("1","2"));
            listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
                }
            });
            listDialog.addQuxiaoBtn(true);
            listDialog.show();
        });
    }
}
