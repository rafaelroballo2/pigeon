package br.eng.mosaic.pigeon.web.remote.dto
{
	import mx.collections.IList;

	public class UserInfo
	{
		public function UserInfo(object:Object=null)
		{
			if(object){
				name=object.name;
				email=object.email;
			}
		}
		private var _name:String;
		
		private var _email:String;
		
		private var _socials:IList;
		
		public function get name():String
		{
			return _name;
		}

		public function set name(value:String):void
		{
			_name = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get socials():IList
		{
			return _socials;
		}

		public function set socials(value:IList):void
		{
			_socials = value;
		}


	}
}