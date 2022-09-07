package com.example.colorful_android.MapView;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class BaseMapview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        Intent intent = getIntent();
        TourSpot tourSpot = (TourSpot) intent.getSerializableExtra("tourSpot");

        // java code
        MapView mapView = new MapView(this);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(tourSpot.getPosition_y()), Double.parseDouble(tourSpot.getPosition_x())), true);
//        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.16865802, 126.62448375), 6, true);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

    }

}
