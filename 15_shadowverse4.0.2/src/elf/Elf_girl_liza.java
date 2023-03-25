package elf;
import base.Follower;
import main.Leader;
import main.TurnEndAddEffect;

public class Elf_girl_liza extends Follower{

	public Elf_girl_liza() {
		name="エルフの少女 リザ";
		rank="elf";
		ap=2;
		hp=2;
		cost=2;
		evUpap=2;
		evUphp=2;
	}
	@Override
	public void reset() {
		name="エルフの少女 リザ";
		rank="elf";
		ap=2;
		hp=2;
		cost=2;
		canAttack=false;attacked=false;
	}

	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");
		for(int i=0;i<turnLeader.getFieldList().size();i++) {
			turnLeader.getFieldList().get(i).setLiza(true);
		}

		//ターン終了時に特定の処理を実行する効果をリーダーに付与する。かつ、"特定の処理"はExecクラスなどではなく、
		//その効果を持つカード（今回ならリザ）のクラスの中にその処理を記述したい。
		//そのためにまずラムダ式を使って"特定の処理"自体を変数（今回ならturnEndAddEffect）に格納し、
		//LeaderクラスにturnEndAddEffectListというリストをフィールドとしてもたせ、それを共通処理側（今だとExecクラスのturnEndメソッド内）で
		//呼び出す形にしている。今後ターン開始時やplay時発動するにリーダーに対して付与する効果が出てきたら、その分だけTurnEnddAddEffectのような
		//関数インターフェースを増やさなければいけない感じになっているので、共通インターフェースのようなものを作るとクラスの数が膨大にならないのかなと思っている（多分）
		TurnEndAddEffect turnEndAddEffect = (p1,p2) ->{
			for(int i=0;i<p2.getFieldList().size();i++) {
				if(p2.getFieldList().get(i).isLiza()==true) {  //リザ(リザの効果を外す)
					System.out.println(name+"のターン終了時リーダー付与効果発動！");
					p2.getFieldList().get(i).setLiza(false);  //「相手のターン終了時まで」なのでp2で良い
				}
			}
		};
		//相手プレイヤーに、ターン終了時、相手のリザ効果を外す効果を付与する。
		for(int i=0;i<noTurnLeader.getFieldList().size();i++) {
			noTurnLeader.getTurnEndAddEffectList().add(turnEndAddEffect);
		}
	}

}
