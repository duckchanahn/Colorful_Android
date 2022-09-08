package com.colorful.colorful_android.Color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.colorful.colorful_android.R;

public class PopupDeletePalette extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_palette);

//        Intent intent_get = getIntent();
//        TourCardView tourCardView = (TourCardView) intent_get.getSerializableExtra("cardView");

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
            Intent intent = new Intent();
                intent.putExtra( "result", true );
//                intent.putExtra( "cardView", tourCardView );
                setResult( RESULT_OK, intent );
                finish();
        });
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
