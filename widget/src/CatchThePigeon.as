package
{
	import br.eng.mosaic.pigeon.web.remote.FbHandler;
	import br.eng.mosaic.pigeon.web.remote.Service;
	import br.eng.mosaic.pigeon.web.remote.dto.UserInfo;
	import br.eng.mosaic.pigeon.web.world.TelaInicial;
	
	import com.adobe.serialization.json.JSON;
	import com.adobe.serialization.json.JSONTokenType;
	
	import flash.display.LoaderInfo;
	import flash.display.Sprite;
	import flash.net.FileFilter;
	import flash.ui.Mouse;
	import flash.ui.MouseCursor;
	
	import mx.rpc.Responder;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	import net.flashpunk.Engine;
	import net.flashpunk.FP;
	import net.flashpunk.World;
	
	public class CatchThePigeon extends Engine
	{
		
	
		private var _service:Service;
		
		public var userName:String;
		
		public var userinfo:UserInfo;
		
		public static var engine:CatchThePigeon;
		
		public function CatchThePigeon()
		{
			super(760,600, 60, false);
			Mouse.hide();
			FP.world = new TelaInicial;
			engine=this;
			
			
			
		}

		public function get service():Service
		{
			if(!_service){
				_service=new Service();
			
			}
			return _service;
		}

		public function set service(value:Service):void
		{
			_service = value;
		}

		override public function init():void {
			var paramObj:Object = LoaderInfo(this.root.loaderInfo).parameters;
			userName = paramObj.userName;
			service.getUserData(userName).addResponder(new Responder(usernameResult, communcationFault));
		}

		private function communcationFault(faultEvent:FaultEvent):void
		{
			
		}

		private function usernameResult(resultEvent:ResultEvent):void
		{
			var resultString:String=resultEvent.result as String;
			var pos:int=resultString.indexOf(":");
			var data:String = resultString.substr(pos+1);
			
			pos=data.indexOf("'");
			data = data.substr(pos+1);
			pos=data.lastIndexOf("'");
			data = data.substr(0, pos);
			
			var object:Object=JSON.decode(data);
			userinfo =new UserInfo(object);
			
			
			
			
		}
	
		
	}
}