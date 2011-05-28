package br.eng.mosaic.pigeon.web.remote.dto
{
	public class UserSocialInfo extends UserInfo
	{
		public function UserSocialInfo()
		{
		}
		
		private var _social_id:String;
		
		private var _oauth_token:String;
		
		public function get social_id():String
		{
			return _social_id;
		}

		public function set social_id(value:String):void
		{
			_social_id = value;
		}

		public function get oauth_token():String
		{
			return _oauth_token;
		}

		public function set oauth_token(value:String):void
		{
			_oauth_token = value;
		}


	}
}