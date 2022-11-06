package main;

import java.util.ArrayList;
import java.util.List;

import atlib.Input;
import base.Card;

public class Exec {

	static Leader myself;
	static Leader enemy;
	static Leader turnLeader;
	static Leader noTurnLeader;
	static int maxValue = -999999;
	static List<List<String>> bestList = new ArrayList<List<String>>();

	static void play() {
		int n;
		while ((n = Input.getInt("手札の中のどれをplayしますか？(ex:1)or戻りたいときは0を入力して下さい。")) != 0) {
			if (n < 0) {
				System.out.println("負の数は無効です。");
				continue;
			} else if (n > myself.getHandCount()) {
				System.out.println("手札枚数は" + myself.getHandCount() + "枚です。もう一度入力して下さい。");
				continue;
			}
			if (!majidePlayable(myself, n - 1)) {
				continue;
			}

			Card playCard = myself.getHandList()[n - 1];
			int choiceNumber = 0;
			if (playCard.getNeedPlayChoiceCount() > 0) {
				choiceNumber = playCard.playChoice(turnLeader, noTurnLeader);
			}

			playCard.play(turnLeader, noTurnLeader, n - 1, choiceNumber);

			break;
		}
	}

	// majideとsaiteigenの違いは、例えばplay時なら手札の中身までは見ないのがsaiteigen。
	// 手札の中身まで一つ一つ判定して、"マジで"play可能なのかを判断するのがmajidePlayable。
	// 初めからmajide判定していいのかな。いいような気もする。最終的にそのほうが処理速度が速いならそうして欲しい。
	//いい感じにプレイヤー側でもコンピュータ側の探索でも使えるようにしている。
	static boolean saiteigenPlayable(Leader leader) {

		boolean result = true;

		if (leader.getPpRest() == 0) {
			System.out.println("残りPPがありません。playできません。");
			result = false;
		}

		if (leader.getHandCount() == 0) {
			System.out.println("手札がありません。playできません。");
			result = false;
		}

		return result;

	}

	// playNumberをturnLeaderでplay可能かどうかを判定する。
	static boolean majidePlayable(Leader leader, int playNumber) {

		boolean result = true;

		Card playCard = leader.getHandList()[playNumber];

		if (playCard.getCost() > leader.getPpRest()) {
			System.out.println("コストが残りPPより大きいのでplayできません。");
			result = false;
		} else if (playCard.getClas() == 'f' && leader.getFieldCount() == 5) { // 選択した手札がフォロワーでかつフィールド埋まってたら～
			System.out.println("フィールドにおけるのは5枚までです。");
			result = false;
		} else if (!playCard.canPlay(leader, noTurnLeader)) {
			System.out.println("play時の条件を満たしていません");
			result = false;
		}

		return result;

	}

	static boolean saiteigenAttackable(Leader leader) {

		boolean result = true;

		if (leader.getFieldCount() == 0) {
			System.out.println("自陣にフォロワーがいません。attackできません。");
			result = false;
		}

		return result;
	}

	// attackNumberをturnLeaderでattack可能かどうかを判定する。
	static boolean majideAttackable(Leader leader, int attackNumber) {

		boolean result = true;

		// 昔は↓のようにしていたが、例えば殴った後に進化すればもう一回殴れるので変えた。念のため残しておく。
		// !turnLeader.getFieldList()[attackNumber].isAt() &&
		// !turnLeader.getFieldList()[attackNumber].isRush()
		if (!leader.getFieldList()[attackNumber].canAttack()) {
			System.out.println("このターンに出したフォロワーorこのターン既に攻撃したフォロワーは攻撃できません。");
			result = false;
		} else if (leader.getFieldList()[attackNumber].isChain()) { // トーヴ・ラプンツェルの通常時効果
			System.out.println("このフォロワーは攻撃不能です。");
			result = false;
		}

		return result;

	}

