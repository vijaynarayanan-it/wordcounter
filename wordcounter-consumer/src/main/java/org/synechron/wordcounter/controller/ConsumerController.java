package org.synechron.wordcounter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.wordcounter.clientproxy.WordCounterProducerProxy;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ConsumerController {
	@Autowired
	private final WordCounterProducerProxy proxy;

	@GetMapping(path = "/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getCountOfTheWord(@PathVariable String word) {
		ResponseEntity<Integer> response = proxy.getCountOfTheWord(word);
		int count = 0;
		if (response != null && response.getBody() != null) {
			count = response.getBody();
		}
		return (count == 0) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(count, HttpStatus.FOUND);
	}
	
	@PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addWords(@RequestBody String[] words) {
		ResponseEntity<String> response = proxy.addWords(words);
		if (response != null && response.getBody() != null) {
			return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
