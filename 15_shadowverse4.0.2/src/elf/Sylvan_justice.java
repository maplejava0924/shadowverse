package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Spell;
import main.Leader;

public class Sylvan_justice extends Spell {

	public Sylvan_justice() {
		name = "森荒らしへの報い";
		rank = "elf";
		cost = 2;
		needPlayChoiceCount = 1;
	}

	@Override
	public void spellEffect(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		System.out.println(name + "を唱えた！");

		effectAttack(noTurnLeader.getFieldList(), choiceNumber, 2); // 2点与える
		if (noTurnLeader.getFieldList().get(choiceNumber).getHp() <= 0) { // もし相手のHPが０以下になったら～
			noTurnLeader.getFieldList().get(choiceNumber).die(noTurnLeader, turnLeader, choiceNumber);
		}

		// 手札にフェアリーを加える。手札制限は意識しなくても、1枚なら森荒らしと変わるだけだから大丈夫でしょう。

		List<Card> cardList = new ArrayList<Card>();

		cardList.add(new Fairy());

		turnLeader.handSet(turnLeader, noTurnLeader, cardList);

	}

	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {

		boolean result = true;

		System.out.println("canPlay森荒らし：" + noTurnLeader.getFieldList().size());

		if (noTurnLeader.getFieldList().size() <= 0) {

			System.out.println("相手のフォロワーがいません");
			result = false;

		}

		return result;

	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;
		if (choiceNumber > noTurnLeader.getFieldList().size() - 1) {
			System.out.println("相手フィールド枚数は" + noTurnLeader.getFieldList().size() + "枚です。もう一度入力して下さい。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).getClas() != 'f') {
			System.out.println("フォロワーを選択してください。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).isAmbush()) { // 攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}

		return result;
	}

}
