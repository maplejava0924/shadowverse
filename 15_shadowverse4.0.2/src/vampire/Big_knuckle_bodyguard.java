package vampire;
import base.Follower;
import main.Leader;


public class Big_knuckle_bodyguard extends Follower{

	public Big_knuckle_bodyguard() {
		name="豪拳の用心棒";
		rank="vampire";
		ap=3;
		hp=4;
		cost=4;
		evUpap=2;
		evUphp=2;
		needPlayChoiceCount = 1;
	}
	@Override
	public void reset() {
		name="豪拳の用心棒";
		rank="vampire";
		ap=3;
		hp=4;
		cost=4;
		canAttack=false;attacked=false;
		needPlayChoiceCount = 1;
	}
	@Override
	public void fanfare(Leader turnLeader,Leader noTurnLeader, int choiceNumber) {
		System.out.println(name+"のファンファーレ発動！");

		//choiceNumber=99の時は選択対象がいないことを表す。
		//豪拳やエメラダはスペルと違ってfanfareの選択対象がいなくてもplayすることができる。
		//myself→いないなら100を入力する。
		//enemy→choice可能な対象が1つもないなら初期値の99がchoiceNumberになる。
		if(choiceNumber!=99) {
			noTurnLeader.getFieldList().get(choiceNumber).die(noTurnLeader, turnLeader,choiceNumber);
		}

		if(!turnLeader.isRevenge()) turnLeader.setLifeCount(turnLeader.getLifeCount()-2);   //前の効果の発動に関係なく発動させてる。
	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;
		if (choiceNumber > noTurnLeader.getFieldList().size() - 1) {
			System.out.println("フィールド枚数は" + noTurnLeader.getFieldList().size() + "枚です。もう一度入力して下さい。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).getClas() != 'f') {
			System.out.println("フォロワーを選択してください。");
			result = false;
		} else if (noTurnLeader.getFieldList().get(choiceNumber).isAmbush()) { // 攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}else if(noTurnLeader.getFieldList().get(choiceNumber).getClas()!='f'||noTurnLeader.getFieldList().get(choiceNumber).getHp()>3) {  //&&→||
			System.out.println("体力3以下の相手フォロワーを選択してください");
			result = false;
		} 
		return result;
	}

}
