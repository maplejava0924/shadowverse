package neutral;

import base.Follower;
import main.Leader;


public class GrimnirWarCyclone extends Follower{

	public GrimnirWarCyclone() {
		name="風の軍神・グリームニル";
		rank="neutral";
		ap=2;
		hp=3;
		cost=3;
		ward=true;
		evUpap=2;
		evUphp=2;
	}
	
	@Override
	public void reset() {
		name="風の軍神・グリームニル";
		rank="neutral";
		ap=2;
		hp=3;
		cost=3;
		ward=true;
		canAttack=false;attacked=false;
	}
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
			if(cost==10) {  //エンハンス効果
			System.out.println(name+"のエンハンス効果発動！");
			for(int i=0;i<4;i++) {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-1);
				for(int k=0;k<noTurnLeader.getFieldCount();k++) {
					//効果が全体処理なので潜伏に当たらないeffectAttackは使用しない
					if(!noTurnLeader.getFieldList()[k].isLiza()) {  //リザの効果を受けてないなら
						noTurnLeader.getFieldList()[k].setHp(noTurnLeader.getFieldList()[k].getHp()-1);
					}
					if(noTurnLeader.getFieldList()[k].getHp()<=0) {
						noTurnLeader.getFieldList()[k].die(noTurnLeader,turnLeader,k);
						k--;  //一個ずれたから。
					}
				}
			}
		}
	}
}
