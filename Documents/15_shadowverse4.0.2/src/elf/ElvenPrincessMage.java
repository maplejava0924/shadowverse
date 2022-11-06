package elf;
import base.Follower;
import main.Leader;

public class ElvenPrincessMage extends Follower{

	public ElvenPrincessMage() {
		name="エルフプリンセスメイジ";
		rank="elf";
		ap=3;
		hp=4;
		cost=4;
		evUpap=1;
		evUphp=1;
	}
	@Override
	public void reset() {
		name="エルフプリンセスメイジ";
		rank="elf";
		ap=3;
		hp=4;
		cost=4;
		canAttack=false;attacked=false;
	}

	@Override
	public void evolveEffect(Leader turnLeader,Leader noTurnLeader,int evolveNumber, int choiceNumber) {
		System.out.println(name+"の進化時能力発動！");
		if(turnLeader.getHandCount()<=7) {  //手札が7枚以下なら～
			
			for (int i=0; i<2;i++) {
				turnLeader.handSet(new Fairy());
			}
			turnLeader.getHandList()[turnLeader.getHandCount()-2].setCost(0);
			turnLeader.getHandList()[turnLeader.getHandCount()-1].setCost(0);
			
		}else if(turnLeader.getHandCount()==8){  //この条件だと0以下とかも含まれるけど考慮はいらんだろう。
			turnLeader.handSet(new Fairy());
			turnLeader.getHandList()[turnLeader.getHandCount()-1].setCost(0);
			turnLeader.setCemetery(turnLeader.getCemetery()+1);
		}else if(turnLeader.getHandCount()==9) {
			turnLeader.setCemetery(turnLeader.getCemetery()+2);
		}
	}
}
