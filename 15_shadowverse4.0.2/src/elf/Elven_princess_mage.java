package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Elven_princess_mage extends Follower {

	public Elven_princess_mage() {
		name = "エルフプリンセスメイジ";
		rank = "elf";
		ap = 3;
		hp = 4;
		cost = 4;
		evUpap = 1;
		evUphp = 1;
	}

	@Override
	public void reset() {
		name = "エルフプリンセスメイジ";
		rank = "elf";
		ap = 3;
		hp = 4;
		cost = 4;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {
		System.out.println(name + "の進化時能力発動！");

		List<Card> cardList = new ArrayList<Card>();

		cardList.add(new Fairy());
		cardList.add(new Fairy());

		int addCount = turnLeader.handSet(turnLeader, noTurnLeader, cardList);

		//実際に加えられたカード数が2なら
		if (addCount == 2) {

			turnLeader.getHandList().get(turnLeader.getHandList().size() - 2).setCost(0);
			turnLeader.getHandList().get(turnLeader.getHandList().size() - 1).setCost(0);

		} else if (addCount == 1) { //この条件だと0以下とかも含まれるけど考慮はいらんだろう。
			turnLeader.getHandList().get(turnLeader.getHandList().size() - 1).setCost(0);
		}

	}
}
