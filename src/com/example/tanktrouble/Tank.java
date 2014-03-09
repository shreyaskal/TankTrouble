package com.example.tanktrouble;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Tank extends Sprite{

	int numBullets;
	
	public Tank(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, int mNumBullets) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		numBullets = mNumBullets;
		
		// TODO Auto-generated constructor stub
	}
	
	public int getNumBullets(){
		return numBullets;
	}
	
	
}
