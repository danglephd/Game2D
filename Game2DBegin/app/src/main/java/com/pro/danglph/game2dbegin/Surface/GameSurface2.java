package com.pro.danglph.game2dbegin.Surface;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.pro.danglph.game2dbegin.Object.Ball;
import com.pro.danglph.game2dbegin.Object.Score;
import com.pro.danglph.game2dbegin.Object.Tiger;
import com.pro.danglph.game2dbegin.R;
import com.pro.danglph.game2dbegin.Thread.GameThread;
import com.pro.danglph.game2dbegin.Utility.CommonFeatures;
import com.pro.danglph.game2dbegin.Utility.SELECTION;

import java.util.Iterator;


/**
 * Created by danglph on 10/07/2017.
 */

public class GameSurface2 extends SurfaceView implements SurfaceHolder.Callback, IGameSurface {
    private static final String TAG = GameSurface2.class.getSimpleName();

    private long totalScore = 0;
    private Score score = null;
    private GameThread gameThread;
    private Tiger tiger = null;
    private Ball[] arrayBall = null;
    private Ball ball = null;
    private Ball startBall = null;
    private Ball t_l_Ball = null;
    private Ball t_r_Ball = null;
    private Ball b_l_Ball = null;
    private Ball b_r_Ball = null;
    int numbrow = 15;
    int numbcol = 17;

//    private Ball t_l_Ball_Temp = null;
//    private Ball t_r_Ball_Temp = null;
//    private Ball b_l_Ball_Temp = null;
//    private Ball b_r_Ball_Temp = null;

