package kr.mindwing.java8test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.stream.IntStream;

/**
    Java 8 기능을 Android Studio 2.2 에서 쓰기 위해 필요한 설정을 확인해보는 프로젝트입니다.
    callMe 변수에 테스트할 인스턴스를 하나씩 대입해서 실행하면 됩니다.

    > 2017-04-17 by mindwing (Jack & Jill 툴체인 내용 제거)
    > 2016-06-29 by mindwing
    > 2016-04-11 by mindwing


    1. project 레벨의 build.gradle 파일에 android gradle 버전을 2.4.0-alpha6 이상으로 명시해야 함.
       (최소 지원버전이 Android Studio 2.4 Preview 6 이며, 여기에 해당하는 gradle plugin 의
        버전은 2.4.0-alpha6 임. 2.4.0 의 정식 버전이 나오면 -alpha6 꼬리표는 없어짐)

        classpath 'com.android.tools.build:gradle:2.4.0-alpha6'


    2. app 레벨의 build.gradle 파일에 설정을 맞춰줌.

    android {
        compileSdkVersion 25        // SDK 25 버전 이상 사용해야 함
        buildToolsVersion '25.0.2'  // build-tool 25.0.0 버전 이상 사용해야 함

        defaultConfig {
            applicationId "kr.mindwing.java8test"
            minSdkVersion 24    // lambda 식, method reference 만 쓴다면 14 이상이어도 OK (Support Library 26 버전부터 API Level 14 이상이 필요하므로 14 이상을 쓰는 것으로 설정함)
            targetSdkVersion 25 // target 레벨은 25 로 고정해도 됨
            ...
        }
    ...
        compileOptions {            // 소스코드와 타겟의 레벨을 Java 8 으로 설정
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
    }
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvLabel;
    private Button btButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLabel = (TextView) findViewById(R.id.label);
        btButton = (Button) findViewById(R.id.button);

        /*
            [app 레벨 build.gradle 설정할 내용]

            minSdkVersion 14     // 최소 14 이상이면 OK (Support Library 26 버전부터 API Level 14 이상이 필요하므로 14 이상을 쓰는 것으로 설정함)
         */
        Runnable callMe = new LambdaTest();
//        Runnable callMe = new MethodReferenceTest();


        /*
            각 클래스들도 주석처리되어 있으므로 테스트하려면 Runnable 생성에 맞는 클래스의 주석도 풀어야 함.

            [app 레벨 build.gradle 설정할 내용]

            minSdkVersion 24    // 최소 24 이상이어야 OK
            targetSdkVersion 24 // 최소 24 이상이어야 OK
         */
//        Runnable callMe = new InterfaceStaticMethodTest();
//        Runnable callMe = new DefaultMethodTest();
//        Runnable callMe = new FunctionStreamPackageTest();

        callMe.run();
    }

    private class LambdaTest implements Runnable {
        @Override
        public void run() {
            btButton.setText("testLambda()");
            btButton.setOnClickListener(v -> tvLabel.setText("OK!!! \n" + ((Button) v).getText()));
        }
    }

    private class MethodReferenceTest implements Runnable {
        @Override
        public void run() {
            btButton.setText("testMethodReference()");
            btButton.setOnClickListener(this::setTvLabel);
        }

        private void setTvLabel(View v) {
            tvLabel.setText("OK!!! \n" + ((Button) v).getText());
        }
    }

    private class InterfaceStaticMethodTest implements Runnable {
        @Override
        public void run() {
            btButton.setOnClickListener(v -> tvLabel.setText(DefaultMethodInterface.getStaticName()));
        }
    }

    private class DefaultMethodTest implements Runnable, DefaultMethodInterface {
        @Override
        public void run() {
            btButton.setOnClickListener(v -> tvLabel.setText(this.getDefaultName()));
        }
    }

    private class FunctionStreamPackageTest implements Runnable {
        @Override
        public void run() {
            btButton.setOnClickListener(v ->
                    IntStream.rangeClosed(1, 10)
                            .reduce((x, y) -> x + y)
                            .ifPresent(z -> tvLabel.setText("sum(1...10) = " + z)));
        }
    }
}
