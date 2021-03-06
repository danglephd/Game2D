package com.pro.danglph.game2dbegin.Object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.pro.danglph.game2dbegin.Surface.IGameSurface;

/**
 * Created by danglph on 12/07/2017.
 */

public class Tiger extends GameMainObject {

    private int rowIndex = 0;
    private int colIndex = -1;
//    private IGameSurface igameSurface;


    public Tiger(Bitmap image, int x, int y, IGameSurface gameSurface) {
        super(image, 6, 3, x, y);
        this.igameSurface = gameSurface;
    }


    public void update() {
        this.colIndex++;

        if (this.colIndex >= this.colCount) {
            this.colIndex = 0;
            this.rowIndex++;

            if (this.rowIndex >= this.rowCount) {
                rowIndex = 0;
                colIndex = 0;
            }
        }
    }

    public void draw(Canvas canvas) {
        Bitmap bitmap = this.createSubImageAt(rowIndex, colIndex);
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}
