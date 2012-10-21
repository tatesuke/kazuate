package com.tatesuke.kazuate;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(String.class).annotatedWith(Names.named("name")).toInstance("まつたけ");
				bind(int.class).annotatedWith(Names.named("price")).toInstance(5000);
				bind(int.class).annotatedWith(Names.named("id")).toInstance(1);
			}
		});
	}
}
