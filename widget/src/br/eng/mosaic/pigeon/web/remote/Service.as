package br.eng.mosaic.pigeon.web.remote
{
	import br.eng.mosaic.pigeon.web.remote.dto.PostarScoreDTO;
	
	import com.adobe.serialization.json.JSON;
	import com.adobe.serialization.json.JSONDecoder;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.http.HTTPService;
	

	public class Service 
	{
		
		private const serviceBaseUrl:String="http://localhost:8080/pigeon";
		
		private var operations:Object;
		
		public function Service()
		{
			operations=new Object;
			
			operations["postarScore"]=createHttpService(serviceBaseUrl+"/postarScore");
			operations["getUserData"]= createHttpService(serviceBaseUrl+"/getUserData.do");
			
			
			
			
		}
		
		
		private function createHttpService(url:String):HTTPService{
			var service:HTTPService = new HTTPService();
			service.url=url;
			service.method="POST";
			
			
			return service;
		}
		
		private function getService(method:String):HTTPService{
			return operations[method] as HTTPService;
		}
		
		
		private function serailize(object:Object){
			
		}
		
		public function postarScore(parametros:PostarScoreDTO):AsyncToken{
			var data:Object=new Object();
			data.jsonSent=JSON.encode(parametros);
			var token:AsyncToken=HTTPService(operations["postarScore"]).send(data);
			return token;
		}
		
		public function mashedPotato():AsyncToken{
			return HTTPService(operations["mashedpotato"]).send();
		}
		
		
		public function getUserData(username:String):AsyncToken{
			var data:Object=new Object();
			data.username=username;
			var token:AsyncToken=HTTPService(operations["getUserData"]).send(data);
			return token;
		}
	}
}