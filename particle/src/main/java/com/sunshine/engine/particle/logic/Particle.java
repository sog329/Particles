package com.sunshine.engine.particle.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sunshine.engine.particle.model.DrawInfo;
import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.ParticleTool;

import java.util.ArrayList;
import java.util.List;

public class Particle {
  private Scene scene = null;
  public long activeTimeDuration = 1000;
  public long activeTimeStart = ParticleConfig.NONE;
  public Rect rcBmp = new Rect();
  protected DrawInfo drawInfo = new DrawInfo();
  public List<Anim> lst = new ArrayList<>();

  public void setRcBmp(Rect rect) {
    rcBmp.set(rect);
  }

  public Particle(Scene scene) {
    this.scene = scene;
    for (int i = 0; i < 3; i++) {
      lst.add(new Anim());
    }
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
   *
   * @param can
   * @param bmp
   * @param drawTime
   * @return
   */
  protected boolean draw(Canvas can, Bitmap bmp, long drawTime) {
      if (activeTimeStart == ParticleConfig.NONE) {
        activeTimeStart = drawTime;
      }
      float rp = 1f * (drawTime - activeTimeStart) / activeTimeDuration;
      if (rp <= 1) {
        for (int i = 0; i < lst.size(); i++) {
          Anim anim = lst.get(i);
          if (ParticleTool.isInRange(rp, anim.duration.getFrom(), anim.duration.getTo())) {
            anim.runAnimation(rp, drawInfo);
            mergeScene();
            int cs = can.save();
            can.rotate(drawInfo.rt, drawInfo.dstRx, drawInfo.dstRy);
            scene.pt.setAlpha(drawInfo.alpha);
            can.drawBitmap(bmp, rcBmp, drawInfo.rcDst, scene.pt);
            can.restoreToCount(cs);
            break;
          }
        }
        return false;
      } else {
        end();
        return true;
      }
  }

  protected void end() {
    activeTimeStart = ParticleConfig.NONE;
  }
}