    private Bitmap bmTiger = BitmapFactory.decodeResource(this.getResources(), R.drawable.tigertranp2);
    private Bitmap bmBallred = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballred);
    private Bitmap bmBallblue = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballblue);
    private Bitmap bmBallgray = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballgray);
    private Bitmap bmBallgreen = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballgreen);
    private Bitmap bmBallmagenta = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballmagenta);
    private Bitmap bmBallyellow = BitmapFactory.decodeResource(this.getResources(), R.drawable.ballyellow);
    private Bitmap bmBall2 = null;
    private SELECTION typeSelection = SELECTION.TYPE1;
    private float scaleValue = 0.4f;

    public GameSurface2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public GameSurface2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
    }

    public GameSurface2(Context context) {
        super(context);

        // Đảm bảo Game Surface có thể focus để điều khiển các sự kiện.
        this.setFocusable(true);

        // Sét đặt các sự kiện liên quan tới Game.
        this.getHolder().addCallback(this);
    }

    private String loadGameData() {

        Activity context = ((Activity) (this.getContext()));

        SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        try {
            totalScore = Long.parseLong(sharedPref.getString(this.getResources().getString(R.string.score), "0"));
            return sharedPref.getString(this.getResources().getString(R.string.board), "");

        } catch (Exception ex) {
            totalScore = 0;
            Log.e(TAG, ex.getMessage());
            return "";
        }
    }

    public void saveGameData() {
        try {
            String boardStr = dataBoard(this.numbrow, this.numbcol, this.arrayBall);
            Activity context = ((Activity) (this.getContext()));

            SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(this.getResources().getString(R.string.score), totalScore + "");
            editor.putString(this.getResources().getString(R.string.board), boardStr);
            editor.commit();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    private String dataBoard(int numbcol, int numbrow, Ball[] arrayBall) {
        if (arrayBall != null) {
            StringBuilder boardStr = new StringBuilder();
            boardStr.append(numbcol);
            boardStr.append(CommonFeatures.splitCharactor_5);
            boardStr.append(numbrow);
            boardStr.append(CommonFeatures.splitCharactor_5);
            for (Ball ball : arrayBall) {
                boardStr.append(ball.getBallColor());
            }
            boardStr.append(CommonFeatures.splitCharactor_3);
            return boardStr.toString();
        } else {
            return "";
        }
    }


    private void initializeGame() {
        String databoard = loadGameData();
        score = new Score(null, this.getWidth() / 2, this.getHeight() / 2, this);
        try {
            databoard = "";
            String[] splitData = databoard.split(CommonFeatures.splitCharactor_5);
            numbrow = Integer.parseInt(splitData[0]);// số cột
            numbcol = Integer.parseInt(splitData[1]);// số dòng
            initBoard(numbrow, numbcol, splitData[2]);// board game
            //sau này bổ sung các lưu khác

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            this.bmBall2 = Bitmap.createScaledBitmap(bmBallred, (int) (bmBallred.getWidth() * scaleValue), (int) (bmBallred.getHeight() * scaleValue), true);
            ///for test color
//        this.t_l_Ball_Temp = new Ball(bmBall2, 50, this.getHeight() / 2 + 200, this);
//        this.t_r_Ball_Temp = new Ball(bmBall2, 350, this.getHeight() / 2 + 200, this);
//        this.b_l_Ball_Temp = new Ball(bmBall2, 50, this.getHeight() / 2 + 400, this);
//        this.b_r_Ball_Temp = new Ball(bmBall2, 350, this.getHeight() / 2 + 400, this);

            numbcol = this.getWidth() / ((int) (bmBallred.getWidth() * scaleValue));
            numbrow = this.getHeight() / ((int) (bmBallred.getHeight() * scaleValue));

            initBoard(numbcol, numbrow, "");
        }
        updateScore();
    }


    private void showBoard(Ball[] arrayBall) {
        String txt = "";
        for (Ball ball : arrayBall) {
            txt += ball.getBallColor() + " ";
        }
        Log.d(TAG, txt);
    }

    private void calculatePoint() {
        int scoreValue = 0;
        for (Ball ball :
                arrayBall) {
            if (ball.isSquared()) {
                int randBall = CommonFeatures.randomIntValue(0, CommonFeatures.MAX_BALL);
                ball.setBitmap(createRandImgBall(randBall));
                ball.setBallColor(randBall);
                scoreValue++;
            }
        }
//                (int i = 0; i < arrayBall.length; i++) {
//            if (arrayBall[i].isSquared()) {
//                int randBall = CommonFeatures.randomIntValue(0, CommonFeatures.MAX_BALL);
//                arrayBall[i].setBitmap(createRandImgBall(randBall));
//                arrayBall[i].setBallColor(randBall);
//                scoreValue++;
//            }
//        }
        totalScore += scoreValue;
//        saveGameData();
        updateScore();
        score.setValue(scoreValue + "000");
        score.setVisible(true);
    }

    private void updateScore() {
        TextView textView = ((Activity) (this.getContext())).findViewById(R.id.textView);
        if (textView != null) {
            textView.setText(totalScore + "000");
        }
    }

    private boolean isBoardEmpty(Ball[] arrayBall) {
//        int maxIndex = arrayBall.length - (this.numbcol + 1);
        int i = 0, j = 0, k = 0, l = 0;
//        long t;
//        t = System.currentTimeMillis();

        try {
            for (i = 0; i < numbrow - 1; i++) {
                for (j = 0; j < numbcol - 1; j++) {
                    int index_t_l = i * (numbcol) + j;
                    for (k = numbcol - 1; k > j; k--) {
                        int index_t_r = i * (numbcol) + k;
//                        Log.i(TAG, "t_l ~ t_r: (" + index_t_l + "," + index_t_r + ") (" + arrayBall[index_t_l].getBallColor() + "," + arrayBall[index_t_r].getBallColor() + ")");
                        if (arrayBall[index_t_r].getBallColor() == arrayBall[index_t_l].getBallColor()) {
                            for (l = numbrow - 1; l > i; l--) {
                                int index_b_l = l * (numbcol) + j;
//                                Log.i(TAG, "t_l ~ t_r ~ b_l: (" + index_t_l + "," + index_t_r + "," + index_b_l + ") (" + arrayBall[index_t_l].getBallColor() + "," + arrayBall[index_t_r].getBallColor() + "," + arrayBall[index_b_l].getBallColor() + ")");
                                if (arrayBall[index_b_l].getBallColor() == arrayBall[index_t_l].getBallColor()) {
                                    int index_b_r = l * (numbcol) + k;
//                                    Log.i(TAG, "t_l ~ t_r ~ b_l ~ b_r: (" + index_t_l + "," + index_t_r + "," + index_b_l + "," + index_b_r + ") (" + arrayBall[index_t_l].getBallColor() + "," + arrayBall[index_t_r].getBallColor() + "," + arrayBall[index_b_l].getBallColor() + "," + arrayBall[index_b_r].getBallColor() + ")");
                                    if (arrayBall[index_b_r].getBallColor() == arrayBall[index_t_l].getBallColor()) {
//                                        Log.i(TAG, ">>>" + index_t_l + "-" + index_t_r + "-" + index_b_l + "-" + index_b_r);

                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
            return false;
        }
//        Log.d(TAG, (System.currentTimeMillis() - t) + "");

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
        for (Ball ball : arrayBall) {
            if (ball.isTouched(x, y)) {
                selectedBall = ball;
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
//
//        t_l_Ball_Temp = null;
//        t_r_Ball_Temp = null;
//        b_l_Ball_Temp = null;
//        b_r_Ball_Temp = null;

//        Log.d(TAG, "start[" + startx + "," + starty + "]");
//        Log.d(TAG, "end[" + endx + "," + endy + "]");

        for (Ball ball : arrayBall) {
            if (ball.isTouched(endx, endy)) {
                startx = startBall.getX() < ball.getX() ? startBall.getX() : ball.getX();
                starty = startBall.getY() < ball.getY() ? startBall.getY() : ball.getY();
                endx = startBall.getX() > ball.getX() ? startBall.getX() : ball.getX();
                endy = startBall.getY() > ball.getY() ? startBall.getY() : ball.getY();

                break;
            }
        }

        for (Ball ball : arrayBall) {
            if (ball.getX() >= startx && ball.getX() <= endx
                    && ball.getY() >= starty && ball.getY() <= endy) {
                ball.setBgStates(typeSelection);
//                Log.d(TAG, "start[" + startx + "," + starty + "]");
//                Log.d(TAG, "end[" + endx + "," + endy + "]");
//                Log.d(TAG, ">>>" + i);

                if (ball.isTouched(startx, starty)) {
                    t_l_Ball = ball;
                    ///for test color
//                    t_l_Ball_Temp.setBallColor(t_l_Ball.getBallColor());
//                    t_l_Ball_Temp.setBitmap(t_l_Ball.getBitmap());
//                    Log.i(TAG, "t_l_Ball>>>" + t_l_Ball.getBallColor());
                } else if (ball.isTouched(endx, starty)) {
                    t_r_Ball = ball;
                    ///for test color
//                    t_r_Ball_Temp.setBallColor(t_r_Ball.getBallColor());
//                    t_r_Ball_Temp.setBitmap(t_r_Ball.getBitmap());
//                    Log.i(TAG, "t_r_Ball>>>" + t_r_Ball.getBallColor());
                } else if (ball.isTouched(startx, endy)) {
                    b_l_Ball = ball;
                    ///for test color
//                    b_l_Ball_Temp.setBallColor(b_l_Ball.getBallColor());
//                    b_l_Ball_Temp.setBitmap(b_l_Ball.getBitmap());
//                    Log.i(TAG, "b_l_Ball>>>" + b_l_Ball.getBallColor());
                } else if (ball.isTouched(endx, endy)) {
                    b_r_Ball = ball;
                    ///for test color
//                    b_r_Ball_Temp.setBallColor(b_r_Ball.getBallColor());
//                    b_r_Ball_Temp.setBitmap(b_r_Ball.getBitmap());
//                    Log.i(TAG, "b_r_Ball>>>" + b_r_Ball.getBallColor());
                }
            } else {
                ball.setBgStates(SELECTION.TYPE1);
            }
        }
    }

    private void clearSquare() {
        typeSelection = SELECTION.TYPE1;
        for (Ball ball : arrayBall) {
            ball.setBgStates(typeSelection);
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
                if (isSquareAvailable(this.t_l_Ball, this.t_r_Ball, this.b_l_Ball, this.b_r_Ball)) {
//                    Log.i(TAG, "AAA>>>" + x + ", " + y);
                    typeSelection = SELECTION.SQUARED_TYPE;
                } else {
                    typeSelection = SELECTION.NOT_SQUARE_TYPE;
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (startBall != null) {
                boolean isLosed = false;
                if (isSquareAvailable(this.t_l_Ball, this.t_r_Ball, this.b_l_Ball, this.b_r_Ball)) {
                    calculatePoint();
                    isLosed = isBoardEmpty(this.arrayBall);
                }
                clearSquare();
                if (isLosed) {
                    //Dừng game
                    //Thua...
                }
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
//        if (tiger != null) {
//            tiger.draw(canvas);
//        }
//        if (ball != null) {
//            ball.draw(canvas);
//        }

        if (this.arrayBall != null && this.arrayBall.length > 0) {
            for (Ball ball : this.arrayBall) {
                ball.draw(canvas);
            }
//            (int i = 0; i < this.arrayBall.length; i++) {
//            }
        }

        if (score != null) {
            score.draw(canvas);
        }

        ///for test color
//        if(t_l_Ball_Temp != null){
//            t_l_Ball_Temp.draw(canvas);
//        }
//        if(t_r_Ball_Temp != null){
//            t_r_Ball_Temp.draw(canvas);
//        }
//        if(b_l_Ball_Temp != null){
//            b_l_Ball_Temp.draw(canvas);
//        }
//        if(b_r_Ball_Temp != null){
//            b_r_Ball_Temp.draw(canvas);
//        }
    }

    public void onPause() {
        this.gameThread.pause();
    }

    public void onResume() {
        this.gameThread.unpause();
    }

    private void initBoard(int numcol, int numrow, String boardGame) {
        int length = numcol * numrow;
        this.arrayBall = new Ball[length];
        int randBall = 0;
        int k = 0;

        for (int i = numrow - 1; i >= 0; i--) {
            for (int j = numcol - 1; j >= 0; j--) {
                randBall = boardGame.equals("") ? CommonFeatures.randomIntValue(0, CommonFeatures.MAX_BALL) : Integer.parseInt(boardGame.charAt(k++) + "");
                bmBall2 = createRandImgBall(randBall);
                arrayBall[i * numcol + j] = new Ball(bmBall2, j * bmBall2.getWidth(), i * bmBall2.getHeight(), this, randBall);
            }
        }

//        if (boardGame.equals("")) {
//            for (int i = 0; i < numrow; i++) {
//                for (int j = 0; j < numcol; j++) {
//                    randBall = CommonFeatures.randomIntValue(0, CommonFeatures.MAX_BALL);
//                    bmBall2 = createRandImgBall(randBall);
//                    arrayBall[i * numcol + j] = new Ball(bmBall2, i * bmBall2.getWidth(), j * bmBall2.getHeight(), this, randBall);
//                }
//            }
//        } else {
//            for (int i = 0; i < numrow; i++) {
//                for (int j = 0; j < numcol; j++) {
//                    randBall = Integer.parseInt(boardGame.charAt(k++) + "");
//                    bmBall2 = createRandImgBall(randBall);
//                    arrayBall[i * numcol + j] = new Ball(bmBall2, i * bmBall2.getWidth(), j * bmBall2.getHeight(), this, randBall);
//                }
//            }
//
//        }
    }


    private void initializeGame2() {
        score = new Score(null, this.getWidth() / 2, this.getHeight() / 2, this);
        this.bmBall2 = Bitmap.createScaledBitmap(bmBallred, (int) (bmBallred.getWidth() * scaleValue), (int) (bmBallred.getHeight() * scaleValue), true);
        ///for test color
//        this.t_l_Ball_Temp = new Ball(bmBall2, 50, this.getHeight() / 2 + 200, this);
//        this.t_r_Ball_Temp = new Ball(bmBall2, 350, this.getHeight() / 2 + 200, this);
//        this.b_l_Ball_Temp = new Ball(bmBall2, 50, this.getHeight() / 2 + 400, this);
//        this.b_r_Ball_Temp = new Ball(bmBall2, 350, this.getHeight() / 2 + 400, this);

        numbcol = 9;
        numbrow = 8;

        initBoard(numbcol, numbrow, "");
//        showBoard(this.arrayBall);
        if (isBoardEmpty(this.arrayBall)) {

        }

        updateScore();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.tiger = new Tiger(bmTiger, 50, this.getHeight() / 2, this);
        this.ball = new Ball(bmBallred, 50, this.getHeight() / 2, this);

        initializeGame();
//        initializeGame2();
        if (this.gameThread == null || this.gameThread.isAlive()) {
            this.gameThread = new GameThread(this, surfaceHolder);
        }
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        this.gameThread.setRunning(false);
        while (retry) {
            try {
                // Luồng cha, cần phải tạm dừng chờ GameThread kết thúc.
                this.gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                retry = false;
                e.printStackTrace();
            }
//            retry = true;
        }
    }

    @Override
    public void update() {
//        if (tiger != null) {
//            tiger.update();
//        }
//        if (ball != null) {
//            ball.update();
//        }
        if (score != null) {
            score.update();
        }
    }


    private Bitmap createRandImgBall(int randBall) {

        switch (randBall) {
            case 0:
                return Bitmap.createScaledBitmap(bmBallred, (int) (bmBallred.getWidth() * scaleValue), (int) (bmBallred.getHeight() * scaleValue), true);
            case 1:
                return Bitmap.createScaledBitmap(bmBallblue, (int) (bmBallblue.getWidth() * scaleValue), (int) (bmBallblue.getHeight() * scaleValue), true);
            case 2:
                return Bitmap.createScaledBitmap(bmBallgray, (int) (bmBallgray.getWidth() * scaleValue), (int) (bmBallgray.getHeight() * scaleValue), true);
            case 3:
                return Bitmap.createScaledBitmap(bmBallgreen, (int) (bmBallgreen.getWidth() * scaleValue), (int) (bmBallgreen.getHeight() * scaleValue), true);
            case 4:
                return Bitmap.createScaledBitmap(bmBallmagenta, (int) (bmBallmagenta.getWidth() * scaleValue), (int) (bmBallmagenta.getHeight() * scaleValue), true);
            case 5:
                return Bitmap.createScaledBitmap(bmBallyellow, (int) (bmBallyellow.getWidth() * scaleValue), (int) (bmBallyellow.getHeight() * scaleValue), true);
            default:
                Log.e(TAG, "Value randBall is too large");
                return Bitmap.createScaledBitmap(bmBallred, (int) (bmBallred.getWidth() * scaleValue), (int) (bmBallred.getHeight() * scaleValue), true);
        }
    }
}
