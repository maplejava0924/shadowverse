package elf;

import base.Spell;
import main.Leader;

public class GlimmeringWings extends Spell {

	public GlimmeringWings() {
		name = "翅の輝き";
		rank = "elf";
		cost = 2;
	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) {
		System.out.println(name + "を唱えた！");
		playOperation(turnLeader, noTurnLeader, playNumber);
		if (turnLeader.getPlayCount() - 1 >= 2) { // このターン中このカードを含めず2枚以上プレイしていたのなら～
			if (turnLeader.getHandCount() <= 7) {
				for (int i = 0; i < 2; i++) {
					turnLeader.draw();
				}
			} else if (turnLeader.getHandCount() == 8) { // pnが8、つまり翅の輝きを唱えた時点で手札がマックスだったなら1枚燃える
				turnLeader.draw();
				//引くだけ引く。
				Leader.NextCard(turnLeader.getDeckList(), turnLeader.getDeckRest());
				turnLeader.setCemetery(turnLeader.getCemetery() + 1);
			}
		} else {
			turnLeader.draw();
		}
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);
	}
}
