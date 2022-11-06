package main;

import java.util.ArrayList;

import base.Card;

public class Leader implements Cloneable {

	private int turnCount; // 経過ターン数
	private int lifeCount = 20; // 体力（初期値：20）
	private int cemetery; // 墓地
	private int ppMax; // 最大PP（最大10、基本的にはターン数と同じ、カラボス・アイラなどを除いて）
	private int ppRest; // そのターン使える残りのPP
	private int handCount; // 今の自分の手札枚数(最大9)
	private int fieldCount; // 今の自分のフィールドのカード数（最大5）
	private int deckRest = 40; // デッキ枚数（初期値：40）
	private int playCount; // そのターン中カードをプレイした枚数(既にプレイした枚数)。
	private int wardCount; // プレイヤーの場の守護の数。
	private int epRest; // プレイヤーの残り進化ポイント。
	private boolean evolved; // プレイヤーがこのターン中進化したならtrue
	private boolean revenge; // 自分が復讐状態か否か。trueなら復讐
	private boolean myturn; // 自分のターンか否か。とりあえずプレイヤー先行固定で。→senkou()導入。
	private boolean senkou; // プレイヤーが先攻ならtrue。

	private String rank; // 自分がエルフなのかヴァンプなのかロイヤルなのか。Stringはclone不要っぽい。
	private Card[] deckList = new Card[deckRest]; // デッキ配列。今後はここがFairy型では駄目。clone記載要。
	private Card[] handList = new Card[9]; // 手札配列。clone記載要。
	private Card[] fieldList = new Card[5]; // フィールド配列。clone記載要。

	// リーダーに対するターン終了時付与効果リスト。関数インターフェースはcloneかけない（clone記載不要とする）。
	private ArrayList<TurnEndAddEffect> turnEndAddEffectList = new ArrayList<TurnEndAddEffect>();

	// 初期ドロー枚数。
	final int INITIAL_DRAW = 3;
	
