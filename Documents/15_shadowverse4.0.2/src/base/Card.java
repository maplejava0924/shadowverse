package base;

import java.util.ArrayList;
import java.util.Objects;

import atlib.Input;
import main.Leader;
import main.TurnEndAddEffect;

public class Card implements Cloneable {

	protected String name; // そのカードの名前。f,s,a

	protected String rank; // そのカードのニュートラル等のクラスのこと。 f,s,a

	protected char clas; // そのカードのクラス(f,s,aで表す)。f,s,a

	protected int cost; // そのカードのコスト。f,s,a

	protected int ap; // そのカードの攻撃力。 fのみ。

	protected int hp; // そのカードの体力。 fのみ。

	protected int evUpap; // そのカードの進化後の攻撃力プラス値。 fのみ。

	protected int evUphp; // そのカードの進化後の体力プラス値。 fのみ。

	protected int needPlayChoiceCount; // play時にチョイスが必要な選択肢数。例）緋色の剣士は1、ドラゴンナイツ（エンハンス）は2、など。デフォルトは0。

	protected int needEvolveChoiceCount; // evolve時にチョイスが必要な選択肢数。例）リリエルは1。デフォルトは0。

	protected int handN; // そのカードの手札番号。 f,s,a

	protected int fN; // そのカードのフィールド番号。 f,a

	protected int deckn; // デバッグ用。そのカードのデッキにおける配列添え字。 f,s,a

	protected boolean canAttack = false; // そのカードがいまattack可能か否か。falseなら攻撃不可。 fのみ。

	protected boolean attacked = false; // そのカードがattackしたか否か。attackしたならtrue。まだしてないならfalse。fのみ。

	protected boolean rush; // そのカードが突進か否か。 fのみ。

	protected boolean storm; // そのカードが疾走か否か。fのみ。

	protected boolean liza; // fのみ。

	protected boolean ward; // そのカードが守護持ちかどうか。fのみ。

	protected boolean ev; // そのカードがこのゲーム中進化したか否か。進化したことがあるならtrue。まだしてないならfalse。fのみ。

	protected boolean enhance; // そのカードがエンハンス効果を今持っているか否か。(コストが大きくなっている状態か否か) fのみ。

	protected boolean chain; // 鎖状態（攻撃不能状態）を司る(トーヴ・ラプンツェル)。trueなら鎖状態。 fのみ。

	protected boolean bane; // そのカードが必殺か否か。 fのみ。

	protected boolean ambush; // そのカードが潜伏か否か。fのみ。trueなら、潜伏

	// カードに対するターン終了時付与効果リスト
	protected ArrayList<TurnEndAddEffect> turnEndAddEffectList = new ArrayList<TurnEndAddEffect>();
	
	//Cardインスタンスを全てのフィールドの値を一致させた状態でコピーするためのメソッド。
	//Leaderクラスのcloneをする際に、LeaderクラスにCard型のリストがいくつか定義されているため、
	//Cardクラス側でもcloneメソッドを用意する必要がある。
	public Card clone() {

		Card card = null;

		try {
			card = (Card) super.clone();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return card;

	}

	// playメソッド
	//choiceNumberはplay時（スペル）かfanfare時（フォロワー）に何かを選択する処理がある場合にのみ使用する。
	//現状play時（スペル）かfanfare時（フォロワー）に選択対象が1つ必要な場合しかカバーできていない。
	//現状のカードプールであれば問題ないが、いくつか選択が必要なカードが出てきた場合に改修が必要。
	//choiceNumberをint型ではなくList<int>型とかにすればいいと思う。
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) {
	}

	// 攻撃メソッド
	//attackNumberが攻撃する側のフィールド番号。attackSakiNumberが攻撃先のフィールド番号。
	public void attack(Leader turnLeader, Leader noTurnLeader, int attackNumber, int attackSakiNumber) {

	}

	// (ラストワードを発動し)フィールドからいなくなり墓地＋１するメソッド
	//n:dieするフォロワーのフィールド番号。
	public void die(Leader turnLeader, Leader noTurnLeader, int n) {

	}

	// 進化メソッド
	//choiceNumberはevolve時に何かを選択する処理がある場合にのみ使用する。
	public void evolve(Leader turnLeader, Leader noTurnLeader, int evolveNumber, int choiceNumber) {

	}

	// 効果によるダメージメソッド
	public void effectAttack(Card[] Field, int a, int b) { // Fieldサイドのa番目にb点与える。

		if (!Field[a].isLiza()) { // リザの効果を受けてないなら、b点与える
			Field[a].setHp(Field[a].getHp() - b);
		}

	}

