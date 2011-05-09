package splash 
{
	import flash.display.BitmapData;
	import flash.display.BlendMode;
	import flash.display.GradientType;
	import flash.display.Graphics;
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.graphics.Graphiclist;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.tweens.misc.NumTween;
	import net.flashpunk.utils.Ease;
	import net.flashpunk.World;
	
	/**
	 * This object displays the FlashPunk splash screen.
	 */
	public class Splash extends Entity
	{
		/**
		 * Embedded graphics.
		 */
		[Embed(source = 'splash_lines.png')] private const SPLASH_LINES:Class;
		[Embed(source = 'splash_cog.png')] private const SPLASH_COG:Class;
		[Embed(source = 'splash_left.png')] private const SPLASH_LEFT:Class;
		[Embed(source = 'splash_right.png')] private const SPLASH_RIGHT:Class;
		
		/**
		 * Image objects.
		 */
		public var list:Graphiclist;
		public var lines:Image;
		public var cog:Image = new Image(SPLASH_COG);
		public var leftText:Image = new Image(SPLASH_LEFT);
		public var rightText:Image = new Image(SPLASH_RIGHT);
		public var fade:Image = Image.createRect(FP.width, FP.height, 0);
		
		/**
		 * Tween information.
		 */
		public var tween:NumTween = new NumTween(tweenEnd);
		public var fader:NumTween = new NumTween(faderEnd);
		public var leftX:int;
		public var rightX:int;
		
		/**
		 * Constructor, start the splash animation.
		 */
		public function Splash(color:uint = 0xFF3366, bgColor:uint = 0x202020, fadeTime:Number = .5, spinTime:Number = 2, spinPause:Number = .5, spins:Number = 720) 
		{
			// Create the lines image.
			var data:BitmapData = new BitmapData(FP.width, FP.height, false, 0x353535),
				g:Graphics = FP.sprite.graphics;
			g.clear();
			g.beginGradientFill(GradientType.RADIAL, [0, 0], [1, 0], [0, 255]);
			g.drawCircle(0, 0, 100);
			FP.matrix.identity();
			FP.matrix.scale(FP.width / 200, FP.height / 200);
			FP.matrix.translate(FP.width / 2, FP.height / 2);
			data.draw(FP.sprite, FP.matrix);
			g.clear();
			g.beginBitmapFill((new SPLASH_LINES).bitmapData);
			g.drawRect(0, 0, FP.width, FP.height);
			data.draw(FP.sprite);
			lines = new Image(data);
			
			// Set the entity information.
			x = FP.width / 2;
			y = FP.height / 2;
			graphic = new Graphiclist(leftText, rightText, cog, lines, fade);
			
			// Set the screen information.
			FP.screen.color = bgColor;
			
			// Set the lines properties.
			lines.blend = BlendMode.SUBTRACT;
			lines.smooth = true;
			lines.x -= x;
			lines.y -= y;
			
			// Set the big cog properties.
			cog.visible = true;
			cog.color = color;
			cog.smooth = true;
			cog.originX = cog.width / 2;
			cog.originY = cog.height / 2;
			cog.x -= cog.originX;
			cog.y -= cog.originY;
			
			// Set the left text properties.
			leftText.color = color;
			leftText.smooth = true;
			leftText.originX = leftText.width;
			leftText.originY = leftText.height / 2;
			leftText.x -= leftText.originX + cog.width / 4 + 4;
			leftText.y -= leftText.originY;
			leftX = leftText.x;
			
			// Set the right text properties.
			rightText.color = color;
			rightText.smooth = true;
			rightText.originY = rightText.height / 2;
			rightText.x += cog.width / 4;
			rightText.y -= rightText.originY;
			rightX = rightText.x;
			
			// Set the fade cover properties.
			fade.x -= x;
			fade.y -= y;
			
			// Set the timing properties.
			_fadeTime = fadeTime;
			_spinTime = spinTime;
			_spinPause = spinPause;
			_spins = spins;
			
			// Add the tweens.
			addTween(tween);
			addTween(fader);
			
			// Make invisible until you start it.
			visible = false;
		}
		
		/**
		 * Start the splash screen.
		 */
		public function start(onComplete:* = null):void
		{
			_onComplete = onComplete;
			visible = true;
			fadeIn();
		}
		
		/**
		 * Update the splash screen.
		 */
		override public function update():void 
		{
			// Text scaling/positioning.
			var t:Number = 1 - tween.scale;
			leftText.x = leftX - t * FP.width / 2;
			rightText.x = rightX + t * FP.width / 2;
			leftText.scaleY = rightText.scaleY = tween.scale;
			leftText.alpha = rightText.alpha = Ease.cubeIn(tween.scale);
			
			// Cog rotation/positioning.
			cog.angle = tween.scale <= 1 ? tween.value : tween.value * 2;
			cog.scale = 2.5 - tween.scale * 2;
			cog.alpha = tween.scale;
			
			// Fade in/out alpha control.
			fade.alpha = fader.value;
			
			// Pause before fade out.
			if (_spinWait > 0)
			{
				_spinWait -= FP.fixed ? 1 : FP.elapsed;
				if (_spinWait <= 0) fadeOut();
			}
		}
		
		/**
		 * When the fade tween completes.
		 */
		private function faderEnd():void
		{
			if (fader.value == 0) tween.tween(_spins, 0, _spinTime, Ease.backOut);
			else splashEnd();
		}
		
		/**
		 * When the tween completes.
		 */
		private function tweenEnd():void
		{
			if (_spinPause >= 0) _spinWait = _spinPause;
			else fadeOut();
		}
		
		/**
		 * When the splash screen has completed.
		 */
		private function splashEnd():void
		{
			if (_onComplete == null) return;
			else if (_onComplete is Function) (function)_onComplete();
			else if (_onComplete is World) FP.world = _onComplete;
			else throw new Error("The onComplete parameter must be a Function callback or World object.");
		}
		
		/**
		 * Fades the splash screen in.
		 */
		private function fadeIn():void
		{
			fader.tween(1, 0, _fadeTime, Ease.cubeOut);
		}
		
		/**
		 * Fades the splash screen out.
		 */
		private function fadeOut():void
		{
			fader.tween(0, 1, _fadeTime, Ease.cubeIn);
		}
		
		/**
		 * Fade in/out time and logo spinning time.
		 */
		private var _fadeTime:Number;
		private var _spinTime:Number;
		private var _spins:Number;
		private var _spinPause:Number;
		private var _spinWait:Number = 0;
		private var _onComplete:*;
	}
}