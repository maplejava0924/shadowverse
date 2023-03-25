package main;


import elf.Ancient_elf;
import elf.Crystalia_tia;
import elf.Elf_child_may;
import elf.Elf_girl_liza;
import elf.Elf_knight_cynthia;
import elf.Elven_princess_mage;
import elf.Fairy_circle;
import elf.Fairy_whisperer;
import elf.Glimmering_wings;
import elf.Natures_guidance;
import elf.Rhinoceroach;
import elf.Sylvan_justice;
import elf.Water_fairy;
import neutral.Actress_feria;
import neutral.Alice_wonderland_explorer;
import neutral.Angelic_barrage;
import neutral.Goblin_leader;
import neutral.Goblin_mage;
import neutral.Grimnir_war_cyclone;
import neutral.Lyrial_celestial_archer;
import neutral.Rapunzel;
import neutral.Wind_god;
import vampire.Alucard;
import vampire.Baphomet;
import vampire.Big_knuckle_bodyguard;
import vampire.Diabolic_drain;
import vampire.Emeralda_demonic_officer;
import vampire.Scarlet_sabreur;
import vampire.Spawn_of_the_abyss;
import vampire.Tove;

public class SetDeck{

public static void execute(Leader myself , Leader enemy) {

		myself.setRank("ヴァンパイア");
		enemy.setRank("エルフ");

		for(int i=0;i<3;i++) {
			myself.getDeckList().add(new Lyrial_celestial_archer());
		}
		for(int i=3;i<6;i++) {
			myself.getDeckList().add(new Actress_feria());
		}
		for(int i=6;i<9;i++) {
			myself.getDeckList().add(new Baphomet());
		}
		for(int i=9;i<12;i++) {
			myself.getDeckList().add(new Tove());
		}
		for(int i=12;i<14;i++) {
			myself.getDeckList().add(new Goblin_mage());
		}
		for(int i=14;i<17;i++) {
			myself.getDeckList().add(new Grimnir_war_cyclone());
		}
		for(int i=17;i<19;i++) {
			myself.getDeckList().add(new Rapunzel());
		}
		for(int i=19;i<22;i++) {
			myself.getDeckList().add(new Goblin_leader());
		}
		for(int i=22;i<25;i++) {
			myself.getDeckList().add(new Alice_wonderland_explorer());
		}
		for(int i=25;i<28;i++) {
			myself.getDeckList().add(new Big_knuckle_bodyguard());
		}
		for(int i=28;i<31;i++) {
			myself.getDeckList().add(new Scarlet_sabreur());
		}
		for(int i=31;i<33;i++) {
			myself.getDeckList().add(new Diabolic_drain());
		}
		for(int i=33;i<35;i++) {
			myself.getDeckList().add(new Alucard());
		}
		for(int i=35;i<37;i++) {
			myself.getDeckList().add(new Emeralda_demonic_officer());
		}
		for(int i=37;i<40;i++) {
			myself.getDeckList().add(new Spawn_of_the_abyss());
		}


		for(int i=0;i<3;i++){    //ここ怖いな→どこやろ
			enemy.getDeckList().add(new Water_fairy());
		}
		for(int i=3;i<6;i++) {
			enemy.getDeckList().add(new Elf_child_may());
		}
		for(int i=6;i<9;i++) {
			enemy.getDeckList().add(new Natures_guidance());
		}
		for(int i=9;i<12;i++) {
			enemy.getDeckList().add(new Fairy_circle());
		}
		for(int i=12;i<15;i++) {
			enemy.getDeckList().add(new Fairy_whisperer());
		}
		for(int i=15;i<18;i++) {
			enemy.getDeckList().add(new Rhinoceroach());
		}
		for(int i=18;i<21;i++) {
			enemy.getDeckList().add(new Elf_girl_liza());
		}
		for(int i=21;i<24;i++) {
			enemy.getDeckList().add(new Sylvan_justice());
		}
		for(int i=24;i<26;i++) {
			enemy.getDeckList().add(new Glimmering_wings());
		}
		for(int i=26;i<27;i++) {
			enemy.getDeckList().add(new Angelic_barrage());
		}
		for(int i=27;i<30;i++) {
			enemy.getDeckList().add(new Ancient_elf());
		}
		for(int i=30;i<33;i++) {
			enemy.getDeckList().add(new Elven_princess_mage());
		}
		for(int i=33;i<34;i++) {
			enemy.getDeckList().add(new Wind_god());
		}
		for(int i=34;i<37;i++) {
			enemy.getDeckList().add(new Crystalia_tia());
		}
		for(int i=37;i<40;i++) {
			enemy.getDeckList().add(new Elf_knight_cynthia());
		}
	}

}
