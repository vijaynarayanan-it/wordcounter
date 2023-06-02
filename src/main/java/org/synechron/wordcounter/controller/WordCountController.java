package org.synechron.wordcounter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.synechron.wordcounter.service.WordCountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wordcounter")
public class WordCountController {
	private final WordCountService wordCountService;

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addWords(@RequestBody String[] words) {
		wordCountService.addWords(words);
		return new ResponseEntity<>("Given word is added", HttpStatus.CREATED);
	}

	@GetMapping("/{word}")
	public ResponseEntity<Integer> getCountOfTheWord(@PathVariable String word) {
		int count = wordCountService.getCount(word);

		return (count == 0) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(count, HttpStatus.FOUND);
	}
}
