package com.sunshine.engine.particle.logic;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;

import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_ACCELERATE;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_COS;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_DECELERATE;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_LINEAR;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_SHAKE;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_SIN;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_SPRING;
import static com.sunshine.engine.particle.util.Config.INTERPOLATOR_TRIANGLE;

/** Created by songxiaoguang on 2017/9/30. */
public class Interpolator {

  private static HashMap<String, TimeInterpolator> map = new HashMap<>();

  public static TimeInterpolator build(String type) {
    TimeInterpolator interpolator = map.get(type);
    if (interpolator == null) {
      if (INTERPOLATOR_LINEAR.equals(type)) {
        interpolator = new LinearInterpolator();
      } else if (INTERPOLATOR_ACCELERATE.equals(type)) {
        interpolator = new AccelerateInterpolator();
      } else if (INTERPOLATOR_DECELERATE.equals(type)) {
        interpolator = new DecelerateInterpolator();
      } else if (INTERPOLATOR_SPRING.equals(type)) {
        interpolator =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                return 4 * p * (1 - p);
              }
            };
      } else if (type.startsWith(INTERPOLATOR_SHAKE)) {
        final int t = getParamInt(type);
        interpolator =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                return (float) Math.pow(1 - p, 2) * (float) Math.sin(2 * Math.PI * t / 2 * p);
              }
            };
      } else if (type.startsWith(INTERPOLATOR_SIN)) {
        final int t = getParamInt(type);
        interpolator =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                return (float) Math.sin(2 * Math.PI * t / 2 * p);
              }
            };
      } else if (type.startsWith(INTERPOLATOR_COS)) {
        final int t = getParamInt(type);
        interpolator =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                return (float) Math.cos(2 * Math.PI * t / 2 * p);
              }
            };
      } else if (type.startsWith(INTERPOLATOR_TRIANGLE)) {
        final int t = getParamInt(type);
        interpolator =
            new TimeInterpolator() {
              @Override
              public float getInterpolation(float p) {
                float fTotal = 2 * p * t;
                int iTotal = (int) fTotal;
                if (iTotal % 2 == 0) {
                  return fTotal - iTotal;
                } else {
                  return 1 - fTotal + iTotal;
                }
              }
            };
      }
      if (interpolator == null) {
        interpolator = new LinearInterpolator();
      } else {
        map.put(type, interpolator);
      }
    }
    return interpolator;
  }

  private static int getParamInt(String type) {
    try {
      return Integer.parseInt(type.split("_")[1]);
    } catch (Exception e) {
      return 2;
    }
  }
}