	// 手札に戻すときにリセットする。
	public void reset() {

	}

	// ターン開始時効果
	public void turnStartEffect(Leader turnLeader, Leader noTurnLeader) {
	}

	// ターン終了時効果
	public void turnEndEffect(Leader turnLeader, Leader noTurnLeader) {
	}

	// プレイ時効果（ラプンツェルなど）
	public void playEffect(Leader turnLeader, Leader noTurnLeader) {
	}

	// 攻撃時効果
	public void attackEffect(Leader turnLeader, Leader noTurnLeader) {
	}

	// ラストワードメソッド(dieEffectクラス)
	public void lastWard(Leader turnLeader, Leader noTurnLeader) {
	}

	// 自分に限らず自分のフォロワーがplayされたときいつでも働く効果（トーヴなど）「自分のフォロワーが」「他のフォロワーが」
	// プレイ時共通効果
	// playedCard:今プレイしたフォロワーのフィールド番号
	public void playCommonEffect(Leader turnLeader, Leader noTurnLeader, Card playedCard) {
	}

	// 自分に限らず自分のフォロワーが攻撃するときいつでも働く効果（ラプンツェルなど）
	// 攻撃時共通効果
	// n:今攻撃しようとしているフォロワーのフィールド番号
	public void attackCommonEffect(Leader turnLeader, Leader noTurnLeader, int n) {
	}

	// 自分に限らず自分のフォロワーが進化するときいつでも働く効果
	// 進化時共通効果
	//// n:今進化したフォロワーのフィールド番号
	public void evolveCommonEffect(Leader turnLeader, Leader noTurnLeader, int n) {
	}

	// 現在そのカードがplay可能かどうかを判定する。デフォルトはtrueで、つまり対象カード（this）をplay可能。
	// フォロワーはfanfare時、スペルはplay時という感じなので、どちらもまとめてこのメソッドで定義することにする。
	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {
		return true;
	}

	// Myselfの選択処理をかく。
	//canPalyChoice、canEvolveChoiceを呼ぶのかでplayChoice、evolveChoiceが分かれているだけなので、
	//canChoiceとかにして、引数で今呼びたいのがplay時、evolve時（今はないがattack時)どれなのかを指定する感じにすれば一つのメソッドにまとまる。
	public int playChoice(Leader turnLeader, Leader noTurnLeader) {

		//ここを99にしてる理由は謎。
		int choiceNumber = 99;

		while (true) {
			// -1する。
			choiceNumber = Input.getInt("効果対象を選んでください。いないなら100。") - 1;

			if (choiceNumber < 0) {
				System.out.println("0または負の数は無効です。");
				continue;
			} else if (choiceNumber == 99) {
				break;
			} else if (this.canPlayChoice(turnLeader, noTurnLeader, choiceNumber)) {
				break;
			}

		}
		return choiceNumber;

	}

	// Myselfの選択処理をかく。
	public int evolveChoice(Leader turnLeader, Leader noTurnLeader) {

		int choiceNumber = 99;

		while (true) {
			// -1する。
			choiceNumber = Input.getInt("効果対象を選んでください。いないなら100。") - 1;

			if (choiceNumber < 0) {
				System.out.println("0または負の数は無効です。");
				continue;
			} else if (choiceNumber == 99) {
				break;
			} else if (this.canEvolveChoice(turnLeader, noTurnLeader, choiceNumber)) {
				break;
			}

		}
		return choiceNumber;

	}

