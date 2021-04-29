# WeiXinPictureTool-master

# 仿微信图片编辑
# 序言
最近写一个图片编辑器，仿造的微信的图片编辑，以下加粗的功能是微信没有的。这个项目可以说是目前编辑功能最多的编辑器。而且功能都支持定制。

 - **绘制方框**
 - **绘制圆形**
 - 绘制文字
 - **绘制箭头**
 - 涂鸦
 - 绘制马赛克
 - 图片剪裁
 

![](https://img-blog.csdnimg.cn/2021043000255941.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyNzA2NTE1,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/202104300025596.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIyNzA2NTE1,size_16,color_FFFFFF,t_70)

# 使用
1.可以定制需要的功能，不需要的功能会自动隐藏。以下是定义的在TRSPictureEditor的常量。

```java
    public static final int BOX_ENABLE = 0x00000001;//方形选择框
    public static final int CIRCLE_ENABLE = 0x00000002;//圆形选择框
    public static final int TXT_ENABLE = 0x00000004;//文字
    public static final int PAINT_ENABLE = 0x00000010;//画笔
    public static final int ARROW_ENABLE = 0x00000020;//箭头
    public static final int MOSAIC_ENABLE = 0x00000040;//马赛克
    public static final int CLIP_ENABLE = 0x00000100;//裁剪

```
只需要把需要的功能用与运算累加起来就行了，具体可以看代码。

2.图片编辑的时候，传入bitmap，返回也是bitmap。 图片编辑器不涉及图片保存的功能，避免功能复杂化。
```java
   					TRSPictureEditor.setStyle(buildStyle());
                    TRSPictureEditor.edit(this, getBitmap(uri), new TRSPictureEditor.EditAdapter() {
                        @Override
                        public void onComplete(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
```

# 源码

> [WeiXinPictureTool-master](https://github.com/zhuguohui/WeiXinPictureTool-master)


# 感谢
我也是站在巨人的肩膀上前进的。感谢这个项目。我在他的基础上增加了上面粗体显示的功能。优化了马赛克显示的效果。在图片放大的情况下，画笔粗细也是等比例缩小的（和微信一样）。

> [Android仿微信图片编辑处理：文字，马赛克，裁剪，涂鸦，旋转图片等](https://blog.csdn.net/zhangphil/article/details/87860431)

