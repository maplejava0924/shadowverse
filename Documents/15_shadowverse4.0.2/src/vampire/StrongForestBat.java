package vampire;
import base.Follower;
public class StrongForestBat extends Follower{

	public StrongForestBat() {
		name="強いぞフォレストバット";
		rank="vampire";
		ap=5;
		hp=1;
		cost=8;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="強いぞフォレストバット";
		rank="vampire";
		ap=1;
		hp=1;
		cost=8;
		canAttack=false;attacked=false;
	}
	
}
