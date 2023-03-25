package neutral;

import base.Follower;

public class Goblin extends Follower{

	public Goblin() {
		name="ゴブリン";
		rank="neutral";
		ap=1;
		hp=2;
		cost=1;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="ゴブリン";
		rank="neutral";
		ap=1;
		hp=2;
		cost=1;
		canAttack=false;attacked=false;
	}
}
