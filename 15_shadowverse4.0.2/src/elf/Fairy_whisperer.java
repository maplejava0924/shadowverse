package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Fairy_whisperer extends Follower {

	public Fairy_whisperer() {
		name = "フェアリーウィスパラー";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 2;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "フェアリーウィスパラー";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 2;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		System.out.println(name + "のファンファーレ発動！");
		List<Card> cardList = new ArrayList<Card>();
		
		cardList.add(new Fairy());
		cardList.add(new Fairy());
				
		turnLeader.handSet(turnLeader, noTurnLeader, cardList);

	}
}
