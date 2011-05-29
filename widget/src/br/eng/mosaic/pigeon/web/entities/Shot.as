package br.eng.mosaic.pigeon.web.entities 
{
	import net.flashpunk.Entity;
	import net.flashpunk.graphics.Image;

	public class Shot extends Entity
	{
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/beam.png')] 
		private const sprite:Class;
		
		
		public var time:int=3;
		public function Shot()
		{
			graphic=new Image(sprite);
			this.width=16;
			this.height=16;
			type = "shot";
		}
		
		override public function update():void{
			time--;
			var enemies:Array=[];
			world.getClass(Enemy, enemies);
			
			if (time <=0){
				world.remove(this);
			}
			
			//enemies.forEach(checkCollistion); 
		}
		
		/*private function checkCollistion(enemy:Enemy, index:int, array:Array):void{
			if(this.collideWith(enemy, x, y)!=null){
				enemy.die();
			}
		}*/
	}
}