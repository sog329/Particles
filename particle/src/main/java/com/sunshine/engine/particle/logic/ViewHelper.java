package com.sunshine.engine.particle.logic;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.ParticleTool;

import java.lang.ref.WeakReference;

import static com.sunshine.engine.particle.util.ParticleConfig.RENDER_INTERVAL;

public class ViewHelper {
  protected Scene scene = null;
  private Area viewArea = new Area();
  private long drawTime = ParticleConfig.NONE;
  protected WeakReference<View> wrView = null;
  protected Runnable rnRequestRender =
      new Runnable() {
        @Override
        public void run() {
          View view = wrView.get();
          if (view != null) {
            view.invalidate();
          }
        }
      };

  public ViewHelper(View view) {
    wrView = new WeakReference(view);
  }

  public void play(String folderPath, boolean isAsset) {
    if (scene == null) {
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
    }
    View view = wrView.get();
    if (view != null) {
      view.removeCallbacks(rnRequestRender);
    }
  }

  public void drawSelf(View view, Canvas can) {
    if (scene != null) {
      drawTime = ParticleTool.getTime();
      boolean needRender = scene.draw(can, drawTime);
      if (needRender) {
        int nextRenderTime = RENDER_INTERVAL - (int) (ParticleTool.getTime() - drawTime);
        view.removeCallbacks(rnRequestRender);
        if (nextRenderTime <= 0) {
          view.invalidate();
        } else {
          view.postDelayed(rnRequestRender, nextRenderTime);
        }
      }
    }
  }

  protected void post(Runnable runnable) {
    View view = wrView.get();
    if (view != null) {
      view.post(runnable);
    }
  }

  protected Context getContext() {
    Context context = null;
    View view = wrView.get();
    if (view != null) {
      context = view.getContext();
    }
    return context;
  }
}
