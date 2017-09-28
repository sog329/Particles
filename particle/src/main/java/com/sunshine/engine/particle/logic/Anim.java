package com.sunshine.engine.particle.logic;

import android.graphics.Point;

import com.sunshine.engine.particle.model.DrawInfo;
import com.sunshine.engine.particle.model.ProcessFloat;
import com.sunshine.engine.particle.model.ProcessInt;

public class Anim {
  protected ProcessInt centerX = new ProcessInt(0, 0);
  protected ProcessInt centerY = new ProcessInt(0, 0);
  protected int halfWidth = 0;
  protected int halfHeight = 0;
  protected Point ptRotate = new Point(0, 0);
  protected ProcessInt rotate = new ProcessInt(0, 0);
  protected ProcessFloat scale = new ProcessFloat(1f, 1f);
  protected int alpha = 255;

  public void runAnimation(float percent, DrawInfo drawInfo) {
    if (percent < 0) {
      percent = 0;
    } else if (percent > 1) {
      percent = 1;
    }
    int x = centerX.get(percent);
    int y = centerY.get(percent);
    float s = scale.get(percent);
    int w = (int) (s * halfWidth);
    int h = (int) (s * halfHeight);

    drawInfo.rcSrc.left = x - w;
    drawInfo.rcSrc.top = y - h;
    drawInfo.rcSrc.right = x + w;
    drawInfo.rcSrc.bottom = y + h;

    drawInfo.rt = rotate.get(percent);

    drawInfo.srcRx = drawInfo.rcSrc.left + (int) (ptRotate.x * s);
    drawInfo.srcRy = drawInfo.rcSrc.top + (int) (ptRotate.y * s);

    drawInfo.alpha = alpha;
  }
}
