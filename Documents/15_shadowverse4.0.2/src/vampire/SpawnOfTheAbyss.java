package vampire;

import base.Follower;
import main.Leader;

public class SpawnOfTheAbyss extends Follower{

	public SpawnOfTheAbyss() {
		name="昏き底より出でる者";
		rank="vampire";
		ap=6;
		hp=7;
		cost=8;
		evUpap=2;
		evUphp=2;
		ambush=true;
	}
	@Override
	public void reset() {
		name="昏き底より出でる者";
		rank="vampire";
		ap=6;
		hp=7;
		cost=8;
		canAttack=false;attacked=false;
		ambush=true;
	}
	@Override
	public void lastWard(Leader turnLeader,Leader noTurnLeader) {
		System.out.println(name+"のラストワード発動！");
		if(this.isAmbush()) {//潜伏なら
			if(this.isEv()) {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-8);  //進化後なら８点与える。
			}else {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-6);  //進化前なら６点与える。
			}
		}
	}
	
	public void attackEffect(Leader turnLeader,Leader noTurnLeader) {  
		System.out.println(name+"の攻撃時効果発動！");
		if(this.isAmbush()) {//潜伏なら
			if(this.isEv()) {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-8);  //進化後なら８点与える。
			}else {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-6);  //進化前なら６点与える。
			}
		}
	}
	
	
}
