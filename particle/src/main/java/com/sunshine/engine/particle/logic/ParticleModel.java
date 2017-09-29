package com.sunshine.engine.particle.logic;

import android.graphics.Point;
import android.graphics.Rect;

import com.sunshine.engine.particle.model.Area;
import com.sunshine.engine.particle.model.ProcessFloat;
import com.sunshine.engine.particle.model.ProcessInt;
import com.sunshine.engine.particle.model.Size;
import com.sunshine.engine.particle.util.ParticleConfig;

import static com.sunshine.engine.particle.util.ParticleConfig.INTERPOLATOR_LINEAR;

public class ParticleModel {
  public Size size = new Size(0, 0);
  public Rect rcBmp = new Rect(0, 0, 0, 0);
  public ProcessFloat chanceRange = new ProcessFloat(0f, 100f);
  public ProcessInt activeTime = new ProcessInt(0, 100);
  public Area areaFrom = new Area();
  public Area areaTo = new Area();
  public String[] interpolatorMove = new String[] {INTERPOLATOR_LINEAR, INTERPOLATOR_LINEAR};
  public Point ptRotate = new Point(0, 0);
  public ProcessInt rotateBegin = new ProcessInt(0, 100);
  public ProcessInt rotateEnd = new ProcessInt(200, 300);
  public ProcessInt alphaBegin = new ProcessInt(150, 255);
  public ProcessInt alphaEnd = new ProcessInt(150, 255);
  public String interpolatorAlpha = INTERPOLATOR_LINEAR;
  public ProcessFloat scaleBegin = new ProcessFloat(1f, 2f);
  public ProcessFloat scaleEnd = new ProcessFloat(0f, .2f);

  // x&y为Scene坐标系
  public void build(Scene scene, Particle p) {
    p.activeTimeDuration = activeTime.random();
    p.activeTimeStart = ParticleConfig.NONE;
    p.setRcBmp(rcBmp);
    // alpha
    int alpha = alphaBegin.random();
    p.anim.alpha.set(alpha, alphaEnd == null ? alpha : alphaEnd.random());
    p.anim.alpha.setInterpolator(interpolatorAlpha);
    // move
    Point ptFrom = areaFrom.getPoint(scene);
    Point ptTo = areaTo.getPoint(scene, ptFrom.x, ptFrom.y);
    p.anim.centerX.set(ptFrom.x, ptTo.x);
    p.anim.centerY.set(ptFrom.y, ptTo.y);
    p.anim.centerX.setInterpolator(interpolatorMove[0]);
    p.anim.centerY.setInterpolator(interpolatorMove[1]);
    p.anim.halfWidth = size.width / 2;
    p.anim.halfHeight = size.height / 2;
    // scale
    float scaleFrom = scaleBegin.random();
    float scaleTo = scaleEnd == null ? scaleFrom : scaleEnd.random();
    p.anim.scale.set(scaleFrom, scaleTo);
    // rotate
    int rotate = rotateBegin.random();
    p.anim.rotate.set(rotate, rotateEnd == null ? rotate : rotateEnd.random());
    p.anim.ptRotate.set(ptRotate.x, ptRotate.y);
  }
}
