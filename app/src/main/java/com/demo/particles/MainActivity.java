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
                sceneView.playByAsset("particle/christmas/config.xml", "particle/christmas/pic");
              }
            });
    findViewById(R.id.flower)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/smell/config.xml", "particle/smell/pic");
              }
            });

    findViewById(R.id.rain)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/what/config.xml", "particle/what/pic");
              }
            });
    findViewById(R.id.smallFlower)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/cry/config.xml", "particle/cry/pic");
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
                sceneView.playByAsset("particle/no/config.xml", "particle/no/pic");
              }
            });

    findViewById(R.id.yeah)
        .setOnClickListener(
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                sceneView.playByAsset("particle/yeah/config.xml", "particle/yeah/pic");
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