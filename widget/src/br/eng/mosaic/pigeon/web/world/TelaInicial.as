package br.eng.mosaic.pigeon.web.world 
{
	import br.eng.mosaic.pigeon.web.entities.Cursor;
	import br.eng.mosaic.pigeon.web.entities.FacebookConfig;
	import br.eng.mosaic.pigeon.web.entities.PlayButton;
	import br.eng.mosaic.pigeon.web.entities.background.*;
	
	import punk.ui.skins.*;
	
	import com.facebook.graph.Facebook;
	
	import flash.text.TextField;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import net.flashpunk.World;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.utils.Input;
	import net.flashpunk.utils.Key;
	
	import punk.ui.*;
	
	/**
	 * ...
	 * @author Kellyton Brito
	 */
	public class  TelaInicial extends World {
		
		public static var pontuacao:int = 0;
		var mensagem = "";
		var textoDoUsuario:Text;
		var userTextEntity:Entity ;
		public var cursor:Cursor=new Cursor;
		
		private var textArea:PunkTextArea;
		
		//public static var cursor:Cursor=new Cursor;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/mosaic_pigeon_snd_menu5.mp3')]
		private static const BKG_MUSIC:Class;
		public static var bkg_music : Sfx = new Sfx(BKG_MUSIC);
		
		private function createBackground(){
			var obj:Entity;
			
			add(new Bg());
			add(new OpeningScreen());
			add(new PlayButton(FP.width/2, FP.height*2/3));
			
			obj = new UserMessage();
			obj.x = FP.width/2 - 130;// - obj.width/2;
			obj.y = FP.height - 100; //obj.height;
			add(obj);
			
			add(new Avatar(150, 20));
			add(new Avatar(250, 20));
			add(new Avatar(350, 20));
			add(new Avatar(450, 20));
			add(new Avatar(550, 20));
			
			add(new Star(210,10));
			
			add (new Help(620,415));
			
			add (new Twitter(20, FP.height * 1/2));
			add (new FacebookButton(20, FP.height * 1/2 + 70));
			add (new Pause(100, FP.height*1/2 + 240));
			
		}
		
		override public function begin():void 
		{
			createBackground();
			
			textArea = new PunkTextArea("<Put Message Here>", FP.width/2 - 115, FP.height - 65, 300, 65, new WhiteAfterlife);
			//textArea = new PunkTextArea("Cade a merda do texto, kct!?!?!?", 0, FP.height - 100, 300, 100);
			
			add(textArea); 
			
			// COlocar a musica de novo
			if (!bkg_music.playing){
				bkg_music.loop(0.5, 1);
			}
			
			add(cursor);
	
			
			super.begin();
		}
		override public function end():void{
			bkg_music.stop();
		}
		
		public function TelaInicial() {
			
		}
		
		 public function startGame():void{
				//FP.world = new MyWorld;
			 FP.world = new Scenario1;
			 //FP.world = new Scenario2;
			 //FP.world = new Scenario3;
		}
		
		override public function update():void {
			super.update();
			
			
			mensagem = Input.keyString;
			
			/*if (userTextEntity!=null){
				remove(userTextEntity);
			}
			
			textoDoUsuario = new Text(mensagem);
			userTextEntity = new Entity(0, 0, textoDoUsuario);
			userTextEntity.x = (FP.width / 2) - (textoDoUsuario.width / 2);
			userTextEntity.y = (100);
			add(userTextEntity);*/
			
			this.bringForward(cursor);
			
		}
		
	}
	
}