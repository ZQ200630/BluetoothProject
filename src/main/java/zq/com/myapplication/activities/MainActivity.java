package zq.com.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import zq.com.myapplication.R;
import zq.com.myapplication.activities.utils.UIUtils;
import zq.com.myapplication.mainpage.MainPageAdapterEntity;
import zq.com.myapplication.mainpage.MainPageBaseAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        List<MainPageAdapterEntity> list = UIUtils.getMainPageList();
        MainPageBaseAdapter adapter = new MainPageBaseAdapter(list, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Intent intent = new Intent(this, BluetoothPageActivity.class);
            startActivity(intent);
        } else if (position == 1) {

            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        }
    }



}