	// attackSakiNumberをturnLeaderのattackNumberでattack可能かどうかを判定する。
	static boolean majideAttackSakiFollower(Leader turnLeader, Leader noTurnLeader, int attackNumber,
			int attackSakiNumber) {

		boolean result = true;
		if (noTurnLeader.getWardCount() > 0 && !noTurnLeader.getFieldList()[attackSakiNumber].isWard()) {
			System.out.println("守護持ちフォロワーしか攻撃できません。");
			result = false;
		} else if (noTurnLeader.getFieldList()[attackSakiNumber].isAmbush()) { // 攻撃先のフォロワーが潜伏持ちなら～
			System.out.println("潜伏フォロワーは選択できません。");
			result = false;
		}

		return result;

	}

	// attackNumberでturnLeaderをattack可能かどうかを判定する。
	static boolean majideAttackSakiLeader(Leader turnLeader, Leader noTurnLeader, int attackNumber) {

		boolean result = true;
		if (noTurnLeader.getWardCount() > 0) {
			System.out.println("守護持ちフォロワーしか攻撃できません。");
			result = false;
		} else if (turnLeader.getFieldList()[attackNumber].isRush()
				&& !turnLeader.getFieldList()[attackNumber].isStorm()) { // 突進だけ持ってて疾走じゃない場合
			System.out.println("突進or進化後フォロワーはリーダーには攻撃できません。");
			result = false;
		}

		return result;

	}

	static boolean saiteigenEvolvable(Leader turnLeader) {

		boolean result = true;

		if (turnLeader.isSenkou()) { // プレイヤー:先攻 コンピュータ:後攻なら～
			if (turnLeader.getTurnCount() < 5) { // 5ターン目より前なら
				System.out.println("先攻:5ターン目まで進化不可です。");
				result = false;
			}
		} else { // プレイヤー:後攻 コンピュータ:先攻なら～
			if (turnLeader.getTurnCount() < 4) { // 4ターン目より前なら
				System.out.println("後攻:4ターン目まで進化不可です。");
				result = false;
			}
		}
		if (turnLeader.getFieldCount() == 0) {
			System.out.println("自陣にフォロワーがいません。evolveできません。");
			result = false;
		} else if (turnLeader.getEpRest() == 0) { // 残り進化ポイントが0なら～
			System.out.println("残り進化ポイントが０です。");
			result = false;
		} else if (turnLeader.isEvolved()) { // プレイヤーがこのターン中進化していたなら～
			System.out.println("このターン中既に進化しているので進化不可です。");
			result = false;
		}

		return result;
	}

	static boolean majideEvolvable(Leader turnLeader, Leader noTurnLeader, int evolveNumber) {

		boolean result = true;
		if (turnLeader.getFieldList()[evolveNumber].isEv()) {
			System.out.println("既に進化したフォロワーは進化不可です。");
			result = false;
		}

		return result;

	}

	static void attack() {
		int n;
		///// 自分のフィールドからどのカードを攻撃させるか選択する/////
		while ((n = Input.getInt("どのカードをattackさせますか？(ex:1)or戻りたいときは0を入力して下さい。")) != 0) {
			if (n < 0) {
				System.out.println("負の数は無効です。");
				continue;
			} else if (n > myself.getFieldCount()) {
				System.out.println("フィールドのカードは" + myself.getFieldCount() + "枚です。もう一度入力して下さい。");
				continue;
			}
			if (!majideAttackable(myself, n - 1)) {
				continue;
			}
			///// 相手フィールドの何に攻撃するか選択する/////
			int a = Input.getInt("どのカードorリーダーにattackしますか？1～6で選択して下さい(6:リーダー)");
			///// フィールドカードへの攻撃/////
			if (a == 1 || a == 2 || a == 3 || a == 4 || a == 5) {
				if (enemy.getFieldCount() == 0) {
					System.out.println("相手のフィールドにフォロワーがいません。attackできません。");
					continue;
				} else if (a <= 0) {
					System.out.println("0と負の数は無効です。");
					continue;
				} else if (a > enemy.getFieldCount()) {
					System.out.println("フィールドのカードは" + enemy.getFieldCount() + "枚です。もう一度入力して下さい。");
					continue;
				}
				if (!majideAttackSakiFollower(myself, enemy, n - 1, a - 1)) {
					continue;
				}

				myself.getFieldList()[n - 1].attack(turnLeader, noTurnLeader, n - 1, a - 1);
				break;
				///// リーダーへの攻撃/////
			} else if (a == 6) {

				if (!majideAttackSakiLeader(myself, enemy, n - 1)) {
					continue;
				}
				myself.getFieldList()[n - 1].attack(turnLeader, noTurnLeader, n - 1, a - 1);
				break;
				///// 違う記号を入力した時/////
			} else {
				System.out.println("1～6で入力して下さい。1～5はフォロワー、6はリーダーという意味です)");
				continue;
			}
		}

	}

