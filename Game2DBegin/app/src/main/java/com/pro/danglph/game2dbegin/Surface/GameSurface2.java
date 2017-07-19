package com.pro.danglph.game2dbegin.Surface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.pro.danglph.game2dbegin.Object.Ball;
import com.pro.danglph.game2dbegin.Object.Tiger;
import com.pro.danglph.game2dbegin.R;
import com.pro.danglph.game2dbegin.Thread.GameThread;
import com.pro.danglph.game2dbegin.Utility.CommonFeatures;
import com.pro.danglph.game2dbegin.Utility.SELECTION;

/**
 * Created by danglph on 10/07/2017.
 */

public class GameSurface2 extends SurfaceView implements SurfaceHolder.Callback, IGameSurface {
    private static final String TAG = GameSurface2.class.getSimpleName();

    private GameThread gameThread;
    private Tiger tiger = null;
    private Ball[] arrayBall = null;
    private Ball ball = null;
    private Ball startBall = null;
    private Ball t_l_Ball = null;
    private Ball t_r_Ball = null;
    private Ball b_l_Ball = null;
    private Ball b_r_Ball = null;
    private Bitmap bmTiger = BitmapFactory.decodeResource(this.getResources(), R.drawable.tigertranp2);
    private Bitmap bmBallred = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballred);
    private Bitmap bmBall2 = null;
    private SELECTION typeSelection = SELECTION.TYPE1;
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

    private void calculatePoint() {
        for (int i = 0; i < arrayBall.length; i++) {
            if (arrayBall[i].isSquared()) {
                arrayBall[i].update();
            }
        }
    }

    private boolean isBoardEmpty(Ball[] arrayBall) {
        return true;
    }

    private boolean isSquareAvailable(Ball t_l_Ball, Ball t_r_Ball, Ball b_l_Ball, Ball b_r_Ball) {
        if (t_l_Ball != null && t_r_Ball != null
                && b_l_Ball != null && b_r_Ball != null) {
            if ((t_l_Ball.getBallColor() == t_r_Ball.getBallColor()) &&
                    (t_l_Ball.getBallColor() == b_l_Ball.getBallColor()) &&
                    (t_l_Ball.getBallColor() == b_r_Ball.getBallColor())) {
                return true;
            }
        }
        return false;
    }

    private Ball selectStartBall(int x, int y) {
        Ball selectedBall = null;
        for (int i = 0; i < arrayBall.length; i++) {
            if (arrayBall[i].isTouched(x, y)) {
                selectedBall = arrayBall[i];
                selectedBall.setBgStates(typeSelection);
                return selectedBall;
            }
        }
        return selectedBall;
    }

    private void selectSquare(int endx, int endy) {
        int startx = endx;
        int starty = endy;
        t_l_Ball = null;
        t_r_Ball = null;
        b_l_Ball = null;
        b_r_Ball = null;


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
                arrayBall[i].setBgStates(typeSelection);
            } else {
                arrayBall[i].setBgStates(SELECTION.TYPE1);
            }

            if (arrayBall[i].isTouched(startx, starty)) {
                t_l_Ball = arrayBall[i];
                Log.i(TAG, "t_l_Ball>>>" + t_l_Ball.getBallColor());
            }
            else if (arrayBall[i].isTouched(endx, endy)) {
                b_r_Ball = arrayBall[i];
                Log.i(TAG, "b_r_Ball>>>" + b_r_Ball.getBallColor());
            }
            else if (arrayBall[i].isTouched(startx, endy)) {
                b_l_Ball = arrayBall[i];
                Log.i(TAG, "b_l_Ball>>>" + b_l_Ball.getBallColor());
            }
            else if (arrayBall[i].isTouched(endx, starty)) {
                t_r_Ball = arrayBall[i];
                Log.i(TAG, "t_r_Ball>>>" + t_r_Ball.getBallColor());
            }
        }
    }

    private void clearSquare() {
        typeSelection = SELECTION.TYPE1;
        for (int i = 0; i < arrayBall.length; i++) {
            arrayBall[i].setBgStates(typeSelection);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            typeSelection = SELECTION.NOT_SQUARE_TYPE;
            startBall = selectStartBall(x, y);
            if (startBall != null) {

            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (startBall != null) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                selectSquare(x, y);
                if(isSquareAvailable(this.t_l_Ball, this.t_r_Ball, this.b_l_Ball, this.b_r_Ball)){
//                    Log.i(TAG, "AAA>>>" + x + ", " + y);
                    typeSelection = SELECTION.SQUARED_TYPE;
                }else{
                    typeSelection = SELECTION.NOT_SQUARE_TYPE;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (startBall != null) {
                if (isSquareAvailable(this.t_l_Ball, this.t_r_Ball, this.b_l_Ball, this.b_r_Ball)) {
                    calculatePoint();
                }
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
//        int randBall = CommonFeatures.randomIntValue(0, 5);
        for (int i = 0; i < numrow; i++) {
            for (int j = 0; j < numcol; j++) {
//                randBall = CommonFeatures.randomIntValue(0, 5);
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
