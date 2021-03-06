package com.sunshine.engine.particle.logic;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.util.Config;
import com.sunshine.engine.particle.util.Tool;

import static com.sunshine.engine.particle.util.Config.RENDER_INTERVAL;

public class ViewHelper {
  protected static final Handler handler = new Handler(Looper.getMainLooper());
  protected Scene scene = null;
  private Area viewArea = new Area();
  private long drawTime = Config.NONE;
  private View view = null;
  protected Runnable rnRequestRender =
      new Runnable() {
        @Override
        public void run() {
          if (view != null) {
            view.invalidate();
          }
        }
      };

  public boolean play(View v, String configPath, String picPath, boolean isAsset) {
    boolean play = false;
    if (scene == null) {
      view = v;
      scene = new Scene(this, configPath, picPath, isAsset);
      scene.resize(viewArea.w, viewArea.h, viewArea.l, viewArea.t);
      SceneBuilder.load(scene);
      play = true;
    }
    return play;
  }

  public void resize(int left, int top, int r, int b) {
    viewArea.l = left;
    viewArea.t = top;
    viewArea.w = r - viewArea.l;
    viewArea.h = b - viewArea.t;
    if (scene != null) {
      scene.resize(viewArea.w, viewArea.h, viewArea.l, viewArea.t);
    }
  }

  public void stop() {
    handler.removeCallbacks(rnRequestRender);
    if (scene != null) {
      scene.destroy();
      scene = null;
    }
    view = null;
  }

  public void stopAsync(final Scene sc) {
    handler.post(
        new Runnable() {
          @Override
          public void run() {
            if (sc != null && sc.equals(scene)) {
              stop();
            }
          }
        });
  }

  public void drawSelf(Canvas can) {
    if (scene != null) {
      drawTime = Tool.getTime();
      boolean needRender = scene.draw(can, drawTime);
      if (needRender) {
        int nextRenderTime = RENDER_INTERVAL - (int) (Tool.getTime() - drawTime);
        handler.removeCallbacks(rnRequestRender);
        if (nextRenderTime <= 0) {
          rnRequestRender.run();
        } else {
          handler.postDelayed(rnRequestRender, nextRenderTime);
        }
      }
    }
  }

  protected Context getContext() {
    Context context = null;
    if (view != null) {
      context = view.getContext();
    }
    return context;
  }
}
