package br.eng.mosaic.pigeon.andenginetest.personagens;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import br.eng.mosaic.pigeon.andenginetest.Stage;

public abstract class Ave extends AnimatedSprite {
	
	protected final PhysicsHandler mPhysicsHandler;
	private final TiledTextureRegion pTextureRegion;

	public Ave(final float pX, final float pY, final TiledTextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);
		this.pTextureRegion = pTextureRegion;
		
		this.setScaleCenterY(Stage.mPlayerTextureRegion.getTileHeight());
		this.setScale(2);
		this.animate(new long[]{200, 200, 200}, 3, 5, true);

		this.mPhysicsHandler = new PhysicsHandler(this);
		this.registerUpdateHandler(this.mPhysicsHandler);
	}


	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	/**
	 * Recupera a velocidade atual da Ave
	 * @return Velocidade da Ave
	 */
	public abstract float getVelocity();
}