package com.sunshine.engine.particle.logic;

import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.sunshine.engine.particle.util.ParticleConfig;
import com.sunshine.engine.particle.util.SpringInterpolator;

import static com.sunshine.engine.particle.util.ParticleConfig.INTERPOLATOR_ACCELERATE;
import static com.sunshine.engine.particle.util.ParticleConfig.INTERPOLATOR_DECELERATE;
import static com.sunshine.engine.particle.util.ParticleConfig.INTERPOLATOR_SPRING;

/** Created by songxiaoguang on 2017/9/30. */
public class InterpolatorBuilder {

  private static LinearInterpolator interpolatorLinear = null;
  private static AccelerateInterpolator interpolatorAccelerate = null;
  private static DecelerateInterpolator interpolatorDecelerate = null;
  private static SpringInterpolator interpolatorSpring = null;

  public static TimeInterpolator get(String type) {
    if (ParticleConfig.INTERPOLATOR_LINEAR.equals(type)) {
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
        interpolatorSpring = new SpringInterpolator();
      }
      return interpolatorSpring;
    } else {
      if (interpolatorLinear == null) {
        interpolatorLinear = new LinearInterpolator();
      }
      return interpolatorLinear;
    }
  }
}
