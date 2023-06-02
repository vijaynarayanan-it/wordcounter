package org.synechron.wordcounter.service.impl;

import static org.synechron.wordcounter.utils.WordUtils.countWords;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.synechron.wordcounter.service.WordCountService;
import org.synechron.wordcounter.utils.WordUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WordCountServiceImpl implements WordCountService {
	@Value("${parallel.thread.count}")
	private int parallelThreadCount;

	private final AddAndCountWordService service;

	@Override
	public void addWords(String... words) {
		String word = String.join(" ", words).trim();
		word = getValidatedAndFormattedWord(word);
		service.executeConsumersForAddingAndCountingTheWords(word,
				(text) -> service.add(countWords(text)), parallelThreadCount);
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
		return WordUtils.translate(word).toLowerCase();
	}
}
