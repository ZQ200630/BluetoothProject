package zq.com.myapplication.activities.utils;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;

import zq.com.myapplication.R;
import zq.com.myapplication.mainpage.MainPageAdapterEntity;

public class UIUtils {
    public static List<MainPageAdapterEntity> getMainPageList() {
        ArrayList<MainPageAdapterEntity> arrayList = new ArrayList<>();
        MainPageAdapterEntity blueTooth = new MainPageAdapterEntity("BlueTooth", "Manage Your Bluetooth Devices", R.drawable.bluetooth);
        MainPageAdapterEntity settings = new MainPageAdapterEntity("Settings", "Some Settings You Can Do", R.drawable.setting);
        arrayList.add(blueTooth);
        arrayList.add(settings);
        return arrayList;
    }
}
