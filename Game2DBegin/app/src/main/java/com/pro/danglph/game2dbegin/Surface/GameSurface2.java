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
    private Ball startBall = null;
    private Bitmap bmTiger = BitmapFactory.decodeResource(this.getResources(), R.drawable.tigertranp2);
    private Bitmap bmBallred = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballred);
    private Bitmap bmBall2 = null;
    private float scaleValue = 0.4f;

    public GameSurface2(Context context) {
        super(context);

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
    }

    private void initializeGame() {

    }

    private void calculatePoint(Ball releaseBall) {

    }

    private boolean isBoardEmpty(Ball[] arrayBall) {
        return true;
    }

    private boolean isSquareAvailable(Ball[] arrayBall) {
        return true;
    }

    private Ball selectStartBall(int x, int y) {
        Ball selectedBall = null;
        for (int i = 0; i < arrayBall.length; i++) {
            if (arrayBall[i].isTouched(x, y)) {
                selectedBall = arrayBall[i];
                selectedBall.setSelected(true);
                return selectedBall;
            }
        }
        return selectedBall;
    }

    private void selectSquare(int endx, int endy) {
        int startx = endx;
        int starty = endy;
//        int startx = startBall.getX() < endx ? startBall.getX() : endx;
//        int starty = startBall.getY() < endy ? startBall.getY() : endy;
//        endx = startBall.getX() < endx ? endx : startBall.getX();
//        endy = startBall.getY() < endy ? endy : startBall.getY();

//        if(startx != startBall.getX() || starty != startBall.getY()){
//            for (int i = 0; i < arrayBall.length; i++) {
//                if (arrayBall[i].isTouched(startx, starty)) {
//                    startx =  arrayBall[i].getX();
//                    starty =  arrayBall[i].getY();
//                    break;
//                }
//            }
//        }

        for (int i = 0; i < arrayBall.length; i++) {
            if (arrayBall[i].isTouched(endx, endy)) {
                startx = startBall.getX() < arrayBall[i].getX() ? startBall.getX() : arrayBall[i].getX();
                starty = startBall.getY() < arrayBall[i].getY() ? startBall.getY() : arrayBall[i].getY();
                endx = startBall.getX() > arrayBall[i].getX() ? startBall.getX() : arrayBall[i].getX();
                endy = startBall.getY() > arrayBall[i].getY() ? startBall.getY() : arrayBall[i].getY();

                break;
            }
        }

        for (int i = 0; i < arrayBall.length; i++) {
            if (arrayBall[i].getX() >= startx && arrayBall[i].getX() <= endx
                    && arrayBall[i].getY() >= starty && arrayBall[i].getY() <= endy) {
                arrayBall[i].setSelected(true);
            } else {
                arrayBall[i].setSelected(false);
            }
        }
    }

    private void clearSquare() {
        for (int i = 0; i < arrayBall.length; i++) {
            arrayBall[i].setSelected(false);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            startBall = selectStartBall(x, y);
            if (startBall != null) {

            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (startBall != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                selectSquare(x, y);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (startBall != null) {
                clearSquare();
            }
        }

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
        this.ball = new Ball(bmBallred, 50, this.getHeight() / 2, this);
        this.bmBall2 = Bitmap.createScaledBitmap(bmBallred, (int) (bmBallred.getWidth() * scaleValue), (int) (bmBallred.getHeight() * scaleValue), true);
        initBoard(12, 12);

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
