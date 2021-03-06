package com.sunshine.engine.particle.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sunshine.engine.particle.util.Tool;
import com.sunshine.engine.particle.util.XmlHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SceneBuilder {
  protected static List<Scene> queue = new ArrayList<>();
  protected static boolean isRun = false;

  public static synchronized void load(Scene scene) {
    if (scene != null) {
      queue.add(scene);
      if (!isRun) {
        isRun = true;
        new Thread(new ParseRunnable()).start();
      }
    }
  }

  private static synchronized Scene getScene() {
    if (queue.size() > 0) {
      return queue.remove(0);
    } else {
      isRun = false;
      return null;
    }
  }

  private static class ParseRunnable implements Runnable {
    @Override
    public void run() {
      android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
      Scene scene = getScene();
      while (scene != null) {
        InputStream is = null;
        boolean success = false;
        try {
          boolean readXml = false;
          if (scene.isAsset) {
            Context context = scene.helper.getContext();
            is = context.getAssets().open(scene.configPath);
          } else {
            File f = new File(scene.configPath);
            is = new FileInputStream(f);
          }
          readXml = XmlHandler.parse(is, scene);
          if (readXml) {
            scene.setInterval();
            scene.layout();
            Bitmap bmp = null;
            if (scene.isAsset) {
              Context context = scene.helper.getContext();
              bmp = Tool.getBmpByAssets(context, scene.picPath);
            } else {
              bmp = BitmapFactory.decodeFile(scene.picPath);
            }
            if (bmp != null) {
              scene.setBmpAsync(bmp);
              success = true;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (is != null) {
            try {
              is.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          if (!success) {
            scene.helper.stopAsync(scene);
          }
        }
        scene = getScene();
      }
    }
  }
}
