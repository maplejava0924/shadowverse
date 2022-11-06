package base;

import main.Leader;

public class Follower extends Card {

	public Follower() {
		clas = 'f';
	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) { // a:playする手札の配列要素
																									// p:プレイヤーorコンピュータ

		turnLeader.fieldSet(this);

		playOperation(turnLeader, noTurnLeader, playNumber);

		if (this.isWard()) {
			turnLeader.setWardCount(turnLeader.getWardCount() + 1); // 守護持ちなら++
		}

		// play時効果
		this.playEffect(turnLeader, noTurnLeader);

		// fanfare時効果
		this.fanfare(turnLeader, noTurnLeader, choiceNumber);

		// play時共通効果
		for (int i = 0; i < turnLeader.getFieldCount(); i++) {
			turnLeader.getFieldList()[i].playCommonEffect(turnLeader, noTurnLeader, this);
		}

	}

	// ファンファーレメソッド
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

	}

	@Override
	public void attack(Leader turnLeader, Leader noTurnLeader, int attackNumber, int attackSakiNumber) {

		// 攻撃時効果
		turnLeader.getFieldList()[attackNumber].attackEffect(turnLeader, noTurnLeader);

		// 攻撃時共通効果
		for (int i = 0; i < turnLeader.getFieldCount(); i++) {
			turnLeader.getFieldList()[i].attackCommonEffect(turnLeader, noTurnLeader, attackNumber);
		}

		// フォロワー
		if (attackSakiNumber == 0 || attackSakiNumber == 1 || attackSakiNumber == 2 | attackSakiNumber == 3
				|| attackSakiNumber == 4) {

			// このあとn 対 n2となる。守護とかあるとこの辺変わるかなー。
			// 相手HP=相手HP-自分AP。交戦時・攻撃時になるとまた変わるよねー。
			noTurnLeader.getFieldList()[attackSakiNumber].setHp(noTurnLeader.getFieldList()[attackSakiNumber].getHp()
					- turnLeader.getFieldList()[attackNumber].getAp());
			turnLeader.getFieldList()[attackNumber].setHp(turnLeader.getFieldList()[attackNumber].getHp()
					- noTurnLeader.getFieldList()[attackSakiNumber].getAp());

			Card pn = turnLeader.getFieldList()[attackNumber], cn = noTurnLeader.getFieldList()[attackSakiNumber];

			if (pn.getHp() <= 0 || cn.isBane()) { // もしプレイヤーのHPが０以下または相手のフォロワーが必殺を持っていたのなら～
				pn.die(turnLeader, noTurnLeader, attackNumber);
			} else { // まだ生きてたら～
				pn.setCanAttack(false); // attackできない状態にする
				pn.setAttacked(true); // attackした
				pn.setRush(false);
				pn.setAmbush(false); // 一度攻撃したら潜伏は消える
			}
			if (cn.getHp() <= 0 || pn.isBane()) { // もしコンピュータのHPが０以下になったまたは相手のフォロワーが必殺をもっていたのなら～
				cn.die(noTurnLeader, turnLeader, attackSakiNumber);
			}

		} else if (attackSakiNumber == 5) { // リーダー
			noTurnLeader.setLifeCount(noTurnLeader.getLifeCount() - turnLeader.getFieldList()[attackNumber].getAp());
			System.out.println("リーダーに攻撃しました。");
			turnLeader.getFieldList()[attackNumber].setCanAttack(false); // attackできない状態にする
			turnLeader.getFieldList()[attackNumber].setAttacked(true); // attackした
			turnLeader.getFieldList()[attackNumber].setRush(false); // 突進も不可に。
			turnLeader.getFieldList()[attackNumber].setAmbush(false); // 一度攻撃したら潜伏は消える
		}

	}

	@Override
	public void die(Leader turnLeader, Leader noTurnLeader, int n) { // n:dieしたフォロワーのField配列要素

		turnLeader.getFieldList()[n].lastWard(turnLeader, noTurnLeader);
		if (turnLeader.getFieldList()[n].isWard())
			turnLeader.setWardCount(turnLeader.getWardCount() - 1); // 守護持ちなら--
		for (int i = 0; i < turnLeader.getFieldCount() - n - 1; i++) { // フィールドの配列をずらす。
			turnLeader.getFieldList()[n + i] = turnLeader.getFieldList()[n + i + 1];
			turnLeader.getFieldList()[n + i].setfN(turnLeader.getFieldList()[n + i].getfN() - 1); // フィールド番号をずらす。
		}
		turnLeader.setFieldCount(turnLeader.getFieldCount() - 1);
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);
	}

	@Override
	public void evolve(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {

		this.setAp(
				turnLeader.getFieldList()[evolveNumber].getAp() + turnLeader.getFieldList()[evolveNumber].getEvUpap());
		this.setHp(
				turnLeader.getFieldList()[evolveNumber].getHp() + turnLeader.getFieldList()[evolveNumber].getEvUphp());
		this.setEv(true);
		if (!this.isAttacked()) { // まだ攻撃してなければ～
			this.setCanAttack(true); // 攻撃可能にする。
		}
		this.setRush(true); // だけど突進ね。

		this.evolveEffect(turnLeader, noTurnLeader, evolveNumber, choiceNumber); // 進化時効果。
		turnLeader.setEvolved(true); // このターンの進化はもう終わり
		turnLeader.setEpRest(turnLeader.getEpRest() - 1); // 進化権一つ減少
	}

	// 進化時効果
	// evolveNumber:いま進化したフォロワーのフィールド番号
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {
	}

}
