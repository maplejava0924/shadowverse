package vampire;

import base.Follower;
import main.Leader;


public class Alucard extends Follower{

	public Alucard() {
		name="アルカード";
		rank="vampire";
		ap=4;
		hp=4;
		cost=7;
		storm=true;  //疾走
		canAttack = true;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="アルカード";
		rank="vampire";
		ap=4;
		hp=4;
		cost=7;
		storm=true;  //疾走
		canAttack = true;
	}
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"ファンファーレ発動！");
		if(turnLeader.isRevenge()) turnLeader.setLifeCount(turnLeader.getLifeCount()+4);  //復讐状態なら4点回復
	}
}
