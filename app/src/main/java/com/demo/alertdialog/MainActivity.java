package com.demo.alertdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder  alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.app_name)
                .setCancelable(true);
        alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                Toast.makeText(MainActivity.this,"keycode:" + keyCode,Toast.LENGTH_LONG).show();
                if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_F12){
                    dialog.dismiss();
                    return  true;
                }
                return false;
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();//将dialog显示出来
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerHomeKeyReceiver(MainActivity.this);
    }

    @Override
    protected void onPause() {

        unregisterHomeKeyReceiver(this);
        super.onPause();
    }


    private static HomeWatcherReceiver mHomeKeyReceiver = null;

    private static void registerHomeKeyReceiver(Context context) {

        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private static void unregisterHomeKeyReceiver(Context context) {

        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }
}
