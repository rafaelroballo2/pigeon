package br.eng.mosaic.pigeon.web.entities 
{
	import br.eng.mosaic.pigeon.web.world.MyWorld;
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;
	import net.flashpunk.graphics.Image;
	import net.flashpunk.graphics.Spritemap;
	import net.flashpunk.masks.Hitbox;
	
	public class Enemy extends Entity
	{
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/inimigo_sprite_dir.png')] 
		private static const INIMIGO_DIR:Class;
		//private static const aliveSprite:Class;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/inimigo_sprite_esq.png')] 
		private static const INIMIGO_ESQ:Class;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/explosao.png')]
		private const EXPLOSAO:Class;
		
		public var sprEnemyDir:Spritemap;
		public var sprEnemyEsq:Spritemap;
		//public var sprExplosao:Spritemap;
		public var sprActive:Spritemap;
		
		private var virou:Boolean = false;
		private var dead:Boolean=false;
		private var deadCount:int;
		
		public function Enemy(x:Number=0, y:Number=0, graphic:Graphic=null, mask:Mask=null)
		{
			super(x, y, graphic, mask);
			
			virou = false;
			
			sprEnemyDir = new Spritemap(INIMIGO_DIR, 100, 110);
			sprEnemyDir.add("vooInimigo", [1, 0, 2, 0], 10, true); 
			
			sprEnemyEsq = new Spritemap(INIMIGO_ESQ, 100, 110);
			sprEnemyEsq.add("vooInimigo", [1, 2, 1, 0], 10, true); 
			
			//sprExplosao = new Spritemap(EXPLOSAO, 112, 107);
			//sprExplosao.add("vooInimigo", [0, 1, 2, 3], 5, false);
			
			if (MyWorld.userX >= this.x){
				sprActive = sprEnemyDir;
			} else {
				sprActive = sprEnemyEsq;
			}
			
			this.graphic = sprActive;
			//this.graphic=new Image(aliveSprite);
			//var hitbox:Hitbox=new Hitbox();
			
			setHitbox(90, 90, 5, 5);
			
			//this.width=40;
			//this.height = 40;
			//this.centerOrigin();
			type = "enemy"; //usado para tratar a colisÃ£o
		}
		
		public function die():void{
			dead=true;
			deadCount=0;
			var cloud:Cloud = new Cloud();
			cloud.x = x;
			cloud.y = y;
			world.add(cloud);
			
			world.remove(this);
			
			//this.graphic=new Image(deadSprite1);
			//sprActive = sprExplosao;
			//this.graphic = sprActive;
			//this.type = "nuvem";
		}
		
		
		override public function update():void{
			super.update();
			
			//Vira o inimigo para o pombo, mas só quando entra na aplicação
			if (!virou){
				if (MyWorld.userX >= this.x){
					sprActive = sprEnemyDir;
				} else {
					sprActive = sprEnemyEsq;
				}
				this.graphic = sprActive;
				
				virou = true;
			}
			sprActive.play("vooInimigo");
			
			if (collide("shot", x, y)){
				die();
			}
			
			/*if(dead&&deadCount++>50){
				world.remove(this);
			}else{*/
				
				//Se colidir, fica parado
				if (!collide("player", x, y) && !dead) {
					
					//Esse fator velocidade vai dar a dificuldade do jogo
					var fatorVelocidade:int = (1 + TelaInicial.pontuacao*2/3);
					
					var userX:int = MyWorld.userX;
					var userY:int = MyWorld.userY;
					
					//Anda 2 pixels para perto do pombo, em cada eixo
					if (userX != x) {
						if (userX > x) {
							x += fatorVelocidade;
						} else {
							x -= fatorVelocidade;
						}
					}
					if (userY != y) {
						if (userY > y) {
							y += fatorVelocidade;
						} else {
							y -= fatorVelocidade;
						}
					}
				}
				
			//}
			
		}
	}
}