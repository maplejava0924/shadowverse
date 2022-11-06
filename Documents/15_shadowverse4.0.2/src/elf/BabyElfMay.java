package elf;
import base.Follower;
import main.Leader;
public class BabyElfMay extends Follower{
	
	public BabyElfMay(){
		name="ベビーエルフメイ";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="ベビーエルフメイ";
		rank="elf";
		ap=1;
		hp=1;
		cost=1;
		canAttack=false;attacked=false;
	}
	
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		if(noTurnLeader.getFieldCount()>0) {
			System.out.println(name+"のファンファーレ発動！");
			int f=(int)(Math.random()*noTurnLeader.getFieldCount());  //0からnoTurnLeader.getFieldCount()-1までの整数
			//ランダム処理のため潜伏に当たらないeffectAttackは使用しない→と思ったがやめた。effectAttackに戻しても何ら問題ないがとりあえずこうしとく。
			if(!noTurnLeader.getFieldList()[f].isLiza()) {  //リザの効果を受けてないなら、1点与える
				noTurnLeader.getFieldList()[f].setHp(noTurnLeader.getFieldList()[f].getHp()-1);
			}
			
			if(noTurnLeader.getFieldList()[f].getHp()<=0) { //もし相手のHPが０以下になったら～
				noTurnLeader.getFieldList()[f].die(noTurnLeader,turnLeader,f);
			}
		}
		
	}
}
