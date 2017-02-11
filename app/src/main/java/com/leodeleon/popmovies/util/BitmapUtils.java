package com.leodeleon.popmovies.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

/**
 * Created by leodeleon on 11/02/2017.
 */

public class BitmapUtils {
  public static Bitmap applyOverlay(Context context, Bitmap sourceImage, int overlayDrawableResourceId){
    Bitmap bitmap = null;
    try{
      int width = sourceImage.getWidth();
      int height = sourceImage.getHeight();
      Resources r = context.getResources();

      Drawable imageAsDrawable =  new BitmapDrawable(r, sourceImage);
      Drawable[] layers = new Drawable[2];

      layers[0] = imageAsDrawable;
      layers[1] = new BitmapDrawable(r, BitmapUtils.decodeSampledBitmapFromResource(r, overlayDrawableResourceId, width, height));
      LayerDrawable layerDrawable = new LayerDrawable(layers);
      bitmap = BitmapUtils.drawableToBitmap(layerDrawable);
    }catch (Exception ex){}
    return bitmap;
  }

  public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                       int reqWidth, int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
  }

  public static int calculateInSampleSize(
    BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) >= reqHeight
        && (halfWidth / inSampleSize) >= reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }

  public static Bitmap drawableToBitmap (Drawable drawable) {
    Bitmap bitmap = null;

    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if(bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }

    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
      bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else {
      bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }
}
