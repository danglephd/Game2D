package com.pro.danglph.game2dbegin.Surface;

import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by danglph on 12/07/2017.
 */

public interface IGameSurface{

    abstract void update();
    abstract void draw(Canvas canvas);

    int getWidth();

    int getHeight();
}
