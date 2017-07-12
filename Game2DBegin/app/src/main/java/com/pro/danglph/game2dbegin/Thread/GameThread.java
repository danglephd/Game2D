package com.pro.danglph.game2dbegin.Thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.pro.danglph.game2dbegin.Surface.GameSurface;

/**
 * Created by danglph on 10/07/2017.
 */

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder) {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {

        long startTime = System.nanoTime();

        while (running) {
            Canvas canvas = null;
            try {
                // Lấy ra đối tượng Canvas và khóa nó lại.
                canvas = this.surfaceHolder.lockCanvas();

                // Đồng bộ hóa.
                synchronized (canvas) {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            } catch (Exception e) {
                // Không làm gì
            } finally {
                if (canvas != null) {
                    // Mở khóa cho Canvas.
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime();
            // Thời gian cập nhập lại giao diện Game
            // (Đổi từ Nanosecond ra milisecond).
            long waitTime = (now - startTime) / 1000000;
            if (waitTime < 10) {
                waitTime = 10; // Millisecond.
            }
            Log.i("info", " Wait Time=" + waitTime);

            try {
                // Ngừng chương trình một chút.
                this.sleep(waitTime);
            } catch (InterruptedException e) {

            }
            startTime = System.nanoTime();
            Log.i("info", ".");
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
