package com.example.tanktrouble;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.DisplayMetrics;
import android.view.*;

public class TankTroubleActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener{


	


	private ITextureRegion tank1, tankTurret1, tank2, tankTurret2;

	private Tank tankOne;
	private Tank tankTwo;
	private TankTurret tankTurretOne;
	private TankTurret tankTurretTwo;
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	
	
	
	
	public EngineOptions onCreateEngineOptions() {
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		CAMERA_WIDTH = metrics.widthPixels;
		CAMERA_HEIGHT = metrics.heightPixels;
		
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
		    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);

	}

	protected void onCreateResources() {	
		try {
			ITexture redTank = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
			    @Override
			    public InputStream open() throws IOException {
			        return getAssets().open("images/RedTank.png");
			    }
			});
			ITexture blueTank = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
			    @Override
			    public InputStream open() throws IOException {
			        return getAssets().open("images/BlueTank.png");
			    }
			});
			ITexture redTankTurret = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
			    @Override
			    public InputStream open() throws IOException {
			        return getAssets().open("images/TankTurret.png");
			    }
			});
			ITexture blueTankTurret = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
			    @Override
			    public InputStream open() throws IOException {
			        return getAssets().open("images/TankTurret.png");
			    }
			});


			

			redTank.load();
			blueTank.load();
			redTankTurret.load();
			blueTankTurret.load();

			this.tank1 = TextureRegionFactory.extractFromTexture(redTank);
			this.tank2 = TextureRegionFactory.extractFromTexture(blueTank);
			this.tankTurret1 = TextureRegionFactory.extractFromTexture(redTankTurret);
			this.tankTurret2 = TextureRegionFactory.extractFromTexture(blueTankTurret);



		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	
	
	protected Scene onCreateScene() {
		final Scene scene = new Scene();
		tankOne = new Tank(20, 20, tank1, getVertexBufferObjectManager(), 5);
		tankTwo = new Tank(20, 100, tank2, getVertexBufferObjectManager(), 5);
		float t1x = tankOne.getX();
		float t1y = tankOne.getY();
		float t2x = tankTwo.getX();
		float t2y = tankTwo.getY();

		tankTurretOne = new TankTurret(t1x - 5, t1y - 3, tankTurret1, getVertexBufferObjectManager(), 5);
		tankTurretTwo = new TankTurret(t2x - 11, t2y - 10, tankTurret2, getVertexBufferObjectManager(), 5);

		scene.setBackground(new RepeatingSpriteBackground(CAMERA_WIDTH,CAMERA_HEIGHT,getTextureManager(), 
				AssetBitmapTextureAtlasSource.create(getAssets(), "images/Background.png"),getVertexBufferObjectManager()));
		
		
		scene.attachChild(tankOne);
		scene.attachChild(tankTwo);
		scene.attachChild(tankTurretOne);
		scene.attachChild(tankTurretTwo);

		scene.setOnSceneTouchListener(this);
		
		return scene;
	}


	public boolean onAreaTouched(TouchEvent t){
		tankOne.setPosition(t.getX(), t.getY());
		tankTurretOne.setPosition(tankOne.getX() - 5, tankTwo.getY() - 3);
		return true;
	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		tankOne.setPosition(pSceneTouchEvent.getMotionEvent().getX(), pSceneTouchEvent.getMotionEvent().getY());
		
		return true;
	}


}
