package neutral;

import base.Follower;
import main.Leader;

public class Rapunzel extends Follower{

	public Rapunzel() {
		name="ラプンツェル";
		rank="neutral";
		ap=2;
		hp=5;
		cost=3;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="ラプンツェル";
		rank="neutral";
		ap=2;
		hp=5;
		cost=3;
		canAttack=false;attacked=false;
	}

	@Override
	public void turnStartEffect(Leader turnLeader,Leader noTurnLeader) {
		System.out.println("ラプンツェルのターン開始時効果発動！");  //何の効果が発動したかわかりづらいので(鎖状態)とした
		this.setChain(true);  //自分のターン開始時鎖状態
	}

	@Override
	public void playEffect(Leader turnLeader,Leader noTurnLeader) {
		System.out.println("ラプンツェルのplay時効果発動！");
		this.setChain(true);  //playしたとき鎖状態
	}

	@Override
	public void attackCommonEffect(Leader turnLeader,Leader noTurnLeader,int n) {
		System.out.println("ラプンツェルの攻撃時共通効果発動！１");
		//「自分の他のフォロワーが攻撃したとき、」
		if(this.getfN() - 1!=n) {
			System.out.println("ラプンツェルの攻撃時共通効果発動！２");
			//鎖状態を解除
			this.setChain(false);
		}

	}

}
