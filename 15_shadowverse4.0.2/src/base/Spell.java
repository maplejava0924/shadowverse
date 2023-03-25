package base;

import main.Leader;

public class Spell extends Card {

	public Spell() {
		clas = 's';
	}
	
	@Override
	public void play(Leader turnLeader, Leader noTurnLeader, int playNumber, int choiceNumber) { // a:playする手札の配列要素
																									// p:プレイヤーorコンピュータ
		playOperation(turnLeader, noTurnLeader, playNumber);

		// スペルの効果
		this.spellEffect(turnLeader, noTurnLeader, choiceNumber);
		
		//スペルはプレイしたら必ず墓地に行く
		turnLeader.setCemetery(turnLeader.getCemetery() + 1);

	}

	// スペルの効果メソッド
	public void spellEffect(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

	}

}
