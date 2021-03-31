package me.kareluo.imaging;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import me.kareluo.imaging.core.IMGMode;
import me.kareluo.imaging.core.IMGText;
import me.kareluo.imaging.core.file.IMGAssetFileDecoder;
import me.kareluo.imaging.core.file.IMGDecoder;
import me.kareluo.imaging.core.file.IMGFileDecoder;
import me.kareluo.imaging.core.util.IMGUtils;
import me.kareluo.imaging.core.util.TUIUtils;

/**
 * Created by felix on 2017/11/14 下午2:26.
 * modifid by zhaixs on 2020/11/12
 */

public class IMGEditActivity extends IMGEditBaseActivity {

    private static final int MAX_WIDTH = 1024;

    private static final int MAX_HEIGHT = 1024;

    public static final String EXTRA_IMAGE_URI = "IMAGE_URI";

    public static final String EXTRA_IMAGE_SAVE_PATH = "IMAGE_SAVE_PATH";

    @Override
    public void onCreated() {

    }

    @Override
    protected void onStart() {
        TUIUtils.setFullScreen(this);
        super.onStart();
    }

    @Override
    public Bitmap getBitmap() {
        Intent intent = getIntent();
        if (intent == null) {
            return null;
        }
        ImageHolder imageHolder = ImageHolder.getInstance();
        Bitmap bitmap = imageHolder.getBitmap();
        imageHolder.setBitmap(null);
        if (bitmap != null) {
            return bitmap;
        }

        Uri uri = intent.getParcelableExtra(EXTRA_IMAGE_URI);
        if (uri == null) {
            return null;
        }

        IMGDecoder decoder = null;

        String path = uri.getPath();
        String scheme = uri.getScheme();
        if (!TextUtils.isEmpty(path) && scheme != null) {
            switch (scheme) {
                case "asset":
                    decoder = new IMGAssetFileDecoder(this, uri);
                    break;
                case "file":
                    decoder = new IMGFileDecoder(uri);
                    break;
            }
        }

        if (decoder == null) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        options.inJustDecodeBounds = true;

        decoder.decode(options);

        if (options.outWidth > MAX_WIDTH) {
            options.inSampleSize = IMGUtils.inSampleSize(Math.round(1f * options.outWidth / MAX_WIDTH));
        }

        if (options.outHeight > MAX_HEIGHT) {
            options.inSampleSize = Math.max(options.inSampleSize,
                    IMGUtils.inSampleSize(Math.round(1f * options.outHeight / MAX_HEIGHT)));
        }

        options.inJustDecodeBounds = false;

        bitmap = decoder.decode(options);

        return bitmap;
    }

    @Override
    public void onText(IMGText text) {
        mImgView.addStickerText(text);
    }

    @Override
    public void onModeClick(IMGMode mode) {
        IMGMode cm = mImgView.getMode();
        if (cm == mode) {
            mode = IMGMode.NONE;
        }
        mImgView.setMode(mode);
        updateModeUI();

        if (mode == IMGMode.CLIP) {
            setOpDisplay(OP_CLIP);
        }
    }

    @Override
    public void onUndoClick() {
        IMGMode mode = mImgView.getMode();
        if (mode == IMGMode.DOODLE) {
            mImgView.undoDoodle();
        } else if (mode == IMGMode.MOSAIC) {
            mImgView.undoMosaic();
        } else if (mode == IMGMode.ROUND) {
            mImgView.undoRound();
        } else if (mode == IMGMode.BOX) {
            mImgView.undoBox();
        } else if (mode == IMGMode.ARROW) {
            mImgView.undoArrow();
        }
    }

    @Override
    public void onCancelClick() {
        ImageHolder.getInstance().getEditListener().onCancel();
        finish();
    }

    @Override
    public void onDoneClick() {
       /* String path = getIntent().getStringExtra(EXTRA_IMAGE_SAVE_PATH);
        if (!TextUtils.isEmpty(path)) {
            Bitmap bitmap = mImgView.saveBitmap();
            Intent data = new Intent();
            //获取到拍照成功后返回的Bitmap
            saveBitmap(bitmap, path);
            data.putExtra("take_photo", true);
            data.putExtra("path", path);
            setResult(RESULT_OK, data);
            finish();
        }*/
        Bitmap bitmap = mImgView.saveBitmap();
        ImageHolder.getInstance().setEditedBitmap(bitmap);
        finish();
    }

    public String saveBitmap(Bitmap bm, String fileAbsolutePath) {
        File f = new File(fileAbsolutePath);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileAbsolutePath;
    }

    @Override
    public void onCancelClipClick() {
        mImgView.cancelClip();
        setOpDisplay(mImgView.getMode() == IMGMode.CLIP ? OP_CLIP : OP_NORMAL);
    }

    @Override
    public void onDoneClipClick() {
        mImgView.doClip();
        setOpDisplay(mImgView.getMode() == IMGMode.CLIP ? OP_CLIP : OP_NORMAL);

    }

    @Override
    public void onResetClipClick() {
        mImgView.resetClip();
    }

    @Override
    public void onRotateClipClick() {
        mImgView.doRotate();
    }

    @Override
    public void onColorChanged(int checkedColor) {
        mImgView.setPenColor(checkedColor);
    }

    /**
     * 编辑图片需要的bitmap保管类
     */

}
