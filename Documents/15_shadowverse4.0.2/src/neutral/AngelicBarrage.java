package neutral;
import base.Spell;
import main.Leader;

public class AngelicBarrage extends Spell{

	public AngelicBarrage() {
		name="エンジェルバレッジ";
		rank="neutral";
		cost=3;
	}

	@Override
	public void play(Leader turnLeader,Leader noTurnLeader,int playNumber,int choiceNumber) {
		System.out.println(name+"を唱えた！");
		playOperation(turnLeader,noTurnLeader,playNumber);
		noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-1);  //effectAttackにしたいけどnoTurnLeader.getLifeCount()だからなぁ
		for(int i=0;i<noTurnLeader.getFieldCount();i++) {

			//効果が全体処理なので潜伏に当たらないeffectAttackは使用しない
			if(!noTurnLeader.getFieldList()[i].isLiza()) {  //リザの効果を受けてないなら
				noTurnLeader.getFieldList()[i].setHp(noTurnLeader.getFieldList()[i].getHp()-1);
			}

			if(noTurnLeader.getFieldList()[i].getHp()<=0) {
				noTurnLeader.getFieldList()[i].die(noTurnLeader,turnLeader,i);
				i--;  //一個ずれたから。
			}
		}
		turnLeader.setCemetery(turnLeader.getCemetery()+1);
	}
}
