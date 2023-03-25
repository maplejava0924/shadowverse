package main;

import java.util.List;

import base.Card;
import neutral.Grimnir_war_cyclone;
import vampire.Baphomet;
import vampire.Diabolic_drain;

public class Show {

	public static void execute(Leader Leader1, Leader Leader2) {

		enhance(Leader1, Leader2);
		revenge(Leader1, Leader2);

		System.out.println("■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□■□");
		System.out.println(sq(!Leader1.isMyturn()) + Leader2.getRank() + "\t\t\t体力：" + Leader2.getLifeCount()
				+ "\t\t\t\t" + rankEffect(Leader2.getRank(), Leader2)); //デバッグ用→cward外した。
		System.out.println("PP(" + Leader2.getPpRest() + "／" + Leader2.getPpMax() + ")\t\t\t\t\t\t\t\t"
				+ Leader2.getTurnCount() + "ターン目");
		System.out.println("EP：" + epInfo(Leader2, Leader1)
				+ "\t\t\t\t\t\t\t\t墓地×" + Leader2.getCemetery());
		System.out.println(costs(Leader2.getHandList(), Leader2.getHandList().size()));
		System.out.println(handcards(Leader2.getHandList(), Leader2.getHandList().size()));
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");
		System.out.println();
		System.out.println(fieldcards(Leader2.getFieldList(), Leader2.getFieldList().size()));
		System.out.println(fieldInfo(Leader2.getFieldList(), Leader2.getFieldList().size()));
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");

		System.out.println();

		System.out.println("――\t\t\t\t\t\t――");
		System.out.println();
		System.out.println(fieldcards(Leader1.getFieldList(), Leader1.getFieldList().size())); //とりあえずpHand[2]のものを→改良済
		System.out.println(fieldInfo(Leader1.getFieldList(), Leader1.getFieldList().size())); //ここもおかしい。→改良。
		System.out.println();
		System.out.println("――\t\t\t\t\t\t――");

		System.out.println();

		System.out.println(costs(Leader1.getHandList(), Leader1.getHandList().size()));
		System.out.println(handcards(Leader1.getHandList(), Leader1.getHandList().size()));
		System.out.println("       ");

		System.out.println();

		System.out.println(Leader1.getTurnCount() + "ターン目\t\t\t\t\t\t\t\t" + rankEffect(Leader1.getRank(), Leader1));
		System.out.println("墓地×" + Leader1.getCemetery() + "\t\t\t\t\t\t\t\tPP(" + Leader1.getPpRest() + "／"
				+ Leader1.getPpMax() + ")");
		System.out.println(sq(Leader1.isMyturn()) + Leader1.getRank() + "\t\t\t\t体力：" + Leader1.getLifeCount()
				+ "\t\t\tEP：" + epInfo(Leader1, Leader2)); //デバッグ用→pward外した。
		System.out.println("-----------------------------------");


	}

	public static void enhance(Leader turnLeader, Leader noTurnLeader) {

		for (Card handCard : turnLeader.getHandList()) {
			if (handCard instanceof Baphomet && turnLeader.getPpRest() >= 5) { //バフォメット
				handCard.setCost(5);
				handCard.setEnhance(true);
			} else if (handCard instanceof Baphomet && turnLeader.getPpRest() < 5) {
				handCard.setCost(2);
				handCard.setEnhance(false);
			}

			if (handCard instanceof Grimnir_war_cyclone && turnLeader.getPpRest() == Card.MAX_PP) { //グリームニル
				handCard.setCost(Card.MAX_PP);
				handCard.setEnhance(true);
			} else if (handCard instanceof Grimnir_war_cyclone && turnLeader.getPpRest() < Card.MAX_PP) {
				handCard.setCost(3);
				handCard.setEnhance(false);
			}
		}
	}

	public static void revenge(Leader Leader1, Leader Leader2) { //復讐状態に自分のターンか否かは関係ない
		//プレイヤー側
		if (Leader1.getRank() != null && Leader1.getRank().equals("ヴァンパイア") && Leader1.getLifeCount() <= Card.REVENGE_LINE) { //自分がヴァンパイアで体力が10以下なら～
			Leader1.setRevenge(true); //復讐状態
		}
		//コンピュータ側
		if (Leader2.getRank() != null && Leader2.getRank().equals("ヴァンパイア") && Leader2.getLifeCount() <= Card.REVENGE_LINE) {
			Leader2.setRevenge(true);
		}

		revengeExec(Leader1, Leader2);
	}

