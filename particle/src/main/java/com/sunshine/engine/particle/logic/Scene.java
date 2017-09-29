package com.sunshine.engine.particle.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.os.Looper;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.model.ProcessFloat;
import com.sunshine.engine.particle.model.Size;
import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.ParticleTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.sunshine.engine.particle.util.ParticleConfig.INTERPOLATOR_SPRING;
import static com.sunshine.engine.particle.util.ParticleConfig.LAYOUT_BOTTOM;
import static com.sunshine.engine.particle.util.ParticleConfig.LAYOUT_CENTER;
import static com.sunshine.engine.particle.util.ParticleConfig.LAYOUT_TOP;

public class Scene {
  protected ViewHelper helper = null;
  private int maxParticle = 0;
  public List<Particle> lstIdleParticle = new ArrayList<>();
  public List<Particle> lstActiveParticle = new ArrayList<>();
  private List<ParticleModel> lstParticleModel = new ArrayList<>(); // 粒子模型
  public String folderPath = null;
  public boolean isAsset = false;
  public Bitmap bmp = null;
  protected static final Paint pt = new Paint();
  private static final PaintFlagsDrawFilter pd =
      new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
  public float scale = 0f;
  private long interval = ParticleConfig.NONE;
  private long lastBornTime = ParticleConfig.NONE;
  public Size scriptSize = new Size(720, 1080);
  public Area viewArea = new Area();
  public Area drawArea = new Area();
  public String layoutType = LAYOUT_CENTER;
  public int duration = 6000;
  private long firstDrawTime = ParticleConfig.NONE;
  private ProcessFloat intensity = new ProcessFloat(0f, 1f, INTERPOLATOR_SPRING);

  static {
    pt.setAntiAlias(true);
  }

  public Scene(ViewHelper helper, String folderPath, boolean isAsset) {
    this.folderPath = folderPath;
    this.isAsset = isAsset;
    this.helper = helper;
  }

  public void addParticleModel(ParticleModel pm) {
    lstParticleModel.add(pm);
  }

  public ParticleModel getLastParticleModel() {
    ParticleModel particleModel = null;
    if (lstParticleModel.size() > 0) {
      particleModel = lstParticleModel.get(lstParticleModel.size() - 1);
    }
    return particleModel;
  }

  public void setMaxParticle(int max) {
    maxParticle = max;
    lstIdleParticle.clear();
    lstActiveParticle.clear();
    while (lstIdleParticle.size() < maxParticle) {
      lstIdleParticle.add(new Particle(this));
    }
  }

  protected void layout() {
    if (scriptSize.width == 0 || scriptSize.height == 0 || viewArea.w == 0 || viewArea.h == 0) {
      return;
    }
    float sc = 1f * viewArea.w / scriptSize.width;
    if (sc * scriptSize.height > viewArea.h) {
      sc = 1f * viewArea.h / scriptSize.height;
    } else {
      float tmp = sc;
      sc = 1f * viewArea.h / scriptSize.height;
      if (sc * scriptSize.width > viewArea.w) {
        sc = tmp;
      } else {
        sc = Math.max(sc, tmp);
      }
    }
    scale = sc;
    drawArea.w = (int) (scale * scriptSize.width);
    drawArea.h = (int) (scale * scriptSize.height);
    drawArea.l = (viewArea.w - drawArea.w) / 2 + viewArea.l;
    if (LAYOUT_CENTER.equals(layoutType)) {
      drawArea.t = (viewArea.h - drawArea.h) / 2 + viewArea.t;
    } else if (LAYOUT_TOP.equals(layoutType)) {
      drawArea.t = viewArea.t;
    } else if (LAYOUT_BOTTOM.equals(layoutType)) {
      drawArea.t = viewArea.h - drawArea.h + viewArea.t;
    } else {
      drawArea.t = (viewArea.h - drawArea.h) / 2 + viewArea.t;
    }
  }

