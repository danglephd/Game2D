package com.pro.danglph.game2dbegin;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pro.danglph.game2dbegin.Surface.GameSurface;
import com.pro.danglph.game2dbegin.Surface.GameSurface2;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Loại bỏ tiêu đề.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//        setContentView(new GameSurface(this));
//        setContentView(new GameSurface2(this));

        setContentView(R.layout.activity_main);

//        GameSurface2 game = findViewById(R.id.gameSurface2);
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(game.getScore().getValue());

    }
}
