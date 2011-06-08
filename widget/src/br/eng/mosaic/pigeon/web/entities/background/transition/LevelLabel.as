package br.eng.mosaic.pigeon.web.entities.background.transition
	
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class LevelLabel extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_level1_img_layer.png')] private const LEVEL1:Class;
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_level2_img_layer.png')] private const LEVEL2:Class;
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_level3_img_layer.png')] private const LEVEL3:Class;
		
		public function LevelLabel(level:Number=1, x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			var image:Image;
			
			switch (level) {
				case 1:
					image = new Image(LEVEL1);
					break;
				case 2:
					image = new Image(LEVEL2);
					//carrega imagem do level 2
					break;
				case 3:
					image = new Image(LEVEL3);
					//carrega imagem do level 3
					break;
			}
			
			graphic = image;
			
			super(x, y, graphic, mask);
			
			this.setHitboxTo(this.graphic);
			
		}
	}
}