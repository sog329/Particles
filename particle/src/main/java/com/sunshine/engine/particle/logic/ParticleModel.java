package com.sunshine.engine.particle.logic;

import android.graphics.Point;
import android.graphics.Rect;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.model.ProcessFloat;
import com.sunshine.engine.particle.model.ProcessInt;
import com.sunshine.engine.particle.model.Size;
import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.ParticleTool;

public class ParticleModel {
  public Size size = new Size(0, 0);
  public Point ptCenter = new Point(0, 0);
  public Rect rcBmp = new Rect(0, 0, 0, 0);
  public ProcessFloat chanceRange = new ProcessFloat(0f, 100f);
  public ProcessInt activeTime = new ProcessInt(0, 100);
  public Area areaFrom = new Area();
  public Area areaTo = new Area();
  public Point ptRotate = new Point(0, 0);
  public ProcessInt rotateBegin = new ProcessInt(0, 100);
  public ProcessInt rotateEnd = new ProcessInt(200, 300);
  public boolean rotateOffset = false;
  public ProcessFloat alphaDuration = new ProcessFloat(.2f, .8f);
  public ProcessInt alpha = new ProcessInt(150, 255);
  public ProcessFloat scaleBegin = new ProcessFloat(1f, 2f);
  public ProcessFloat scaleEnd = new ProcessFloat(0f, .2f);

  // x&y为Scene坐标系
  public void build(Scene scene, Particle p) {
    p.activeTimeDuration = activeTime.random();
    p.activeTimeStart = ParticleConfig.NONE;
    p.setRcBmp(rcBmp);
    // anim
    int alphaInt = alpha.random();

    Point ptFrom = areaFrom.getPoint(scene);
    Point ptTo = areaTo.getPoint(scene, ptFrom.x, ptFrom.y);
    int moveFromX = ptFrom.x;
    int moveFromY = ptFrom.y;
    int moveToX = ptTo.x;
    int moveToY = ptTo.y;
    int moveAppearX = ParticleTool.getAppearValue(moveFromX, moveToX, alphaDuration.getFrom());
    int moveAppearY = ParticleTool.getAppearValue(moveFromY, moveToY, alphaDuration.getFrom());
    int moveDisappearX = ParticleTool.getDisappearValue(moveFromX, moveToX, alphaDuration.getTo());
    int moveDisappearY = ParticleTool.getDisappearValue(moveFromY, moveToY, alphaDuration.getTo());

    float scaleFrom = scaleBegin.random();
    float scaleTo = scaleEnd.random();
    float scaleAppear = ParticleTool.getAppearValue(scaleFrom, scaleTo, alphaDuration.getFrom());
    float scaleDisappear =
        ParticleTool.getDisappearValue(scaleFrom, scaleTo, alphaDuration.getTo());

    int rotateFrom = rotateBegin.random();
    int rotateTo = rotateEnd.random();
    if (rotateOffset) {
      rotateTo += rotateFrom;
    }
    int rotateAppear = ParticleTool.getAppearValue(rotateFrom, rotateTo, alphaDuration.getFrom());
    int rotateDisappear =
        ParticleTool.getDisappearValue(rotateFrom, rotateTo, alphaDuration.getTo());

    int fx, fy, tx, ty;
    float sf, st;

    // anim 1*******************************
    Anim anim = p.lst.get(0);
    anim.duration.set(0f, alphaDuration.getFrom());
    // alpha
    anim.alpha.set(0, alphaInt);
    // move&scale
    fx = moveFromX;
    fy = moveFromY;
    tx = moveAppearX;
    ty = moveAppearY;
    sf = scaleFrom;
    st = scaleAppear;
    anim.left.set(fx - (int) (sf * ptCenter.x), tx - (int) (st * ptCenter.x));
    anim.top.set(fy - (int) (sf * ptCenter.y), ty - (int) (st * ptCenter.y));
    anim.right.set(
        fx + (int) (sf * (size.width - ptCenter.x)), tx + (int) (st * (size.width - ptCenter.x)));
    anim.bottom.set(
        fy + (int) (sf * (size.height - ptCenter.y)), ty + (int) (st * (size.height - ptCenter.y)));
    // rotate
    anim.rotate.set(rotateFrom, rotateAppear);
    anim.ptRotate.set(ptRotate.x, ptRotate.y);
    // anim 2*******************************
    anim = p.lst.get(1);
    // active time
    anim.duration.set(alphaDuration.getFrom(), alphaDuration.getTo());
    // alpha
    anim.alpha.set(alphaInt, alphaInt);
    // move&scale
    fx = moveAppearX;
    fy = moveAppearY;
    tx = moveDisappearX;
    ty = moveDisappearY;
    sf = scaleAppear;
    st = scaleDisappear;
    anim.left.set(fx - (int) (sf * ptCenter.x), tx - (int) (st * ptCenter.x));
    anim.top.set(fy - (int) (sf * ptCenter.y), ty - (int) (st * ptCenter.y));
    anim.right.set(
        fx + (int) (sf * (size.width - ptCenter.x)), tx + (int) (st * (size.width - ptCenter.x)));
    anim.bottom.set(
        fy + (int) (sf * (size.height - ptCenter.y)), ty + (int) (st * (size.height - ptCenter.y)));
    // rotate
    anim.rotate.set(rotateAppear, rotateDisappear);
    anim.ptRotate.set(ptRotate.x, ptRotate.y);
    // anim 3*******************************
    anim = p.lst.get(2);
    // active time
    anim.duration.set(alphaDuration.getTo(), 1f);
    // alpha
    anim.alpha.set(alphaInt, 0);
    // move&scale
    fx = moveDisappearX;
    fy = moveDisappearY;
    tx = moveToX;
    ty = moveToY;
    sf = scaleDisappear;
    st = scaleTo;
    anim.left.set(fx - (int) (sf * ptCenter.x), tx - (int) (st * ptCenter.x));
    anim.top.set(fy - (int) (sf * ptCenter.y), ty - (int) (st * ptCenter.y));
    anim.right.set(
        fx + (int) (sf * (size.width - ptCenter.x)), tx + (int) (st * (size.width - ptCenter.x)));
    anim.bottom.set(
        fy + (int) (sf * (size.height - ptCenter.y)), ty + (int) (st * (size.height - ptCenter.y)));
    // rotate
    anim.rotate.set(rotateDisappear, rotateTo);
    anim.ptRotate.set(ptRotate.x, ptRotate.y);
  }
}
