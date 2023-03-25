package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Follower;
import main.Leader;

public class Ancient_elf extends Follower {

	public Ancient_elf() {
		name = "エンシェントエルフ";
		rank = "elf";
		ap = 2;
		hp = 3;
		cost = 3;
		ward = true;
		evUpap = 2;
		evUphp = 2;
	}

	@Override
	public void reset() {
		name = "エンシェントエルフ";
		rank = "elf";
		ap = 2;
		hp = 3;
		cost = 3;
		ward = true; //守護無くされたりしてもreset。
		canAttack = false;
		attacked = false;

	}

	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		int count=turnLeader.getFieldList().size();
		while(turnLeader.getFieldList().size()>1) {  //エンシェントエルフ一体になるまで繰り返す。
			if(turnLeader.getFieldList().get(0).isWard()) {
				turnLeader.setWardCount(turnLeader.getWardCount()-1);  //忘れずに！
			}
			if(turnLeader.getHandList().size()==MAX_HAND) {
				// フィールドの頭のやつをフィールドから削除する。
				//Listの機能で勝手に間はつめられる。
				turnLeader.getFieldList().remove(0);
				for(int i=0;i<turnLeader.getFieldList().size()-1;i++) {
					turnLeader.getFieldList().get(i).setfN(turnLeader.getFieldList().get(i).getfN()-1);
				}
				turnLeader.setCemetery(turnLeader.getCemetery()+1);
			}else {
				turnLeader.getFieldList().get(0).reset();
				List<Card> cardList = new ArrayList<Card>();
				
				cardList.add(turnLeader.getFieldList().get(0));
						
				turnLeader.handSet(turnLeader, noTurnLeader, cardList);
				// フィールドの頭のやつをフィールドから削除する。
				//Listの機能で勝手に間はつめられる。
				turnLeader.getFieldList().remove(0);
				for(int i=0;i<turnLeader.getFieldList().size()-1;i++) {  //フィールドをずらすのを忘れずに。
					turnLeader.getFieldList().get(i).setfN(turnLeader.getFieldList().get(i).getfN()-1);
				}
			}
		}
		turnLeader.getFieldList().get(0).setAp(turnLeader.getFieldList().get(0).getAp()+count-1);
		turnLeader.getFieldList().get(0).setHp(turnLeader.getFieldList().get(0).getHp()+count-1);
		
	}
}
