package kr.mindwing.java8test;

/**
    [app 레벨 build.gradle 설정내용 (25 이상으로 설정)]

    compileSdkVersion 25
    buildToolsVersion '25.0.0'
    minSdkVersion 14
    targetSdkVersion 25

    min 을 24 미만 버전으로 설정하면 빌드시 에러나니 아래 메서드 2개를 전부 주석처리해야 함.
*/
public interface DefaultMethodInterface {
    default String getDefaultName() {
        return "It's me,\nInterface Default Method !!!";
    }

    static String getStaticName() {
        return "It's me,\nInterface Static Method !!!";
    }
}
