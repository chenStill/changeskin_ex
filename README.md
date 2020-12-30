# changeskin_ex

这是基于鸿洋大神的换肤框架的扩展库和使用说明  
在原有的基础上添加了Shape 资源文件的换肤  
添加了   
 1.图片Vector资源换肤  
 2.选择器Selector换肤  
 3.字体颜色选择器Selector换肤  
 4.资源Shape文件里的换肤（只换内容，只换框，换框和字体）  
 5.资源shapeGradient渐变的换肤（只换渐变，只换框，换框和字体的颜色）  

===
## Step.1 引用jar包
 ### 在项目的根目录下的build.gradle 添加jitpack
  ```  
 repositories {
        maven { url "https://jitpack.io" }
        google()
        jcenter()
    }
  ```  
 在项目的app目录下的build.gradle 添加jitpack   
 ```  
 dependencies {
   implementation 'com.github.hkdoke:changeskin_ex:0.1'
} 
```  

## Step.2 初始化
### 在Application中onCreate的方法初始化控件
  SkinManager.getInstance().init(this);
### 在Activity中的onCreate方法注册
 SkinManager.getInstance().register(this);
### 在Activity中的onDestroy方法销毁
  SkinManager.getInstance().unregister(this);  
   注：也可以写在BaseActivity中统一注册和引用
   
## Step.3 使用
 ### 在需要换肤的控件的xml中
  android:tag="skin:color_theme:textColor"

 ```  
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        android:onClick="chagex"
        android:textColor="@color/color_theme"
        android:tag="skin:color_theme:textColor"
      />
 ```  
在colors中需要添加换肤后的颜色
  <color name="color_theme">#03DAC5</color>
  <color name="color_theme_red">#DA032E</color>      

## 最后一键换肤
 ```  
   public void chagex(View view) {
        if (isChange) {
            isChange = false;
            SkinManager.getInstance().changeSkin("red");
        } else {
            isChange = true;
            SkinManager.getInstance().changeSkin("");

        }
    }  
 ```  
 # 灰常感谢鸿洋大神！！！ 
 希望大家有能力的给大神的玩安卓赞助下哈，三毛两毛都是爱，十块五块都可爱。  
 [玩安卓]https://www.wanandroid.com/index  
 鸿洋大神项目原地址  
 [Changeskin]https://github.com/hongyangAndroid/ChangeSkin
