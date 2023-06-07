package org.synechron.wordcounter.service.impl;

import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.synechron.wordcounter.service.WordCountService;
import org.synechron.wordcounter.utils.WordUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordCountServiceImpl implements WordCountService {
	private final AddAndCountWordService service;

	@Override
	public void addWords(String... words) {
		Stream.of(words)
		.map(word-> getValidatedAndFormattedWord(word))
		.forEach(service::addWord);
	}

	@Override
	public int getCount(String word) {
		word = getValidatedAndFormattedWord(word);
		return service.getCountOfTheWord(word);
	}

	private String getValidatedAndFormattedWord(String word) {
		if (!word.matches("^[ A-Za-z]+$")) {
			throw new IllegalArgumentException(String.format("Given word : %s is not valid", word));
		}
		return WordUtils.translate(word).trim().toLowerCase();
	}
}
