package main;

@FunctionalInterface
public interface TurnEndAddEffect{

	void run(Leader turnLeader,Leader noTurnLeader);
}
