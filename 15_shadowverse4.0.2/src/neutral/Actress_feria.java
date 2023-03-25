package neutral;

import base.Follower;
import main.Leader;;

public class Actress_feria extends Follower {

	public Actress_feria() {
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
		for (int i = 0; i < turnLeader.getFieldList().size(); i++) {
			String s = turnLeader.getFieldList().get(i).getRank();
			if (s != null && s.equals("neutral") && evolveNumber != i
					&& turnLeader.getFieldList().get(i).getClas() == 'f') { //自分の他のニュートラルフォロワー
				turnLeader.getFieldList().get(i).setAp(turnLeader.getFieldList().get(i).getAp() + 1);
				turnLeader.getFieldList().get(i).setHp(turnLeader.getFieldList().get(i).getHp() + 1);
			}
		}
	}
}
