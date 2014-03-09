package com.example.tanktrouble;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class TankTurret extends Sprite{

	float angle;
	
	public TankTurret(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int mAngle) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		angle = mAngle;
		
		// TODO Auto-generated constructor stub
	}
	
	public float getAngle(){
		return angle;
	}
	
}
