package com.sunshine.engine.particle.logic;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.util.Config;
import com.sunshine.engine.particle.util.Tool;

import static com.sunshine.engine.particle.util.Config.RENDER_INTERVAL;

public class ViewHelper {
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

  public void play(View v, String folderPath, boolean isAsset) {
    if (scene == null) {
      view = v;
      scene = new Scene(this, folderPath, isAsset);
      scene.resize(viewArea.w, viewArea.h, viewArea.l, viewArea.t);
      SceneBuilder.load(scene);
    }
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
    if (scene != null) {
      scene.destroy();
      scene = null;
    }
    if (view != null) {
      view.removeCallbacks(rnRequestRender);
      view = null;
    }
  }

  public void stopAsync(final Scene sc) {
    Tool.post(
        new Runnable() {
          @Override
          public void run() {
            if (sc != null && sc.equals(scene)) {
              stop();
            }
          }
        });
  }

  public void drawSelf(View view, Canvas can) {
    if (scene != null) {
      drawTime = Tool.getTime();
      boolean needRender = scene.draw(can, drawTime);
      if (needRender) {
        int nextRenderTime = RENDER_INTERVAL - (int) (Tool.getTime() - drawTime);
        view.removeCallbacks(rnRequestRender);
        if (nextRenderTime <= 0) {
          view.invalidate();
        } else {
          view.postDelayed(rnRequestRender, nextRenderTime);
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
