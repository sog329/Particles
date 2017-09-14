package com.sunshine.engine.particle.logic;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.util.ParticleConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SceneViewHelper {
  protected List<Scene> lstScene = new ArrayList();
  private Area viewArea = new Area();
  private long lastDrawTime = ParticleConfig.NONE;
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

  public SceneViewHelper(View view) {
    wrView = new WeakReference(view);
  }

  public void play(String folderPath, boolean isAsset) {
    for (Scene old : lstScene) {
      if (old != null) {
        if (folderPath.equals(old.folderPath)) {
          return;
        }
      }
    }
    Scene scene = new Scene(this, folderPath, isAsset);
    lstScene.add(scene);
    scene.resize(viewArea.w, viewArea.h, viewArea.l, viewArea.t);
    SceneBuilder.load(scene);
  }

  public void resize(int left, int top, int r, int b) {
    viewArea.l = left;
    viewArea.t = top;
    viewArea.w = r - viewArea.l;
    viewArea.h = b - viewArea.t;
    for (Scene scene : lstScene) {
      scene.resize(viewArea.w, viewArea.h, viewArea.l, viewArea.t);
    }
  }

  public void stop() {
    while (lstScene.size() > 0) {
      stop(lstScene.get(0).folderPath);
    }
  }

  public void stop(String folderPath) {
    if (folderPath != null) {
      for (Scene scene : lstScene) {
        if (folderPath.equals(scene.folderPath)) {
          scene.destroy();
          break;
        }
      }
    }
  }

  public void drawSelf(View view, Canvas can) {
    drawSelf(view, can, System.currentTimeMillis());
  }

  protected void drawSelf(View view, Canvas can, long dt) {
    lastDrawTime = System.currentTimeMillis();
    boolean needRender = false;
    for (Scene scene : lstScene) {
      needRender = scene.draw(can, dt) || needRender;
    }
    if (needRender) {
      int nextRenderTime =
          ParticleConfig.RENDER_INTERVAL
              - Math.abs((int) (System.currentTimeMillis() - lastDrawTime));
      view.removeCallbacks(rnRequestRender);
      if (nextRenderTime <= 0) {
        view.invalidate();
      } else {
        view.postDelayed(rnRequestRender, nextRenderTime);
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