	static void evolve() {
		int n;
		while ((n = Input.getInt("手札の中のどれをevolveしますか？(ex:1)or戻りたいときは0を入力して下さい。")) != 0) {
			if (n < 0) {
				System.out.println("負の数は無効です。");
				continue;
			} else if (n > myself.getFieldCount()) {
				System.out.println("フィールドのカードは" + myself.getFieldCount() + "枚です。もう一度入力して下さい。");
				continue;
			}
			if (!majideEvolvable(myself, enemy, n - 1)) {
				continue;
			}

			Card evolveCard = myself.getFieldList()[n - 1];
			int choiceNumber = 0;

			if (evolveCard.getNeedEvolveChoiceCount() > 0) {
				choiceNumber = evolveCard.evolveChoice(turnLeader, noTurnLeader);
			}

			System.out.println(myself.getFieldList()[n - 1].getName() + "が進化！");
			myself.getFieldList()[n - 1].evolve(turnLeader, noTurnLeader, n - 1, choiceNumber); // 進化でap,hp強化,
																								// 進化有無変数ev=true,
																								// 突進変数rush=true

			break;
		}
	}

	static void action() {
		String s = "";
		while (true) {
			s = Input.getString("何をしますか？(play/attack/evolve/end)");
			if (s != null && s.equals("play")) {
				if (saiteigenPlayable(turnLeader)) {
					play();
				}

			} else if (s != null && s.equals("attack")) {
				if (saiteigenAttackable(turnLeader)) {
					attack();
				}

			} else if (s != null && s.equals("evolve")) {
				if (saiteigenEvolvable(turnLeader)) {
					evolve();
				}

			} else if (s != null && s.equals("end")) {

				turnEnd(turnLeader);

				myself.setMyturn(false); // いまはいちおうこれにしておく。
				turnLeader = enemy;
				noTurnLeader = myself;
				break; // ターン終了。endメソッドの役割とは。
			} else {
				System.out.println("【play/attack/evolve/endで入力してください】");
			}
			Show.execute(myself, enemy);
		}
	}

	static void turnEnd(Leader turnLeader) {
		
		for (int i = 0; i < turnLeader.getFieldCount(); i++) {
			
			// 自分の各フォロワーのターン終了時効果を発動する。
			turnLeader.getFieldList()[i].turnEndEffect(turnLeader, noTurnLeader);
			
			// 自分の各カードのターン終了時付与効果を発動する。
			for (int j = 0; j < turnLeader.getFieldList()[i].getTurnEndAddEffectList().size(); j++) {
				turnLeader.getFieldList()[i].getTurnEndAddEffectList().get(j).run(turnLeader, noTurnLeader);
			}
		}
		
		// リーダーに対するターン終了時付与効果を発動する。
		for (int i = 0; i < turnLeader.getTurnEndAddEffectList().size(); i++) {
			turnLeader.getTurnEndAddEffectList().get(i).run(turnLeader, noTurnLeader);
		}
		
		turnLeader.setPlayCount(0); // playした枚数をリセット。
		
	}
	
	static void enemyTansaku() {

		List<String> firstList = new ArrayList<String>();
		List<List<String>> savedoList = new ArrayList<List<String>>();

		firstList.add("nothing");
		System.out.println("hogeはじめます！");

		// 評価値を初期化する。
		//評価値がmaxValueより大きい時maxValueに評価値を保存するので、
		//そこそこ値が大きいと評価値がマイナスなこともよくあるのですごく小さい値を初期値にしている。
		//あまりいけてない。
		maxValue = -999999;

		// デバッグ用
		hogeExeCount = 0;

		hoge(myself, enemy, firstList, savedoList);

		System.out.println("hogeおわりました！");
		System.out.println("最大評価値：" + maxValue);
		System.out.println("ベストリスト：" + bestList.toString());

		Show.execute(myself, enemy);

		// 実際にベストリストの内容をmyself,enemyで実行する。
		for (int i = 0; i < bestList.size(); i++) {
			execute(myself, enemy, bestList.get(i));
			System.out.println("enemyの残りPPは　" + enemy.getPpRest());
		}

	}

