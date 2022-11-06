package vampire;
import base.Card;
import base.Follower;
import main.Leader;


public class Baphomet extends Follower{

	static int n;
	static int[] kuji;
	
	public Baphomet() {
		name="バフォメット";
		rank="vampire";
		ap=2;
		hp=1;
		cost=2;
		evUpap=2;
		evUphp=2;
	}
	
	@Override
	public void reset() {
		name="バフォメット";
		rank="vampire";
		ap=2;
		hp=1;
		cost=2;
		canAttack=false;attacked=false;
	}
	
	public int kuji() {  //0～pmax-1のなかでランダムに1つ数字を重複なく取り出す。
		
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
		boolean b=false;
		while(true) {
			int a=kuji();
			Card c=turnLeader.getDeckList()[a];
			if(c.getAp()>=5&&c.getRank().equals("vampire")&&c.getClas()=='f') {
				c.setDeckn(a);
				turnLeader.getDeckList()[a]=turnLeader.getDeckList()[turnLeader.getDeckRest()-1];
				turnLeader.setDeckRest(turnLeader.getDeckRest()-1);
				turnLeader.getHandList()[turnLeader.getHandCount()]=c;
				turnLeader.handSet(c);
				b=true;
				break;	
			}
			if(n==0)break;
		}
		if(b&&cost==5) {  //エンハンス効果
			System.out.println(name+"のエンハンス効果発動！");
			if(turnLeader.getHandList()[turnLeader.getHandCount()-1].getCost()-3>=0) {
				turnLeader.getHandList()[turnLeader.getHandCount()-1].setCost(turnLeader.getHandList()[turnLeader.getHandCount()-1].getCost()-3);
			}else {  //ここelse if .pHand[.pn-1].getCost()-3<0てしなきゃいけないの？
				turnLeader.getHandList()[turnLeader.getHandCount()-1].setCost(0);
			}
		}
			
	}
}
