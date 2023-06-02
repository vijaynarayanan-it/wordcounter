package org.synechron.wordcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WordcounterDiscoveryServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(WordcounterDiscoveryServerApplication.class, args);
	}
}
