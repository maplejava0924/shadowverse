package elf;

import base.Spell;
import main.Leader;

public class NaturesGuidance extends Spell {

	public NaturesGuidance() {
		name = "自然の導き";
		rank = "elf";
		cost = 1;
		needPlayChoiceCount = 1;

	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) {

		System.out.println(name + "を唱えた！");

		playOperation(turnLeader, noTurnLeader, playNumber);
		if (turnLeader.getFieldList()[choiceNumber].isWard())
			turnLeader.setWardCount(turnLeader.getWardCount() - 1); // 忘れずに！
		turnLeader.getFieldList()[choiceNumber].reset(); // リセットしないと手札戻して出した時にバフとかが引き継がれちゃう。
		turnLeader.handSet(turnLeader.getFieldList()[choiceNumber]);
		for (int i = 0; i < turnLeader.getFieldCount() - choiceNumber - 1; i++) { // フィールドをずらすのを忘れずに。
			turnLeader.getFieldList()[choiceNumber + i] = turnLeader.getFieldList()[choiceNumber + i + 1];
			turnLeader.getFieldList()[choiceNumber + i]
					.setfN(turnLeader.getFieldList()[choiceNumber + i].getfN() - 1);
		}
		turnLeader.setFieldCount(turnLeader.getFieldCount() - 1);

		if (turnLeader.getHandCount() <= 8) {
			turnLeader.draw();
		} else if (turnLeader.getHandCount() == 9) {
			Leader.NextCard(turnLeader.getDeckList(), turnLeader.getDeckRest());
			turnLeader.setCemetery(turnLeader.getCemetery() + 1);
		}

		turnLeader.setCemetery(turnLeader.getCemetery() + 1);
	}

	@Override
	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {

		boolean result = true;

		// 自分のフォロワーいないと詠唱できない。
		if (turnLeader.getFieldCount() <= 0) {
			System.out.println("自分のフィールドに選択できるカードがありません。");
			result = false;
		}

		return result;

	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;
		if (choiceNumber > turnLeader.getFieldCount() - 1) {
			System.out.println("自分のフィールド枚数は" + turnLeader.getFieldCount() + "枚です。もう一度入力して下さい。");
			result = false;
		}
		return result;
	}

}
