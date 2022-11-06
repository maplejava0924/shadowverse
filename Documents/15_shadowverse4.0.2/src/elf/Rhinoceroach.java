package elf;
import base.Follower;
import main.Leader;

public class Rhinoceroach extends Follower{

	protected static int effectUpAp;

	public Rhinoceroach() {
		name="リノセウス";
		rank="elf";
		ap=1;
		hp=1;
		cost=2;
		storm=true;  //疾走
		canAttack = true;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="リノセウス";
		rank="elf";
		ap=1;
		hp=1;
		cost=2;
		storm=true;  //疾走
		canAttack = true;
	}



	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		//「このターン中に（このカードを含めず）カードをプレイした枚数」なので-1
		effectUpAp=turnLeader.getPlayCount()-1;
		this.setAp(this.getAp()+effectUpAp);
	}

	//ターン終了時効果
	public void turnEndEffect(Leader turnLeader,Leader noTurnLeader) {
		System.out.println(name+"のターン終了時効果発動！");

		//進化済みなら～
		if(this.isEv()) {
			this.setAp(3);
		}else {
			this.setAp(1);
		}
	}

}
