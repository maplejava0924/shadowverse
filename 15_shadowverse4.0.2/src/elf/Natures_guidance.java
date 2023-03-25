package elf;

import java.util.ArrayList;
import java.util.List;

import base.Card;
import base.Spell;
import main.Leader;

public class Natures_guidance extends Spell {

	public Natures_guidance() {
		name = "自然の導き";
		rank = "elf";
		cost = 1;
		needPlayChoiceCount = 1;

	}

	@Override
	public void spellEffect(Leader turnLeader, Leader noTurnLeader,int choiceNumber)  {

		System.out.println(name + "を唱えた！");

		if (turnLeader.getFieldList().get(choiceNumber).isWard())
			turnLeader.setWardCount(turnLeader.getWardCount() - 1); // 忘れずに！
		turnLeader.getFieldList().get(choiceNumber).reset(); // リセットしないと手札戻して出した時にバフとかが引き継がれちゃう。
		
		List<Card> cardList = new ArrayList<Card>();
		
		cardList.add(turnLeader.getFieldList().get(choiceNumber));
				
		turnLeader.handSet(turnLeader, noTurnLeader, cardList);
		
		// 手札に戻したnの手札をリストから削除する。
		//Listの機能で勝手に間はつめられる。
		turnLeader.getFieldList().remove(choiceNumber);

		for (int i = 0; i < turnLeader.getFieldList().size() - choiceNumber - 1; i++) {
			// フィールド番号のみずらす。
			turnLeader.getFieldList().get(choiceNumber + i)
					.setfN(turnLeader.getFieldList().get(choiceNumber + i).getfN() - 1);
		}

		if (turnLeader.getHandList().size() <= MAX_HAND - 1) {
			turnLeader.draw(turnLeader, noTurnLeader);
		} else if (turnLeader.getHandList().size() == MAX_HAND) {
			Leader.NextCard(turnLeader.getDeckList());
			turnLeader.setCemetery(turnLeader.getCemetery() + 1);
		}

	}

	@Override
	public boolean canPlay(Leader turnLeader, Leader noTurnLeader) {

		boolean result = true;

		// 自分のフォロワーいないと詠唱できない。
		if (turnLeader.getFieldList().size() <= 0) {
			System.out.println("自分のフィールドに選択できるカードがありません。");
			result = false;
		}

		return result;

	}

	@Override
	public boolean canPlayChoice(Leader turnLeader, Leader noTurnLeader, int choiceNumber) {

		boolean result = true;
		if (choiceNumber > turnLeader.getFieldList().size() - 1) {
			System.out.println("自分のフィールド枚数は" + turnLeader.getFieldList().size() + "枚です。もう一度入力して下さい。");
			result = false;
		}
		return result;
	}

}