  protected void resize(int width, int height, int left, int top) {
    viewArea.w = width;
    viewArea.h = height;
    viewArea.l = left;
    viewArea.t = top;
    layout();
  }

  protected boolean draw(Canvas can, long dt) {
    if (ParticleTool.equalsZero(scale)) {
      return false;
    }
    if (bmp == null) {
      return false;
    } else {
      if (firstDrawTime == 0) {
        firstDrawTime = ParticleTool.getTime();
      }
      float percent = 1f * (ParticleTool.getTime() - firstDrawTime) / duration;
      buildActiveParticle(dt, percent);
      renderActiveParticle(can, bmp, dt);
      if (percent > 1 && lstActiveParticle.size() == 0) {
        destroy();
      }
      return true;
    }
  }

  private void buildActiveParticle(long dt, float percent) {
    int numActive = (int) (intensity.get(percent) * maxParticle);
    if (numActive > lstActiveParticle.size()) {
      int num = maxParticle - lstActiveParticle.size();
      if (judgeBorn(num)) {
        // 产生粒子
        for (int i = 0; i < ParticleConfig.MAX_PER_FRAME; i++) {
          int dValue = lstIdleParticle.size();
          if (dValue > 0) {
            Particle p = lstIdleParticle.get(0);
            lastBornTime = ParticleTool.getTime();
            ParticleModel pm = null;
            if (lstParticleModel.size() == 1) {
              pm = lstParticleModel.get(0);
            } else {
              float random = (float) Math.random();
              for (int j = 0; j < lstParticleModel.size(); j++) {
                pm = lstParticleModel.get(j);
                if (pm != null
                    && ParticleTool.isInRange(
                        random, pm.chanceRange.getFrom(), pm.chanceRange.getTo())) {
                  break;
                }
              }
            }
            if (pm != null) {
              pm.build(this, p);
              if (dValue > ParticleConfig.N_LIFE) { // 一次要增加过多
                p.activeTimeStart = dt - (long) (p.activeTimeDuration * Math.random());
              }
              lstIdleParticle.remove(p);
              lstActiveParticle.add(p);
            }
          } else {
            break;
          }
        }
      }
    }
  }

  private void renderActiveParticle(Canvas can, Bitmap bmp, long drawTime) {
    int cs = can.save();
    can.translate(-viewArea.l, -viewArea.t);
    can.setDrawFilter(pd);
    Iterator<Particle> it = lstActiveParticle.iterator();
    while (it.hasNext()) {
      Particle particle = it.next();
      if (particle.draw(can, bmp, drawTime)) {
        it.remove();
        lstIdleParticle.add(particle);
      }
    }
    can.restoreToCount(cs);
  }

  public void destroy() {
    if (Looper.getMainLooper() == Looper.myLooper()) {
      if (bmp != null) {
        bmp.recycle();
        bmp = null;
      }
      helper.scene = null;
    } else {
      helper.post(
          new Runnable() {
            @Override
            public void run() {
              destroy();
            }
          });
    }
  }

  private boolean judgeBorn(int n) {
    boolean b = false;
    if (n > ParticleConfig.N_BORN) {
      b = true;
    } else {
      long now = ParticleTool.getTime();
      if (Math.abs(now - lastBornTime) >= interval) {
        b = true;
      }
    }
    return b;
  }

  // 计算粒子诞生间隔
  public void setInterval() {
    int sum = 0;
    for (ParticleModel particleModel : lstParticleModel) {
      float p = particleModel.chanceRange.getTo() - particleModel.chanceRange.getFrom();
      long d = (particleModel.activeTime.getFrom() + particleModel.activeTime.getTo()) / 2;
      sum += p * d;
    }
    interval = sum / maxParticle;
  }

  public void setBmp(final Bitmap bitmap) {
    helper.post(
        new Runnable() {
          @Override
          public void run() {
            bmp = bitmap;
            if (bmp != null) {
              helper.rnRequestRender.run();
            }
          }
        });
  }
}
