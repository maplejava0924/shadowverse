package neutral;
import base.Follower;
import main.Leader;

public class Wind_god extends Follower{

	public Wind_god() {
		name="風神";
		rank="neutral";
		ap=1;
		hp=5;
		cost=5;
		evUpap=2;
		evUphp=2;
	}
	
	@Override
	public void reset() {
		name="風神";
		rank="neutral";
		ap=1;
		hp=5;
		cost=5;
		canAttack=false;attacked=false;
	}
	
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		for(int i=0;i<turnLeader.getFieldList().size();i++) {
			turnLeader.getFieldList().get(i).setAp(turnLeader.getFieldList().get(i).getAp()+1);
		}
	}
	
	//ターン開始時効果
	public void turnStartEffect(Leader turnLeader,Leader noTurnLeader) {  
		System.out.println("風神のターン開始時効果発動！");
		for(int i=0;i<turnLeader.getFieldList().size();i++) {
			if(turnLeader.getFieldList().get(i).getClas()=='f')
				turnLeader.getFieldList().get(i).setAp(turnLeader.getFieldList().get(i).getAp()+1);
		}
	}
	
	
}
