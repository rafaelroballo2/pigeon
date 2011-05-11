package br.eng.mosaic.pigeon.web.world
{
	import br.eng.mosaic.pigeon.web.entities.Cursor;
	import br.eng.mosaic.pigeon.web.entities.FacebookConfig;
	import br.eng.mosaic.pigeon.web.entities.PlayButton;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import net.flashpunk.World;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.utils.Input;

	public class LevelComplete extends World
	{
		var mensagem = "";
		var textoDoUsuario:Text;
		var userTextEntity:Entity ;
		public var cursor:Cursor=new Cursor;
		
		//public static var cursor:Cursor=new Cursor;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/smb2-title.mp3')]
		private static const BKG_MUSIC:Class;
		public static var bkg_music = new Sfx(BKG_MUSIC);
		
		override public function begin():void 
		{
			
			if (!bkg_music.playing){
				bkg_music.play(0.5, 1);
			}
			
			add(cursor);
			
			// step 1 tell flashPunk what size you want the text
			Text.size = 30;
			
			// step 2 create a Text object
			var myText:Text = new Text(  "Level Complete!");
			
			// step 3 create an Entity to easily display the text
			var myTextEntity:Entity = new Entity(0, 0, myText);
			
			// optional step 3b - position the text somewhere else - here I center it on the screen.
			myTextEntity.x = (FP.width / 2) - (myText.width / 2);
			myTextEntity.y = (FP.height / 2) - (myText.height / 2);
			
			// step 4 add the entity to the world
			add(myTextEntity);
			
			
			var pontuacao:Text = new Text("Score = " + TelaInicial.pontuacao);
			var pontuacaoEntity:Entity = new Entity(0, 0, pontuacao);
			pontuacaoEntity.x = 0; // (FP.width - 10) - (pontuacaoEntity.width);
			pontuacaoEntity.y = (20);
			add(pontuacaoEntity);
			
			
			
			
			super.begin();
		}
		
		
		public function startGame():void{
			FP.world = new MyWorld;
		}
		
		override public function update():void {
			super.update();
			
			
			
			
		
			
			this.bringForward(cursor);
			
		}
		
	}
}