package br.eng.mosaic.pigeon.web.entities 
{
	import br.eng.mosaic.pigeon.web.world.Scenario;
	import br.eng.mosaic.pigeon.web.world.Scenario1;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class Cursor extends ScenarioEntity
	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/sight.png')] 
		private const sprite:Class;
		
		
		public function Cursor(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null)
		{
			super(x, y, graphic, mask);
			this.graphic=new Image(sprite);
			
			width=87;
			height=87;
		}
		
		public override function update():void{
				x=world.camera.x+Input.mouseX;
				y =Input.mouseY;
		}
		
	}
}