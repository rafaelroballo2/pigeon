package br.eng.mosaic.pigeon.web.entities
{
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;

	public class PlayButton extends Entity
	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/iniciar.png')]
		private const sprite:Class; 
		
		public function PlayButton(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null)
		{
			super(x, y, graphic, mask);
			this.graphic=new Image(sprite);
			
			this.setHitboxTo(this.graphic);
			
		}
		
		
		public override function update():void{
			if(Input.mousePressed&&this.collidePoint(x, y, Input.mouseX, Input.mouseY)){
				if(FP.world is TelaInicial){
					TelaInicial(FP.world).startGame();	
				}
			}
		}
		
	}
}