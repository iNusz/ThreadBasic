package com.seunghoshin.android.threadbasic_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RainActivity extends AppCompatActivity {

    FrameLayout ground;
    Stage stage;

    int deviceWidth, deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rain);

        ground = (FrameLayout) findViewById(R.id.ground);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels; // 스마트폰의 가로사이즈
        deviceHeight = metrics.heightPixels;

        // 커스텀 뷰를 생성하고
        stage = new Stage(getBaseContext());
        // 레이아웃에 담아주면 화면에 커스텀뷰의 내용이 출력됩니다
        ground.addView(stage);

        findViewById(R.id.btnRun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTask();
            }
        });

    }

    private void runTask() {
        // 빗방울을 1초마다 1개씩 생성
        Rain rain = new Rain();
        rain.start();
        // 화면을 1초마다 한번씩 갱신
        DrawCanvas drawCanvas = new DrawCanvas();
        drawCanvas.start();
    }


    //화면을 1초에 한번씩 그려주는 클래스 (onDraw를 호출)
    class DrawCanvas extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.postInvalidate();
            }
        }
    }


    // 빗방울을 만들어 주는 클래스
    class Rain extends Thread {
        @Override
        public void run() {

            // 특정 범위의 숫자를 랜덤하게 생성할 때 사용
            Random random = new Random();

            for (int j = 0; j < 200; j++) {

                // 빗방울 하나를 생성해서 stage에 더해준다
                RainDrop rainDrop = new RainDrop();
                rainDrop.radius = random.nextInt(25) + 5; // 5부터 29까지

                rainDrop.x = random.nextInt(deviceWidth); // 0부터 ~ 디바이스 가로사이즈 사이

                rainDrop.y = 0f;

                rainDrop.speed = random.nextInt(10) + 5; //초당 이동하는 픽셀거리 / 5~14까지이다 /

                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                rainDrop.paint = paint;

                // 생성한 빗방울을 stage에 더해준다
                stage.addRainDrop(rainDrop);

                // 생성한 빗방울을 동작시킨다
                rainDrop.start();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    }


    // 뷰를 상속 받는순간 화면에 그려질수 있는 객체를 만드는것 / 버튼도 인플레이터를 통해서 xml버튼을 인플레이팅을 하면 view클래스로 만들어주는것처럼 ..
    // 코드로 xml위젯을 만드는것
    class Stage extends View {
        //paint 메소드를 Stage가 new될때 생성해준다
        Paint paint;
        List<RainDrop> rainDrops = new ArrayList<>();

        public Stage(Context context) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(3.0f); // 선의 굵
            paint.setAntiAlias(true); // 좀더 부드럽게 보이게 해준다
        }

        // 화면에 로드되는 순간 호출되는 순간
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //                x좌표 y좌표 반지름 컬러,굵기 등   / 단위는 픽셀 단위 이다
            // TOdo 왜 조건문을 걸어줬는지 궁금 . .
            for (RainDrop drop : rainDrops) {
                canvas.drawCircle(drop.x, drop.y, drop.radius, drop.paint);
//            canvas.drawArc(57,57,183,183,0,150,true,paint);
            }
        }

        // 생성한 빗방울을 stage에 더해주는 메소드
        public void addRainDrop(RainDrop rainDrop) {
            this.rainDrops.add(rainDrop);
        }
    }

    class RainDrop extends Thread {
        float radius; //크기
        Paint paint; //색깔
        float y; // y좌표
        int speed; //속도
        float x; // x좌표
        boolean run = true;

        @Override
        public void run() {
            int count = 0;
            while (y < deviceHeight) {
                count++;
                y = count * speed;

                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            run = false;
        }
    }


}
