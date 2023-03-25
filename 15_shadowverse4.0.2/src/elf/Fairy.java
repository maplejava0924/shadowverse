package elf;
import base.Follower;

public class Fairy extends Follower{

	public Fairy(){
		name="フェアリー";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="フェアリー";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		canAttack=false;attacked=false;
	}
}
