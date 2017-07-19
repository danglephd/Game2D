package com.pro.danglph.game2dbegin.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pro.danglph.game2dbegin.Surface.IGameSurface;

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
    private boolean isSelected = false;
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
        bitmap = this.image;
        p = new Paint();
        p.setColor(Color.GREEN);
        p.setAlpha(50);
    }

    private void drawBackground(Canvas canvas) {
        if (this.isSelected) {
            p.setColor(Color.GREEN);
            canvas.drawRect((float) this.x, (float) this.y, (float) this.x + (float) this.getWidth(), (float) this.y + (float) this.getHeight(), p);
        }
    }

    private void drawValue(Canvas canvas) {
        switch (ballColor){
            case 0:
                p.setColor(Color.BLUE);
                break;
            case 1:
                p.setColor(Color.GREEN);
                break;
            case 2:
                p.setColor(Color.GRAY);
                break;
            case 3:
                p.setColor(Color.RED);
                break;
            case 4:
                p.setColor(Color.YELLOW);
                break;
            default:
                p.setColor(Color.GREEN);
                break;
        }

        float x = (float) this.x + 10;
        float y = (float) this.y + 10;
        canvas.drawRect(x, y, x + (float) this.getWidth() - 20, y + (float) this.getHeight() - 20, p);
    }

    public void draw(Canvas canvas) {
        drawBackground(canvas);
        if (this.isVisible) {
            canvas.drawBitmap(bitmap, this.x, this.y, null);
        }
        drawValue(canvas);
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
        bitmap = Bitmap.createScaledBitmap(image, (int) (image.getWidth() * scaleValue), (int) (image.getHeight() * scaleValue), true);
    }

    public void setScaleValue(float scaleValue) {
        this.scaleValue = scaleValue;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getBallColor() {
        return ballColor;
    }

    public void setBallColor(int ballColor) {
        this.ballColor = ballColor;
    }
}
