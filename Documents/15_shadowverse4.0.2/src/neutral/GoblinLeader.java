package neutral;

import base.Follower;
import main.Leader;

public class GoblinLeader extends Follower{

	public GoblinLeader() {
		name="ゴブリンリーダー";
		rank="neutral";
		ap=1;
		hp=2;
		cost=3;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="ゴブリンリーダー";
		rank="neutral";
		ap=1;
		hp=2;
		cost=3;
		canAttack=false;attacked=false;
	}
	
	public void turnEndEffect(Leader turnLeader,Leader noTurnLeader) {  
		
		System.out.println("ゴブリンリーダーの効果発動！");
		if(turnLeader.getFieldCount()<=4) {
			//ゴブリンを召喚
			turnLeader.fieldSet(new Goblin());
		}else {
			//消滅
		}
	}
	
}
