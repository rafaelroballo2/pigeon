package br.eng.mosaic.pigeon.web.entities.background.transition
	
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class Go extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/go.png')] private const IMAGE:Class;
		
		public function Go(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){	
			graphic = new Image(IMAGE);
			
			super(x, y, graphic, mask);
			
			this.setHitboxTo(this.graphic);
			
		}
	}
}