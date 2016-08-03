package com.lifeistech.android.tapamole;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //スコア表示用のTextView
    TextView scoreText;
    //タイマー表示用のTextView
    TextView timeText;

    int[] imageResources = {
            //imageViewのリソースID配列
            R.id.imageView,R.id.imageView2,R.id.imageView3,
            R.id.imageView4,R.id.imageView5,R.id.imageView6,
            R.id.imageView7,R.id.imageView8,R.id.imageView9,
            R.id.imageView10,R.id.imageView11,R.id.imageView12
    };

    //モグラの配列
    Mole[] moles;

    //時間の変数
    int time;
    //スコアの配列
    int score;

    //タイマー
    Timer timer;
    //タイマーで処理する内容
    TimerTask timerTask;
    //Handler
    Handler h;
    //ランダムな数字を発生させる
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //レイアウトのTextViewと関連付け
        scoreText = (TextView)findViewById(R.id.scoreText);
        timeText = (TextView)findViewById(R.id.timeText);

        //モグラの数だけモグラの配列を作る
        moles = new Mole[12];
        for (int i =0;i < 12;i++) {
            //レイアウトのImageViewを一つずつ取り出し
            ImageView imageView = (ImageView)findViewById(imageResources[i]);
            //imageViewを使ってi番目のモグラのインスタンスを生成
            moles[i]=new Mole(imageView);
        }

        //Handlerのインスタンスを生成
        h = new Handler();
    }

    public void start(View v) {
        //タイマーの初期化
        time = 60;
        //スコアの初期化
        score = 0;
        timeText.setText(String.valueOf(time));
        scoreText.setText(String.valueOf(score));

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //TODO一定時間ごとに行う処理を書く
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        //0<=r<12のランダムな数字を生成
                        int r =random.nextInt(12);
                        //r番目のモグラが飛び出す
                        moles[r].start();

                        //時間を1減らす
                        time = time -1;
                        timeText.setText(String.valueOf(time));
                        //時間が0以下になったら
                        if (time <= 0) {
                            //タイマーを終了させる
                            timer.cancel();
                        }
                    }
                });

            }
        };
        timer.schedule(timerTask,0,1000);
    }

    public void tapMole(View v) {
        //叩いたImageViewのタグを取得
        String tag_str = (String) v.getTag();
        //tag_strをint型(整数)に変換
        int tag_int = Integer.valueOf(tag_str);

        //対応したモグラを叩く
        score +=moles[tag_int].tapMole();

        scoreText.setText(String.valueOf(score));
    }
}
