package com.sunshine.engine.particle.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;

import java.io.InputStream;

import static com.sunshine.engine.particle.util.Config.SEPARATOR;
import static com.sunshine.engine.particle.util.Config.ZERO_FLOAT;

public class Tool {
  public static boolean equalsFloat(float f1, float f2) {
    return Math.abs(f1 - f2) < .00001f;
  }

  public static boolean equalsZero(float f) {
    return equalsFloat(f, ZERO_FLOAT);
  }

  public static Bitmap getBmpByAssets(Context ctx, String str) {
    Bitmap bmp = null;
    if (ctx != null) {
      AssetManager am = ctx.getAssets();
      bmp = getBmpByAm(am, str);
    }
    return bmp;
  }

  private static Bitmap getBmpByAm(AssetManager am, String str) {
    Bitmap bmp = null;
    if (am != null) {
      InputStream is = null;
      try {
        is = am.open(str);
        bmp = BitmapFactory.decodeStream(is);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        try {
          if (is != null) {
            is.close();
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
    return bmp;
  }

  public static String[] getAry(String str) {
    if (str == null) {
      return null;
    } else {
      return str.split(SEPARATOR);
    }
  }

  public static boolean isInRange(float f, float min, float max) {
    if (Tool.equalsFloat(f, min) || Tool.equalsFloat(f, max)) {
      return true;
    } else {
      return f > min && f < max;
    }
  }

  public static long getTime() {
    return SystemClock.elapsedRealtime();
  }
}
