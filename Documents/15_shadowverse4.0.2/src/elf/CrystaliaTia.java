package elf;
import base.Follower;
import main.Leader;

public class CrystaliaTia extends Follower{

	public CrystaliaTia() {
		name="クリスタリアプリンセス・ティア";
		rank="elf";
		ap=1;
		hp=1;
		cost=5;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="クリスタリアプリンセス・ティア";
		rank="elf";
		ap=1;
		hp=1;
		cost=5;
		canAttack=false;attacked=false;
	}

	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");

		if(turnLeader.getFieldCount()<=4) {  //フィールドのカード数が4枚以下なら～
			//クリスタリアイブを召喚
			turnLeader.fieldSet(new CrystaliaEve());

			choiceNumber = 0;
			//fanfareのchoiceNumberと同じに認識されてるのがなんか怖い。
			if(turnLeader.getPlayCount()-1>=2) {  //このターン中このカードを含めず2枚以上プレイしていたのなら～
				turnLeader.getFieldList()[turnLeader.getFieldCount()-1].evolve(turnLeader,noTurnLeader,turnLeader.getFieldCount()-1,choiceNumber);
				turnLeader.setEpRest(turnLeader.getEpRest() + 1); //進化権消費しないので+1。
				turnLeader.getFieldList()[turnLeader.getFieldCount()-1].setWard(true);
				turnLeader.setWardCount(turnLeader.getWardCount()+1); //これいるよね
			}
		}else {  //5枚なら～
			//消滅？
		}

	}
}
