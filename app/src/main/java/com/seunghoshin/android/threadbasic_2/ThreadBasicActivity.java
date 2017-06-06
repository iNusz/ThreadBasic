package com.seunghoshin.android.threadbasic_2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ThreadBasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_basic);

        // 1.1 Thread 생성 (내부적 쓰레드)
        Thread thread = new Thread(){
            @Override
            public void run() {
                Log.i("Thread test","Hello Thread!");
            }
        };

        // 1.2 Thread 실행
        thread.start(); // run() 함수를 실행


        // 2 1. Thread 생성 (인터페이스 구현이 되어서 소스코드를 일부분만 구현해준다) (내부적 쓰레드)
        Runnable thread2 = new Runnable() {
            @Override
            public void run() {
                Log.i("Thread test","Hello Runnable!");
            }
        };
        // 2.2 Thread 실행
        new Thread(thread2).start();



        // 3.2 Thread 실행
        CustomThread thread3 = new CustomThread();
        thread3.start();

        // 4.2 Runnable 실행
        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();

    }



}



// 3.1 Thread 생성 (상속받는것보다 4방법의 implement로  interface로 쓰는게 더 낫다) (외부적 쓰레드)
class CustomThread extends Thread{
    @Override
    public void run() {
        Log.i("Thread test","Hello Custom Thread!");
    }
}

// 4.1 Runnable 구현 ( interface로 받아야지 다른 상속을 받을 수 있기 때문이다) (외부적 쓰레드) (제일 많이쓰인다)
class CustomRunnable implements Runnable{

    @Override
    public void run() {
        Log.i("Thread test","Hello Custom Runnable!");
    }
}