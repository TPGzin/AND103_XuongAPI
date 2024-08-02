package edu.fpt.lenovo.shoponline.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import edu.fpt.lenovo.shoponline.R;
import edu.fpt.lenovo.shoponline.ultil.CheckConnetion;

public class HelloActivity extends AppCompatActivity {
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        Timer timer = new Timer();
        if(CheckConnetion.haveNetworkConnection(getApplicationContext()))
        {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(HelloActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2000);
        }
        else
        {
            CheckConnetion.ShowToastLong(getApplicationContext(),"Khong co mang");
            finish();
        }
    }
}
