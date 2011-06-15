package br.eng.mosaic.pigeon.web.world
{
	import br.eng.mosaic.pigeon.web.entities.Enemy;
	import br.eng.mosaic.pigeon.web.entities.Figean;
	import br.eng.mosaic.pigeon.web.entities.Figeon;
	import br.eng.mosaic.pigeon.web.entities.Pigeon;
	import br.eng.mosaic.pigeon.web.entities.Sigeon;
	import br.eng.mosaic.pigeon.web.entities.background.scenario1.Life;
	import br.eng.mosaic.pigeon.web.entities.background.scenario1.*;
	import br.eng.mosaic.pigeon.web.entities.background.transition.*;
	import br.eng.mosaic.pigeon.web.ui.*;
	
	import br.eng.mosaic.pigeon.web.ScoreManager;
	
	import flash.geom.Point;
	
	import mx.collections.IList;
	
	import net.flashpunk.FP;
	import net.flashpunk.World;
	
	import punk.ui.*;
	import punk.ui.skins.*;
	
	public class Scenario extends World
	{
		
		public static const FIGEON:int = 1;
		public static const SIGEON:int = 2;
		public static const FIGEAN:int = 3;
		
		public var pigeon:Pigeon;
		
		protected var pigeonType:int;
		
		private var life1:Life;
		private var life2:Life;
		private var life3:Life;
		
		private var scoreLabel:PigeonLabel;
		
		public var scenarioSpeed:int=2;
		public function Scenario()
		{
			super();
			
		}
		
		public function createHud():void{
			switch (pigeonType) {
				case FIGEON:
					add(new HudFigeon(10,10));
					break;
				case SIGEON:
					add(new HudSigeon(10,10));
					break;
				case FIGEAN:
					add(new HudFigean(10,10));
					break;
			}
		}
		
		public function createScore():void{
			add (new PigeonLabel("Score:", 618, 70, 1, 1, new WhiteAfterlife));
			scoreLabel = new PigeonLabel("dddddd", 705, 70, 1, 1, new WhiteAfterlife); 
			add (scoreLabel);
		}
		
		public override function begin():void{
			
			switch (pigeonType) {
				case FIGEON:
					pigeon = new Figeon();
					break;
				case SIGEON:
					pigeon = new Sigeon();
					break;
				case FIGEAN:
					pigeon = new Figean();
					break;
			}
		}
		
		public function get enemyMaxCount():int{
			return 4;
		}
		
		public function get totalEnemies():int{
			return 10;
		}
		
		public function createLives():void{
			life1 = new Life(112, 40);
			life2 = new Life(158, 40);
			life3 = new Life(204, 40);
			add(life1);
			add(life2);
			add(life3);
		}
		
		public function decreaseLife(remainLives:int):void{
			/*switch (remainLives) {
				case 0:
					FP.world.remove(life1);
					break;
				case 1:
					FP.world.remove (life2);
					break;
				case 2:
					FP.world.remove (life3);
					break;
			}*/
		
			
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
		
		override public function update():void{
			super.update();
			if (pigeon.lives == 2){
				remove(life3);
			} else if (pigeon.lives == 1){
				remove(life2);
			}else if (pigeon.lives == 0){
				remove(life1);
			}
			
			scoreLabel.text = ScoreManager.getInstance().getScore().toString();
			
		}
	}
}