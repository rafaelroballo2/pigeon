package br.eng.mosaic.pigeon.andenginetest;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.content.Intent;
import br.eng.mosaic.pigeon.andenginetest.personagens.BadPigeon;
import br.eng.mosaic.pigeon.andenginetest.personagens.Pigeon;
import br.ufpe.cin.mosaic.pigeon.business.android.facebook.LoginFacebook;

public class Stage extends BaseGameActivity {
	// ===========================================================
	// Constants-
	// ===========================================================

	public static final float DEMO_VELOCITY = 10.0f;

	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;

	private Texture mTexture;
	public static TiledTextureRegion mPlayerTextureRegion;
	public static TiledTextureRegion mEnemyTextureRegion1;
	public static TiledTextureRegion mEnemyTextureRegion2;
	public static TiledTextureRegion mEnemyTextureRegion3;

	private Texture mAutoParallaxBackgroundTexture;

	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerMid;
	private TextureRegion mParallaxLayerFront;

	@Override
	public Engine onLoadEngine() {
		System.out.println("onLoadEngine");
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));		
	}

	@Override
	public void onLoadResources() {
		System.out.println("onLoadResources");
		this.mTexture = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mPlayerTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/bird.png", 0, 0, 3, 4);
		this.mEnemyTextureRegion1 = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/badpig.png", 97, 0, 3, 4);
		this.mEnemyTextureRegion2 = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/badpig.png", 97, 0, 3, 4);
		this.mEnemyTextureRegion3 = TextureRegionFactory.createTiledFromAsset(this.mTexture, this, "gfx/badpig.png", 97, 0, 3, 4);

		//----- Background ------
		this.mAutoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_front.png", 0, 0);
		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_back.png", 0, 188);
		this.mParallaxLayerMid = TextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "gfx/parallax_background_layer_mid.png", 0, 669);

		this.mEngine.getTextureManager().loadTextures(this.mTexture, this.mAutoParallaxBackgroundTexture);
		//-----------------------
	}

	@Override
	public Scene onLoadScene() {
		System.out.println("onLoadEngine/");
		this.mEngine.registerUpdateHandler(new FPSLogger());

		/*Criando a Cena e inserindo o background*/
		final Scene scene = new Scene(1);
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront)));
		scene.setBackground(autoParallaxBackground);

		/*Criando Retangulo para colisão*/
		final int rectangleX = (CAMERA_WIDTH);
		final int rectangleY = (CAMERA_HEIGHT - Stage.mPlayerTextureRegion.getTileHeight()) / 2;
		final Rectangle colisionRectangle = new Rectangle(rectangleX, rectangleY, 32, 32);
		colisionRectangle.registerEntityModifier(new LoopEntityModifier(new ParallelEntityModifier(new RotationModifier(6, 0, 360), new SequenceEntityModifier(new ScaleModifier(3, 1, 1.5f), new ScaleModifier(3, 1.5f, 1)))));
		
		scene.getLastChild().attachChild(colisionRectangle);

		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int playerX = (CAMERA_WIDTH - Stage.mPlayerTextureRegion.getTileWidth()) / 4;
		final int playerY = (CAMERA_HEIGHT - Stage.mPlayerTextureRegion.getTileHeight()) / 2;
		
		final Pigeon pigeon = new Pigeon(playerX, playerY, Stage.mPlayerTextureRegion);
		
		final BadPigeon badPigeon1 = new BadPigeon(playerX - 80, playerY, Stage.mEnemyTextureRegion1);
		final BadPigeon badPigeon2 = new BadPigeon(playerX - 100, playerY + 100, Stage.mEnemyTextureRegion2);
		final BadPigeon badPigeon3 = new BadPigeon(playerX - 140, playerY - 100, Stage.mEnemyTextureRegion3);
		
		scene.getLastChild().attachChild(pigeon);
		scene.getLastChild().attachChild(pigeon);
		
		scene.getLastChild().attachChild(badPigeon1);
		scene.getLastChild().attachChild(badPigeon2);
		scene.getLastChild().attachChild(badPigeon3);
		
		/* The actual collision-checking. */
		scene.registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				
				if(colisionRectangle.collidesWith(pigeon)) {
					
					colisionRectangle.setColor(1, 0, 0);
					//Chama a tela de login do facebook quando o pombo alcanca o final da tela
					Intent i = new Intent(getBaseContext(),LoginFacebook.class);
					startActivity(i);
				} else {
					
					colisionRectangle.setColor(0, 1, 0);
				}
				
			}
		});
	
		return scene;
	}

	@Override
	public void onLoadComplete() {
		System.out.println("onLoadComplete");

	}
	
}