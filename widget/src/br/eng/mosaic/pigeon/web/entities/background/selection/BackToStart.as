package br.eng.mosaic.pigeon.web.entities.background.selection
	
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	
	import br.eng.mosaic.pigeon.web.world.*;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class BackToStart extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/backToStart.png')] private const IMAGE:Class;
		
		public function BackToStart(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			
			super(x, y, graphic, mask);
			
			this.setHitboxTo(this.graphic);
						
		}
		
		/*public override function update():void{
			if(Input.mousePressed&&this.collidePoint(x, y, Input.mouseX+44, Input.mouseY+44)){
				//Deveria passar o pombo como parametro
				FP.world = new TelaInicial();
			}
		}*/
		
	}
}