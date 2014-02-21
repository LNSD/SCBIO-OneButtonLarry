package org.scbio.onebuttonlarry;

public class Highscore {

	private long score;
	private String player;
	
	public Highscore(String player, long score){
		this.score = score;
		this.player = player;
	}
	
	public long getScore() {
		return score;
	}

	public String getPlayer() {
		return player;
	}

}
