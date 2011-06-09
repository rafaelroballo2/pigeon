package br.eng.mosaic.pigeon.web.world 
{
	import br.eng.mosaic.pigeon.web.entities.Cursor;
	import br.eng.mosaic.pigeon.web.entities.background.Avatar;
	import br.eng.mosaic.pigeon.web.entities.background.Bg;
	import br.eng.mosaic.pigeon.web.entities.background.FacebookButton;
	import br.eng.mosaic.pigeon.web.entities.background.Figean;
	import br.eng.mosaic.pigeon.web.entities.background.Figeon;
	import br.eng.mosaic.pigeon.web.entities.background.Help;
	import br.eng.mosaic.pigeon.web.entities.background.Pause;
	import br.eng.mosaic.pigeon.web.entities.background.Sigeon;
	import br.eng.mosaic.pigeon.web.entities.background.Twitter;
	import br.eng.mosaic.pigeon.web.entities.background.UserMessage;
	import br.eng.mosaic.pigeon.web.entities.background.transition.Back;
	import br.eng.mosaic.pigeon.web.entities.background.transition.BgScreenTransition;
	import br.eng.mosaic.pigeon.web.entities.background.transition.Go;
	import br.eng.mosaic.pigeon.web.entities.background.transition.LevelLabel;
	import br.eng.mosaic.pigeon.web.entities.background.transition.Points;
	import br.eng.mosaic.pigeon.web.entities.background.transition.Separator;
	
	import net.flashpunk.Entity;
	import net.flashpunk.FP;
	import net.flashpunk.Sfx;
	import net.flashpunk.World;
	import net.flashpunk.graphics.Text;
	import net.flashpunk.utils.Input;
	
	import punk.ui.*;
	import punk.ui.skins.WhiteAfterlife;
	
	/**
	 * ...
	 * @author Kellyton Brito
	 */
	public class TransitionScreen extends World {
		
		public static var pontuacao:int = 0;
		var mensagem = "";
		var textoDoUsuario:Text;
		var userTextEntity:Entity ;
		public var cursor:Cursor=new Cursor;
		
		
		private var pigeonType:int;
		
		private var level:int;
		
		private var textArea:PunkTextArea;
		
		//public static var cursor:Cursor=new Cursor;
		
		[Embed(source = 'br/eng/mosaic/pigeon/web/assets/smb2-title.mp3')]
		private static const BKG_MUSIC:Class;
		public static var bkg_music = new Sfx(BKG_MUSIC);
		
		var go:Go;
		var back:Back;
		
		private function createBackground(){
			var obj:Entity;
			
			add(new Bg());
			add(new BgScreenTransition());
			
			obj = new UserMessage();
			obj.x = FP.width/2 - 165;
			obj.y = FP.height - 100; 
			add(obj);
			
			add (new Points(250,30));
			add (getPigeon(250,19));
			
			//TODO ajeitar para aparecer bonitinho
			back = new Back(272, 380); 
			add (back);
			add (new Separator(280 + 67, 380));
			
			go = new Go(415, 380);
			add(go);
			
			//TODO Ajeitar a posição para aparecer bonitinho
			add(new Avatar(450, 20));
			add(new Avatar(550, 20));
			add(new Avatar(650, 20));
			add(new Avatar(450, 150));
			add(new Avatar(550, 150));
			
			//add(new Star(210,10));
			
			add (new Help(635,420));
			
			add (new FacebookButton(20, FP.height * 1.8/3));
			add (new Twitter(20, FP.height * 1.8/3 + 70));
			add (new Pause(100, FP.height*1/2 + 240));
			
			switch (level) {
				case 1:
					add(new LevelLabel(1, 250, 270));
					break;
				case 2:
					add(new LevelLabel(2, 250, 270));
					//carrega imagem do level 2
					break;
				case 3:
					add(new LevelLabel(3, 250, 270));
					//carrega imagem do level 3
					break;
			}
			
		}

		private function getPigeon(x:int, y:int):Entity
		{
			switch (pigeonType){
				case Scenario.FIGEON:
					return new Figeon(x, y);
				case Scenario.FIGEAN:
					return new Figean(x,y);
				case Scenario.SIGEON:
					return new Sigeon(x, y);
			}
			return null;
		}
		
		override public function begin():void 
		{
			createBackground();
			
			textArea = new PunkTextArea("<Put Message Here>", FP.width/2 - 150, FP.height - 65, 300, 65, new WhiteAfterlife);
			
			add(textArea); 
			
			// COlocar a musica de novo
			/*if (!bkg_music.playing){
			bkg_music.play(0.5, 1);
			}*/
			
			add(cursor);
			
			
			super.begin();
		}
		
		public function TransitionScreen(level:int, pigeonType:int) {
			this.level = level;
			this.pigeonType = pigeonType;
			switch (level) {
				case 1:
					//carrega imagem do level 1
					break;
				case 2:
					//carrega imagem do level 2
					break;
				case 3:
					//carrega imagem do level 3
					break;
			}
		}
		
		public function startGame():void{
			//FP.world = new MyWorld;
		}
		
		override public function update():void {
			super.update();
			
			if(Input.mousePressed && go.collidePoint(go.x, go.y, Input.mouseX+44, Input.mouseY+44)){
				switch (level) {
					case 1:
						FP.world = new Scenario1(pigeonType);
						break;
					case 2:
						FP.world = new Scenario2(pigeonType);
						break;
					case 3:
						FP.world = new Scenario3(pigeonType);
						break;
				}
			}
			
			if(Input.mousePressed && go.collidePoint(back.x, go.y, Input.mouseX, Input.mouseY)){
				switch (level) {
					case 1:
						FP.world = new TelaInicial;
						break;
					case 2:
						FP.world = new Scenario1(1);
						break;
					case 3:
						FP.world = new Scenario2(1);
						break;
				}
			}
			
			this.bringForward(cursor);
		}
		
	}
	
}