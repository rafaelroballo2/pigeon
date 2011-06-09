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
	
	public class Sigeon extends Pigeon
	{
		
		
		
		
		//[Embed(source = 'br/eng/mosaic/pigeon/web/assets/pombo_sprite.png')] private const pigeon:Class; 
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/sprites_man_pigeons.png')] 
		private const pigeon:Class;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/explosao.png')]
		private const EXPLOSAO:Class; 
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_explosion.mp3')]
		private const GRITO:Class;
		
		
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_figeon.mp3')]
		private static const BKG_MUSIC:Class;
		
		
		public static var bkg_music : Sfx = new Sfx(BKG_MUSIC);
		
		private  var _grito:Sfx = new Sfx(GRITO);
		
		
		public override function get grito():Sfx{
			return _grito;
		}
		
		public function Sigeon()
		{
		
			sprPigeon = new Spritemap(pigeon, 100, 100);
			sprPigeon.add("voo", [8, 9, 10, 11, 12, 13, 14, 15], 10, true); 
			graphic = sprPigeon;
			
			//O tamanho acertável é 10x10 menor, e o centro fica 5x5 desclocado, para 
			//o hitbox continuar central
			setHitbox(80,100, 5, 5);
			
			y=300;
			lives = maxLives;
		}
		
	
		
		
	}
}