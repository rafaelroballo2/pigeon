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
			Facebook.init("150586265008036", initCallBack);
			
		}
		
		public function initCallBack(sucess:Object, fail:Object):void{
			if(sucess !=null){
				
			}else{
			}
		}
		
		public function login():void{
			init();
			Facebook.login(loginCallback, {perms:"user_birthday,read_stream,publish_stream"});
		}
		
		public function loginCallback(sucess:Object, fail:Object):void{
			if(sucess !=null){
				
			}else{
				login();
			}
		}
		
		
		public function submitPost(text:String):void
		{
			Facebook.api("/me/feed",submitPostHandler,{message:text}, "POST");
		}
		
		private function submitPostHandler(sucess:Object, fail:Object):void{
			
		}
	}
}