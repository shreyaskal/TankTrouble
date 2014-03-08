package com.example.tanktrouble;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import android.app.Activity;

public class TankTroubleActivity extends Activity{

	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
		    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

	}
	 
	protected void onCreateResources() {
	    // TODO Auto-generated method stub
	}
	 
	protected Scene onCreateScene() {
	    // TODO Auto-generated method stub
	    return null;
	}

	
	
}
