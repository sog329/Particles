package com.sunshine.engine.particle.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sunshine.engine.particle.model.DrawInfo;
import com.sunshine.engine.particle.util.Config;

public class Particle {
  private Scene scene = null;
  public long activeTimeDuration = 1000;
  public long activeTimeStart = Config.NONE;
  public Rect rcBmp = new Rect();
  protected DrawInfo drawInfo = new DrawInfo();
  protected Anim anim = new Anim();

  public void setRcBmp(Rect rect) {
    rcBmp.set(rect);
  }

  public Particle(Scene scene) {
    this.scene = scene;
  }

  private void mergeScene() {
    // move
    drawInfo.rcDst.left = (int) (drawInfo.rcSrc.left * scene.scale) + scene.drawArea.l;
    drawInfo.rcDst.top = (int) (drawInfo.rcSrc.top * scene.scale) + scene.drawArea.t;
    drawInfo.rcDst.right = (int) (drawInfo.rcSrc.right * scene.scale) + scene.drawArea.l;
    drawInfo.rcDst.bottom = (int) (drawInfo.rcSrc.bottom * scene.scale) + scene.drawArea.t;
    // rotate
    drawInfo.dstRx = (int) (drawInfo.srcRx * scene.scale) + scene.drawArea.l;
    drawInfo.dstRy = (int) (drawInfo.srcRy * scene.scale) + scene.drawArea.t;
  }

  /**
   * @param can
   * @param bmp
   * @param drawTime
   * @return
   */
  protected boolean draw(Canvas can, Bitmap bmp, long drawTime) {
    if (activeTimeStart == Config.NONE) {
      activeTimeStart = drawTime;
    }
    int activeTime = (int) (drawTime - activeTimeStart);
    float rp = 1f * activeTime / activeTimeDuration;
    if (rp <= 1) {
      anim.runAnimation(rp, drawInfo);
      mergeScene();
      int cs = can.save();
      can.rotate(drawInfo.rt, drawInfo.dstRx, drawInfo.dstRy);
      scene.pt.setAlpha(drawInfo.alpha);
      can.drawBitmap(bmp, rcBmp, drawInfo.rcDst, scene.pt);
      can.restoreToCount(cs);
      return false;
    } else {
      end();
      return true;
    }
  }

  protected void end() {
    activeTimeStart = Config.NONE;
  }
}