	// デバッグ用
	static int hogeExeCount = 0;

	static void hoge(Leader myself, Leader enemy, List<String> nowdoList, List<List<String>> savedoList) {

		// デバッグ用
		hogeExeCount++;

		System.out.println("■■hogeスタート " + hogeExeCount + "回目");

		// クローンする（ちゃんとできてるか怪しい→後記：たぶんできてそう）。
		Leader vMyself = myself.clone();
		Leader vEnemy = enemy.clone();

		// savedoListを参照渡しにならないようにクローニングする。
		// 本当はnowdoListもそうしなきゃいけなそうだけどなんか大丈夫。
		List<List<String>> savedoListClone = new ArrayList<List<String>>();

		for (int i = 0; i < savedoList.size(); i++) {

			savedoListClone.add(savedoList.get(i));
		}

		System.out.println("nowdoListは、、" + nowdoList.toString());
		System.out.println("savedoListは、、" + savedoListClone.toString());

		System.out.println("vEnemy PP残り値：" + vEnemy.getPpRest() + "  PP最大値：" + vEnemy.getPpMax());

		// nowdoListの内容をみて今実行できるか判定して、できるなら～
		if (canExecute(vMyself, vEnemy, nowdoList)) {

			System.out.println("savedoList保存します:" + savedoListClone.toString());
			System.out.println("savedoList保存対象:" + nowdoList.toString());

			// savedoListに実際にdoした内容をいれるために、参照渡しにならないようにクローニングする。
			List<String> nowdoListClone = new ArrayList<String>();

			for (int i = 0; i < nowdoList.size(); i++) {

				nowdoListClone.add(nowdoList.get(i));

			}
			// savedoListに実際にdoした内容をいれる。
			savedoListClone.add(nowdoListClone);
			System.out.println("savedoList保存しました:" + savedoListClone.toString());

			// nowdoListの内容を与えられたLeaderたちで実行する。
			execute(vMyself, vEnemy, nowdoList);

			// 次の手を探すためnowdoListはクリアする。
			nowdoList.clear();
			nowdoList.add("nothing");

		}

		// nowdoListの次の選択肢(choices)を調査し、選択肢をリスト化して返す。
		List<List<String>> nextChoicesList = returnNextChoicesList(vMyself, vEnemy, nowdoList);
		System.out.println("choiceListは、、" + nextChoicesList.toString());

		// リストが空かどうか判定。空なら処理終了。
		while (nextChoicesList.size() != 0) {
			System.out.println("looooop");
			// リスト内の最小値をnextdoにセット
			List<String> nextdoList = nextChoicesList.get(0);

			// リスト内の最小値をremoveする。
			nextChoicesList.remove(0);

			// nextdoの値を判定する
			// endでないなら
			if (!nextdoList.get(0).equals("end")) {

				hoge(vMyself, vEnemy, nextdoList, savedoListClone);
				continue;

			} else { // endなら

				turnEnd(vEnemy);

				// vMyself, vEnemyを評価し、評価値を返す
				int nowValue = evaluate(vMyself, vEnemy);
				System.out.println("endします。");
				System.out.println("☆☆" + savedoListClone.toString() + "評価値：" + nowValue);

				// 評価値が今までの値より高いなら～
				if (nowValue > maxValue) {
					// 評価値の最大値を更新し、最も強い手としてbestListに手を保存する。
					maxValue = nowValue;
					bestList = savedoListClone;
				}
				// //評価値が今までの値と同じか低いなら何もしない（処理終了）
			}
		}

		System.out.println("hogeおわり");
	}

