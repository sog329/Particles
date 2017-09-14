package com.sunshine.engine.particle.model;

/** Created by songxiaoguang on 2017/9/13. */
public abstract class ProcessObj<T> {
  protected T from;
  protected T to;
  protected T delta;

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

  public T getDelta() {
    return delta;
  }
}
