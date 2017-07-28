package com.pxr.tutorial.menu.Object;

import android.widget.ProgressBar;

import com.pxr.tutorial.menu.Utility.CommonFeatures;

/**
 * Created by danglph on 28/07/2017.
 */

public class GameTimer {

    private ProgressBar pgbar = null;
    private long lastupdateNanoTime;

    public void initTime(ProgressBar pgbar) {
        this.pgbar = pgbar;
        long now = System.nanoTime();
        lastupdateNanoTime = now; //26300461937940
        if (this.pgbar != null) {
            this.pgbar.setMax((int) (now / 1000000 + CommonFeatures.MAX_TIME));//GameTimer in milliseconds
            this.pgbar.setProgress(this.pgbar.getMax() / 2);
        }
    }

    public void update(int addingTime) {
        long now = System.nanoTime();

        // Đổi nano giây ra mili giây (1 nanosecond = 1 / 1000000 millisecond).
        int deltaTime = (int) ((now - lastupdateNanoTime) / 1000000 - addingTime * 1000);
        lastupdateNanoTime = now;
        if (this.pgbar != null) {
            this.pgbar.setProgress(this.pgbar.getProgress() - deltaTime);
        }
    }

}