	// 敵視点で今の評価を行う。
	static int evaluate(Leader myself, Leader enemy) {

		int result = 0;

		int enemyValue = 0;

		for (int i = 0; i < enemy.getFieldCount(); i++) {

			Card fieldCard = enemy.getFieldList()[i];

			enemyValue += (fieldCard.getAp() + fieldCard.getHp()) * 10;

		}

		enemyValue += enemy.getFieldCount() * 10;

		enemyValue += enemy.getLifeCount() * 7;

		enemyValue += enemy.getHandCount() * 5;

		enemyValue += enemy.getEpRest() * 60;

		enemyValue += enemy.getPpMax() * 5;

		enemyValue += enemy.getWardCount() * 9;

		int myselfValue = 0;

		for (int i = 0; i < myself.getFieldCount(); i++) {

			Card fieldCard = myself.getFieldList()[i];

			myselfValue += (fieldCard.getAp() + fieldCard.getHp()) * 20;

		}

		myselfValue += myself.getFieldCount() * 10;

		myselfValue += myself.getLifeCount() * 7;

		myselfValue += myself.getHandCount() * 5;

		myselfValue += myself.getPpMax() * 5;

		myselfValue += myself.getWardCount() * 9;

		result = enemyValue - myselfValue;

		return result;
	}

	// nowdoListの内容をenemyで実行する。
	static void execute(Leader myself, Leader enemy, List<String> nowdoList) {

		String nextdo = nowdoList.get(0);
		int executeNumber = Integer.parseInt(nowdoList.get(1));
		int choiceNumber = 99;

		switch (nextdo) {
		case "play":

			// choiceNumberの初期値を99にすることで、豪拳やエメラダなど、チョイスしなくてもplayできるカードに対応する。

			if (nowdoList.size() >= 3) {
				choiceNumber = Integer.parseInt(nowdoList.get(2));
			}

			Card playCard = enemy.getHandList()[executeNumber];

			System.out.println(
					"いま、" + nextdo + " " + executeNumber + " " + choiceNumber + "します！（" + playCard.getName() + "）");
			playCard.play(enemy, myself, executeNumber, choiceNumber);

			break;

		case "attack":

			int attackSakiNumber = Integer.parseInt(nowdoList.get(2));
			Card attackCard = enemy.getFieldList()[executeNumber];

			System.out.println("いま、" + nextdo + " " + executeNumber + " " + attackSakiNumber + "します！（"
					+ attackCard.getName() + "）");
			attackCard.attack(enemy, myself, executeNumber, attackSakiNumber);

			break;

		case "evolve":

			// choiceNumberの初期値を99にすることで、チョイスしなくてもevolveできるカードに対応する。
			// 現状そのようなカードは入れてない。

			if (nowdoList.size() >= 3) {
				choiceNumber = Integer.parseInt(nowdoList.get(2));
			}

			Card evolveCard = enemy.getFieldList()[executeNumber];

			System.out.println(
					"いま、" + nextdo + " " + executeNumber + " " + choiceNumber + "します！ (" + evolveCard.getName() + "）");
			evolveCard.evolve(enemy, myself, executeNumber, choiceNumber);

			break;
		}
	}

