package com.pro.danglph.game2dbegin.Surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pro.danglph.game2dbegin.Object.Ball;
import com.pro.danglph.game2dbegin.Object.Tiger;
import com.pro.danglph.game2dbegin.R;
import com.pro.danglph.game2dbegin.Thread.GameThread;

/**
 * Created by danglph on 10/07/2017.
 */

public class GameSurface2 extends SurfaceView implements SurfaceHolder.Callback, IGameSurface {

    private GameThread gameThread;
    private Tiger tiger = null;
    private Ball[] arrayBall = null;
    private Ball ball = null;
    private Bitmap bmTiger = BitmapFactory.decodeResource(this.getResources(), R.drawable.tigertranp2);
    private Bitmap bmBall = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballred);
    private Bitmap bmBall2 = null;
    private float scaleValue = 0.4f;

    public GameSurface2(Context context) {
        super(context);

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (ball != null && ball.isTouched(x, y)) {
                ball.decreaseScaleVal();
            }

            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (tiger != null) {
            tiger.draw(canvas);
        }
        if (ball != null) {
            ball.draw(canvas);
        }

        if (this.arrayBall != null && this.arrayBall.length > 0) {
            for (int i = 0; i < this.arrayBall.length; i++) {
                arrayBall[i].draw(canvas);
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.tiger = new Tiger(bmTiger, 50, this.getHeight() / 2, this);
        this.ball = new Ball(bmBall, 50, this.getHeight() / 2, this);
        this.bmBall2 = Bitmap.createScaledBitmap(bmBall, (int) (bmBall.getWidth() * scaleValue), (int) (bmBall.getHeight() * scaleValue), true);
        initBoard(9, 9);

        this.gameThread = new GameThread(this, surfaceHolder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    private void initBoard(int numcol, int numrow) {
        int length = numcol * numrow;
        this.arrayBall = new Ball[length];
        for (int i = 0; i < numrow; i++) {
            for (int j = 0; j < numcol; j++) {
                arrayBall[i * numcol + j] = new Ball(bmBall2, i * bmBall2.getWidth(), j * bmBall2.getHeight(), this);
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                this.gameThread.setRunning(false);

                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
                this.gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = true;
        }
    }

    @Override
    public void update() {
        if (tiger != null) {
            tiger.update();
        }
        if (ball != null) {
            ball.update();
        }

    }
}
