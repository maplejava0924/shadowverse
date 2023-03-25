package neutral;
import base.Spell;
import main.Leader;

public class Angelic_barrage extends Spell{

	public Angelic_barrage() {
		name="エンジェルバレッジ";
		rank="neutral";
		cost=3;
	}

	@Override
	public void spellEffect(Leader turnLeader, Leader noTurnLeader,int choiceNumber)  {
		System.out.println(name+"を唱えた！");
		noTurnLeader.setLifeCount(noTurnLeader.getLifeCount()-1);  //effectAttackにしたいけどnoTurnLeader.getLifeCount()だからなぁ
		for(int i=0;i<noTurnLeader.getFieldList().size();i++) {

			//効果が全体処理なので潜伏に当たらないeffectAttackは使用しない
			if(!noTurnLeader.getFieldList().get(i).isLiza()) {  //リザの効果を受けてないなら
				noTurnLeader.getFieldList().get(i).setHp(noTurnLeader.getFieldList().get(i).getHp()-1);
			}

			if(noTurnLeader.getFieldList().get(i).getHp()<=0) {
				noTurnLeader.getFieldList().get(i).die(noTurnLeader,turnLeader,i);
				i--;  //一個ずれたから。
			}
		}
	}
}
