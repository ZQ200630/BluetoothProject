package zq.com.myapplication.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zq.com.myapplication.R;
import zq.com.myapplication.activities.utils.BluetoothUtils;
import zq.com.myapplication.adapter.MyBaseAdapter;

public class BluetoothDeviceListPageActivity extends AppCompatActivity {
    List<BluetoothDevice> deviceList = new ArrayList<>();
    MyBaseAdapter adapterForList = new MyBaseAdapter(this, deviceList);

    private class BluetoothReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(!deviceList.contains(device)) {
                        deviceList.add(device);
                        adapterForList.frest(deviceList);
                    }
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                    final BluetoothDevice stateChangeDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    int state = stateChangeDevice.getBondState();
                    List<BluetoothDevice> newData = new ArrayList<>();

                    switch (state) {
                        case BluetoothDevice.BOND_NONE:
                            for (BluetoothDevice bluetoothDevice : deviceList) {
                                if(!bluetoothDevice.getAddress().equals(stateChangeDevice.getAddress())) {
                                    newData.add(bluetoothDevice);
                                }
                            }
                            newData.add(stateChangeDevice);

                            adapterForList.frest(newData);
                            Toast.makeText(context, "配对已取消", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothDevice.BOND_BONDING:
                            break;
                        case BluetoothDevice.BOND_BONDED:
                            Toast.makeText(context, stateChangeDevice.getName(), Toast.LENGTH_SHORT).show();
                            for (BluetoothDevice bluetoothDevice : deviceList) {
                                if(!bluetoothDevice.getAddress().equals(stateChangeDevice.getAddress())) {
                                    newData.add(bluetoothDevice);
                                }
                            }
                            newData.add(stateChangeDevice);

                            adapterForList.frest(newData);
//                            Toast.makeText(context, "配对成功", Toast.LENGTH_SHORT).show();
                            break;
                    }
            }



        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_device_list_main);
        getPermission();
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        startActivity(discoverableIntent);
        BluetoothUtils.startDiscover(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(new BluetoothReceive(), filter);
        ListView listView = findViewById(R.id.BluetoothDeviceListview);
        listView.setAdapter(adapterForList);
        final SwipeRefreshLayout refreshLayout =findViewById(R.id.refrestLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                deviceList.clear();
                if(adapter.isDiscovering()) {
                    adapter.cancelDiscovery();
                }
                adapter.startDiscovery();
                adapterForList.frest(deviceList);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BluetoothDeviceListPageActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }


    private final int ACCESS_LOCATION = 1;

    @SuppressLint("WrongConstant")
    private void getPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int permissionCheck = 0;
            permissionCheck = this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionCheck += this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                //未获得权限
                this.requestPermissions( // 请求授权
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_LOCATION);// 自定义常量,任意整型
            }
        }
    }
}
