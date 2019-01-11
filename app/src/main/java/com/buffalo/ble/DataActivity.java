package com.buffalo.ble;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by 601042 on 2017/5/26.
 */
public class DataActivity extends AppCompatActivity {
    private static final String TAG = "DataActivity";
    Button data_btn_write;
    Button data_btn_notify;
    Button data_btn_send;
    EditText data_edt_write;
    TextView data_tv_show;
    Handler mHandler = new Handler();
    GetDataCallback callback = new GetDataCallback() {
        @Override
        public void onGetData(String characteristicUUid, final byte[] data) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                   data_tv_show.setText(data_tv_show.getText().toString()+CodeUtil.bytesToString(data));
                }
            });
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initView();
        AppContext.getInstance().getmBle().setDataCallback(callback);

    }

    private void initView() {
        data_btn_write = (Button) findViewById(R.id.data_btn_write);
        data_btn_notify = (Button) findViewById(R.id.data_btn_notify);
        data_btn_send = (Button) findViewById(R.id.data_btn_send);

        data_edt_write = (EditText) findViewById(R.id.data_edt_write);
        data_tv_show = (TextView) findViewById(R.id.data_tv_show);
        data_btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataActivity.this,ServicesList.class);
                i.putExtra("type",1);
                startActivity(i);
            }
        });
        data_btn_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataActivity.this,ServicesList.class);
                i.putExtra("type",0);
                startActivity(i);
            }
        });
        data_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppContext.getInstance().getmBle().sendByBLE("1b000012292e8bfb0100363636365603391e");
            }
        });



    }
}
