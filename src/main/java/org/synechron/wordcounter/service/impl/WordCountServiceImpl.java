package org.synechron.wordcounter.service.impl;

import org.springframework.stereotype.Service;
import org.synechron.wordcounter.service.WordCountService;

@Service
public class WordCountServiceImpl implements WordCountService {

	@Override
	public void addWords(String... words) {
	}

	@Override
	public int getCount(String word) {
		return 0;
	}
}
