package com.tatesuke.kazuate;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

public class KazuateModelImpl implements KazuateModel {

	private State state;

	private int max;

	private int min;

	private int answer;

	private int correctAnswer;

	@Inject
	public KazuateModelImpl(
			@Named(InjectNames.MIN) int min,
			@Named(InjectNames.MAX) int max,
			@Named(InjectNames.ANSWER) int answer) {
		this.max = max;
		this.min = min;
		this.answer = answer;
		reset();
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public void setAnswer(int answer) {
		this.answer = answer;
	}

	@Override
	public void check() {
		if (correctAnswer == answer) {
			state = State.CORRECT;
		} else if (correctAnswer < answer) {
			state = State.BIGGER;
		} else {
			state = State.SMALLER;
		}
	}

	@Override
	public State getState() {
		return state;
	}

	@Override
	public void reset() {
		correctAnswer = new Random().nextInt(max - min + 1) + min;
		state = State.INIT;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public int getAnswer() {
		return answer;
	}

}
