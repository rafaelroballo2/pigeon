package br.eng.mosaic.pigeon.web.entities
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class Background extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/background.png')] private const BACKGROUND:Class;
		
		public function Background(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(BACKGROUND);
			
			super(x, y, graphic, mask);
			
		}
	}
}