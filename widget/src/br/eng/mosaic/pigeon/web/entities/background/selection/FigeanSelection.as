package br.eng.mosaic.pigeon.web.entities.background.selection
	
{
	
	import br.eng.mosaic.pigeon.web.world.*;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class FigeanSelection extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/figean_selection.png')] private const IMAGE:Class;
		
		public function FigeanSelection(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			
			this.setHitboxTo(graphic);
			
			super(x, y, graphic, mask);
			
		}
		
		public override function update():void{
			if(Input.mousePressed&&this.collidePoint(x, y, Input.mouseX, Input.mouseY)){
				//Deveria passar o pombo como parametro
				FP.world = new TransitionScreen(1);
			}
		}
		
	}
}