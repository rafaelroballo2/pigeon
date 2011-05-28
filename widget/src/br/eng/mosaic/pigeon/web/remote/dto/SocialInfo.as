package br.eng.mosaic.pigeon.web.remote.dto
{
	public class SocialInfo
	{
		public function SocialInfo()
		{
		}
		
		private var _id:String;
		
		private var _token:String;
		
		private var _social:String;

		public function get id():String
		{
			return _id;
		}

		public function set id(value:String):void
		{
			_id = value;
		}

		public function get token():String
		{
			return _token;
		}

		public function set token(value:String):void
		{
			_token = value;
		}

		public function get social():String
		{
			return _social;
		}

		public function set social(value:String):void
		{
			_social = value;
		}


	}
}