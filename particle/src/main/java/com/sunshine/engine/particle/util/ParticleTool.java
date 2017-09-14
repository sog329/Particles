package com.sunshine.engine.particle.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.InputStream;

import static com.sunshine.engine.particle.util.ParticleConfig.PIC_NAME;
import static com.sunshine.engine.particle.util.ParticleConfig.SCRIPT_NAME;
import static com.sunshine.engine.particle.util.ParticleConfig.SEPARATOR;
import static com.sunshine.engine.particle.util.ParticleConfig.ZERO_FLOAT;

public class ParticleTool {

  public static String getScriptPath(String folderPath) {
    String path = null;
    if (folderPath != null) {
      StringBuilder sb = new StringBuilder(folderPath);
      if (!folderPath.endsWith(File.separator)) {
        sb.append(File.separator);
      }
      path = sb.append(SCRIPT_NAME).toString();
    }
    return path;
  }

  public static String getPicPath(String folderPath) {
    String path = null;
    if (folderPath != null) {
      StringBuilder sb = new StringBuilder(folderPath);
      if (!folderPath.endsWith(File.separator)) {
        sb.append(File.separator);
      }
      path = sb.append(PIC_NAME).toString();
    }
    return path;
  }

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

  public static float getAppearValue(float from, float to, float percent) {
    return from + (to - from) * percent;
  }

  public static float getDisappearValue(float from, float to, float percent) {
    return from + (to - from) * percent;
  }

  public static int getAppearValue(int from, int to, float percent) {
    return from + (int) ((to - from) * percent);
  }

  public static int getDisappearValue(int from, int to, float percent) {
    return from + (int) ((to - from) * percent);
  }

  public static boolean isInRange(float f, float min, float max) {
    if (ParticleTool.equalsFloat(f, min) || ParticleTool.equalsFloat(f, max)) {
      return true;
    } else {
      return f > min && f < max;
    }
  }
}
