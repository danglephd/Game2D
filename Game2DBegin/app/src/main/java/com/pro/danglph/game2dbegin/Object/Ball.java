package com.pro.danglph.game2dbegin.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pro.danglph.game2dbegin.Surface.IGameSurface;
import com.pro.danglph.game2dbegin.Utility.CommonFeatures;
import com.pro.danglph.game2dbegin.Utility.SELECTION;

/**
 * Created by danglph on 10/07/2017.
 */

public class Ball extends GameMainObject {

    private int rowIndex = 0;
    private int colIndex = 0;
    private float scaleValue = 1f;
    private boolean isVisible = true;

    private int ballColor = 0;

    private Bitmap bitmap = null;
    private SELECTION bgStates = SELECTION.TYPE1;
    private Paint p = null;

    public Ball(Bitmap image, int x, int y, IGameSurface iGameSurface, int ballColor) {
        super(image, 1, 1, x, y);
        this.igameSurface = iGameSurface;
        this.ballColor = ballColor;
        bitmap = image;
        p = new Paint();
        p.setColor(Color.GREEN);
        p.setAlpha(50);
    }

    public Ball(Bitmap image, int x, int y, IGameSurface iGameSurface) {
        super(image, 1, 1, x, y);
        this.igameSurface = iGameSurface;
        this.ballColor = CommonFeatures.randomIntValue(0, CommonFeatures.MAX_BALL);
        bitmap = image;
        p = new Paint();
        p.setColor(Color.GREEN);
        p.setAlpha(50);
    }

    private void drawBackground(Canvas canvas) {
        switch(this.bgStates)
        {
            case TYPE1:
                break;
            case NOT_SQUARE_TYPE:
                p.setColor(Color.GRAY);
                canvas.drawRect((float) this.x, (float) this.y, (float) this.x + (float) this.getWidth(), (float) this.y + (float) this.getHeight(), p);
                break;
            case SQUARED_TYPE:
                p.setColor(Color.GREEN);
                canvas.drawRect((float) this.x, (float) this.y, (float) this.x + (float) this.getWidth(), (float) this.y + (float) this.getHeight(), p);
                break;
            default:
                p.setColor(Color.GRAY);
                canvas.drawRect((float) this.x, (float) this.y, (float) this.x + (float) this.getWidth(), (float) this.y + (float) this.getHeight(), p);
                break;
        }
//        p.setColor(Color.GREEN);


//        if (this.bgStates) {
//        }
    }

    private void drawValue(Canvas canvas) {
        switch (ballColor){
            case 0:
                p.setColor(Color.RED);
                break;
            case 1:
                p.setColor(Color.BLUE);
                break;
            case 2:
                p.setColor(Color.GRAY);
                break;
            case 3:
                p.setColor(Color.GREEN);
                break;
            case 4:
                p.setColor(Color.MAGENTA);
                break;
            case 5:
                p.setColor(Color.YELLOW);
                break;
            default:
                p.setColor(Color.WHITE);
                break;
        }

        float x = (float) this.x + 15;
        float y = (float) this.y + 15;
        canvas.drawRect(x, y, x + (float) this.getWidth() - 30, y + (float) this.getHeight() - 30, p);
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
        if (this.isVisible) {
            canvas.drawBitmap(bitmap, this.x, this.y, null);
        }
//        drawValue(canvas);
    }

    public void decreaseScaleVal() {
        if (scaleValue > 0) {
            scaleValue -= 0.1;
        }
    }

    public boolean isTouched(int x, int y) {
        return (this.x <= x && x <= this.x + width
                && this.y <= y && y <= this.y + this.height);
    }

    public void update() {
        ballColor = CommonFeatures.randomIntValue(0, 5);

        bitmap = Bitmap.createScaledBitmap(image, (int) (image.getWidth() * scaleValue), (int) (image.getHeight() * scaleValue), true);
    }

    public void setScaleValue(float scaleValue) {
        this.scaleValue = scaleValue;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setBgStates(SELECTION bgStates) {
        this.bgStates = bgStates;
    }

    public int getBallColor() {
        return ballColor;
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
    }

    public boolean isSquared() {
        return bgStates == SELECTION.SQUARED_TYPE;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
