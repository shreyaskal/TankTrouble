package com.example.tanktrouble;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Bullet extends Sprite{

    private BitmapTextureAtlas mBitmapTextureAtlas;
    private TiledTextureRegion mFaceTextureRegion;
	int numBounces;
	int velocityC;
	public Bullet(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int mNumBounces) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		numBounces = mNumBounces;
		velocityC = 15;
		// TODO Auto-generated constructor stub
	}
	
	public int getNumBullets(){
		return numBounces;
	}
	
	public void bounce(){
		numBounces--;
	}
	
	public void fire(int tankX, int tankY, int touchX, int touchY){
		float deltaX = touchX - tankX;
		float deltaY = touchY - tankY;
		
	}
}

