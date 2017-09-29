package com.sunshine.engine.particle.model;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.SpringInterpolator;

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

  public void setInterpolator(String type) {
    if (ParticleConfig.INTERPOLATOR_LINEAR.equals(type)) {
      timeInterpolator = new LinearInterpolator();
    } else if (ParticleConfig.INTERPOLATOR_ACCELERATE.equals(type)) {
      timeInterpolator = new AccelerateInterpolator();
    } else if (ParticleConfig.INTERPOLATOR_DECELERATE.equals(type)) {
      timeInterpolator = new DecelerateInterpolator();
    } else if (ParticleConfig.INTERPOLATOR_SPRING.equals(type)) {
      timeInterpolator = new SpringInterpolator();
    } else {
      timeInterpolator = new LinearInterpolator();
    }
  }
}