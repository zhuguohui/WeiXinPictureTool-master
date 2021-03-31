package me.kareluo.imaging;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;


/**
 * 入口类
 * 直接使用edit方法传入需要编辑的bitmap
 * 最后在editListener中的onComplete方法获取编辑后的图片
 * 框架不再保存图片到设备上。
 */
public class TRSPictureEditor {

    public static void edit(Context context, Bitmap bitmap, EditListener editListener) {
        if (editListener == null) {
            return;
        }
        if (bitmap == null) {
            editListener.onError(new RuntimeException("图片为空"));
            return;
        }
        if (context == null) {
            editListener.onError(new RuntimeException("context为空"));
            return;
        }
        ImageHolder.getInstance().reset();
        ImageHolder.getInstance().setEditListener(editListener);
        ImageHolder.getInstance().setBitmap(bitmap);
        context.startActivity(new Intent(context, IMGEditActivity.class));
    }

    public interface EditListener {
        void onCancel();

        void onComplete(Bitmap bitmap);

        void onError(Throwable throwable);
        //失败
    }

    /**
     * 一个空的实现，方便调用者只实现部分方法
     */
    public static class EditAdapter implements EditListener {

        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Bitmap bitmap) {

        }


        @Override
        public void onError(Throwable throwable) {

        }
    }
}
