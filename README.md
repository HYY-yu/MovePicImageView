# MovePicImageView
可以自动移动内部图片的ImageView

效果
--- 
![github](https://github.com/HYY-yu/MovePicImageView/blob/master/moveout.gif)

使用 
---
 在工程的build.gradle下添加：
 ``` 
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```
 
 在项目的build.gradle下:
 ```compile 'com.github.HYY-yu:MovePicImageView:1.0.0'```
 
 示例代码：
 ```
    // 直接在xml中添加属性 app:move_pic_bg="@drawable/bg"
    
    //或者在代码中
     movePicImageView.setBG(R.drawable.flowbloom);
     movePicImageView.invalidate();

 ```
 