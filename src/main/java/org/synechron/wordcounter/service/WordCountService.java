package org.synechron.wordcounter.service;

public interface WordCountService {
	void addWords(String... words);

	int getCount(String word);
}
