package elf;

import base.Spell;
import main.Leader;

public class Glimmering_wings extends Spell {

	public Glimmering_wings() {
		name = "翅の輝き";
		rank = "elf";
		cost = 2;
	}

	@Override
	public void spellEffect(Leader turnLeader, Leader noTurnLeader,int choiceNumber)  {
		System.out.println(name + "を唱えた！");
		if (turnLeader.getPlayCount() - 1 >= 2) { // このターン中このカードを含めず2枚以上プレイしていたのなら～
			if (turnLeader.getHandList().size() <= MAX_HAND - 2) {
				for (int i = 0; i < 2; i++) {
					turnLeader.draw(turnLeader, noTurnLeader);
				}
			} else if (turnLeader.getHandList().size() == MAX_HAND - 1) { // pnが8、つまり翅の輝きを唱えた時点で手札がマックスだったなら1枚燃える
				turnLeader.draw(turnLeader, noTurnLeader);
				//引くだけ引く。
				Leader.NextCard(turnLeader.getDeckList());
				turnLeader.setCemetery(turnLeader.getCemetery() + 1);
			}
		} else {
			turnLeader.draw(turnLeader, noTurnLeader);
		}
		
	}
}
