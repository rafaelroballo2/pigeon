package br.eng.mosaic.pigeon.web.entities.background.scenario1
	
{
	import br.eng.mosaic.pigeon.web.entities.ScenarioEntity;
	
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class HudFigeon extends ScenarioEntity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/hud_layer_figeon.png')] private const IMAGE:Class;
		
		
		public function HudFigeon(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			
			super(x, y, graphic, mask);
			
		}
	}
}