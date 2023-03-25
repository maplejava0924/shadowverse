package vampire;
import base.Follower;
public class Forest_bat extends Follower{
	public Forest_bat() {
		
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
