package zq.com.myapplication.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import zq.com.myapplication.R;
import zq.com.myapplication.activities.utils.BluetoothUtils;
import zq.com.myapplication.service.Client;
import zq.com.myapplication.service.Server;

public class BluetoothPageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothpage_main);
        Switch switch1 = findViewById(R.id.switch_enableBluetooth);
        Button button1 = findViewById(R.id.bluetoothListButton);
        switch1.setChecked(BluetoothUtils.isEnable());
        switch1.setOnClickListener(this);
        button1.setOnClickListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
        Button button2 = findViewById(R.id.manageConnectionButton);
        button2.setOnClickListener(this);
        Button button = findViewById(R.id.becomeServer);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_enableBluetooth:
                BluetoothUtils.changeBluetoothState(this);
                break;
            case R.id.bluetoothListButton:
                Switch switch1 = findViewById(R.id.switch_enableBluetooth);
                if(switch1.isChecked()) {
                    Toast.makeText(this, "进入蓝牙配对页面", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, BluetoothDeviceListPageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "进入失败，请打开蓝牙", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.manageConnectionButton:
                Intent manageBlutoothIntent = new Intent(this, BluetoothManageActivity.class);
                startActivity(manageBlutoothIntent);
                break;
            case R.id.becomeServer:
                Intent intent = new Intent(this, Server.class);
                startActivity(intent);
        }
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    Switch switch1 = findViewById(R.id.switch_enableBluetooth);
                    switch (state) {
                        case BluetoothAdapter.STATE_OFF:
                            switch1.setChecked(false);
                            break;
                        case BluetoothAdapter.STATE_ON:
                            switch1.setChecked(true);
                            break;
                    }
            }
        }
    };

}
