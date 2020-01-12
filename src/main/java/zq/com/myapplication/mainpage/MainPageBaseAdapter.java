package zq.com.myapplication.mainpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import zq.com.myapplication.R;

public class MainPageBaseAdapter extends BaseAdapter {
    List<MainPageAdapterEntity> mData;
    Context context;

    public MainPageBaseAdapter(List<MainPageAdapterEntity> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MainPageAdapterEntity getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        ImageView device_img;
        TextView device_name;
        TextView device_desp;
        MainPageAdapterEntity entity = getItem(position);
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.mainpage_listview,null);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        device_name= (TextView) convertView.findViewById(R.id.name);
        device_desp = (TextView) convertView.findViewById(R.id.desp);
        device_img = (ImageView) convertView.findViewById(R.id.img);
        device_img.setImageResource(entity.getImg());
        device_desp.setText(entity.getDescription());
        device_name.setText(entity.getName());
        return convertView;
    }

    class ViewHolder{

    }
}
