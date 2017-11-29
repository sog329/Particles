package com.sunshine.engine.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sunshine.engine.particle.logic.ViewHelper;

public class SceneView extends View {
  private ViewHelper mViewHelper = new ViewHelper();

  public SceneView(Context context) {
    super(context);
  }

  public SceneView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SceneView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * 播放Asset资源
   *
   * @param configPath 脚本路径
   * @param picPath 图片路径
   * @return 是否成功投递播放任务，若当前正在播放则返回false
   */
  public boolean playByAsset(String configPath, String picPath) {
    return mViewHelper.play(this, configPath, picPath, true);
  }

  /**
   * 播放外部资源
   *
   * @param configPath 脚本路径
   * @param picPath 图片路径
   * @return 是否成功投递播放任务，若当前正在播放则返回false
   */
  public boolean play(String configPath, String picPath) {
    return mViewHelper.play(this, configPath, picPath, false);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    mViewHelper.resize(left, top, right, bottom);
  }

  public void stop() {
    mViewHelper.stop();
  }

  @Override
  public boolean onTouchEvent(MotionEvent me) {
    return false;
  }

  @Override
  protected void onDraw(Canvas can) {
    super.onDraw(can);
    mViewHelper.drawSelf(can);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    stop();
  }
}
