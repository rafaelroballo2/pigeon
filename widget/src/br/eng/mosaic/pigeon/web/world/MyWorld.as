package br.eng.mosaic.pigeon.web.world 
{
	import net.flashpunk.World;
	import net.flashpunk.utils.Input;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import br.eng.mosaic.pigeon.web.entities.Background;
	import br.eng.mosaic.pigeon.web.entities.Cursor;
	import br.eng.mosaic.pigeon.web.entities.Enemy;
	import br.eng.mosaic.pigeon.web.entities.Pigeon;
	import br.eng.mosaic.pigeon.web.entities.Shot;

	public class MyWorld extends World
	{
	
		public static var playing = false;
		
		public static var userX:int = 0;
		public static var userY:int = 0;
		
		//public static var cursor:Cursor=new Cursor;
		public var cursor:Cursor=new Cursor;
		
		public function MyWorld() {
			
			add(new Background);
			//addGraphic(new Background);
			
			add(new Pigeon);
			add(cursor);
			var enemy:Enemy = new Enemy();
			
			//enemy.x= 0;
			//Cria em algum lugar X aleatorio acima
			enemy.x = Math.random()%(FP.width)
			enemy.y= 0;
			add(enemy);
			
			enemy = new Enemy();
			enemy.x= FP.width - enemy.width;
			//Cria em algum lugar X aleatorio acima
			//enemy.x = Math.random()%(FP.width)
			enemy.y=0;
			add(enemy);
			
			enemy = new Enemy();
			//Cria em algum lugar X aleatorio abaixo
			enemy.x=0;
			//enemy.x = Math.random()%(FP.width)
			enemy.y=FP.height - enemy.height;
			add(enemy);
			
			
			enemy = new Enemy();
			//Cria em algum lugar X aleatorio abaixo
			enemy.x=FP.width - enemy.width;
			//enemy.x = Math.random()%(FP.width)
			enemy.y=FP.height - enemy.height;
			add(enemy);
			
		}
		
		private var shots:Array=[];
		
		
		
		override public function update():void{
			super.update();
			if(Input.mousePressed){
				var shot:Shot=new Shot();
				shot.x=cursor.x + cursor.width/2 - shot.width/2;
				shot.y=cursor.y + cursor.height/2 - shot.height/2;
				shots[shots.length]=shot;
				add(shot);
			}
			this.bringForward(cursor);
			updateShots();
			
		}
		
		
		private function findExpiredShots(item:Shot, index:int, array:Array):Boolean{
			return item.time<0;
		}
		
		private function findAliveShots(item:Shot, index:int, array:Array):Boolean{
			return !findExpiredShots(item, index, array);
		}
		
		
		private function removeShot(item:Shot, index:int, array:Array):void{
			remove(item);
			
		}
		
		private function updateShots():void{
			var temp:Array=shots.filter(findExpiredShots);
			temp.forEach(removeShot);
			shots=shots.filter(findAliveShots);
		}
	}
}