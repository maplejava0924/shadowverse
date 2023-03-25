package vampire;

import base.Follower;
import main.Leader;

public class Emeralda_demonic_officer extends Follower {

	public Emeralda_demonic_officer() {
		name = "デモンオフィサー・エメラダ";
		rank = "vampire";
		ap = 4;
		hp = 5;
		cost = 7;
		evUpap = 2;
		evUphp = 2;
		needPlayChoiceCount = 1;
	}

	@Override
	public void reset() {
		name = "デモンオフィサー・エメラダ";
		rank = "vampire";
		ap = 4;
		hp = 5;
		cost = 7;
		storm = false;
		needPlayChoiceCount = 1;
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		System.out.println(name + "のファンファーレ発動！");
		if (choiceNumber != 99) {
			noTurnLeader.getFieldList().get(choiceNumber).die(noTurnLeader, turnLeader, choiceNumber); // 相手のフォロワーまたはアミュレットを破壊
		}
		if (turnLeader.isRevenge()) {
			storm = true; // 復讐状態なら疾走
			canAttack = true;
		}
	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;

		if (choiceNumber > noTurnLeader.getFieldList().size() - 1) {
			System.out.println("フィールドのカードは" + noTurnLeader.getFieldList().size() + "枚です。もう一度入力して下さい。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).isAmbush()) { // 攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}

		return result;
	}

}
