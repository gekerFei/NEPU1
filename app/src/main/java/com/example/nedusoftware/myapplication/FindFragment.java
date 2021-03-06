package com.example.nedusoftware.myapplication;

/**
 * Created by NEDUsoftware on 2016/11/3.
 */

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;


public class FindFragment extends Fragment implements View.OnClickListener{

    private LocationManager locationmanager;
    private ToggleButton toggleButton;
    private BaiduMap baidumap;
    private MapView mapView;
    private String provider;
    private Button button,button1;

    public static FindFragment newInstance(String param1) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();

        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initView(View view){
        button = (Button)view.findViewById(R.id.location);
        button1 = (Button)view.findViewById(R.id.mylocation);
    }
public void initListener(){
    button.setOnClickListener(this);
    button1.setOnClickListener(this);
}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                Intent intent = new Intent(getActivity(), Location.class);
                startActivity(intent);
                break;
            case R.id.mylocation:
                Intent intent1 = new Intent(getActivity(),MyLocation.class);
                startActivity(intent1);
                break;
        }
    }
}





