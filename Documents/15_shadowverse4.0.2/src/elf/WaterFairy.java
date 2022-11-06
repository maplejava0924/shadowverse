package elf;
import base.Follower;
import main.Leader;

public class WaterFairy extends Follower {
	
	public WaterFairy(){
		name="ウォーターフェアリー";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="ウォーターフェアリー";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		canAttack=false;attacked=false;
	}
	
	@Override
	public void lastWard(Leader turnLeader,Leader noTurnLeader) {
		System.out.println("ウォーターフェアリーのラストワード発動！");
		if(turnLeader.getHandCount()<=8) {  //手札が8枚以下なら～
			turnLeader.handSet(new Fairy());
		}else if(turnLeader.getHandCount()==9){  
			turnLeader.setCemetery(turnLeader.getCemetery()+1);
		}
		
	}
}
