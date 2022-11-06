package main;

import base.Card;
import neutral.GrimnirWarCyclone;
import vampire.Baphomet;
import vampire.DiabolicDrain;

public class Show {

	public static void execute(Leader Leader1, Leader Leader2) {

		enhance(Leader1, Leader2);
		revenge(Leader1, Leader2);

		System.out.println("■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");
		System.out.println(sq(!Leader1.isMyturn()) + Leader2.getRank() + "\t\t\t体力：" + Leader2.getLifeCount()
				+ "\t\t\t\t" + rankEffect(Leader2.getRank(), Leader2)); //デバッグ用→cward外した。
		System.out.println("PP(" + Leader2.getPpRest() + "／" + Leader2.getPpMax() + ")\t\t\t\t\t\t\t\t"
				+ Leader2.getTurnCount() + "ターン目");
		System.out.println("EP：" + epInfoEnemy(Leader1, Leader2, Leader2.getEpRest(), Leader2.getTurnCount())
				+ "\t\t\t\t\t\t\t\t墓地×" + Leader2.getCemetery());
		System.out.println(costs(Leader2.getHandList(), Leader2.getHandCount()));
		System.out.println(handcards(Leader2.getHandList(), Leader2.getHandCount()));
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");
		System.out.println();
		System.out.println(fieldcards(Leader2.getFieldList(), Leader2.getFieldCount()));
		System.out.println(fieldInfo(Leader2.getFieldList(), Leader2.getFieldCount()));
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");

		System.out.println();

		System.out.println("――\t\t\t\t\t\t――");
		System.out.println();
		System.out.println(fieldcards(Leader1.getFieldList(), Leader1.getFieldCount())); //とりあえずpHand[2]のものを→改良済
		System.out.println(fieldInfo(Leader1.getFieldList(), Leader1.getFieldCount())); //ここもおかしい。→改良。
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");

		System.out.println();

		System.out.println(costs(Leader1.getHandList(), Leader1.getHandCount()));
		System.out.println(handcards(Leader1.getHandList(), Leader1.getHandCount()));
		System.out.println("       " + handNums(Leader1.getHandList(), Leader1.getHandCount()));

		System.out.println();

		System.out.println(Leader1.getTurnCount() + "ターン目\t\t\t\t\t\t\t\t" + rankEffect(Leader1.getRank(), Leader1));
		System.out.println("墓地×" + Leader1.getCemetery() + "\t\t\t\t\t\t\t\tPP(" + Leader1.getPpRest() + "／"
				+ Leader1.getPpMax() + ")");
		System.out.println(sq(Leader1.isMyturn()) + Leader1.getRank() + "\t\t\t\t体力：" + Leader1.getLifeCount()
				+ "\t\t\tEP：" + epInfomyself(Leader1, Leader2, Leader1.getEpRest(), Leader1.getTurnCount())); //デバッグ用→pward外した。
		System.out.println("-----------------------------------");

	}

	public static void enhance(Leader turnLeader, Leader noTurnLeader) {

		for (int i = 0; i < turnLeader.getHandCount(); i++) {
			if (turnLeader.getHandList()[i] instanceof Baphomet && turnLeader.getPpRest() >= 5) { //バフォメット
				turnLeader.getHandList()[i].setCost(5);
				turnLeader.getHandList()[i].setEnhance(true);
			} else if (turnLeader.getHandList()[i] instanceof Baphomet && turnLeader.getPpRest() < 5) {
				turnLeader.getHandList()[i].setCost(2);
				turnLeader.getHandList()[i].setEnhance(false);
			}

			if (turnLeader.getHandList()[i] instanceof GrimnirWarCyclone && turnLeader.getPpRest() == 10) { //グリームニル
				turnLeader.getHandList()[i].setCost(10);
				turnLeader.getHandList()[i].setEnhance(true);
			} else if (turnLeader.getHandList()[i] instanceof GrimnirWarCyclone && turnLeader.getPpRest() < 10) {
				turnLeader.getHandList()[i].setCost(3);
				turnLeader.getHandList()[i].setEnhance(false);
			}
		}
	}

	public static void revenge(Leader Leader1, Leader Leader2) { //復讐状態に自分のターンか否かは関係ない
		//プレイヤー側
		if (Leader1.getRank() != null && Leader1.getRank().equals("ヴァンパイア") && Leader1.getLifeCount() <= 10) { //自分がヴァンパイアで体力が10以下なら～
			Leader1.setRevenge(true); //復讐状態
		}
		//コンピュータ側
		if (Leader2.getRank() != null && Leader2.getRank().equals("ヴァンパイア") && Leader2.getLifeCount() <= 10) {
			Leader2.setRevenge(true);
		}

		revengeExec(Leader1, Leader2);
	}

