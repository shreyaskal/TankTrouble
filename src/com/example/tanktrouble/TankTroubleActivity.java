package com.example.tanktrouble;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class TankTroubleActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener, SensorEventListener{


	Sensor mGravity;
	SensorManager sm;
    private PhysicsWorld mPhysicsWorld;
	private ITextureRegion tank1, tankTurret1, tank2, tankTurret2;
	boolean isInitialized = false;
	private Tank tankOne;
	private Tank tankTwo;
	private TankTurret tankTurretOne;
	private TankTurret tankTurretTwo;
	private int CAMERA_WIDTH;
	private int CAMERA_HEIGHT;
	private float tankOneW;
	private float tankOneH;
	private float tankTwoW;
	private float tankTwoH;
	float theX, theY, theZ;
//	private Body t1;
//	Rectangle tankRect;
    final FixtureDef TANK_FIXTURE_DEF = PhysicsFactory.createFixtureDef(
            1,     // density
            1,  // elasticity 
            0); // friction
    private final float DIFFERENCE_FACTOR = (float) 6.3;


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

			sm = (SensorManager)getSystemService(SENSOR_SERVICE); //enabling sensor; decided to put it in onCreateResources just because we need it to run on startup of game
			mGravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
			sm.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL); 
			

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
		Vector2 v = new Vector2(0, SensorManager.GRAVITY_EARTH);
        mPhysicsWorld = new PhysicsWorld(v, false);

        
		final Scene scene = new Scene();
		tankOne = new Tank(20, 20, tank1, getVertexBufferObjectManager(), 5);
		tankTwo = new Tank(20, 100, tank2, getVertexBufferObjectManager(), 5);
		
		tankOneW = tankOne.getWidth();
		tankOneH = tankOne.getHeight();
		tankTwoW = tankTwo.getWidth();
		tankTwoH = tankTwo.getHeight();
		
		float t1x = tankOne.getX();
		float t1y = tankOne.getY();
		float t2x = tankTwo.getX();
		float t2y = tankTwo.getY();

		tankTurretOne = new TankTurret(t1x - 5, t1y - 3, tankTurret1, getVertexBufferObjectManager(), 5);
		tankTurretTwo = new TankTurret(t2x - 11, t2y - 10, tankTurret2, getVertexBufferObjectManager(), 5);

//		tankRect = new Rectangle(t1x, t1y, t1x + tankOneW, t1y + tankOneH, vertexBufferObjectManager);
		
		scene.setBackground(new RepeatingSpriteBackground(CAMERA_WIDTH,CAMERA_HEIGHT,getTextureManager(), 
				AssetBitmapTextureAtlasSource.create(getAssets(), "images/Background.png"),getVertexBufferObjectManager()));
		
//		t1 = PhysicsFactory.createCircleBody(this.mPhysicsWorld, tankRect, BodyType.DynamicBody, TANK_FIXTURE_DEF);
		
		scene.attachChild(tankOne);
		scene.attachChild(tankTwo);
		scene.attachChild(tankTurretOne);
		scene.attachChild(tankTurretTwo);

		scene.setOnSceneTouchListener(this);
		
		return scene;
	}


//	public boolean onAreaTouched(TouchEvent t){
//		
//		
//		
//		tankOne.setPosition(t.getX() - (tankOneW / 2), t.getY() - (tankOneH / 2));
//		tankTurretOne.setPosition(tankOne.getX() - 5 - (tankOneW / 2), tankOne.getY() - 3 - (tankOneW / 2));
//		return true;
//	}


	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
//		tankOne.setPosition(pSceneTouchEvent.getMotionEvent().getX() - (tankOneW / 2), pSceneTouchEvent.getMotionEvent().getY() - (tankOneH / 2));
//		tankTurretOne.setPosition(pSceneTouchEvent.getMotionEvent().getX() - 5 - (tankOneW / 2), pSceneTouchEvent.getMotionEvent().getY() - 5 - (tankOneH / 2));
		return true;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];

		if (!isInitialized) {

			theX = 0;
			theY = 0;
			theZ = 0;
			isInitialized = true;

		} 
		
		else {

			theX = x;
			theY = y;
			theZ = z;
			
			if (Math.abs(theY) > 1){
				tankOne.setRotation(tankOne.getRotation() + theY * 3);

			}
			if (Math.abs(theZ - DIFFERENCE_FACTOR) > 1.5){
				float degree = tankOne.getRotation();
				float xPosT1I = tankOne.getX();
				float yPosT1I = tankOne.getY();
				float xPosT1F = (float) (xPosT1I + (Math.sin(degree) * 4));
				float yPosT1F = (float) (yPosT1I + (Math.cos(degree) * 4));
				tankOne.setPosition(xPosT1F, yPosT1F);
			}
			
		}
	}
	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}


}
