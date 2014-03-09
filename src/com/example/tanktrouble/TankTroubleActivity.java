package com.example.tanktrouble;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.app.Activity;

public class TankTroubleActivity extends SimpleBaseGameActivity{

	

	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private ITextureRegion tank1, tankTurret1, tank2, tankTurret2;
	
	
	public EngineOptions onCreateEngineOptions() {
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
		Tank tankOne = new Tank(20, 20, tank1, getVertexBufferObjectManager(), 5);
		Tank tankTwo = new Tank(20, 100, tank2, getVertexBufferObjectManager(), 5);
		float t1x = tankOne.getX();
		float t1y = tankOne.getY();
		float t2x = tankTwo.getX();
		float t2y = tankTwo.getY();
		
		TankTurret tankTurretOne = new TankTurret(t1x - 5, t1y - 3, tankTurret1, getVertexBufferObjectManager(), 5);
		TankTurret tankTurretTwo = new TankTurret(t2x - 11, t2y - 10, tankTurret2, getVertexBufferObjectManager(), 5);
		
		scene.attachChild(tankOne);
		scene.attachChild(tankTwo);
		scene.attachChild(tankTurretOne);
		scene.attachChild(tankTurretTwo);
		
		return scene;
	}



	
	
}
