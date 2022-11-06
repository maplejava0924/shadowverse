package vampire;
import base.Follower;
public class ForestBat extends Follower{
	public ForestBat() {
		
		name="フォレストバット";
		rank="vampire";
		ap=1;
		hp=1;
		cost=1;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="フォレストバット";
		rank="vampire";
		ap=1;
		hp=1;
		cost=1;
		canAttack=false;attacked=false;
	}
}
