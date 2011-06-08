package br.eng.mosaic.pigeon.web.entities.background.scenario1
	
{
	import br.eng.mosaic.pigeon.web.entities.ScenarioEntity;
	
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class HudSigeon extends ScenarioEntity 	{
		
		//[Embed(source = 'br/eng/mosaic/pigeon/web/assets/background _screen_transition.png')] private const IMAGE:Class;
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/hud_layer_sigeon.png')] private const IMAGE:Class;
		
		public function HudSigeon(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			
			super(x, y, graphic, mask);
			
		}
	}
}