package com.sunshine.engine.particle.model;

/** Created by songxiaoguang on 2017/9/13. */
public class ProcessFloat extends ProcessObj<Float> {


  public ProcessFloat(Float from, Float to) {
    super(from, to);
  }

  public ProcessFloat(Float from, Float to, String type) {
    super(from, to, type);
  }

  @Override
  public void set(Float from, Float to) {
    this.from = from;
    this.to = to;
    delta = to - from;
  }

  @Override
  public Float get(float percent) {
    return from + delta * interpolator.getInterpolation(percent);
  }
}
