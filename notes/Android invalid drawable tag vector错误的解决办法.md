在Android中使用Vector来替代传统的图片有很多好处，比如自适应，体积小，不失真等。但是，在Android5.0以下版本使用时会有兼容性问题，在Androi 5.0以下的设备可能会报这样的错误： 
Caused by: org.xmlpull.v1.XmlPullParserException: Binary XML file line #1: invalid drawable tag vector

解决办法 
1.首先在使用时，我们需要添加依赖 
例如：

```
compile 'com.android.support:support-vector-drawable:25.3.1'
```

2.在defaultConfig下面添加声明 
vectorDrawables.useSupportLibrary = true

例如：
```gradle
android {
    compileSdkVersion 25
    buildToolsVersion "26.0.0"
    publishNonDefault true


    defaultConfig {
        minSdkVersion 16
        targetSdkVersion 25
        versionCode 2
        versionName "1.1"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

```
在使用的时候注意用srcCompat代替src

3.在Activity的oncreate中加入如下代码即可

**AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);**

```java
@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }
```

这样一来，我们就可以在5.0以下的设备上使用Vector了。