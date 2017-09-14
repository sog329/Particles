package com.sunshine.engine.particle.logic;

import android.graphics.Point;

import com.sunshine.engine.particle.model.DrawInfo;
import com.sunshine.engine.particle.model.ProcessFloat;
import com.sunshine.engine.particle.model.ProcessInt;

public class Anim {
  protected ProcessFloat duration = new ProcessFloat(0f, 0f);
  protected ProcessInt left = new ProcessInt(0, 0);
  protected ProcessInt right = new ProcessInt(0, 0);
  protected ProcessInt top = new ProcessInt(0, 0);
  protected ProcessInt bottom = new ProcessInt(0, 0);
  protected Point ptRotate = new Point(0, 0);
  protected ProcessInt rotate = new ProcessInt(0, 0);
  protected ProcessInt alpha = new ProcessInt(255, 255);

  public void runAnimation(float percent, DrawInfo drawInfo) {
    if (duration.getDelta() == 0) {
      percent = 0;
    } else {
      percent = (percent - duration.getFrom()) / duration.getDelta();
      if (percent < 0) {
        percent = 0;
      } else if (percent > 1) {
        percent = 1;
      }
    }
    drawInfo.rcSrc.left = left.get(percent);
    drawInfo.rcSrc.top = top.get(percent);
    drawInfo.rcSrc.right = right.get(percent);
    drawInfo.rcSrc.bottom = bottom.get(percent);

    drawInfo.rt = rotate.get(percent);

    drawInfo.srcRx = drawInfo.rcSrc.left + ptRotate.x;
    drawInfo.srcRy = drawInfo.rcSrc.top + ptRotate.y;

    drawInfo.alpha = alpha.get(percent);
  }
}
