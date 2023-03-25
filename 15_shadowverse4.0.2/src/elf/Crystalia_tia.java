package elf;

import base.Follower;
import main.Leader;

public class Crystalia_tia extends Follower {

	public Crystalia_tia() {
		name = "クリスタリアプリンセス・ティア";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 5;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "クリスタリアプリンセス・ティア";
		rank = "elf";
		ap = 1;
		hp = 1;
		cost = 5;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {
		System.out.println(name + "のファンファーレ発動！");

		if (turnLeader.getFieldList().size() <= MAX_FIELD - 1) { //フィールドのカード数が4枚以下なら～
			//クリスタリアイブを召喚
			fieldSet(turnLeader, noTurnLeader, new Crystalia_eve());

			choiceNumber = 0;
			//fanfareのchoiceNumberと同じに認識されてるのがなんか怖い。
			if (turnLeader.getPlayCount() - 1 >= 2) { //このターン中このカードを含めず2枚以上プレイしていたのなら～
				//無料進化
				turnLeader.getFieldList().get(turnLeader.getFieldList().size() - 1).evolve(turnLeader, noTurnLeader,
						turnLeader.getFieldList().size() - 1, choiceNumber,true);
				turnLeader.getFieldList().get(turnLeader.getFieldList().size() - 1).setWard(true);
				turnLeader.setWardCount(turnLeader.getWardCount() + 1); //これいるよね
			}
		} else { //5枚なら～
			//消滅？
		}

	}
}
