package br.eng.mosaic.pigeon.web
{
	public class ScoreManager
	{
		
		private static var instance:ScoreManager = new ScoreManager();
		private var score:int = 0;
		
		public static var ENEMYDOWN:int = 1;
		public static var LEVELCLEAR:int = 2;
		
		public static function getInstance():ScoreManager{
			return instance;
		}
		
		public function ScoreManager()
		{
		}
		
		/**
		 * Type can be: ENEMYDOWN or LEVELCLEAR. Level is the level where event occurs
		 * */
		public function updateScore(type:int, level:int=1){
			if (type == ENEMYDOWN){
				score+=1;
			} else if (type == LEVELCLEAR){
				score+=level;
			}
		}
		
		/*private function updateScore(points:int):void{
			score+=points;
		}*/
		public function getScore():int{
			return score;
		}
	}
}