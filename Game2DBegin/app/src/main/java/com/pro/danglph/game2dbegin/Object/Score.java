package com.pro.danglph.game2dbegin.Object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pro.danglph.game2dbegin.Surface.IGameSurface;

/**
 * Created by danglph on 20/07/2017.
 */

public class Score extends GameMainObject {

    private boolean isVisible = true;
    private Paint p = null;
    private String text = "24350678";
    private int transparent = 100;

    public Score(Bitmap image, int rowCount, int colCount, int x, int y) {
        super(image, rowCount, colCount, x, y);
        p = new Paint();
        p.setColor(Color.GREEN);
        p.setAlpha(transparent);
    }

    public Score(Bitmap image, int x, int y, IGameSurface iGameSurface) {
        super(image, 1, 1, x, y);
        this.igameSurface = iGameSurface;
        p = new Paint();
        p.setTextSize(100.0f);
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.RED);
        p.setAlpha(transparent);
    }

    public void draw(Canvas canvas) {
        if (this.isVisible) {
            canvas.drawText(text, (float) this.getX(), (float) this.getY(), p);
        }
    }

    public void update() {
        transparent -= 10;
        if (transparent < 0) {
            transparent = 200;
        }
        p.setAlpha(transparent);
    }
}
