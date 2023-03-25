package neutral;

import base.Follower;
import main.Leader;

public class Goblin_leader extends Follower {

	public Goblin_leader() {
		name = "ゴブリンリーダー";
		rank = "neutral";
		ap = 1;
		hp = 2;
		cost = 3;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "ゴブリンリーダー";
		rank = "neutral";
		ap = 1;
		hp = 2;
		cost = 3;
		canAttack = false;
		attacked = false;
	}

	public void turnEndEffect(Leader turnLeader, Leader noTurnLeader) {

		System.out.println("ゴブリンリーダーの効果発動！");
		if (turnLeader.getFieldList().size() <= MAX_FIELD - 1) {
			//ゴブリンを召喚
			fieldSet(turnLeader, noTurnLeader, new Goblin());
		} else {
			//消滅
		}
	}

}
