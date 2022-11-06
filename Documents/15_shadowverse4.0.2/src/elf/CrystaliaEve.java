package elf;
import base.Follower;

public class CrystaliaEve extends Follower{

	public CrystaliaEve() {
		name="クリスタリア・イヴ";
		rank="elf";
		ap=4;
		hp=4;
		cost=4;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="クリスタリア・イヴ";
		rank="elf";
		ap=4;
		hp=4;
		cost=4;
		canAttack=false;attacked=false;
	}
}
