package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Spell;
import main.Leader;

public class Fairy_circle extends Spell {

	public Fairy_circle() {
		name = "フェアリーサークル";
		rank = "elf";
		cost = 1;
	}

	@Override
	public void spellEffect(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		System.out.println(name + "を唱えた！");
		
		List<Card> cardList = new ArrayList<Card>();
		
		cardList.add(new Fairy());
		cardList.add(new Fairy());
				
		turnLeader.handSet(turnLeader, noTurnLeader, cardList);

	}

}
