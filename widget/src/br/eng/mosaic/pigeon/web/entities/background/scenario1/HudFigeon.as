package br.eng.mosaic.pigeon.web.entities.background.scenario1
	
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	public class HudFigeon extends Entity 	{
		
		//[Embed(source = 'br/eng/mosaic/pigeon/web/assets/background _screen_transition.png')] private const IMAGE:Class;
		[Embed(source = 'F:/kellyton/faculdade/doutorado/4. Disciplinas/2011.1/IN952 - EngenhariaSoftware/Fabrica/Repositorio/design/Sprites/Sprint 3/Imagens 760x600/Cenarios/Cenario 01/hud_layer_figeon.png')] private const IMAGE:Class;
		
		public function HudFigeon(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			
			super(x, y, graphic, mask);
			
		}
	}
}