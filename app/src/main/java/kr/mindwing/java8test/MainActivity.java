package kr.mindwing.java8test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
    Java 8 기능을 Android Studio 2.0 & 2.1 에서 쓰기 위해 필요한 설정을 확인해보는 프로젝트입니다.
    callMe 변수에 테스트할 인스턴스를 하나씩 대입해서 실행하면 됩니다.

    2016-04-11 by mindwing


    1. project 레벨의 build.gradle 파일에 android gradle 버전을 2.1.0 으로 명시해야 함.

        classpath 'com.android.tools.build:gradle:2.1.0-alpha5'


    2. app 레벨의 build.gradle 파일에 jackOptions 설정을 켜야 함.

    android {
        defaultConfig {
            applicationId "kr.mindwing.java8test"
            ...
            jackOptions {
                enabled true // Jack & Jill 활성화
            }
        }
    ...
        compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
 */
public class MainActivity extends AppCompatActivity {
    TextView tvLabel;
    Button btButton;
    Runnable callMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLabel = (TextView) findViewById(R.id.label);
        btButton = (Button) findViewById(R.id.button);

        // 하나씩 comment-out 하고, 해당 클래스의 주석대로 설정을 해주면 테스트가능함.
//        callMe = new LambdaTest();
//        callMe = new MethodReferenceTest();
//        callMe = new DefaultMethodTest();
        callMe = new FunctionPackageTest();

        callMe.run();
    }

    /*
        [app 레벨 build.gradle 설정내용]

        compileSdkVersion 23
        buildToolsVersion '24.0.0 rc2' // Jack & Jill
        minSdkVersion 9
        targetSdkVersion 9
     */
    class LambdaTest implements Runnable {
        @Override
        public void run() {
            btButton.setText("testLambda()");
            btButton.setOnClickListener(v -> tvLabel.setText(((Button) v).getText()));
        }
    }

    /*
        [app 레벨 build.gradle 설정내용]

        compileSdkVersion 23
        buildToolsVersion '24.0.0 rc2' // Jack & Jill
        minSdkVersion 9
        targetSdkVersion 9
     */
    class MethodReferenceTest implements Runnable {
        @Override
        public void run() {
            btButton.setText("testMethodReference()");
            btButton.setOnClickListener(this::setTvLabel);
        }

        private void setTvLabel(View v) {
            tvLabel.setText(((Button) v).getText());
        }
    }

    /*
        [app 레벨 build.gradle 설정내용]

        compileSdkVersion 23
        buildToolsVersion '24.0.0 rc2' // Jack & Jill
        minSdkVersion 21
        targetSdkVersion 21
     */
    class DefaultMethodTest implements Runnable {
        @Override
        public void run() {
            btButton.setOnClickListener(v -> tvLabel.setText(DefaultMethodInterface.getName()));
        }
    }

    /*
        [app 레벨 build.gradle 설정내용]

        compileSdkVersion 'android-N' // N 테스트버전을 설치한 Nexus 5X 에서 테스트해봄.
        buildToolsVersion '24.0.0 rc2' // Jack & Jill
        minSdkVersion 'N' // compileSdk 가 'android-N' 이면 minSdk 는 'N' 으로 고정됨.
        targetSdkVersion 'N' // minSdk 보다 더 작은 값일 수 없으므로 자동으로 'N' 이 됨.
     */
    class FunctionPackageTest implements Runnable {
        @Override
        public void run() {
            java.util.function.Consumer<View> cons = v -> tvLabel.setText(((Button) v).getText());

            btButton.setOnClickListener(v -> tvLabel.setText(cons.getClass().getName()));
        }
    }
}
