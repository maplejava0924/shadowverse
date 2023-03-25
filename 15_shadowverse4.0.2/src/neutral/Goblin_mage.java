package neutral;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Goblin_mage extends Follower {

	static int n;
	static int[] kuji;

	public Goblin_mage() {
		name = "ミニゴブリンメイジ";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 3;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "ミニゴブリンメイジ";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 3;
		canAttack = false;
		attacked = false;
	}

	public int kuji() {
		int a = (int) (Math.random() * n);
		int t = kuji[a];
		kuji[a] = kuji[n - 1];
		n--;
		return t;
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		System.out.println(name + "のファンファーレ発動！");
		n = turnLeader.getDeckList().size();
		kuji = new int[n];
		for (int i = 0; i < n; i++) {
			kuji[i] = i;
		}
		while (true) {
			int a = kuji();
			Card c = turnLeader.getDeckList().get(a);
			if (c.getCost() == 2) {
				c.setDeckn(a);
				turnLeader.getDeckList().remove(a);
				List<Card> cardList = new ArrayList<Card>();

				cardList.add(c);

				turnLeader.handSet(turnLeader, noTurnLeader, cardList);

				break;
			}
		}
	}
}
