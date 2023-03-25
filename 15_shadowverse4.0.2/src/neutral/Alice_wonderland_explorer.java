package neutral;

import base.Card;
import base.Follower;
import main.Leader;


public class Alice_wonderland_explorer extends Follower{

	public Alice_wonderland_explorer() {
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
		for(Card handCard : turnLeader.getHandList()) {
			String s=handCard.getRank();
			if(s!=null&&s.equals("neutral")&&handCard.getClas()=='f') {  //手札のニュートラルフォロワー
				handCard.setAp(handCard.getAp()+1);
				handCard.setHp(handCard.getHp()+1);
			}
		}
		for(int i=0;i<turnLeader.getFieldList().size()-1;i++) {  //他の
			String s=turnLeader.getFieldList().get(i).getRank();
			if(s!=null&&s.equals("neutral")&&turnLeader.getFieldList().get(i).getClas()=='f') {  //自分のニュートラルフォロワー
				turnLeader.getFieldList().get(i).setAp(turnLeader.getFieldList().get(i).getAp()+1);
				turnLeader.getFieldList().get(i).setHp(turnLeader.getFieldList().get(i).getHp()+1);
			}
		}
	}
}