	// nowdoListの次の選択肢を調査し、選択肢をリスト化して返す。
	static List<List<String>> returnNextChoicesList(Leader myself, Leader enemy, List<String> nowdoList) {

		List<List<String>> choiceList = new ArrayList<List<String>>();

		String nextdo = nowdoList.get(0);

		switch (nextdo) {

		case "nothing":

			if (saiteigenPlayable(enemy)) {

				List<String> nextdoList = new ArrayList<String>();
				nextdoList.add("play");
				choiceList.add(nextdoList);

			}
			if (saiteigenAttackable(enemy)) {
				List<String> nextdoList = new ArrayList<String>();

				nextdoList.add("attack");
				choiceList.add(nextdoList);
			}
			if (saiteigenEvolvable(enemy)) {
				List<String> nextdoList = new ArrayList<String>();
				nextdoList.add("evolve");
				choiceList.add(nextdoList);
			}

			List<String> nextdoList = new ArrayList<String>();
			nextdoList.add("end");
			choiceList.add(nextdoList);

			break;

		case "play":

			// playする対象すら決まってない状態なら～
			if (nowdoList.size() == 1) {
				// playする対象を決めてあげる。
				for (int i = 0; i < enemy.getHandCount(); i++) {

					if (majidePlayable(enemy, i)) {

						// いま探索リストに入れようとしているカード（enemy.getHandList()[i]）と探索リスト（choiceList）に既に入っているカードを比べて、
						// equlasの結果が同じなら探索リストに入れない。
						boolean compareResult = false;

						// play時効果のiはフィールドともハンドとも確定してないからどうしよう
						for (int j = 0; j < choiceList.size(); j++) {

							List<String> nextdoList2 = choiceList.get(j);
							int m = Integer.valueOf(nextdoList2.get(nextdoList2.size() - 1));
							compareResult = enemy.getHandList()[m].equals(enemy.getHandList()[i]);

							if (compareResult) {
								System.out.println(
										enemy.getHandList()[m].getName() + "　と　" + enemy.getHandList()[i].getName()
												+ "は同じカード！" + nextdo + "　choiceListにいれません！！");
							}

						}

						if (!compareResult) {
							List<String> nextdoList1 = new ArrayList<String>();
							nextdoList1.add(nextdo);
							nextdoList1.add(String.valueOf(i));
							choiceList.add(nextdoList1);
						}
					}

				}
			} else { // 1以外なら（つまり2以上なら。想定では0以下はこない認識）
				// いまのところ選択肢が一つの時しかカバーできていない。
				// もしもっと拡張するなら、nowdoListをnextdoListにforのたびにクローンすればよい。
				// そこに新しいiをaddしてそのnextdoListをchoiceListにaddする形にすればよい。
				int playNumber = Integer.parseInt(nowdoList.get(1));
				Card playCard = enemy.getHandList()[playNumber];

				// 大体play時効果の宛先は6くらいまでじゃない？っていう。
				// 逆にあんまり大きい数字指定してもどっかでNullPointerになりそう。
				for (int i = 0; i < 6; i++) {

					if (playCard.canPlayChoice(enemy, myself, i)) {
						List<String> nextdoList1 = new ArrayList<String>();
						nextdoList1.add(nextdo);
						nextdoList1.add(String.valueOf(playNumber));
						nextdoList1.add(String.valueOf(i));
						choiceList.add(nextdoList1);
					}

				}

				// もし選択肢がないなら～
				// 豪拳やエメラダなど、チョイスは必要だが、必須ではなくチョイス対象がいなくてもplayできるもののための処理。
				if (choiceList.size() == 0) {
					List<String> nextdoList1 = new ArrayList<String>();
					System.out.println("□playチョイスの選択肢がありません！！！");
					nextdoList1.add(nextdo);
					nextdoList1.add(String.valueOf(playNumber));
					choiceList.add(nextdoList1);

				}

				// choiceカウントを減らす。
				playCard.setNeedPlayChoiceCount(playCard.getNeedPlayChoiceCount() - 1);

			}
			break;
		case "attack":

			// attackするフォロワーすら決まってない状態なら～
			if (nowdoList.size() == 1) {
				// attackするフォロワーを決めてあげる。
				for (int i = 0; i < enemy.getFieldCount(); i++) {

					if (majideAttackable(enemy, i)) {

						boolean compareResult = false;

						for (int j = 0; j < choiceList.size(); j++) {

							List<String> nextdoList2 = choiceList.get(j);
							int m = Integer.valueOf(nextdoList2.get(nextdoList2.size() - 1));
							compareResult = enemy.getFieldList()[m].equals(enemy.getFieldList()[i]);

							if (compareResult) {
								System.out.println(
										enemy.getFieldList()[m].getName() + "　と　" + enemy.getFieldList()[i].getName()
												+ "は同じカード！" + nextdo + "　choiceListにいれません！！");
							}

						}

						if (!compareResult) {
							List<String> nextdoList1 = new ArrayList<String>();
							nextdoList1.add(nextdo);
							nextdoList1.add(String.valueOf(i));
							choiceList.add(nextdoList1);
						}
					}

				}
			} else if (nowdoList.size() == 2) {
				// attack先の対象を決めてあげる。

				// attackするフォロワーのフィールド番号。
				int attackNumber = Integer.parseInt(nowdoList.get(1));

				// attack先を決めるので、enemyではなくmyselfのfieldCount。
				for (int i = 0; i < myself.getFieldCount(); i++) {

					if (majideAttackSakiFollower(enemy, myself, attackNumber, i)) {

						boolean compareResult = false;

						for (int j = 0; j < choiceList.size(); j++) {

							List<String> nextdoList2 = choiceList.get(j);
							int m = Integer.valueOf(nextdoList2.get(nextdoList2.size() - 1));
							compareResult = myself.getFieldList()[m].equals(myself.getFieldList()[i]);

							if (compareResult) {
								System.out.println(
										myself.getFieldList()[m].getName() + "　と　" + myself.getFieldList()[i].getName()
												+ "は同じカード！" + nextdo + "Saki　choiceListにいれません！！");
							}

						}

						if (!compareResult) {
							List<String> nextdoList1 = new ArrayList<String>();
							nextdoList1.add(nextdo);
							nextdoList1.add(String.valueOf(attackNumber));
							nextdoList1.add(String.valueOf(i));
							choiceList.add(nextdoList1);
						}
					}

				}
				if (majideAttackSakiLeader(enemy, myself, attackNumber)) {
					List<String> nextdoList1 = new ArrayList<String>();
					nextdoList1.add(nextdo);
					nextdoList1.add(String.valueOf(attackNumber));
					nextdoList1.add(String.valueOf(5)); // リーダーへの攻撃を表すのは5。
					choiceList.add(nextdoList1);
				}

			}
			// 現状、攻撃時に何かをチョイスする効果をもつフォロワーはいないと思う。
			// なので、1,2以外の処理はいらないはず。

			break;

		case "evolve":

			// evolveする対象すら決まってない状態なら～
			if (nowdoList.size() == 1) {
				// evolveする対象を決めてあげる。
				for (int i = 0; i < enemy.getFieldCount(); i++) {

					if (majideEvolvable(enemy, myself, i)) {

						boolean compareResult = false;

						for (int j = 0; j < choiceList.size(); j++) {

							List<String> nextdoList2 = choiceList.get(j);
							int m = Integer.valueOf(nextdoList2.get(nextdoList2.size() - 1));
							compareResult = enemy.getFieldList()[m].equals(enemy.getFieldList()[i]);

							if (compareResult) {
								System.out.println(
										enemy.getFieldList()[m].getName() + "　と　" + enemy.getFieldList()[i].getName()
												+ "は同じカード！" + nextdo + "　choiceListにいれません！！");
							}

						}

						if (!compareResult) {
							List<String> nextdoList1 = new ArrayList<String>();
							nextdoList1.add(nextdo);
							nextdoList1.add(String.valueOf(i));
							choiceList.add(nextdoList1);
						}
					}

				}
			} else { // 1以外なら（つまり2以上なら。想定では0以下はこない認識）
				// いまのところ選択肢が一つの時しかカバーできていない。

				int evolveNumber = Integer.parseInt(nowdoList.get(1));
				Card evolveCard = enemy.getFieldList()[evolveNumber];

				// 大体効果の対象は6くらいまでじゃない？っていう。
				// 逆にあんまり大きい数字指定してもどっかでNullPointerになりそう。
				for (int i = 0; i < 6; i++) {

					if (evolveCard.canEvolveChoice(enemy, myself, i)) {
						List<String> nextdoList1 = new ArrayList<String>();
						nextdoList1.add(nextdo);
						nextdoList1.add(String.valueOf(evolveNumber));
						nextdoList1.add(String.valueOf(i));
						choiceList.add(nextdoList1);
					}

				}

				// 現状、進化してチョイスするフォロワーのうち、チョイスしてもしなくてもいいフォロワーがいないので以下の処理は意味がないが、
				// playの時と同じようにチョイスできなくても進化は出来るフォロワーは今後出そうなので書いておく。
				// リリエルはリーダーには最低でもいつでも打てる。
				if (choiceList.size() == 0) {
					System.out.println("□進化チョイスの選択肢がありません！！！");
					List<String> nextdoList1 = new ArrayList<String>();
					nextdoList1.add(nextdo);
					nextdoList1.add(String.valueOf(evolveNumber));
					choiceList.add(nextdoList1);

				}

				// choiceカウントを減らす。
				evolveCard.setNeedEvolveChoiceCount(evolveCard.getNeedEvolveChoiceCount() - 1);

			}
			break;
		}

		return choiceList;
	}

