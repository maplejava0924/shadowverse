package neutral;

import base.Follower;
import main.Leader;;

public class ActressFeria extends Follower {

	public ActressFeria() {
		name = "歌劇姫・フェリア";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 2;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "歌劇姫・フェリア";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 2;
		canAttack = false;
	}

	@Override
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {
		System.out.println(name + "の進化時能力発動！");
		for (int i = 0; i < turnLeader.getFieldCount(); i++) {
			String s = turnLeader.getFieldList()[i].getRank();
			if (s != null && s.equals("neutral") && evolveNumber != i
					&& turnLeader.getFieldList()[i].getClas() == 'f') { //自分の他のニュートラルフォロワー
				turnLeader.getFieldList()[i].setAp(turnLeader.getFieldList()[i].getAp() + 1);
				turnLeader.getFieldList()[i].setHp(turnLeader.getFieldList()[i].getHp() + 1);
			}
		}
	}
}
