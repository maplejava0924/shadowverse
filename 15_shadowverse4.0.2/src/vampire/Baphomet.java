package vampire;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Baphomet extends Follower {

	static int n;
	static int[] kuji;

	public Baphomet() {
		name = "バフォメット";
		rank = "vampire";
		ap = 2;
		hp = 1;
		cost = 2;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "バフォメット";
		rank = "vampire";
		ap = 2;
		hp = 1;
		cost = 2;
		canAttack = false;
		attacked = false;
	}

	public int kuji() { //0～pmax-1のなかでランダムに1つ数字を重複なく取り出す。

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
		boolean b = false;
		while (true) {
			int randomNumber = kuji();
			Card choiceCard = turnLeader.getDeckList().get(randomNumber);
			if (choiceCard.getAp() >= 5 && choiceCard.getRank().equals("vampire") && choiceCard.getClas() == 'f') {
				
				turnLeader.getDeckList().remove(randomNumber);

				List<Card> cardList = new ArrayList<Card>();

				cardList.add(choiceCard);

				turnLeader.handSet(turnLeader, noTurnLeader, cardList);

				b = true;
				break;
			}
			if (n == 0)
				break;
		}
		if (b && cost == 5) { //エンハンス効果
			System.out.println(name + "のエンハンス効果発動！");
			if (turnLeader.getHandList().get(turnLeader.getHandList().size()).getCost() - 3 >= 0) {
				turnLeader.getHandList().get(turnLeader.getHandList().size())
						.setCost(turnLeader.getHandList().get(turnLeader.getHandList().size()).getCost() - 3);
			} else { //ここelse if .pHand[.pn-1].getCost()-3<0てしなきゃいけないの？
				turnLeader.getHandList().get(turnLeader.getHandList().size()).setCost(0);
			}
		}

	}
}
