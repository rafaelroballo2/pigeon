package br.eng.mosaic.pigeon.web.world
{
	import br.eng.mosaic.pigeon.web.entities.Enemy;
	import br.eng.mosaic.pigeon.web.entities.Pigeon;
	import br.eng.mosaic.pigeon.web.entities.background.scenario1.Life;
	
	import flash.geom.Point;
	
	import mx.collections.IList;
	
	import net.flashpunk.FP;
	import net.flashpunk.World;
	
	public class Scenario extends World
	{
		
		public var pigeon:Pigeon = new Pigeon();
		
		public var scenarioSpeed:int=2;
		public function Scenario()
		{
			super();
		}
		
		public function get enemyMaxCount():int{
			return 4;
		}
		
		public function get totalEnemies():int{
			return 10;
		}
		
		public function createLives():void{
			add (new Life(112, 40));
			add (new Life(158, 40));
			add (new Life(204, 40));
			
		}
		
		
		
		public var positions:IList;
		
		public function updateEnemiesCount():void{
			var enemies:Array=[];
			
			getClass(Enemy, enemies);
			
			var count:int = enemyMaxCount- enemies.length;
			
			for(var i:int=0;i<count;i++){
				trace(classCount(Enemy)<enemyMaxCount);
				var positionNumber:int=(Math.random()*1000)%positions.length;
				var enemy:Enemy = new Enemy();
				var position:Point = Point(positions.getItemAt(positionNumber));
				enemy.x=position.x+FP.camera.x;
				enemy.y=position.y;
				while(enemy.collideWith(pigeon, enemy.x, enemy.y)){
					positionNumber=(Math.random()*1000)%positions.length;
					enemy = new Enemy();
					position = Point(positions.getItemAt(positionNumber));
					enemy.x=position.x+FP.camera.x;
					enemy.y=position.y;
				}
				add(enemy);				
			}
		}
	}
}