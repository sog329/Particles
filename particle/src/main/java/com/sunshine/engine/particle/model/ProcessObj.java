package com.sunshine.engine.particle.model;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.sunshine.engine.particle.util.ResilienceInterpolator;

/** Created by songxiaoguang on 2017/9/13. */
public abstract class ProcessObj<T> {
  protected T from;
  protected T to;
  protected T delta;
  protected TimeInterpolator timeInterpolator = new LinearInterpolator();

  public abstract void set(T from, T to);

  public abstract T get(float percent);

  public T random() {
    return get((float) Math.random());
  }

  public T getFrom() {
    return from;
  }

  public T getTo() {
    return to;
  }

  public void setInterpolator(int interpolator) {
    switch (interpolator) {
      case 0:
        timeInterpolator = new LinearInterpolator();
        break;
      case 1:
        timeInterpolator = new AccelerateInterpolator();
        break;
      case 2:
        timeInterpolator = new DecelerateInterpolator();
        break;
      case 3:
        timeInterpolator = new ResilienceInterpolator();
        break;
      default:
        timeInterpolator = new LinearInterpolator();
        break;
    }
  }
}
