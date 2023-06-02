package org.synechron.wordcounter.clientproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "wordcounter-producer")
public interface WordCounterProducerProxy {
	@GetMapping(path = "/api/v1/wordcounter/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getCountOfTheWord(@PathVariable String word);

	@PostMapping(path = "/api/v1/wordcounter/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addWords(@RequestBody String[] words);
}
