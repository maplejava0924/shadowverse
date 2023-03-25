package elf;

import base.Follower;
import main.Leader;

public class Elf_knight_cynthia extends Follower {

	public Elf_knight_cynthia() {
		name = "エルフナイト・シンシア";
		rank = "elf";
		ap = 5;
		hp = 6;
		cost = 6;
		evUpap = 1;
		evUphp = 1;
	}

	@Override
	public void reset() {
		name = "エルフナイト・シンシア";
		rank = "elf";
		ap = 5;
		hp = 6;
		cost = 6;
		canAttack = false;
		attacked = false;
	}

	@Override
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {
		System.out.println("シンシアの進化時効果発動！");
		if (turnLeader.getFieldList().size() <= MAX_FIELD - 2) {
			//２回繰り返す
			for (int i = 0; i < 2; i++) {
				//フェアリーを召喚
				fieldSet(turnLeader, noTurnLeader, new Fairy());
			}
		} else if (turnLeader.getFieldList().size() == MAX_FIELD - 1) {
			//フェアリーを召喚
			fieldSet(turnLeader, noTurnLeader, new Fairy());
			//1枚消滅
		} else {
			//2枚消滅
		}

	}

	public void attackCommonEffect(Leader turnLeader, Leader noTurnLeader, int n) {
		System.out.println("シンシアの攻撃時共通効果発動！１");
		//今攻撃しようとしているフォロワーのフィールド番号(fNは表示用なので-1が必要)とこのインスタンスのフィールド番号が異なっている時～(「自分の他のフォロワーが攻撃するとき、」)
		if (this.getfN() - 1 != n) {
			System.out.println("シンシアの攻撃時共通効果発動！２");
			turnLeader.getFieldList().get(n).setAp(turnLeader.getFieldList().get(n).getAp() + 1);
		}
	}
}
