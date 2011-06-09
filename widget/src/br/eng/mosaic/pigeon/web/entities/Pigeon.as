package br.eng.mosaic.pigeon.web.entities 
{
	import br.eng.mosaic.pigeon.web.entities.background.scenario1.Life;
	import br.eng.mosaic.pigeon.web.world.LevelComplete;
	import br.eng.mosaic.pigeon.web.world.MyWorld;
	import br.eng.mosaic.pigeon.web.world.Scenario;
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Screen;
	import net.flashpunk.Sfx;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	
	public class Pigeon extends ScenarioEntity
	{
		private var gritou:Boolean = false;
		public static var playing = false;
		public var dead:Boolean=false;
		public var finished:Boolean=false;
		private var deadCount:int = 0;
		private const deadCountLimit:int = 50;
		
		private var velocity:int = 1;
		public var lives: int;
		public var maxLives : int = 3;
		
		
		
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/explosao.png')]
		private const EXPLOSAO:Class; 
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_explosion.mp3')]
		private const GRITO:Class;
		private var _grito:Sfx = new Sfx(GRITO);
		
		public function get grito():Sfx{
			return _grito;
		}
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_sigeon.mp3')]
		private static const BKG_MUSIC:Class;
		
		public static var bkg_music : Sfx = new Sfx(BKG_MUSIC);
		
		private var _sprPigeon:Spritemap;
		
		public function get sprPigeon():Spritemap{
			return _sprPigeon;
		}
		
		public function set sprPigeon(spr:Spritemap):void{
			_sprPigeon = spr;
		}
		
		public function Pigeon()
		{
		
			
			y=300;
			lives = maxLives;
		}
		
	
		private function die():void{
			//O pombo vira uma nuvem explodida
			var cloud:Cloud = new Cloud();
			cloud.x = x;
			cloud.y = y;
			world.add(cloud);
			
			//e as penas voam. cada uma aparece em uma quina do pombo
			var pena:Pena = new Pena(Pena.PLAYER, x - 10, y);
			world.add(pena);
			pena = new Pena(Pena.PLAYER, x +(this.width), y - 10);
			world.add(pena);
			pena = new Pena(Pena.PLAYER, x, y + (this.height/2));
			world.add(pena);
			pena = new Pena(Pena.PLAYER, x +(this.width/2), y + (this.height/2) + 20);
			world.add(pena);
			
			//this.graphic = null;
			dead = true;
			type="player";
			world.remove(this);
		}
		
		
		
		public function finalize():void{
			velocity += 5;
		}
		var contadorcanto  : int = 200; 
		override public function update():void {
			super.update();
			
			MyWorld.userX = x;
			MyWorld.userY = y;
			
			if(contadorcanto == 0){
				if (!bkg_music.playing){
					bkg_music.play(1, 1);
				}contadorcanto = 400;
			}else -- contadorcanto;
			
			
			//Check de colisões
			if (true || (x < FP.width && !dead)) {
				sprPigeon.play("voo")
				x+=velocity;
				
						
				//Morte do pombo 
				var enemy:Enemy = Enemy(collide("enemy", x, y));
				if (enemy) {
					--lives;
					enemy.die();
					if(lives == 0){
						die();	
					}
				}
			
			//Venceu
			}
			if ((x - FP.camera.x) >= (FP.width) && !dead){
				
				finished = true;
				//FP.world = new TelaInicial;
				//TelaInicial.pontuacao += 1;
			}
			
			if (dead) {
				if (!gritou) {
					grito.play(1, 1);
					gritou = true;
				}
				/*if (deadCount++ >= deadCountLimit) {
					world.remove(this);
					FP.world = new TelaInicial;
				}*/
			}
			
			//Movimento do pombo
			if (Input.check(Key.RIGHT)||Input.check(Key.D)){
				x+=1;
			}
			if (Input.check(Key.LEFT)||Input.check(Key.A)){
				x-=1;
			}
			if (Input.check(Key.UP)||Input.check(Key.W)){
				y-=1;
			}
			if (Input.check(Key.DOWN)||Input.check(Key.S)){
				y+=1;
			}

		}
	}
}