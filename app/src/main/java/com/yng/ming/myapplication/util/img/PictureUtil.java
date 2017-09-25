package com.yng.ming.myapplication.util.img;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by LengM on 2017/9/25 0025
 * 解决三星机型中，系统拍照后自动旋转问题
 * 使用：
 * 1.调用readPictureDegree方法查看exif中是否进行过旋转
 * 2.如果已经旋转，则调用toturn方法将已旋转的角度恢复
 */

public class PictureUtil {

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     *
     * @param img    图片
     * @param degree 旋转的角度
     * @return bitmap
     */
    public static Bitmap toturn(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(+degree);
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

}
