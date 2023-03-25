package neutral;

import base.Follower;
import main.Leader;

public class Lyrial_celestial_archer extends Follower {

	public Lyrial_celestial_archer() {
		name = "天弓の天使・リリエル";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 2;
		evUpap = 2;
		evUphp = 2;
		needEvolveChoiceCount = 1;
	}

	@Override
	public void reset() {
		name = "天弓の天使・リリエル";
		rank = "neutral";
		ap = 2;
		hp = 2;
		cost = 2;
		canAttack = false;
		needEvolveChoiceCount = 1;
	}

	@Override
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber,int choiceNumber) {
		System.out.println(name + "の進化時能力発動！");
		if (choiceNumber == 0 || choiceNumber == 1 || choiceNumber == 2 || choiceNumber == 3 || choiceNumber == 4) {

			effectAttack(noTurnLeader.getFieldList(), choiceNumber, 1);
			if (noTurnLeader.getFieldList().get(choiceNumber).getHp() <= 0) { //もし相手のHPが０以下になったら～
				noTurnLeader.getFieldList().get(choiceNumber).die(noTurnLeader, turnLeader, choiceNumber);
			}
		} else if (choiceNumber == 5) {
			noTurnLeader.setLifeCount(noTurnLeader.getLifeCount() - 1);
		}
	}

	@Override
	public boolean canEvolveChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

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
