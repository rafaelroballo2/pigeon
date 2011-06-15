package br.eng.mosaic.pigeon.web.ui
{
	import punk.ui.*;
	import punk.ui.skin.*;
	
	import br.eng.mosaic.pigeon.web.world.*;
	
	public class PigeonLabel extends PunkLabel
	{
		public function PigeonLabel(text:String = "", x:Number = 0, y:Number = 0, width:int = 1, height:int = 1, skin:PunkSkin = null){
			super(text, x, y, width, height, skin);
		}
		
		public override function update():void{
			super.update();
			x+=Scenario(this.world).scenarioSpeed;
		}
		
	}
}