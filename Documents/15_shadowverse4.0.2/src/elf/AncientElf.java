package elf;
import base.Follower;
import main.Leader;

public class AncientElf extends Follower{

	public AncientElf() {
		name="エンシェントエルフ";
		rank="elf";
		ap=2;
		hp=3;
		cost=3;
		ward=true;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="エンシェントエルフ";
		rank="elf";
		ap=2;
		hp=3;
		cost=3;
		ward=true;  //守護無くされたりしてもreset。
		canAttack=false;attacked=false;
		
	}
	
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		int count=turnLeader.getFieldCount();
		while(turnLeader.getFieldCount()>1) {  //エンシェントエルフ一体になるまで繰り返す。
			if(turnLeader.getFieldList()[0].isWard()) turnLeader.setWardCount(turnLeader.getWardCount()-1);  //忘れずに！
			if(turnLeader.getHandCount()==9) {
				for(int i=0;i<turnLeader.getFieldCount()-1;i++) {
					turnLeader.getFieldList()[i]=turnLeader.getFieldList()[i+1];
					turnLeader.getFieldList()[i].setfN(turnLeader.getFieldList()[i].getfN()-1);
				}
				turnLeader.setCemetery(turnLeader.getCemetery()+1);
				turnLeader.setFieldCount(turnLeader.getFieldCount()-1);
			}else {
				turnLeader.getFieldList()[0].reset();
				turnLeader.handSet(turnLeader.getFieldList()[0]);
				for(int i=0;i<turnLeader.getFieldCount()-1;i++) {  //フィールドをずらすのを忘れずに。
					turnLeader.getFieldList()[i]=turnLeader.getFieldList()[i+1];
					turnLeader.getFieldList()[i].setfN(turnLeader.getFieldList()[i].getfN()-1);
				}
				turnLeader.setFieldCount(turnLeader.getFieldCount()-1);
			}
		}
		turnLeader.getFieldList()[0].setAp(turnLeader.getFieldList()[0].getAp()+count-1);
		turnLeader.getFieldList()[0].setHp(turnLeader.getFieldList()[0].getHp()+count-1);
		
	}
}
