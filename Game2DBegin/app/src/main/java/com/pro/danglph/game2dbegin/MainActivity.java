package com.pro.danglph.game2dbegin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pro.danglph.game2dbegin.Surface.GameSurface;
import com.pro.danglph.game2dbegin.Surface.GameSurface2;
import com.pro.danglph.game2dbegin.Surface.IGameSurface;

public class MainActivity extends Activity {
    IGameSurface game = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(game != null && (game instanceof GameSurface2)){
            ((GameSurface2) game).saveGameData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(game != null && (game instanceof GameSurface2)) {
            ((GameSurface2) game).onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(game != null && (game instanceof GameSurface2)){
            ((GameSurface2) game).saveGameData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Loại bỏ tiêu đề.
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);


//        setContentView(new GameSurface(this));
//        setContentView(new GameSurface2(this));


        setContentView(R.layout.activity_main);
//        setContentView(R.layout.game_layout);

        game = findViewById(R.id.gameSurface2);
//        TextView textView = findViewById(R.id.textView);
//        textView.setText(game.getScore().getValue());

    }


}
