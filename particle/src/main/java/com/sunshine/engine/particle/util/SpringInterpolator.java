package com.sunshine.engine.particle.util;

import android.animation.TimeInterpolator;

/** Created by songxiaoguang on 2017/9/28. */
public class SpringInterpolator implements TimeInterpolator {
  @Override
  public float getInterpolation(float p) {
    return 4 * p * (1 - p);
  }
}
