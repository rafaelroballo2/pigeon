package
{
	import br.eng.mosaic.pigeon.web.remote.FbHandler;
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import flash.display.Sprite;
	import flash.net.FileFilter;
	import flash.ui.Mouse;
	import flash.ui.MouseCursor;
	
	import net.flashpunk.Engine;
	import net.flashpunk.FP;
	import net.flashpunk.World;
	
	public class CatchThePigeon extends Engine
	{
		
		public var _fbHandler:FbHandler=new FbHandler();
		
		public function get fbHandler():FbHandler{
			if(!_fbHandler){
				_fbHandler=new FbHandler();
			}
			return _fbHandler;
		}
		
		
		public static var engine:CatchThePigeon;
		
		public function CatchThePigeon()
		{
			super(760,600, 60, false);
			Mouse.hide();
			//FP.world=new MyWorld;
			FP.world = new TelaInicial;
			engine=this;
			
		}
		override public function init():void {
			//trace("Carregou"); 
		}
		
	}
}