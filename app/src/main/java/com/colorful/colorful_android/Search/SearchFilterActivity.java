package com.colorful.colorful_android.Search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.colorful.colorful_android.DTO.TourSpot;
import com.colorful.colorful_android.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterActivity extends Activity {

    private ArrayList<TourSpot> tourSpotList;
    private ArrayList<TourSpot> tourSpotList_filter;
    private ArrayList<String> checkedArea;
    private ImageView btn_Back;
    private Button btn_excuteFilter;

    private ChipGroup chipGroupArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filter_page);

        Intent intent_getDate = getIntent();
        this.tourSpotList = (ArrayList<TourSpot>) intent_getDate.getSerializableExtra("tourSpot");
        this.checkedArea = (ArrayList<String>) intent_getDate.getSerializableExtra("tourSpot");
        Log.e("filter", "tourSpotList Size : " + tourSpotList.size());

        this.btn_Back = findViewById(R.id.btn_back);
        this.btn_Back.setOnClickListener( v -> {
            finish();
        });


        this.chipGroupArea = findViewById(R.id.chip_group_area);
        this.chipGroupArea.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                checkedArea = new ArrayList<>();
                for (int i : chipGroupArea.getCheckedChipIds()) {
                    checkedArea.add(((Chip)findViewById(i)).getText().toString());
                }
            }
        });

        this.btn_excuteFilter = findViewById(R.id.excute_filter);
        this.btn_excuteFilter.setOnClickListener( v -> {
            if(checkedArea == null) {
                Toast.makeText(this, "필터를 적용해주세요!", Toast.LENGTH_SHORT);
                return;
            } else {
                String checked = "";
                for (String area : checkedArea) {
                    for (int i = 0; i < tourSpotList.size(); i++) {
                        tourSpotList.get(i).getArea();
                        area.toString();
                        if(tourSpotList.get(i).getArea().contains(area)) {
                            Log.e("filter", "tourSpotList name : " + tourSpotList.get(i).getName() + " tourSpotList area : " + tourSpotList.get(i).getArea());
                            tourSpotList_filter.add(tourSpotList.get(i));
                        }
                    }
                }
            }

            Intent intent_setFilter = new Intent(this, SerachActivity.class);
            intent_setFilter.putExtra("chipGroup", checkedArea);
            intent_setFilter.putExtra("chipGroup", tourSpotList_filter);

            finish();
            getParent().finish();
        });
    }
}
