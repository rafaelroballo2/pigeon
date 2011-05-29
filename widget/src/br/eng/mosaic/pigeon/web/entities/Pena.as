package br.eng.mosaic.pigeon.web.entities 
{
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.FP;
	
	public class Pena extends Entity
	{
		
		public static const PLAYER:int = 1;
		public static const ENEMY:int = 2;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/pena_pomboAzul.png')]
		private const PENA_AZUL:Class;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/pena_inimigo.png')]
		private const PENA_INIMIGO:Class;
		
		
		public function Pena(personagem:int = 0, x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			super(x, y, graphic, mask);
			
			if (personagem == PLAYER){
				this.graphic = new Image(PENA_AZUL)
			} else if (personagem == ENEMY){
				this.graphic = new Image(PENA_INIMIGO)
			}
		}
		
		override public function update():void{
			if (y < FP.height){
				y+=10;	
			} else {
				world.remove(this);
			}
		}		
	}
}