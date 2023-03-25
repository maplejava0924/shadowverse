package vampire;
import base.Follower;

public class WardForestBat extends Follower{
	public WardForestBat() {
		
		name="守護ストバット";
		rank="vampire";
		ap=1;
		hp=1;
		cost=1;
		ward=true;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="守護ストバット";
		rank="vampire";
		ap=1;
		hp=1;
		cost=1;
		canAttack=false;attacked=false;
	}
}
