package elf;

import base.Spell;
import main.Leader;

public class SylvanJustice extends Spell {

	public SylvanJustice() {
		name = "森荒らしへの報い";
		rank = "elf";
		cost = 2;
		needPlayChoiceCount = 1;
	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) {

		System.out.println(name + "を唱えた！");

		playOperation(turnLeader, noTurnLeader, playNumber);
		effectAttack(noTurnLeader.getFieldList(), choiceNumber, 2); // 2点与える
		if (noTurnLeader.getFieldList()[choiceNumber].getHp() <= 0) { // もし相手のHPが０以下になったら～
			noTurnLeader.getFieldList()[choiceNumber].die(noTurnLeader, turnLeader, choiceNumber);
		}
		
		// 手札にフェアリーを加える。手札制限は意識しなくても、1枚なら森荒らしと変わるだけだから大丈夫でしょう。
		turnLeader.handSet(new Fairy());
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);

	}

	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {

		boolean result = true;

		if (noTurnLeader.getFieldCount() <= 0) {

			System.out.println("相手のフォロワーがいません");
			result = false;

		}

		return result;

	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;
		if (choiceNumber > noTurnLeader.getFieldCount() - 1) {
			System.out.println("相手フィールド枚数は" + noTurnLeader.getFieldCount() + "枚です。もう一度入力して下さい。");
			result = false;
		} else if (noTurnLeader.getFieldList()[choiceNumber].getClas() != 'f') {
			System.out.println("フォロワーを選択してください。");
			result = false;
		} else if (noTurnLeader.getFieldList()[choiceNumber].isAmbush()) { // 攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}

		return result;
	}

}
