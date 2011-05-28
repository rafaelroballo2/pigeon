package br.eng.mosaic.pigeon.web.remote.dto
{
	public class WidgetInfo
	{
		public function WidgetInfo()
		{
		}
		
		
		private var _description:String;
		
		private var _name:String;
		

		public function get description():String
		{
			return _description;
		}

		public function set description(value:String):void
		{
			_description = value;
		}

		public function get name():String
		{
			return _name;
		}

		public function set name(value:String):void
		{
			_name = value;
		}


	}
}