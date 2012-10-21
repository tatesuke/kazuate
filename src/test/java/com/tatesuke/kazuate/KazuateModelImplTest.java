package com.tatesuke.kazuate;

import static org.junit.Assert.*;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.tatesuke.kazuate.KazuateModel.State;

import static org.hamcrest.Matchers.*;

public class KazuateModelImplTest {


	@Test
	public void コンストラクタのテスト() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		assertThat(model.getMax(), is(100));
		assertThat(model.getMin(), is(10));
		assertThat(model.getAnswer(), is(50));
		assertThat(model.getState(), is(State.INIT));
	}

	@Test
	public void コンストラクタのテスト_インジェクター() {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(int.class).annotatedWith(Names.named(InjectNames.MIN)).toInstance(30);
				bind(int.class).annotatedWith(Names.named(InjectNames.MAX)).toInstance(200);
				bind(int.class).annotatedWith(Names.named(InjectNames.ANSWER)).toInstance(100);
			}
		});
		KazuateModelImpl model = injector.getInstance(KazuateModelImpl.class);
		assertThat(model.getMax(), is(200));
		assertThat(model.getMin(), is(30));
		assertThat(model.getAnswer(), is(100));
		assertThat(model.getState(), is(State.INIT));
	}

	@Test
	public void setValueのテスト() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		model.setAnswer(25);
		assertThat(model.getAnswer(), is(25));
	}

	@Test
	public void correctAnswerが指定範囲でばらつくか_() {
		int min = 10;
		int max = 100;
		int iterate = 10000;
		double erroMargin = 0.35;

		int range = max - min + 1;
		int[] counts = new int[range];
		for (int i = 0; i < iterate; i++) {
			int correctAnswer = new KazuateModelImpl(min, max, 0).getCorrectAnswer();
			int index = correctAnswer - min;
			counts[index]++;
		}

		double trueValue = (double)iterate / range;
		for (int count : counts) {
			double error = Math.abs((trueValue - count) / trueValue);
			assertThat(error, is(lessThan(erroMargin)));
		}
	}

	@Test
	public void check_小さい() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		model.setCorrectAnswer(50);
		model.setAnswer(49);
		model.check();
		assertThat(model.getState(), is(State.SMALLER));
	}

	@Test
	public void check_大きい() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		model.setCorrectAnswer(50);
		model.setAnswer(51);
		model.check();
		assertThat(model.getState(), is(State.BIGGER));
	}

	@Test
	public void check_正解() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		model.setCorrectAnswer(50);
		model.setAnswer(50);
		model.check();
		assertThat(model.getState(), is(State.CORRECT));
	}

	@Test
	public void reset() {
		KazuateModelImpl model = new KazuateModelImpl(10, 100,  50);
		model.setCorrectAnswer(50);
		model.setAnswer(51);
		model.reset();
		assertThat(model.getState(), is(State.INIT));
	}

}