	//Leaderインスタンスを全てのフィールドの値を一致させた状態でコピーするためのメソッド。
	//プリミティブ型のフィールドの場合はsuper.clone()でクローン出来るが、
	//参照型のフィールドの場合はsuper.clone()ではクローン出来ないので、
	//個別にクローンするためのコードを書く必要がある。ただし、String型はsuper.clone()でクローン出来ているようである。
	@Override
	public Leader clone() { // 基本的にはpublic修飾子を付け、自分自身の型を返り値とする
		Leader leader = null;

		/*
		 * ObjectクラスのcloneメソッドはCloneNotSupportedExceptionを投げる可能性があるので、try-catch文で記述(
		 * 呼び出し元に投げても良い)
		 */
		try {
			leader = (Leader) super.clone(); // 親クラスのcloneメソッドを呼び出す(親クラスの型で返ってくるので、自分自身の型でのキャストを忘れないようにする)
			
			//参照型は個別にクローン処理を記載。
			leader.deckList = new Card[this.deckRest];
			if (this.deckList != null) {
				for (int i = 0; i < this.deckRest; i++) {
					leader.deckList[i] = this.deckList[i].clone();
				}
			}

			leader.handList = new Card[9];
			if (this.handList != null) {
				for (int i = 0; i < this.handCount; i++) {
					leader.handList[i] = this.handList[i].clone();
				}
			}

			leader.fieldList = new Card[5];
			if (this.fieldList != null) {
				for (int i = 0; i < this.fieldCount; i++) {
					leader.fieldList[i] = this.fieldList[i].clone();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return leader;
	}

	//初期手札を配る処理をするメソッド。
	//dealやfieldSet、draw、start等の処理がLeaderクラスに記載されている理由は特にない。
	//多分CardでもLeaderでもない処理用のクラスを作ったほうがいいんだと思う。
	//fieldSet(turnLeader,card)とか。
	public void deal() {

		for (int i = 0; i < INITIAL_DRAW; i++) {
			draw();
		}

	}

	public void fieldSet(Card card) {

		// フィールドにcardをセット。
		this.getFieldList()[this.getFieldCount()] = card;
		// フィールド番号をセット。
		this.getFieldList()[this.getFieldCount()].setfN(this.getFieldCount() + 1);
		// フィールドカウントを+1。
		this.setFieldCount(this.getFieldCount() + 1);

	}

	public void handSet(Card card) {

		// ハンドにcardをセット。
		this.getHandList()[this.getHandCount()] = card;
		// ハンド番号をセット。
		this.getHandList()[this.getHandCount()].setHandN(this.getHandCount() + 1);
		// ハンドカウントを+1。
		this.setHandCount(this.getHandCount() + 1);

	}

	public void draw() {

		// ハンドにNextcardをセット。
		this.getHandList()[this.getHandCount()] = NextCard(this.deckList, this.deckRest);
		// ハンド番号をセット。
		this.getHandList()[this.getHandCount()].setHandN(this.getHandCount() + 1);
		// ハンドカウントを+1。
		this.setHandCount(this.getHandCount() + 1);

	}

	//ターン開始時の処理。
	public void start(Leader turnLeader, Leader noTurnLeader) {

		this.turnCount++;
		if (this.ppMax < 10) {
			this.ppMax++;
		}
		this.ppRest = this.ppMax;
		this.evolved = false; // このターン中の進化の有無をリセット。
		if (this.handCount <= 8) { // 手札9枚制限
			draw();
		} else {
			NextCard(this.deckList, this.deckRest); // 引くけれども引くだけで手札には入らない。
			this.cemetery++; // 溢れた手札はなんだったのかはわからない。
		}

		for (int i = 0; i < this.fieldCount; i++) {
			this.fieldList[i].setCanAttack(true); // 攻撃可
			this.fieldList[i].setAttacked(false); //まだattackしてないに変える。
			this.fieldList[i].turnStartEffect(turnLeader, noTurnLeader);
			// pField[i].setRush(false); //突進じゃなくなる

		}
		System.out.println("あなたのターン");

	}

	// 自分の山札を引く。取り出すって感じかな。
	public static Card NextCard(Card[] deck, int max) { 

		int a = (int) (Math.random() * max); // 0~39
		
		deck[a].setDeckn(a); // デバッグ用。

		Card f = deck[a];
		deck[a] = deck[max - 1];
		
		max--;
		return f;

	}

	public int getTurnCount() {
		return turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	public int getCemetery() {
		return cemetery;
	}

	public void setCemetery(int cemetery) {
		this.cemetery = cemetery;
	}

	public int getPpMax() {
		return ppMax;
	}

	public void setPpMax(int ppMax) {
		this.ppMax = ppMax;
	}

	public int getPpRest() {
		return ppRest;
	}

	public void setPpRest(int ppRest) {
		this.ppRest = ppRest;
	}

	public int getDeckRest() {
		return deckRest;
	}

	public void setDeckRest(int deckRest) {
		this.deckRest = deckRest;
	}

	public int getHandCount() {
		return handCount;
	}

	public void setHandCount(int handCount) {
		this.handCount = handCount;
	}

	public int getFieldCount() {
		return fieldCount;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public int getPlayCount() {
		return playCount;
	}

	public void setPlayCount(int playCount) {
		this.playCount = playCount;
	}

	public int getWardCount() {
		return wardCount;
	}

	public void setWardCount(int wardCount) {
		this.wardCount = wardCount;
	}

	public int getEpRest() {
		return epRest;
	}

	public void setEpRest(int epRest) {
		this.epRest = epRest;
	}

	public boolean isEvolved() {
		return evolved;
	}

	public void setEvolved(boolean evolved) {
		this.evolved = evolved;
	}

	public boolean isRevenge() {
		return revenge;
	}

	public void setRevenge(boolean revenge) {
		this.revenge = revenge;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public boolean isMyturn() {
		return myturn;
	}

	public void setMyturn(boolean myturn) {
		this.myturn = myturn;
	}

	public boolean isSenkou() {
		return senkou;
	}

	public void setSenkou(boolean senkou) {
		this.senkou = senkou;
	}

	public Card[] getDeckList() {
		return deckList;
	}

	public void setDeckList(Card[] deckList) {
		this.deckList = deckList;
	}

	public Card[] getHandList() {
		return handList;
	}

	public void setHandList(Card[] handList) {
		this.handList = handList;
	}

	public Card[] getFieldList() {
		return fieldList;
	}

	public void setFieldList(Card[] fieldList) {
		this.fieldList = fieldList;
	}

	public ArrayList<TurnEndAddEffect> getTurnEndAddEffectList() {
		return turnEndAddEffectList;
	}

	public void setTurnEndAddEffectList(ArrayList<TurnEndAddEffect> turnEndAddEffectList) {
		this.turnEndAddEffectList = turnEndAddEffectList;
	}

}
