package com.sunshine.engine.particle.model;

/** Created by songxiaoguang on 2017/9/13. */
public class ProcessInt extends ProcessObj<Integer> {

  public ProcessInt(int from, int to) {
    set(from, to);
  }

  @Override
  public void set(Integer from, Integer to) {
    this.from = from;
    this.to = to;
    delta = to - from;
  }

  @Override
  public Integer get(float percent) {
    return from + (int) (delta * timeInterpolator.getInterpolation(percent));
  }
}