	public static void revengeExec(Leader Leader1, Leader Leader2) { //復讐時効果
		//プレイヤー側
		for (int i = 0; i < Leader1.getHandCount(); i++) { //ディアボリックドレイン
			if (Leader1.getHandList()[i] instanceof DiabolicDrain && Leader1.isRevenge()) { //復讐状態なら
				Leader1.getHandList()[i].setCost(1); //このカードのコストを-4する。復讐状態ならshowのたびに-4されてしまうので1。
			} else if (Leader1.getHandList()[i] instanceof DiabolicDrain && !Leader1.isRevenge()) { //復讐状態でないなら
				Leader1.getHandList()[i].setCost(5); //コストは5
			}
		}
		//コンピュータ側
		for (int i = 0; i < Leader2.getFieldCount(); i++) {
			if (Leader2.getHandList()[i] instanceof DiabolicDrain && Leader2.isRevenge()) {
				Leader2.getHandList()[i].setCost(1);
			} else if (Leader2.getHandList()[i] instanceof DiabolicDrain && !Leader2.isRevenge()) {
				Leader2.getHandList()[i].setCost(5);
			}
		}
	}

	static String sq(boolean turn) {
		if (turn) {
			return "◇";
		} else {
			return "";
		}
	}

	//int nのやつはshowにそのまま置くもの。int aのやつはそれの付属品。

	static String costsleft(Card[] c, int a) {

		if (c[a].isEnhance()) {
			return "≪";
		}
		return "<";
	}

	static String costsright(Card[] c, int a) {

		if (c[a].isEnhance()) {
			return "≫";
		}
		return ">";
	}

	static String costs(Card[] c, int n) { //手札のコストと空白をかえす。まさかの多態性。
		String s = "";
		for (int i = 0; i < n; i++) {
			s += costsleft(c, i) + c[i].getCost() + costsright(c, i) + "\t\t\t";
		}
		return s;
	}

	static String handcards(Card[] c, int n) { //手札の名前と空白をかえす
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "<" + c[i].getName() + c[i].getDeckn() + ">\t"; //getDecknはデバッグ用。
		}
		return s;
	}

	static String handNums(Card[] hand, int n) { //手札番号と空白をかえす。一応今のところプレイヤーのみ表示。
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "(" + hand[i].getHandN() + ")\t\t\t";
		}
		return s;
	}

	static String cardsleft(Card[] c, int a) {

		if (c[a].isEv()) {
			return "≪";
		} else if (c[a].isWard()) {
			return "[[";
		} else if (c[a].isAmbush()) {
			return "～";
		} else {
			return "<";
		}
	}

	static String cardsright(Card[] c, int a) {

		if (c[a].isEv()) {
			return "≫";
		} else if (c[a].isWard()) {
			return "]]";
		} else if (c[a].isAmbush()) {
			return "～";
		} else {
			return ">";
		}
	}

	static String fieldcards(Card[] c, int n) { //フィールドの名前と空白をかえす
		String s = "";
		for (int i = 0; i < n; i++) {
			s += cardsleft(c, i) + LizaEffect(c, i) + c[i].getName() + c[i].getDeckn() + cardsright(c, i) + "\t"; //getDecknはデバッグ用。リザの効果が付与されてたら☆マーク。
		}
		return s;
	}

	static String fieldNums(Card[] c, int a) {
		String s = "";

		s = "(" + c[a].getfN() + ")"; //かっこも組み込みました。Stringになってくれる。

		if (!c[a].canAttack() || c[a].isChain()) { //もし攻撃不可能かつ突進でない、または鎖状態ならなら●をかえす。
			s = "●";

		}
		return s;
	}

	static String fieldInfo(Card[] c, int n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "     <" + c[i].getAp() + ">  " + fieldNums(c, i) + "  <" + c[i].getHp() + ">\t\t";

		}
		return s;
	}

	static String LizaEffect(Card[] c, int a) {

		if (c[a].isLiza()) {
			return "☆";
		} else {
			return "";
		}

	}

	static String epInfomyself(Leader Leader1, Leader Leader2, int Ep, int turn) { //p:Leaderか否か Ep:どちらのEpか
		String s1 = "◆";
		String s2 = "◆";
		String s3 = "◆";
		if (Leader1.isSenkou()) {
			s3 = "";
		}
		if ((Leader1.isSenkou() && turn > 4) || (!Leader1.isSenkou() && turn > 3)) {
			if (Ep > 0) {
				s1 = "◇";
			}
			if (Ep > 1) {
				s2 = "◇";
			}
			if (Ep > 2) {
				s3 = "◇";
			}
		}
		String s = s1 + s2 + s3;
		return s;

	}

	static String epInfoEnemy(Leader Leader1, Leader Leader2, int Ep, int turn) { //p:Leaderか否か Ep:どちらのEpか
		String s1 = "◆";
		String s2 = "◆";
		String s3 = "◆";
		if (!Leader1.isSenkou()) {
			s3 = "";
		}
		if ((!Leader1.isSenkou() && turn > 4) || (Leader1.isSenkou() && turn > 3)) {
			if (Ep > 0) {
				s1 = "◇";
			}
			if (Ep > 1) {
				s2 = "◇";
			}
			if (Ep > 2) {
				s3 = "◇";
			}
		}
		String s = s1 + s2 + s3;
		return s;

	}

	static String rankEffect(String s, Leader Leader) { //s:myRank(String) p:プレイヤーtrue/コンピュータfalse
		String t = ""; //returnする

		if (s != null && s.equals("ヴァンパイア")) {
			String r = "\t"; //復讐か否か。
			if (Leader.isRevenge())
				r = "復讐";
			t = "[" + r + "]";
		} else if (s.equals("エルフ")) {
			t = "プレイ数×" + Leader.getPlayCount();
		}
		return t;

	}

}
