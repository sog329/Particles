package com.sunshine.engine.particle.model;

import android.animation.TimeInterpolator;

import com.sunshine.engine.particle.logic.Interpolator;

import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_LINEAR;

/** Created by songxiaoguang on 2017/9/13. */
public abstract class ProcessObj<T> {
  protected T from;
  protected T to;
  protected T delta;
  protected TimeInterpolator interpolator = Interpolator.build(INTERPOLATOR_LINEAR);

  public ProcessObj(T from, T to) {
    set(from, to);
  }

  public ProcessObj(T from, T to, String type) {
    set(from, to);
    setInterpolator(type);
  }

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
    interpolator = Interpolator.build(type);
  }
}
