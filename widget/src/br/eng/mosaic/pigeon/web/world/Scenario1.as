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
	import net.flashpunk.graphics.Backdrop;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.utils.Input;
	
	public class Scenario1 extends Scenario
	{
		
		
		public static var playing:Boolean = false;
		
		public static var userX:int = 0;
		public static var userY:int = 0;
		
		
		
		//public static var cursor:Cursor=new Cursor;
		public var cursor:Cursor=new Cursor;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_environmentsound1.mp3')]
		private static const BKG_MUSIC:Class;
		public static var bkg_music : Sfx = new Sfx(BKG_MUSIC);
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/layer_01.png')] 
		private const BG_LAYER1:Class;	

		public static const FIGEON = 1;
		public static const SIGEON = 2;
		public static const FIGEAN = 3;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/layer_02.png')] 
		private const BG_LAYER2:Class;

		var typePigeon:int;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/layer_03.png')] 
		private const BG_LAYER3:Class;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/layer_04.png')] 
		private const BG_LAYER4:Class;
		private function createBackground(){
			
			var backDropLayer1:Backdrop=new Backdrop(BG_LAYER1, true, false);
			backDropLayer1.scrollX=0.1;
			addGraphic(backDropLayer1);
			
			var backDropLayer2:Backdrop=new Backdrop(BG_LAYER2, true, false);
			backDropLayer2.scrollX=0.3;
			addGraphic(backDropLayer2);
			backDropLayer2.y=450;
			
			var backDropLayer3:Backdrop=new Backdrop(BG_LAYER3, true, false);
			backDropLayer3.y=500;
			backDropLayer3.scrollX=0.5;
			addGraphic(backDropLayer3);
			
			var backDropLayer4:Backdrop=new Backdrop(BG_LAYER4, true, false);
			backDropLayer4.scrollX=1;
			backDropLayer4.y=577;
			addGraphic(backDropLayer4);
			
			add(new HudFigeon(10,10));
			
			
			
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
			
			pigeon = new Pigeon();
			
			add(pigeon);
			
			add(cursor);
			
		}
		
		private var shots:Array=[];
		
		override public function begin():void 
		{
			initPositions();
			scenarioSpeed+=2;
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
			cursor.x+=scenarioSpeed;
			updateEnemiesCount();
			FP.camera.x += scenarioSpeed;
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
			
				cursor.x+=scenarioSpeed;
		}
		
	
		
		
		
		
		
		
		
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