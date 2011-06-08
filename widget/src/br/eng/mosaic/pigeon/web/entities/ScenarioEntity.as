package br.eng.mosaic.pigeon.web.entities
{
	import br.eng.mosaic.pigeon.web.world.Scenario;
	
	import net.flashpunk.Entity;
	import net.flashpunk.Graphic;
	import net.flashpunk.Mask;

	public class ScenarioEntity extends Entity
	{
		public function ScenarioEntity(x:int=0, y:int=0, graphic:Graphic=null, mask:Mask=null)		{
			super(x, y, graphic, mask);
		}
		
	
		
		
		public override function update():void{
			super.update();
			x+=Scenario(this.world).scenarioSpeed;
		}
		
	}
}