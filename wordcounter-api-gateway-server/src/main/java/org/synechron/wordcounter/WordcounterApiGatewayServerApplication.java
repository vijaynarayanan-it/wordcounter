package org.synechron.wordcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
@EnableEurekaClient
public class WordcounterApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordcounterApiGatewayServerApplication.class, args);
	}

	@Bean
	public HttpClient httpClient() {
	    return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
	}
}
