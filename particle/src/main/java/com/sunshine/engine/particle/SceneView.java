package com.sunshine.engine.particle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sunshine.engine.particle.logic.SceneViewHelper;

public class SceneView extends View {
  private SceneViewHelper sceneViewHelper = new SceneViewHelper(this);

  public SceneView(Context context) {
    super(context);
  }

  public SceneView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public SceneView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void playByAsset(String folderPath) {
    sceneViewHelper.play(folderPath, true);
  }

  public void play(String folderPath) {
    sceneViewHelper.play(folderPath, false);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    sceneViewHelper.resize(left, top, right, bottom);
  }

  public void stop() {
    sceneViewHelper.stop();
  }

  @Override
  public boolean onTouchEvent(MotionEvent me) {
    return false;
  }

  @Override
  protected void onDraw(Canvas can) {
    super.onDraw(can);
    sceneViewHelper.drawSelf(this, can);
  }

  public void stop(String folderPath) {
    sceneViewHelper.stop(folderPath);
  }
}
