package br.eng.mosaic.pigeon.web.world 
{
	import br.eng.mosaic.pigeon.web.entities.*;
	import br.eng.mosaic.pigeon.web.entities.background.*;
	import br.eng.mosaic.pigeon.web.entities.background.scenario1.*;
	
	import flash.geom.Point;
	
	import mx.collections.ArrayList;
	import mx.collections.IList;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import net.flashpunk.World;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class Scenario1 extends World
	{
		
		//private var pigeon:Pigeon = new Pigeon();
		
		private var pigeon:Pigeon;
		
		public static var playing = false;
		
		public static var userX:int = 0;
		public static var userY:int = 0;
		
		//public static var cursor:Cursor=new Cursor;
		public var cursor:Cursor=new Cursor;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_environmentsound1.mp3')]
		private static const BKG_MUSIC:Class;
		public static var bkg_music : Sfx = new Sfx(BKG_MUSIC);
		
		var typePigeon:int;
		
		private function createBackground(){
			
			add(new MainLayer());
			add(new Layer02(0, 450));
			add(new Layer03(0, 500));
			add(new Layer04(0, 577));
			
			add(new HudFigeon(10,10));
			
			add (new Life(112, 40));
			add (new Life(158, 40));
			add (new Life(204, 40));
			
			
		}
		
		public function Scenario1(typePigeon:int) {	
			//add(new Background);
			//addGraphic(new Background);
			
			this.typePigeon = typePigeon;
			
			createBackground();
			
			//Criar uma interface pra permitir fazer isso
			/*switch (typePigeon) {
				case 1:
					pigeon = new Pigeon();
					break;
				case 2:
					pigeon = new Pigeon();
					break;
				case 3:
					pigeon = new Figean();
					break;
			}*/
			
			add(pigeon);
			
			add(cursor);
			
		}
		
		private var shots:Array=[];
		
		override public function begin():void 
		{
			initPositions();
			// COlocar a musica de novo
			if (!bkg_music.playing){
				bkg_music.play(0.2, 1);
			}
			updateEnemiesCount();
		}
		override public function end():void{
			bkg_music.stop();
		}
		
		override public function update():void{
			updateEnemiesCount();
			super.update();
			if(Input.mousePressed){
				var shot:Shot=new Shot();
				shot.x=cursor.x + cursor.width/2 - shot.width/2;
				shot.y=cursor.y + cursor.height/2 - shot.height/2;
				shots[shots.length]=shot;
				add(shot);
				if (!bkg_music.playing){
					bkg_music.play(0.2, 1);
				}
			}
			this.bringForward(cursor);
			
			//Se não tem mais inimigos, termina
			if (this.classCount(Enemy)==0){
				pigeon.finalize();
			}
			
			// go to transition screen
			if (pigeon.dead){
				if (this.classCount(Pigeon)==0 && this.classCount(Cloud)==0)
				FP.world = new TransitionScreen(1);
			}
			// go to transition screen
			if (pigeon.finished){
				FP.world = new TransitionScreen(2);
			}
		}
		
		public function get enemyMaxCount():int{
			return 4;
		}
		
		public function get totalEnemies():int{
			return 10;
		}
		
		private function updateEnemiesCount():void{
			var enemies:Array=[];
			
			getClass(Enemy, enemies);
			
			var count:int = enemyMaxCount- enemies.length;
			
			for(var i:int=0;i<count;i++){
				trace(classCount(Enemy)<enemyMaxCount);
				var positionNumber:int=(Math.random()*1000)%positions.length;
				var enemy:Enemy = new Enemy();
				var position:Point = Point(positions.getItemAt(positionNumber));
				enemy.x=position.x;
				enemy.y=position.y;
				while(enemy.collideWith(pigeon, enemy.x, enemy.y)){
					positionNumber=(Math.random()*1000)%positions.length;
					enemy = new Enemy();
					position = Point(positions.getItemAt(positionNumber));
					enemy.x=position.x;
					enemy.y=position.y;
				}
				add(enemy);				
			}
		}
		
		
		private var positions:IList;
		
		
		
		private function initPositions():void{
			positions=new ArrayList();
			
			
			
			initHorizontalPositions(positions, 0);
			initHorizontalPositions(positions, FP.height);
			initVerticalPositions(positions, 0);
			initVerticalPositions(positions, FP.height);
		}
		
		private const numHorizontalPositions:int=10;
		
		private function initHorizontalPositions(list:IList, y:int){
			var width:int=FP.engine.width;
			var step:int = width/numHorizontalPositions;
			
			var x:int=0;
			
			for(var i:int =0 ; i<numHorizontalPositions ; i++){
				var position:Point = new Point(x, y);
				x+=step;
				list.addItem(position);
			}
			
			
		}
		
		private const numVerticalPositions:int=5;
		
		private function initVerticalPositions(list:IList, x:int){
			var height:int=FP.engine.height;
			var step:int = height/numVerticalPositions;
			
			var y:int=0;
			
			for(var i:int =0 ; i<numHorizontalPositions ; i++){
				var position:Point = new Point(x, y);
				y+=step;
				list.addItem(position);
			}			
		}
	}
}