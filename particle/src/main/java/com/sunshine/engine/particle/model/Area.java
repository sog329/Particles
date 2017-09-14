package com.sunshine.engine.particle.model;

import android.graphics.Point;

import com.sunshine.engine.particle.logic.Scene;

/** Created by songxiaoguang on 2017/9/13. */
public class Area {
  public static final int MATCH_PARENT = -1;
  public int l = 0;
  public boolean isOffsetLeft = false;
  public int t = 0;
  public boolean isOffsetTop = false;
  public int w = 100;
  public int h = 100;
  private Point pt = new Point(0, 0);

  public Point getPoint(Scene scene) {
    int width = w;
    int left = l;
    if (w == MATCH_PARENT) {
      left = (int) (l - scene.drawArea.l / scene.scale);
      width = (int) (scene.viewArea.w / scene.scale) - left;
    }
    pt.x = left + (int) (width * Math.random());
    pt.y = t + (int) (h * Math.random());
    return pt;
  }

  public Point getPoint(Scene sc, int fromX, int fromY) {
    getPoint(sc);
    if (isOffsetLeft) {
      pt.x += fromX;
    }
    if (isOffsetTop) {
      pt.y += fromY;
    }
    return pt;
  }
}
