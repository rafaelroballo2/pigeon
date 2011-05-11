package br.eng.mosaic.pigeon.web.remote
{
	import com.facebook.graph.Facebook;
	
	import flash.events.EventDispatcher;
	

	public class FbHandler extends EventDispatcher
	{
		public function FbHandler()
		{
		}
		
		public function init():void{
			Facebook.init("199453210096251", initCallBack);
		}
		
		public function initCallBack(sucess:Object, fail:Object):void{
			if(sucess !=null){
				
			}else{
			}
		}
		
		public function login():void{
			Facebook.login(loginCallback);
		}
		
		public function loginCallback(sucess:Object, fail:Object):void{
			if(sucess !=null){
				
			}else{
				login();
			}
		}
	}
}