	// nowdoListの内容がenemyにおいて実行可能かどうか判定する。
	static boolean canExecute(Leader myself, Leader enemy, List<String> nowdoList) {

		boolean result = true;

		// まだ何をするかしか決まっていない（もしくは"nothing"）で対象が決まってないなら～
		if (nowdoList.size() == 1) {
			// まだdoできない
			result = false;
		} else { // 1以外なら（つまり2以上なら。想定では0以下はこない認識）
			String nextdo = nowdoList.get(0);
			int choiceNumber = Integer.parseInt(nowdoList.get(1));

			switch (nextdo) {
			case "play":
				// playしようとしているカードを取得する。

				Card playCard = enemy.getHandList()[choiceNumber];

				// （前）もし今のnowdoListで決められている選択の数がplayしようとしているカードの、playに必要な選択肢の数より少ないなら～
				// （前）nowdoListとのサイズ比較の際には"play"と"[playする手札番号]"の分のサイズを引く（2引く）。
				// （前）if (nowdoList.size() - 2 < playCard.getNeedPlayChoiceCount()) {
				// →前はこうしていたが、豪拳やエメラダなど、一度チョイス可能な対象がいるか試しては欲しいが、いなくても
				// playできるカードのために、needPlayChoiceCountをチョイスの度に-1するよう変更した。

				// choiceがまだ必要なら～
				if (playCard.getNeedPlayChoiceCount() > 0) {
					// まだdoできない
					result = false;
				}
				break;

			case "attack":

				// attackNumber(攻撃するフォロワーのフィールド番号）が決まっただけなら～
				// 逆にattackの場合、攻撃時こうかでチョイスが発生するカードはない認識なので、攻撃元と攻撃先が決まってさえいればOKとしている。
				if (nowdoList.size() == 2) {
					// まだdoできない
					result = false;
				}

				break;

			case "evolve":
				// evolveしようとしているカードを取得する。
				Card evolveCard = enemy.getFieldList()[choiceNumber];

				// choiceがまだ必要なら～
				if (evolveCard.getNeedEvolveChoiceCount() > 0) {
					// まだdoできない
					result = false;
				}
				break;
			}
		}

		return result;
	}