	public static void revengeExec(Leader Leader1, Leader Leader2) { //復讐時効果
		//プレイヤー側
		for (Card handCard : Leader1.getHandList()) { //ディアボリックドレイン
			if (handCard instanceof Diabolic_drain && Leader1.isRevenge()) { //復讐状態なら
				handCard.setCost(1); //このカードのコストを-4する。復讐状態ならshowのたびに-4されてしまうので1。
			} else if (handCard instanceof Diabolic_drain && !Leader1.isRevenge()) { //復讐状態でないなら
				handCard.setCost(5); //コストは5
			}
		}
		//コンピュータ側
		for (Card handCard : Leader2.getHandList()) {
			if (handCard instanceof Diabolic_drain && Leader2.isRevenge()) {
				handCard.setCost(1);
			} else if (handCard instanceof Diabolic_drain && !Leader2.isRevenge()) {
				handCard.setCost(5);
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

	static String costsleft(List<Card> c, int a) {

		if (c.get(a).isEnhance()) {
			return "≪";
		}
		return "<";
	}

	static String costsright(List<Card> c, int a) {

		if (c.get(a).isEnhance()) {
			return "≫";
		}
		return ">";
	}

	static String costs(List<Card> c, int n) { //手札のコストと空白をかえす。まさかの多態性。
		String s = "";
		for (int i = 0; i < n; i++) {
			s += costsleft(c, i) + c.get(i).getCost() + costsright(c, i) + "\t\t\t";
		}
		return s;
	}

	static String handcards(List<Card> c, int n) { //手札の名前と空白をかえす
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "<" + c.get(i).getName() + c.get(i).getDeckn() + ">\t"; //getDecknはデバッグ用。
		}
		return s;
	}


	static String cardsleft(List<Card> c, int a) {

		if (c.get(a).isEv()) {
			return "≪";
		} else if (c.get(a).isWard()) {
			return "[[";
		} else if (c.get(a).isAmbush()) {
			return "～";
		} else {
			return "<";
		}
	}

	static String cardsright(List<Card> c, int a) {

		if (c.get(a).isEv()) {
			return "≫";
		} else if (c.get(a).isWard()) {
			return "]]";
		} else if (c.get(a).isAmbush()) {
			return "～";
		} else {
			return ">";
		}
	}

	static String fieldcards(List<Card> c, int n) { //フィールドの名前と空白をかえす
		String s = "";
		for (int i = 0; i < n; i++) {
			s += cardsleft(c, i) + LizaEffect(c, i) + c.get(i).getName() + c.get(i).getDeckn() + cardsright(c, i) + "\t"; //getDecknはデバッグ用。リザの効果が付与されてたら☆マーク。
		}
		return s;
	}

	static String fieldNums(List<Card> c, int a) {
		String s = "";

		s = "(" + c.get(a).getfN() + ")"; //かっこも組み込みました。Stringになってくれる。

		if (!c.get(a).canAttack() || c.get(a).isChain()) { //もし攻撃不可能かつ突進でない、または鎖状態ならなら●をかえす。
			s = "●";

		}
		return s;
	}

	static String fieldInfo(List<Card> c, int n) {
		String s = "";
		for (int i = 0; i < n; i++) {
			s += "     <" + c.get(i).getAp() + ">  " + fieldNums(c, i) + "  <" + c.get(i).getHp() + ">\t\t";

		}
		return s;
	}

	static String LizaEffect(List<Card> c, int a) {

		if (c.get(a).isLiza()) {
			return "☆";
		} else {
			return "";
		}

	}

	static String epInfo(Leader Leader1, Leader Leader2) { //p:Leaderか否か Ep:どちらのEpか
		String s1 = "◆";
		String s2 = "◆";
		String s3 = "◆";
		if (Leader1.isSenkou()) {
			s3 = "";
		}
		if ((Leader1.isSenkou() && Leader1.getTurnCount() > 4) || (!Leader1.isSenkou() && Leader1.getTurnCount() > 3)) {
			if (Leader1.getEpRest() > 0) {
				s1 = "◇";
			}
			if (Leader1.getEpRest() > 1) {
				s2 = "◇";
			}
			if (Leader1.getEpRest() > 2) {
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
