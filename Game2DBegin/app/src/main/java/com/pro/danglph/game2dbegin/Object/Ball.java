package com.pro.danglph.game2dbegin.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.pro.danglph.game2dbegin.Surface.IGameSurface;

/**
 * Created by danglph on 10/07/2017.
 */

public class Ball extends GameMainObject {

    private int rowIndex = 0;
    private int colIndex = 0;
    private float scaleValue = 1f;

    private Bitmap bitmap = null;
    public Ball(Bitmap image, int x, int y, IGameSurface iGameSurface) {
        super(image, 1, 1, x, y);
        this.igameSurface = iGameSurface;
        bitmap = this.image;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }

    public void decreaseScaleVal(){
        if(scaleValue > 0) {
            scaleValue -= 0.1;
        }
    }

    public boolean isTouched(int x, int y) {
        return (this.x < x && x < this.x + width
                && this.y < y && y < this.y + this.height);
    }

    public void update() {
        bitmap = Bitmap.createScaledBitmap(image, (int)(image.getWidth() * scaleValue), (int)(image.getHeight() * scaleValue), true);
    }

    public void setScaleValue(float scaleValue) {
        this.scaleValue = scaleValue;
    }
}
