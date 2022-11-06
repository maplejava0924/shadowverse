package elf;
import base.Spell;
import main.Leader;

public class FairyCircle extends Spell{

	public FairyCircle() {
		name="フェアリーサークル";
		rank="elf";
		cost=1;
	}

	@Override
	public void play(Leader turnLeader,Leader noTurnLeader,int playNumber,int choiceNumber) {

		System.out.println(name+"を唱えた！");
		playOperation(turnLeader,noTurnLeader,playNumber);
		if(turnLeader.getHandCount()<=7) {  //手札が7枚以下なら～
			for (int i=0; i<2;i++) {
				turnLeader.handSet(new Fairy());
			}
		}else if(turnLeader.getHandCount()==8){  //この条件だと0以下とかも含まれるけど考慮はいらんだろう。
			turnLeader.handSet(new Fairy());
			turnLeader.setCemetery(turnLeader.getCemetery()+1);
		}
		turnLeader.setCemetery(turnLeader.getCemetery()+1);

	}

}
