package elf;

import base.Follower;
import main.Leader;

public class ElfKnightCynthia extends Follower{

	public ElfKnightCynthia() {
		name="エルフナイト・シンシア";
		rank="elf";
		ap=5;
		hp=6;
		cost=6;
		evUpap=1;
		evUphp=1;
	}

	@Override
	public void reset() {
		name="エルフナイト・シンシア";
		rank="elf";
		ap=5;
		hp=6;
		cost=6;
		canAttack=false;attacked=false;
	}

	@Override
	public void evolveEffect(Leader turnLeader,Leader noTurnLeader,int evolveNumber,int choiceNumber) {
		System.out.println("シンシアの進化時効果発動！");
			if(turnLeader.getFieldCount()<=3) {
				//２回繰り返す
				for(int i=0;i<2;i++) {
					//フェアリーを召喚
					turnLeader.fieldSet(new Fairy());
				}
			}else if(turnLeader.getFieldCount()==4) {
				//フェアリーを召喚
				turnLeader.fieldSet(new Fairy());
				//1枚消滅
			}else {
				//2枚消滅
			}

	}

	public void attackCommonEffect(Leader turnLeader,Leader noTurnLeader,int n) {
		System.out.println("シンシアの攻撃時共通効果発動！１");
		//今攻撃しようとしているフォロワーのフィールド番号(fNは表示用なので-1が必要)とこのインスタンスのフィールド番号が異なっている時～(「自分の他のフォロワーが攻撃するとき、」)
		if(this.getfN() - 1!=n) {
			System.out.println("シンシアの攻撃時共通効果発動！２");
			turnLeader.getFieldList()[n].setAp(turnLeader.getFieldList()[n].getAp()+1);
		}
	}
}
