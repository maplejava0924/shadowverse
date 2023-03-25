package neutral;

import base.Follower;
import main.Leader;

public class Grimnir_war_cyclone extends Follower {

	public Grimnir_war_cyclone() {
		name = "風の軍神・グリームニル";
		rank = "neutral";
		ap = 2;
		hp = 3;
		cost = 3;
		ward = true;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "風の軍神・グリームニル";
		rank = "neutral";
		ap = 2;
		hp = 3;
		cost = 3;
		ward = true;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		if (cost == MAX_PP) { //エンハンス効果
			System.out.println(name + "のエンハンス効果発動！");
			for (int i = 0; i < 4; i++) {
				noTurnLeader.setLifeCount(noTurnLeader.getLifeCount() - 1);
				for (int k = 0; k < noTurnLeader.getFieldList().size(); k++) {
					//効果が全体処理なので潜伏に当たらないeffectAttackは使用しない
					if (!noTurnLeader.getFieldList().get(k).isLiza()) { //リザの効果を受けてないなら
						noTurnLeader.getFieldList().get(k).setHp(noTurnLeader.getFieldList().get(k).getHp() - 1);
					}
					if (noTurnLeader.getFieldList().get(k).getHp() <= 0) {
						noTurnLeader.getFieldList().get(k).die(noTurnLeader, turnLeader, k);
						k--; //一個ずれたから。
					}
				}
			}
		}
	}
}
