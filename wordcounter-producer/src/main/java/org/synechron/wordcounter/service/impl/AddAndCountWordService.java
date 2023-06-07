package org.synechron.wordcounter.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AddAndCountWordService {
	public static final int INITIAL_CAPACITY = 4096;
	public static final float LOAD_FACTOR = 0.75f;
	public static final int SIZE_ONE = 1;
	private final Map<String, AtomicInteger> wordCountMap;

	public AddAndCountWordService() {
		this(1);
	}

	public AddAndCountWordService(int parLevel) {
		this.wordCountMap = (parLevel == SIZE_ONE) ? new HashMap<>()
				: new ConcurrentHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR, parLevel);
	}

	public int getCountOfTheWord(String word) {
		return wordCountMap.containsKey(word) ? wordCountMap.get(word).get() : 0;
	}

	public void addWord(String word) {
		AtomicInteger countValue = wordCountMap.get(word);
		if (countValue != null) {
			countValue.incrementAndGet();
		} else {
			if (wordCountMap instanceof ConcurrentMap) {
				countValue = new AtomicInteger(1);
				countValue = wordCountMap.putIfAbsent(word, countValue);
				// Another thread might have added the same value in the meantime
				if (countValue != null) {
					countValue.incrementAndGet();
				}
			} else {
				wordCountMap.put(word, new AtomicInteger(1));
			}
		}
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof AddAndCountWordService)) {
			return false;
		}
		AddAndCountWordService wordCountService = (AddAndCountWordService) object;
		if (wordCountMap.size() != wordCountService.wordCountMap.size()) {
			return false;
		}
		for (Entry<String, AtomicInteger> entry : wordCountService.wordCountMap.entrySet()) {
			if (wordCountMap.get(entry.getKey()).get() != entry.getValue().get()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(wordCountMap);
	}
}