	// 現在そのカードをplay後、choiceNumberのカードをchoice可能かどうかを判定する。デフォルトはtrueで、つまりchoiceNumberをchoice可能。
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		return true;
	}

	// 現在そのカードをevolve後、choiceNumberのカードをchoice可能かどうかを判定する。デフォルトはtrueで、つまりchoiceNumberをchoice可能。
	public boolean canEvolveChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		return true;
	}

	//Follower、Spellそれぞれのplay時の共通的な処理を記載している。
	public void playOperation(Leader turnLeader, Leader noTurnLeader, int playNumber) {

		// 残りPP＝残りPP－コスト
		turnLeader.setPpRest(turnLeader.getPpRest() - this.getCost());

		// 手札をずらしていく。
		for (int i = 0; i < turnLeader.getHandCount() - playNumber - 1; i++) {
			turnLeader.getHandList()[playNumber + i] = turnLeader.getHandList()[playNumber + i + 1];
			turnLeader.getHandList()[playNumber + i].setHandN(turnLeader.getHandList()[playNumber + i].getHandN() - 1); // 手札番号をずらす。
		}
		turnLeader.setHandCount(turnLeader.getHandCount() - 1);

		// このターン中playした枚数をカウント
		turnLeader.setPlayCount(turnLeader.getPlayCount() + 1);
	}

	// hashCodeとequalsはCardクラスにパラメータの変化があったら一緒に変えないといけない！
	// equalsをオーバーライドするときに書かないといけないやつ
	@Override
	public int hashCode() {
		return Objects.hash(ambush, ap, attacked, bane, canAttack, chain, clas, cost, enhance, ev, evUpap, evUphp, hp,
				liza, name, needEvolveChoiceCount, needPlayChoiceCount, rank, rush, storm, turnEndAddEffectList, ward);
	}

	// handN、fN、deckn以外の全てのパラメータが同じか判定する。
	// enemy探索の処理の高速化に使う。すでに探索リストに入れたカードとパラメータが全て同じなら探索の候補に入れないようにする。
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		return ambush == other.ambush && ap == other.ap && attacked == other.attacked && bane == other.bane
				&& canAttack == other.canAttack && chain == other.chain && clas == other.clas && cost == other.cost
				&& enhance == other.enhance && ev == other.ev && evUpap == other.evUpap && evUphp == other.evUphp
				&& hp == other.hp && liza == other.liza && Objects.equals(name, other.name)
				&& needEvolveChoiceCount == other.needEvolveChoiceCount
				&& needPlayChoiceCount == other.needPlayChoiceCount && Objects.equals(rank, other.rank)
				&& rush == other.rush && storm == other.storm
				&& Objects.equals(turnEndAddEffectList, other.turnEndAddEffectList) && ward == other.ward;
	}

	public String getName() {
		return name;
	}

	public String getRank() {
		return rank;
	}

	public char getClas() {
		return clas;
	}

	public int getCost() {
		return cost;
	}

	public int getHandN() {
		return handN;
	}

	public int getfN() {
		return fN;
	}

	public int getDeckn() {
		return deckn;
	}

	public int getAp() {
		return ap;
	}

	public int getHp() {
		return hp;
	}

	public boolean canAttack() {
		return canAttack;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public void setClas(char clas) {
		this.clas = clas;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public void setHandN(int handN) {
		this.handN = handN;
	}

	public void setfN(int fN) {
		this.fN = fN;
	}

	public void setDeckn(int deckn) {
		this.deckn = deckn;
	}

	public void setAp(int ap) {
		this.ap = ap;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setCanAttack(boolean canAttack) {
		this.canAttack = canAttack;
	}

	public boolean isLiza() {
		return liza;
	}

	public void setLiza(boolean liza) {
		this.liza = liza;
	}

	public boolean isWard() {
		return ward;
	}

	public void setWard(boolean ward) {
		this.ward = ward;
	}

	public boolean isEv() {
		return ev;
	}

	public void setEv(boolean ev) {
		this.ev = ev;
	}

	public int getEvUpap() {
		return evUpap;
	}

	public int getEvUphp() {
		return evUphp;
	}

	public void setEvUpap(int evUpap) {
		this.evUpap = evUpap;
	}

	public void setEvUphp(int evUphp) {
		this.evUphp = evUphp;
	}

	public boolean isRush() {
		return rush;
	}

	public void setRush(boolean rush) {
		this.rush = rush;
	}

	public boolean isEnhance() {
		return enhance;
	}

	public void setEnhance(boolean enhance) {
		this.enhance = enhance;
	}

	public boolean isChain() {
		return chain;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	public boolean isBane() {
		return bane;
	}

	public void setBane(boolean bane) {
		this.bane = bane;
	}

	public boolean isAmbush() {
		return ambush;
	}

	public void setAmbush(boolean ambush) {
		this.ambush = ambush;
	}

	public ArrayList<TurnEndAddEffect> getTurnEndAddEffectList() {
		return turnEndAddEffectList;
	}

	public void setTurnEndAddEffectList(ArrayList<TurnEndAddEffect> turnEndAddEffectList) {
		this.turnEndAddEffectList = turnEndAddEffectList;
	}

	public int getNeedPlayChoiceCount() {
		return needPlayChoiceCount;
	}

	public void setNeedPlayChoiceCount(int needpPlayChoiceCount) {
		this.needPlayChoiceCount = needpPlayChoiceCount;
	}

	public int getNeedEvolveChoiceCount() {
		return needEvolveChoiceCount;
	}

	public void setNeedEvolveChoiceCount(int needEvolveChoiceCount) {
		this.needEvolveChoiceCount = needEvolveChoiceCount;
	}

	public boolean isStorm() {
		return storm;
	}

	public void setStorm(boolean storm) {
		this.storm = storm;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}


}
