package elf;
import base.Follower;
import main.Leader;
public class Elf_child_may extends Follower{
	
	public Elf_child_may(){
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
		if(noTurnLeader.getFieldList().size()>0) {
			System.out.println(name+"のファンファーレ発動！");
			int f=(int)(Math.random()*noTurnLeader.getFieldList().size());  //0からnoTurnLeader.getFieldList().size()-1までの整数
			//ランダム処理のため潜伏に当たらないeffectAttackは使用しない→と思ったがやめた。effectAttackに戻しても何ら問題ないがとりあえずこうしとく。
			if(!noTurnLeader.getFieldList().get(f).isLiza()) {  //リザの効果を受けてないなら、1点与える
				noTurnLeader.getFieldList().get(f).setHp(noTurnLeader.getFieldList().get(f).getHp()-1);
			}
			
			if(noTurnLeader.getFieldList().get(f).getHp()<=0) { //もし相手のHPが０以下になったら～
				noTurnLeader.getFieldList().get(f).die(noTurnLeader,turnLeader,f);
			}
		}
		
	}
}
