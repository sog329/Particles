package com.sunshine.engine.particle.model;

/** Created by songxiaoguang on 2017/9/13. */
public class ProcessInt extends ProcessObj<Integer> {


  public ProcessInt(Integer from, Integer to) {
    super(from, to);
  }

  public ProcessInt(Integer from, Integer to, String type) {
    super(from, to, type);
  }

  @Override
  public void set(Integer from, Integer to) {
    this.from = from;
    this.to = to;
    delta = to - from;
  }

  @Override
  public Integer get(float percent) {
    return from + (int) (delta * interpolator.getInterpolation(percent));
  }
}
