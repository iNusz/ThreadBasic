package com.seunghoshin.android.threadbasic_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // 1. 버튼을 클릭하면 1부터 100까지 출력하는 함수를 실행
        findViewById(R.id.btn10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                print100T("MainThread");
            }
        });
        // 2. Thread 클래스 에서 1부터 100까지 출력하는 함수를 실행
        new Thread(){
            @Override
            public void run() {
                print100T("SubThread") ;
            }
        }.start();

        // 3. 위의 Thread 클래스의 실행순서를 1번과 바꿔서 실행
    }


    public void print100T(String cal){
        for(int i = 0;  i<100; i++){
            Log.i("100T", cal+"Current Number ====="+i);
            try {
                Thread.sleep(1000); //(1000) = 1초
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
