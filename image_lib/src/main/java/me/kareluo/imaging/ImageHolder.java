package me.kareluo.imaging;

import android.graphics.Bitmap;

import java.util.concurrent.locks.ReentrantLock;

 class ImageHolder {
    private static ImageHolder instance;
    private Bitmap bitmap;
    private Bitmap editedBitmap;
    private TRSPictureEditor.EditListener editListener;
    private final static ReentrantLock lock = new ReentrantLock();

    private ImageHolder() {
    }

    public static ImageHolder getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ImageHolder();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setEditedBitmap(Bitmap editedBitmap) {
        this.editedBitmap = editedBitmap;
        if (editListener != null) {
            editListener.onComplete(editedBitmap);
        }
    }

    public TRSPictureEditor.EditListener getEditListener() {
        return editListener;
    }

    public void setEditListener(TRSPictureEditor.EditListener editListener) {
        this.editListener = editListener;
    }

    public void reset() {
        this.bitmap = null;
        this.editedBitmap = null;
        this.editListener=null;
    }
}