	static void animation() {
		System.out.print("思考中…");
		for (int i = 0; i < 10; i++) {
			System.out.print("……");
			delay(50);
		}
		System.out.println();
	}

	static void delay(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static void cAction() {

		animation();
		enemyTansaku();
		Show.execute(myself, enemy);
		animation();

		turnEnd(turnLeader);

		myself.setMyturn(true);
		turnLeader = myself;
		noTurnLeader = enemy;
	}

	public static void decideSenkou() {
		int n = 1; // デバッグ用
		// int n = (int) (Math.random())*2; // 本番用
		if (n == 0) {
			myself.setMyturn(true);
			turnLeader = myself;
			noTurnLeader = enemy;
			myself.setSenkou(true); // プレイヤー:先攻 コンピュータ:後攻
			myself.setEpRest(2);
			enemy.setEpRest(3);
		} else {
			myself.setMyturn(false);
			turnLeader = enemy;
			noTurnLeader = myself;
			myself.setSenkou(false); // プレイヤー:後攻 コンピュータ:先攻
			myself.setEpRest(3);
			enemy.setEpRest(2);
		}

	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

		myself = new Leader();
		enemy = new Leader();

		decideSenkou();
		SetDeck.execute(myself, enemy);
		myself.deal();
		enemy.deal();

		while (myself.getLifeCount() > 0 && enemy.getLifeCount() > 0 && myself.getDeckRest() > 0
				&& enemy.getDeckRest() > 0) {

			turnLeader.start(turnLeader, noTurnLeader);
			Show.execute(myself, enemy);
			if (myself.isMyturn()) {
				System.out.println(turnLeader);
				action();
			} else {
				System.out.println(turnLeader);
				cAction();
			}
		}
		if (myself.getLifeCount() <= 0 || myself.getDeckRest() == 0) {
			System.out.println("You Lose");
		} else if (enemy.getLifeCount() <= 0 || enemy.getDeckRest() == 0) {
			System.out.println("You Win");
		}
	}

}
