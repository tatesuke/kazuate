package com.tatesuke.kazuate;

public interface KazuateModel {

	public enum State {INIT, BIGGER, SMALLER, CORRECT}
	public void check();
	public State getState();
	public void reset();
	public int getAnswer();
	void setAnswer(int value);
	int getMax();
	int getMin();

}
