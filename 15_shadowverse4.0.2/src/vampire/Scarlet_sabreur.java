package vampire;

import base.Follower;
import main.Leader;

public class Scarlet_sabreur extends Follower {

	public Scarlet_sabreur() {
		name = "緋色の剣士";
		rank = "vampire";
		ap = 2;
		hp = 4;
		cost = 5;
		evUpap = 2;
		evUphp = 2;
		bane = true; //必殺
		needPlayChoiceCount = 1;

	}

	@Override
	public void reset() {
		name = "緋色の剣士";
		rank = "vampire";
		ap = 2;
		hp = 4;
		cost = 5;
		canAttack = false;
		bane = true; //必殺
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		System.out.println(name + "のファンファーレ発動！");

		if (choiceNumber == 0 || choiceNumber == 1 || choiceNumber == 2 || choiceNumber == 3 || choiceNumber == 4) {
			effectAttack(noTurnLeader.getFieldList(), choiceNumber, 2); //相手に2点与える
			if (noTurnLeader.getFieldList().get(choiceNumber).getHp() <= 0) { //もし相手のHPが０以下になったら～
				noTurnLeader.getFieldList().get(choiceNumber).die(noTurnLeader, turnLeader, choiceNumber);
			}
		} else if (choiceNumber == 5) {
			noTurnLeader.setLifeCount(noTurnLeader.getLifeCount() - 2); //相手に2点
		}

		if (turnLeader.getLifeCount() <= 18) {
			turnLeader.setLifeCount(turnLeader.getLifeCount() + 2); //自分は2点回復
		} else if (turnLeader.getLifeCount() == 19) {
			turnLeader.setLifeCount(turnLeader.getLifeCount() + 1);
		} else {
			//0回復
		}
	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;

		if (noTurnLeader.getFieldList().size() == 0) {
			System.out.println("相手のフィールドにフォロワーがいません。");
			result = false;
		} else if (choiceNumber > noTurnLeader.getFieldList().size() - 1) {
			System.out.println("フィールドのカードは" + noTurnLeader.getFieldList().size() + "枚です。もう一度入力して下さい。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).isAmbush()) { //攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}

		return result;
	}

}
