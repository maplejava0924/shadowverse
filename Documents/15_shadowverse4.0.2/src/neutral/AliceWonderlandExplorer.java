package neutral;

import base.Follower;
import main.Leader;


public class AliceWonderlandExplorer extends Follower{

	public AliceWonderlandExplorer() {
		name="不思議の探究者・アリス";
		rank="neutral";
		ap=3;
		hp=4;
		cost=4;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="不思議の探究者・アリス";
		rank="neutral";
		ap=3;
		hp=4;
		cost=4;
		canAttack=false;attacked=false;
	}
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		for(int i=0;i<turnLeader.getHandCount();i++) {
			String s=turnLeader.getHandList()[i].getRank();
			if(s!=null&&s.equals("neutral")&&turnLeader.getHandList()[i].getClas()=='f') {  //手札のニュートラルフォロワー
				turnLeader.getHandList()[i].setAp(turnLeader.getHandList()[i].getAp()+1);
				turnLeader.getHandList()[i].setHp(turnLeader.getHandList()[i].getHp()+1);
			}
		}
		for(int i=0;i<turnLeader.getFieldCount()-1;i++) {  //他の
			String s=turnLeader.getFieldList()[i].getRank();
			if(s!=null&&s.equals("neutral")&&turnLeader.getFieldList()[i].getClas()=='f') {  //自分のニュートラルフォロワー
				turnLeader.getFieldList()[i].setAp(turnLeader.getFieldList()[i].getAp()+1);
				turnLeader.getFieldList()[i].setHp(turnLeader.getFieldList()[i].getHp()+1);
			}
		}
	}
}
