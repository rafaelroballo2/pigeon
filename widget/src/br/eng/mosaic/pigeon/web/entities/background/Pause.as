package br.eng.mosaic.pigeon.web.entities.background
	
{
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class Pause extends Entity 	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/pause.png')] private const IMAGE:Class;
		
		public function Pause(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			//super(x, y, graphic, mask);
			
			graphic = new Image(IMAGE);
			setHitboxTo(graphic);
			
			super(x, y, graphic, mask);
		}
		
		
		
		
		public override function update():void{
			if(Input.mousePressed){
				if( collidePoint(x, y, Input.mouseX+44, Input.mouseY+44)){
					var engine:CatchThePigeon=CatchThePigeon.engine; 
				 if(engine.soundEnabled){
						engine.soundVolume=FP.volume;
					 FP.volume=0;
					engine.soundEnabled=false;
				 }else{
					 FP.volume=engine.soundVolume;
					 engine.soundEnabled=true;
				 }
				}
			}
		}
	}
	}