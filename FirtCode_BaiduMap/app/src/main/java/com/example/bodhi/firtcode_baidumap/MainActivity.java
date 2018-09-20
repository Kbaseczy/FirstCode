package com.example.bodhi.firtcode_baidumap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener; // 实现该接口时 registerLocationListener 表示deprecated,最后改成继承 BDAbstractLocationListener
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/*
    1.申请权限时，因为涉及多个权限申请，于是用List集合转载所有的权限
        最后，在申请时，将List转为Array
        在 请求判断时，用到循环判断，只要有一个权限被拒绝，改程序就会停止运行
    2.  implements BDLocationListener 改为 extends BDAbstractLocationListener,因为registerLocationListener 的 deprecated 问题
    3.
 */
public class MainActivity extends AppCompatActivity {

    public LocationClient locationClient;
    private TextView locationText;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFistLocate = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.bMap_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        locationText = findViewById(R.id.text_location);

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]); //todo 将permissionList转为Array
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else{
            initLocation();
        }

    }
    private void initLocation(){
        updateLocation();
        locationClient.start();
    }
    private void updateLocation(){
        // todo 通过LocationClientOption 实现实时更新定位信息->5s/次
        //todo  LocationClientOption
        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors); todo 设置定位方式GPS
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location){
        //todo baidumap  用到的
        // TODO 获取当前位置map   LatLng,MapStatusUpdate,MapStatusUpdateFactory,animateMapStatus
        if(isFistLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(20f); //todo 设置缩放级别
            baiduMap.animateMapStatus(update);
            isFistLocate = false;
        }
        // todo 获取当前所在位置  MyLocationData.Builder   MyLocationData  setMyLocationData
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(MainActivity.this,"必须同意所有权限才能使用该程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    initLocation();
                }else{
                    Toast.makeText(MainActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            if(location.getLocType() == BDLocation.TypeGpsLocation ||
                    location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder currentPosition = new StringBuilder();
                    currentPosition.append("Latitude:").append(location.getLatitude()).append("\t\t\t");
                    currentPosition.append("Longitude:").append(location.getLongitude()).append("\n");
                    currentPosition.append("Country:").append(location.getCountry()).append("\t\t\t");
                    currentPosition.append("Province:").append(location.getProvince()).append("\n");
                    currentPosition.append("City:").append(location.getCity()).append("\t\t\t");
                    currentPosition.append("District:").append(location.getDistrict()).append("\n");
                    currentPosition.append("Street:").append(location.getStreet()).append("\t\t\t");
                    currentPosition.append("Location style:");
                    if(location.getLocType() == BDLocation.TypeGpsLocation){
                        currentPosition.append("GPS");
                    }else if(location.getLocType() == BDLocation.TypeNetWorkLocation){
                        currentPosition.append("NetWork");
                    }
                    locationText.setText(currentPosition);
                }
            });
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
