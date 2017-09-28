package com.sunshine.engine.particle.model;

/** Created by songxiaoguang on 2017/9/13. */
public class ProcessFloat extends ProcessObj<Float> {

  public ProcessFloat(float from, float to) {
    set(from, to);
  }

  @Override
  public void set(Float from, Float to) {
    this.from = from;
    this.to = to;
    delta = to - from;
  }

  @Override
  public Float get(float percent) {
    return from + delta * timeInterpolator.getInterpolation(percent);
  }
}
