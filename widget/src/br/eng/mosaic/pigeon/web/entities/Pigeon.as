package br.eng.mosaic.pigeon.web.entities 
{
	import br.eng.mosaic.pigeon.web.world.LevelComplete;
	import br.eng.mosaic.pigeon.web.world.MyWorld;
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;	
	
	public class Pigeon extends Entity
	{
		private var sprPigeon2:Spritemap;
		private var terminou:Boolean = false;
		private var colidiu :Boolean= false;
		private var contadorColisao:int = 0;
		private var gritou:Boolean = false;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/pombo_sprite.png')]
		private const pigeon:Class; 
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/explosao.png')]
		private const EXPLOSAO:Class; 
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/gritao.mp3')]
		private const GRITO:Class;
		public var grito:Sfx = new Sfx(GRITO);
		
		public var sprPigeon:Spritemap;
		
		public function Pigeon()
		{
		
			sprPigeon = new Spritemap(pigeon, 90, 110);
			sprPigeon.add("voo", [1, 0, 2, 0], 10, true); 
			graphic = sprPigeon;
			
			//essa linha é igual às duas de baixo: faz a mesma coisa
			//setHitbox(90, 110);
			setHitbox(80,100, 5, 5);
			//height = 45;
			//width = 50;
			
			y=300;
		}
		
		override public function update():void {
			super.update();
			
			//Check de colisões
			if (x < FP.width && !terminou) {
				sprPigeon.play("voo")
				x+=1;
				//x += (2 + TelaInicial.pontuacao);
				
				MyWorld.userX = x;
				MyWorld.userY = y;
				
				//Explode mas não para
				if (!colidiu && collide("enemy", x, y)) {
					colidiu = true;
					
					sprPigeon2 = new Spritemap(EXPLOSAO, 112, 107);
					sprPigeon2.add("explosao", [0, 1, 2, 3], 5, true); 
					//sprPigeon2.add("explosao", [0, 1, 2]); 
					graphic = sprPigeon2;
				}
				
			} else if (x >= FP.width && !terminou){
				terminou = true;
				//FP.world = new LevelComplete;
				FP.world = new TelaInicial;
				TelaInicial.pontuacao += 1;
			}
			
			if (colidiu) {
				if (!gritou) {
					grito.play();
					gritou = true;
				}
				if (contadorColisao == 100) {
					terminou = true;
					FP.world = new TelaInicial;
				} else {
					//sprPigeon.play("explosao");
					contadorColisao++;
					sprPigeon2.play("explosao")
				}
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