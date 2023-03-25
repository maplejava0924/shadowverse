package base;

import main.Leader;

public class Follower extends Card {

	public Follower() {
		clas = 'f';
	}

	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) { // a:playする手札の配列要素
																									// p:プレイヤーorコンピュータ

		fieldSet(turnLeader, noTurnLeader, this);

		playOperation(turnLeader, noTurnLeader, playNumber);

		if (this.isWard()) {
			turnLeader.setWardCount(turnLeader.getWardCount() + 1); // 守護持ちなら++
		}

		// fanfare時効果
		this.fanfare(turnLeader, noTurnLeader, choiceNumber);

		// play時共通効果
		for (int i = 0; i < turnLeader.getFieldList().size(); i++) {
			turnLeader.getFieldList().get(i).playCommonEffect(turnLeader, noTurnLeader, this);
		}

	}

	// ファンファーレメソッド
	public void fanfare(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

	}

	@Override
	public void attack(Leader turnLeader, Leader noTurnLeader, int attackNumber, int attackSakiNumber) {

		// 攻撃時効果
		turnLeader.getFieldList().get(attackNumber).attackEffect(turnLeader, noTurnLeader);

		// 攻撃時共通効果
		for (int i = 0; i < turnLeader.getFieldList().size(); i++) {
			turnLeader.getFieldList().get(i).attackCommonEffect(turnLeader, noTurnLeader, attackNumber);
		}

		// フォロワー
		if (attackSakiNumber == 0 || attackSakiNumber == 1 || attackSakiNumber == 2 | attackSakiNumber == 3
				|| attackSakiNumber == 4) {

			// このあとn 対 n2となる。守護とかあるとこの辺変わるかなー。
			// 相手HP=相手HP-自分AP。交戦時・攻撃時になるとまた変わるよねー。
			noTurnLeader.getFieldList().get(attackSakiNumber)
					.setHp(noTurnLeader.getFieldList().get(attackSakiNumber).getHp()
							- turnLeader.getFieldList().get(attackNumber).getAp());
			turnLeader.getFieldList().get(attackNumber).setHp(turnLeader.getFieldList().get(attackNumber).getHp()
					- noTurnLeader.getFieldList().get(attackSakiNumber).getAp());

			Card pn = turnLeader.getFieldList().get(attackNumber),
					cn = noTurnLeader.getFieldList().get(attackSakiNumber);

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
			noTurnLeader
					.setLifeCount(noTurnLeader.getLifeCount() - turnLeader.getFieldList().get(attackNumber).getAp());
			System.out.println("リーダーに攻撃しました。");
			turnLeader.getFieldList().get(attackNumber).setCanAttack(false); // attackできない状態にする
			turnLeader.getFieldList().get(attackNumber).setAttacked(true); // attackした
			turnLeader.getFieldList().get(attackNumber).setRush(false); // 突進も不可に。
			turnLeader.getFieldList().get(attackNumber).setAmbush(false); // 一度攻撃したら潜伏は消える
		}

	}

	@Override
	public void die(Leader turnLeader, Leader noTurnLeader, int n) { // n:dieしたフォロワーのField配列要素

		turnLeader.getFieldList().get(n).lastWard(turnLeader, noTurnLeader);
		if (turnLeader.getFieldList().get(n).isWard()) {
			// 守護持ちなら--
			turnLeader.setWardCount(turnLeader.getWardCount() - 1);
		}
		// dieしたnの手札をリストから削除する。
		//Listの機能で勝手に間はつめられる。
		turnLeader.getFieldList().remove(n);

		for (int i = 0; i < turnLeader.getFieldList().size() - n - 1; i++) { // フィールドの配列をずらす。
			turnLeader.getFieldList().get(n + i).setfN(turnLeader.getFieldList().get(n + i).getfN() - 1); // フィールド番号をずらす。
		}
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);
	}

	@Override
	public void evolve(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber,
			boolean freeEvolved) {

		this.setAp(
				turnLeader.getFieldList().get(evolveNumber).getAp()
						+ turnLeader.getFieldList().get(evolveNumber).getEvUpap());
		this.setHp(
				turnLeader.getFieldList().get(evolveNumber).getHp()
						+ turnLeader.getFieldList().get(evolveNumber).getEvUphp());
		this.setEv(true);
		if (!this.isAttacked()) { // まだ攻撃してなければ～
			this.setCanAttack(true); // 攻撃可能にする。
		}
		this.setRush(true); // だけど突進ね。

		this.evolveEffect(turnLeader, noTurnLeader, evolveNumber, choiceNumber); // 進化時効果。
		
		//無料進化でないなら
		if (!freeEvolved) {
			// このターンの進化はもう終わり
			turnLeader.setEvolved(true);
			// 進化権一つ減少
			turnLeader.setEpRest(turnLeader.getEpRest() - 1); 
		}
		
	}

	// 進化時効果
	// evolveNumber:いま進化したフォロワーのフィールド番号
	public void evolveEffect(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {
	}

}
