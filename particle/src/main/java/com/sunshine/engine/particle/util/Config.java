package com.sunshine.engine.particle.util;

/** Created by songxiaoguang on 2017/9/14. */
public class Config {
  public static final long NONE = -1l;
  public static final float ZERO_FLOAT = 0f;
  public static final String SEPARATOR = ",";
	public static final int N_BORN = 2; // 憋到N个，就立即生产1个粒子，避免越憋越多的问题。
	public static final int RENDER_INTERVAL = 8; // ms
	public static final int MAX_PER_FRAME = 1; // 每帧最多产生n个粒子

	public static final String INTERPOLATOR_LINEAR = "linear";
	public static final String INTERPOLATOR_ACCELERATE = "accelerate";
	public static final String INTERPOLATOR_DECELERATE = "decelerate";
	public static final String INTERPOLATOR_SPRING = "spring";
	public static final String INTERPOLATOR_WAVE = "wave";

	public static final String LAYOUT_CENTER = "center";
	public static final String LAYOUT_TOP = "top";
	public static final String LAYOUT_BOTTOM = "bottom";
}
