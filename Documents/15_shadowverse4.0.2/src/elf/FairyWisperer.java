package elf;
import base.Follower;
import main.Leader;

public class FairyWisperer extends Follower{
	
	public FairyWisperer() {
		name="フェアリーウィスパラー";
		rank="elf";
		ap=1;
		hp=1;
		cost=2;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="フェアリーウィスパラー";
		rank="elf";
		ap=1;
		hp=1;
		cost=2;
		canAttack=false;attacked=false;
	}
	
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		if(turnLeader.getHandCount()<=7) {  //手札が7枚以下なら～
			for (int i=0; i<2;i++) {
				turnLeader.handSet(new Fairy());
			}
		}else if(turnLeader.getHandCount()==8){
			turnLeader.handSet(new Fairy());
			turnLeader.setCemetery(turnLeader.getCemetery()+1);
		}

	}
}
