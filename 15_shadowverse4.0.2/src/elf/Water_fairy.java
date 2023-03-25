package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Water_fairy extends Follower {

	public Water_fairy() {
		name = "ウォーターフェアリー";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 1;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "ウォーターフェアリー";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 1;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void lastWard(Leader turnLeader, Leader noTurnLeader) {
		System.out.println("ウォーターフェアリーのラストワード発動！");
		List<Card> cardList = new ArrayList<Card>();
		
		cardList.add(new Fairy());
				
		turnLeader.handSet(turnLeader, noTurnLeader, cardList);

	}
}
