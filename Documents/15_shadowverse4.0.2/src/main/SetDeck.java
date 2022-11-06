package main;

import elf.AncientElf;
import elf.BabyElfMay;
import elf.CrystaliaTia;
import elf.ElfGirlLiza;
import elf.ElfKnightCynthia;
import elf.ElvenPrincessMage;
import elf.FairyCircle;
import elf.FairyWisperer;
import elf.GlimmeringWings;
import elf.NaturesGuidance;
import elf.Rhinoceroach;
import elf.SylvanJustice;
import elf.WaterFairy;
import neutral.ActressFeria;
import neutral.AliceWonderlandExplorer;
import neutral.AngelicBarrage;
import neutral.GoblinLeader;
import neutral.GoblinMage;
import neutral.GrimnirWarCyclone;
import neutral.LyrialCelestialArcher;
import neutral.Rapunzel;
import neutral.WindGod;
import vampire.Alucard;
import vampire.Baphomet;
import vampire.BigKnuckleBodyguard;
import vampire.DiabolicDrain;
import vampire.EmeraldaDemonicOfficer;
import vampire.ScarletSabreur;
import vampire.SpawnOfTheAbyss;
import vampire.Tove;

public class SetDeck{

public static void execute(Leader myself , Leader enemy) {

		myself.setRank("ヴァンパイア");
		enemy.setRank("エルフ");


		for(int i=0;i<3;i++) {
			myself.getDeckList()[i]=new LyrialCelestialArcher();
		}
		for(int i=3;i<6;i++) {
			myself.getDeckList()[i]=new ActressFeria();
		}
		for(int i=6;i<9;i++) {
			myself.getDeckList()[i]=new Baphomet();
		}
		for(int i=9;i<12;i++) {
			myself.getDeckList()[i]=new Tove();
		}
		for(int i=12;i<14;i++) {
			myself.getDeckList()[i]=new GoblinMage();
		}
		for(int i=14;i<17;i++) {
			myself.getDeckList()[i]=new GrimnirWarCyclone();
		}
		for(int i=17;i<19;i++) {
			myself.getDeckList()[i]=new Rapunzel();
		}
		for(int i=19;i<22;i++) {
			myself.getDeckList()[i]=new GoblinLeader();
		}
		for(int i=22;i<25;i++) {
			myself.getDeckList()[i]=new AliceWonderlandExplorer();
		}
		for(int i=25;i<28;i++) {
			myself.getDeckList()[i]=new BigKnuckleBodyguard();
		}
		for(int i=28;i<31;i++) {
			myself.getDeckList()[i]=new ScarletSabreur();
		}
		for(int i=31;i<33;i++) {
			myself.getDeckList()[i]=new DiabolicDrain();
		}
		for(int i=33;i<35;i++) {
			myself.getDeckList()[i]=new Alucard();
		}
		for(int i=35;i<37;i++) {
			myself.getDeckList()[i]=new EmeraldaDemonicOfficer();
		}
		for(int i=37;i<40;i++) {
			myself.getDeckList()[i]=new SpawnOfTheAbyss();
		}


		for(int i=0;i<3;i++){    //ここ怖いな→どこやろ
			enemy.getDeckList()[i]=new WaterFairy();
		}
		for(int i=3;i<6;i++) {
			enemy.getDeckList()[i]=new BabyElfMay();
		}
		for(int i=6;i<9;i++) {
			enemy.getDeckList()[i]=new NaturesGuidance();
		}
		for(int i=9;i<12;i++) {
			enemy.getDeckList()[i]=new FairyCircle();
		}
		for(int i=12;i<15;i++) {
			enemy.getDeckList()[i]=new FairyWisperer();
		}
		for(int i=15;i<18;i++) {
			enemy.getDeckList()[i]=new Rhinoceroach();
		}
		for(int i=18;i<21;i++) {
			enemy.getDeckList()[i]=new ElfGirlLiza();
		}
		for(int i=21;i<24;i++) {
			enemy.getDeckList()[i]=new SylvanJustice();
		}
		for(int i=24;i<26;i++) {
			enemy.getDeckList()[i]=new GlimmeringWings();
		}
		for(int i=26;i<27;i++) {
			enemy.getDeckList()[i]=new AngelicBarrage();
		}
		for(int i=27;i<30;i++) {
			enemy.getDeckList()[i]=new AncientElf();
		}
		for(int i=30;i<33;i++) {
			enemy.getDeckList()[i]=new ElvenPrincessMage();
		}
		for(int i=33;i<34;i++) {
			enemy.getDeckList()[i]=new WindGod();
		}
		for(int i=34;i<37;i++) {
			enemy.getDeckList()[i]=new CrystaliaTia();
		}
		for(int i=37;i<40;i++) {
			enemy.getDeckList()[i]=new ElfKnightCynthia();
		}
	}

}
