package neutral;

import base.Card;
import base.Follower;
import main.Leader;


public class GoblinMage extends Follower{

	static int n;
	static int[] kuji;
	
	public GoblinMage() {
		name="ミニゴブリンメイジ";
		rank="neutral";
		ap=2;
		hp=2;
		cost=3;
		evUpap=2;
		evUphp=2;
	}
	
	@Override
	public void reset() {
		name="ミニゴブリンメイジ";
		rank="neutral";
		ap=2;
		hp=2;
		cost=3;
		canAttack=false;attacked=false;
	}
	public int kuji() {
		int a=(int)(Math.random()*n);
		int t=kuji[a];
		kuji[a]=kuji[n-1];
		n--;
		return t;
	}
	
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
			n=turnLeader.getDeckRest();
			kuji=new int[n];
			for(int i=0;i<n;i++) {
				kuji[i]=i;
			}
			while(true) {
				int a=kuji();
				Card c=turnLeader.getDeckList()[a];
				if(c.getCost()==2) {
					c.setDeckn(a);
					turnLeader.getDeckList()[a]=turnLeader.getDeckList()[turnLeader.getDeckRest()-1];
					turnLeader.setDeckRest(turnLeader.getDeckRest()-1);
					turnLeader.handSet(c);
					break;
				}
			}
	}
}
