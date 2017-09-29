package com.demo.particles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunshine.engine.particle.SceneView;

public class MainActivity extends AppCompatActivity {

  private SceneView sceneView = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

		sceneView = (SceneView) findViewById(R.id.scene);

		findViewById(R.id.christmasTree).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sceneView.playByAsset("particle/christmasTree");
			}
		});

		findViewById(R.id.snow2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sceneView.playByAsset("particle/snow2");
			}
		});
  }

	@Override
	protected void onDestroy(){
		super.onDestroy();
		sceneView.stop();
		sceneView = null;
	}
}
