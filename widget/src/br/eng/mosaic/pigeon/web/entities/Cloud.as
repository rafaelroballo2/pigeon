package br.eng.mosaic.pigeon.web.entities 
{
	import net.flashpunk.Entity;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	
	public class Cloud extends Entity
	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/explosao.png')]
		private const EXPLOSAO:Class; 
		
		private var sprNuvem:Spritemap;
		
		public function Cloud(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null){
			super(x, y, graphic, mask);
			
			sprNuvem = new Spritemap(EXPLOSAO, 112, 107);
			sprNuvem.add("explosao", [0, 1, 2, 3], 10, false);
			this.graphic = sprNuvem;
			
		}
		
		override public function update():void{
			if(sprNuvem.currentAnim){
				sprNuvem.play("explosao")
			} else {
				world.remove(this);
			}
		}		
	}
}