package cn;

import java.awt.Color;

import javax.swing.JLabel;

public class Player implements Cloneable{
	public int score;
	public String name;
	public Player(String name){
		this.name=name;
		this.score=0;
	}

	public int getScore() {
		return this.score;
	}
	public String getName() {
		return this.name;
	}
	public void setScore(int score) {
		this.score=score;
	}
	public void setName(String name) {
		this.name=name;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Player)super.clone();
	}
}


