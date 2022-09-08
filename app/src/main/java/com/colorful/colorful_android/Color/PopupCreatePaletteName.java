package com.colorful.colorful_android.Color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.colorful.colorful_android.R;

public class PopupCreatePaletteName extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_palette_name);

//        Intent intent_get = getIntent();
//        TourCardView tourCardView = (TourCardView) intent_get.getSerializableExtra("cardView");

        EditText paletteName = findViewById(R.id.palette_name);

        Button cancel = findViewById(R.id.btn_cancel);
        cancel.setOnClickListener( v -> {
            Intent intent = new Intent();
            intent.putExtra( "result", false );
//            intent.putExtra( "cardView", tourCardView );
            setResult( RESULT_OK, intent );
            finish();
        });
        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener( v -> {

            if(paletteName.getText().toString() == null) {
                Toast.makeText(getBaseContext(), "팔레트 이름을 입력해주세요", Toast.LENGTH_SHORT);
            } else {
                Intent intent = new Intent();
                intent.putExtra("result", true);
                intent.putExtra("paletteName", paletteName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
