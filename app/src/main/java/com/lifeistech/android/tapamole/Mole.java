package com.lifeistech.android.tapamole;

import android.widget.ImageView;

/**
 * Created by hnk_1031 on 16/08/03.
 */
public class Mole {
    //モグラの状態 0:潜っている 1:出てきている 2:叩かれている
    int state;
    //モグラのImageView
    ImageView moleImage;
    //Handlerスレッド間の処理を投げる役割
    android.os.Handler h;
    //Handlerで投げる処理の中身をk書くためのクラス
    Runnable hide;

    public Mole(ImageView imageView) {
        state = 0;
        moleImage = imageView;
        moleImage.setImageResource(R.drawable.mole1);

        h = new android.os.Handler();
        hide = new Runnable() {
            @Override
            public void run() {
                state = 0;
                moleImage.setImageResource(R.drawable.mole1);
            }
        };
    }

    public void start() {
        //モグラが潜っている状態
        if (state == 0) {
            state = 1;
            moleImage.setImageResource(R.drawable.mole2);

            h.postDelayed(hide,1000);
        }
    }

    public int tapMole() {
        //モグラが出ている状態
        if (state == 1) {
            state = 2;
            moleImage.setImageResource(R.drawable.mole3);

            //start時のpostを消去
            h.removeCallbacks(hide);
            h.postDelayed(hide,1000);
            //スコア(1点)を繰り返す
            return 1;
        }
        //スコア(0点)を繰り返す
        return 0;
    }
}
