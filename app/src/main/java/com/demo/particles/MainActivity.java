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

    findViewById(R.id.christmasTree)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/christmasTree/config.xml", "particle/christmasTree/pic");
              }
            });

    findViewById(R.id.snow2)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/snow2/config.xml", "particle/snow2/pic");
              }
            });
    findViewById(R.id.flower)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/flower/config.xml", "particle/flower/pic");
              }
            });

    findViewById(R.id.rain)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/rain/config.xml", "particle/rain/pic");
              }
            });
    findViewById(R.id.smallFlower)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/smallFlower/config.xml", "particle/smallFlower/pic");
              }
            });

    findViewById(R.id.snow)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/snow/config.xml", "particle/snow/pic");
              }
            });
    findViewById(R.id.star)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/hi/config.xml", "particle/hi/pic");
              }
            });

    findViewById(R.id.snow3)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/snow3/config.xml", "particle/snow3/pic");
              }
            });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    sceneView.stop();
    sceneView = null;
  }
}