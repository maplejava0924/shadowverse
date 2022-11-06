package vampire;

import base.Card;
import base.Follower;
import main.Leader;

public class Tove extends Follower{

	public Tove() {
		name="トーヴ";
		rank="vampire";
		ap=3;
		hp=3;
		cost=2;
		evUpap=2;
		evUphp=2;
		chain=true;
	}
	
	@Override
	public void reset() {
		name="トーヴ";
		rank="vampire";
		ap=3;
		hp=3;
		cost=2;
		chain=true;
		canAttack=false;attacked=false;
	}
	
	@Override
	public void playCommonEffect(Leader turnLeader,Leader noTurnLeader,Card playedCard	) {  
		System.out.println("トーヴのプレイ時共通効果発動！１");
				if(playedCard.getRank().equals("neutral")) {  //そのplayしたフォロワーがニュートラルのとき～
					if(this.isChain()) {  //もしそのトーヴが鎖状態なら～
						System.out.println("トーヴのプレイ時共通効果発動！２");
						this.setChain(false);  //鎖状態を解く
						this.setRush(true); //突進を得る
					}
				}
	}
	
	
}
