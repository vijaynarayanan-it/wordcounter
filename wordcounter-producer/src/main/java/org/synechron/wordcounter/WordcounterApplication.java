package org.synechron.wordcounter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.synechron.wordcounter.service.impl.AddAndCountWordService;

@SpringBootApplication
@EnableEurekaClient
public class WordcounterApplication implements ApplicationRunner {
	private static final int DEFAULT_PARALLEL_COUNT = Runtime.getRuntime().availableProcessors();

	@Value("${parallel.thread.count:1}")
	private int parallelThreadCount;

	public static void main(String[] args) {
		SpringApplication.run(WordcounterApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (parallelThreadCount == 1) {
			parallelThreadCount = DEFAULT_PARALLEL_COUNT;
		}
	}

	@Bean
	public AddAndCountWordService addAndCountWordService() {
		return new AddAndCountWordService(parallelThreadCount);
	}
}
