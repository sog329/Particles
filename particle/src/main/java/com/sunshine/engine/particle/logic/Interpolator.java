package com.sunshine.engine.particle.logic;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_ACCELERATE;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_DECELERATE;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_LINEAR;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_SPRING;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_WAVE;

/** Created by songxiaoguang on 2017/9/30. */
public class Interpolator {

  private static TimeInterpolator interpolatorLinear = null;
  private static TimeInterpolator interpolatorAccelerate = null;
  private static TimeInterpolator interpolatorDecelerate = null;
  private static TimeInterpolator interpolatorSpring = null;

  public static TimeInterpolator build(String type) {
    if (INTERPOLATOR_LINEAR.equals(type)) {
      if (interpolatorLinear == null) {
        interpolatorLinear = new LinearInterpolator();
      }
      return interpolatorLinear;
    } else if (INTERPOLATOR_ACCELERATE.equals(type)) {
      if (interpolatorAccelerate == null) {
        interpolatorAccelerate = new AccelerateInterpolator();
      }
      return interpolatorAccelerate;
    } else if (INTERPOLATOR_DECELERATE.equals(type)) {
      if (interpolatorDecelerate == null) {
        interpolatorDecelerate = new DecelerateInterpolator();
      }
      return interpolatorDecelerate;
    } else if (INTERPOLATOR_SPRING.equals(type)) {
      if (interpolatorSpring == null) {
        interpolatorSpring =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                return 4 * p * (1 - p);
              }
            };
      }
      return interpolatorSpring;
    } else if (type.startsWith(INTERPOLATOR_WAVE)) {
      final int[] t = new int[1];
      try {
        t[0] = Integer.parseInt(type.split("_")[1]);
      } catch (Exception e) {
        e.printStackTrace();
        t[0] = 2;
      }
      return new TimeInterpolator() {
        @Override
        public float getInterpolation(float p) {
          return .5f + (float) Math.cos(2 * Math.PI * t[0] / 2 * p);
        }
      };
    } else {
      if (interpolatorLinear == null) {
        interpolatorLinear = new LinearInterpolator();
      }
      return interpolatorLinear;
    }
  }
}
