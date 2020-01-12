package zq.com.myapplication.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import zq.com.myapplication.R;
import zq.com.myapplication.activities.utils.BluetoothUtils;

public class MyBaseAdapter extends BaseAdapter {
    List<BluetoothDevice> mData;
    Context context;

    public MyBaseAdapter(Context context, List<BluetoothDevice> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        TextView device_name;
        TextView device_address;
        TextView device_bonded;
        final BluetoothDevice device = getItem(position);
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.bluetooth_device_list,null);
            viewHolder.btn_setting = (Button) convertView.findViewById(R.id.bluetoothListButton);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        device_name= (TextView) convertView.findViewById(R.id.bluetoothListName);
        device_address = (TextView) convertView.findViewById(R.id.bluetoothListAddress);
        device_bonded = (TextView) convertView.findViewById(R.id.bluetoothListBonded);
        if (device.getName() == null) {
            device_name.setText("未命名");
        } else {
            device_name.setText(device.getName());
        }
        device_address.setText(device.getAddress() + "  ");

        if (device.getBondState() == 12) {
            device_bonded.setText("已配对");
            viewHolder.btn_setting.setText("取消配对");
        } else if(device.getBondState() == 10) {
            device_bonded.setText("未配对");
            viewHolder.btn_setting.setText("配对");
        } else {
            device_bonded.setText("????");
        }
        viewHolder.btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (device.getBondState() == 10) {
                    device.createBond();
                    if (device.getName() == null) {
                        Toast.makeText(context, "正在与 未命名 配对", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "正在与 " + device.getName() + " 配对", Toast.LENGTH_SHORT).show();
                    }
                } else if(device.getBondState() == 12) {
                    Method m = null;
                    try {
                        m = device.getClass().getMethod("removeBond", (Class[]) null);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    try {
                        m.invoke(device, (Object[]) null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return convertView;
    }

    class ViewHolder{
        Button btn_setting;
    }

    public void frest(List<BluetoothDevice> mData) {
        this.mData = BluetoothUtils.sortBluetoothDevice(mData);

        notifyDataSetChanged();
    }

}
