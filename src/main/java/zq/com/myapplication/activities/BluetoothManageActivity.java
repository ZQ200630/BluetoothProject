package zq.com.myapplication.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import zq.com.myapplication.R;
import zq.com.myapplication.adapter.MyBaseBluetoothManageAdapter;

public class BluetoothManageActivity extends AppCompatActivity {
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetoothmanage_main);
        Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
        List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>(bluetoothDeviceSet);
        MyBaseBluetoothManageAdapter myBaseBluetoothManageAdapter = new MyBaseBluetoothManageAdapter(this, bluetoothDeviceList);
        ListView listView = findViewById(R.id.bluetoothManageListView);
        listView.setAdapter(myBaseBluetoothManageAdapter);
    }
}
