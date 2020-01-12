package zq.com.myapplication.activities.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.app.ActivityCompat.startActivityForResult;

public class BluetoothUtils {


    private static BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public static boolean isEnable() {
        return bluetoothAdapter.isEnabled();
    }

    public static void changeBluetoothState(Activity context) {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            Toast.makeText(context, "Bluetooth Has Been Closed", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(context, intent, RESULT_OK, null);
        }
    }

    public static void startDiscover(Context context) {
        Toast.makeText(context, "开始扫描", Toast.LENGTH_SHORT).show();
        bluetoothAdapter.startDiscovery();
    }

    public static List<BluetoothDevice> sortBluetoothDevice(List<BluetoothDevice> mData) {
        List<BluetoothDevice> data = new ArrayList<>();
        int bondedCount = 0;
        int index = 0;
        for (BluetoothDevice mDatum : mData) {
            if (mDatum.getBondState() == 12) {
                data.add(0, mDatum);
                bondedCount++;
                index = bondedCount;
            } else if(mDatum.getName() != null) {
                data.add(index, mDatum);
            } else {
                data.add(mDatum);
            }
        }
        return data;
    }
}
