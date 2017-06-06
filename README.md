# Thread


기본적인 Thread를 사용해 보자



## Thread 생성


총 4가지 방법이 있는데 4번이 많이쓰인다


<br/>


내부적 쓰레드




```java
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
```



<br/>

외부적 쓰레드

###### Oncreate()


```java
        // 3.2 Thread 실행
        CustomThread thread3 = new CustomThread();
        thread3.start();

        // 4.2 Runnable 실행
        Thread thread4 = new Thread(new CustomRunnable());
        thread4.start();
```



###### 외부클래스




```java
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
```

# 빗방울 생성하기

## RainDrop
각각의 구성요소와 빗방울이 떨어지는 속도랑 범위를 설정해준다





```java
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
```


## runTask
빗방울을 1초마다 1개씩 생성 , 화면에 1초마다 한번씩 갱신



```java
private void runTask(){
        // 빗방울을 1초마다 1개씩 생성
        Rain rain = new Rain();
        rain.start();
        // 화면을 1초마다 한번씩 갱신
        DrawCanvas drawCanvas = new DrawCanvas();
        drawCanvas.start();
    }
```


## DrawCanvas
화면을 1초에 한번씩 그려주는 클래스(onDraw를 호출)



```java
class DrawCanvas extends Thread{
        @Override
        public void run(){
            while(true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.postInvalidate();
            }
        }
    }
```


## Rain
빗방울을 만들어 주는 클래스




```java
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
```



## Stage
여기서 View를 상속받는데 , 그 이유는 화면에 그려질수 있는 객체를 만들기 위해서다 .
버튼도 inflater를 통해서 xml버튼 -> view클래스로 만들어주는데 이건 반대과정 이라고 생각하면 된다.

```java
class Stage extends View {

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
            for (RainDrop drop : rainDrops) {
                canvas.drawCircle(drop.x, drop.y, drop.radius, drop.paint);
            }
        }
        // 생성한 빗방울을 stage에 더해주는 메소드
        public void addRainDrop(RainDrop rainDrop) {
            this.rainDrops.add(rainDrop);
        }
    }
```



## Android Emulator

![ThreadBasic_2.jpg]()
