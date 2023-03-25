package main;

import java.util.ArrayList;
import java.util.List;

import base.Card;

public class Leader implements Cloneable {

	private int turnCount; // 経過ターン数
	private int lifeCount = 20; // 体力（初期値：20）
	private int cemetery; // 墓地
	private int ppMax; // 最大PP（最大10、基本的にはターン数と同じ、カラボス・アイラなどを除いて）
	private int ppRest; // そのターン使える残りのPP

	private int playCount; // そのターン中カードをプレイした枚数(既にプレイした枚数)。
	private int wardCount; // プレイヤーの場の守護の数。
	private int epRest; // プレイヤーの残り進化ポイント。
	private boolean evolved; // プレイヤーがこのターン中進化したならtrue
	private boolean revenge; // 自分が復讐状態か否か。trueなら復讐
	private boolean myturn; // 自分のターンか否か。とりあえずプレイヤー先行固定で。→senkou()導入。
	private boolean senkou; // プレイヤーが先攻ならtrue。

	private String rank; // 自分がエルフなのかヴァンプなのかロイヤルなのか。Stringはclone不要っぽい。
	private List<Card> handList = new ArrayList<Card>(); // 手札配列。clone記載要。
	private List<Card> fieldList = new ArrayList<Card>(); // フィールド配列。clone記載要。
	private List<Card> deckList = new ArrayList<Card>(); // フィールド配列。clone記載要。

	// リーダーに対するターン終了時付与効果リスト。関数インターフェースはcloneかけない（clone記載不要とする）。
	private ArrayList<TurnEndAddEffect> turnEndAddEffectList = new ArrayList<TurnEndAddEffect>();

	// 初期ドロー枚数。
	public static final int INITIAL_DRAW = 3;
	public static final int MAX_DECK = 40;

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
			leader.deckList = new ArrayList<Card>();
			if (this.deckList != null) {
				for (int i = 0; i < this.deckList.size(); i++) {
					leader.deckList.add(this.deckList.get(i).clone());
				}
			}

			leader.handList = new ArrayList<Card>();
			if (this.handList != null) {
				for (int i = 0; i < this.handList.size(); i++) {
					leader.handList.add(this.handList.get(i).clone());
				}
			}

			leader.fieldList = new ArrayList<Card>();
			if (this.fieldList != null) {
				for (int i = 0; i < this.fieldList.size(); i++) {
					leader.fieldList.add(this.fieldList.get(i).clone());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return leader;
	}

	//最終的に何枚のカードを加えられたのか、という値が呼び出し元で必要な場合があるので、その値をreturnする
	public int handSet(Leader turnLeader, Leader noTurnLeader, List<Card> cardList) {

		//カードリストのサイズは今手札に加えるように要求のあったカードの数である
		int addRequestCount = cardList.size();

		//一旦実際に手札に加えるカード数を要求のあったカード数すべてにする
		int addCount = addRequestCount;

		//加えた後の手札の合計が手札の上限より大きいなら
		if (turnLeader.getHandList().size() + addRequestCount > Card.MAX_HAND) {

			//手札に加えられないカード数
			int lostCount = turnLeader.getHandList().size() + addRequestCount - Card.MAX_HAND;
			//手札に加えられないカード数分、墓地をプラスする
			turnLeader.setCemetery(turnLeader.getCemetery() + lostCount);
			//手札に加えるよう要求のあったカード数から、手札に加えられないカード数分引いて実際に手札に加えるカード数とする
			addCount = addRequestCount - lostCount;

		}

		//実際に手札に加えるカード数分カードを手札に加える
		for (int i = 0; i < addCount; i++) {
			// ハンドにcardをセット。
			turnLeader.getHandList().add(cardList.get(i));
		}
		
		return addCount;

	}

	//初期手札を配る処理をするメソッド。
	//dealやfieldSet、draw、start等の処理がLeaderクラスに記載されている理由は特にない。
	//多分CardでもLeaderでもない処理用のクラスを作ったほうがいいんだと思う。
	//fieldSet(turnLeader,card)とか。
	public void deal(Leader turnLeader, Leader noTurnLeader) {

		for (int i = 0; i < INITIAL_DRAW; i++) {
			draw(turnLeader, noTurnLeader);
		}

	}

	public void draw(Leader turnLeader, Leader noTurnLeader) {

		List<Card> cardList = new ArrayList<Card>();

		cardList.add(NextCard(this.deckList));
		// ハンドにNextcardをセット。
		handSet(turnLeader, noTurnLeader, cardList);

	}

	//ターン開始時の処理。
	public void start(Leader turnLeader, Leader noTurnLeader) {

		this.turnCount++;
		if (this.ppMax < Card.MAX_PP) {
			this.ppMax++;
		}
		this.ppRest = this.ppMax;
		this.evolved = false; // このターン中の進化の有無をリセット。
		draw(turnLeader, noTurnLeader);
		//後攻1ターン目なら2ドローなので追加でもう一回draw()
		if(!turnLeader.isSenkou()&&turnLeader.getTurnCount() ==1) {
			draw(turnLeader, noTurnLeader);
		}

		for (int i = 0; i < this.fieldList.size(); i++) {
			this.fieldList.get(i).setCanAttack(true); // 攻撃可
			this.fieldList.get(i).setAttacked(false); //まだattackしてないに変える。
			this.fieldList.get(i).turnStartEffect(turnLeader, noTurnLeader);
			this.fieldList.get(i).setRush(false); //突進じゃなくなる

		}
		System.out.println("あなたのターン");

	}

	// 自分の山札を引く。取り出すって感じかな。
	public static Card NextCard(List<Card> deckList) {

		int nextCardNumber = (int) (Math.random() * deckList.size()); // 0~39

		Card nextCard = deckList.get(nextCardNumber);
		
		// デバッグ用。
		nextCard.setDeckn(nextCardNumber);

		deckList.remove(nextCardNumber);

		return nextCard;

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

	public List<Card> getDeckList() {
		return deckList;
	}

	public void setDeckList(List<Card> deckList) {
		this.deckList = deckList;
	}

	public List<Card> getHandList() {
		return handList;
	}

	public void setHandList(List<Card> handList) {
		this.handList = handList;
	}

	public List<Card> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<Card> fieldList) {
		this.fieldList = fieldList;
	}

	public ArrayList<TurnEndAddEffect> getTurnEndAddEffectList() {
		return turnEndAddEffectList;
	}

	public void setTurnEndAddEffectList(ArrayList<TurnEndAddEffect> turnEndAddEffectList) {
		this.turnEndAddEffectList = turnEndAddEffectList;
	}

}
