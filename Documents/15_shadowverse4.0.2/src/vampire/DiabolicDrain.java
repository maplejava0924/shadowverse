package vampire;

import base.Spell;
import main.Leader;

public class DiabolicDrain extends Spell {

	public DiabolicDrain() {
		name = "ディアボリックドレイン";
		rank = "vampire";
		cost = 5;
		needPlayChoiceCount = 1;
	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) {
		System.out.println(name + "を唱えた！");

		playOperation(turnLeader, noTurnLeader, playNumber);
		effectAttack(noTurnLeader.getFieldList(), choiceNumber, 4); //4点与える
		if (noTurnLeader.getFieldList()[choiceNumber].getHp() <= 0) { //もし相手のHPが０以下になったら～
			noTurnLeader.getFieldList()[choiceNumber].die(noTurnLeader, turnLeader, choiceNumber);
		}
		if (turnLeader.getLifeCount() <= 18) {
			turnLeader.setLifeCount(turnLeader.getLifeCount() + 2); //自分は2点回復
		} else if (turnLeader.getLifeCount() == 19) {
			turnLeader.setLifeCount(turnLeader.getLifeCount() + 1);
		} else {
			//0回復
		}
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);
	}

	@Override
	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {

		boolean result = true;

		//相手のフォロワーいないと詠唱できない。回復もしない。
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
		} else if (noTurnLeader.getFieldList()[choiceNumber].isAmbush()) { //攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}
		return result;
	}

}
