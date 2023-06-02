package org.synechron.wordcounter.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.CollectionUtils;

public class AddAndCountWordService {
	public static final int INITIAL_CAPACITY = 4096;
	public static final float LOAD_FACTOR = 0.75f;
	public static final int SIZE_ONE = 1;
	private final Map<String, AtomicInteger> wordCountMap;

	private volatile ScheduledExecutorService consumers = null;
	private BlockingQueue<String> wordBlockingQueue;

	public interface Consumer<T> {
		void consume(T t);
	}

	public AddAndCountWordService() {
		this(1);
	}

	public AddAndCountWordService(int parLevel) {
		this.wordCountMap = (parLevel == SIZE_ONE) ? new HashMap<>()
				: new ConcurrentHashMap<>(INITIAL_CAPACITY, LOAD_FACTOR, parLevel);
	}

	public int getSize() {
		return wordCountMap.size();
	}

	public void add(String word, int count) {
		AtomicInteger cc = wordCountMap.get(word);
		if (cc != null) {
			cc.addAndGet(count);
		} else {
			if (wordCountMap instanceof ConcurrentMap) {
				cc = wordCountMap.putIfAbsent(word, new AtomicInteger(count));
				// Another thread might have added the same value in the meantime
				if (cc != null) {
					cc.addAndGet(count);
				}
			} else {
				wordCountMap.put(word, new AtomicInteger(count));
			}
		}
	}

	public void add(AddAndCountWordService wc) {
		for (Map.Entry<String, AtomicInteger> e : wc.wordCountMap.entrySet()) {
			add(e.getKey(), e.getValue().get());
		}
	}

	public int getCountOfTheWord(String word) {
		if (wordCountMap.containsKey(word)) {
			return wordCountMap.get(word).get();
		}
		return 0;
	}

	public void executeConsumersForAddingAndCountingTheWords(String word, Consumer<String> consumer,
			int parallelCount) {
		wordBlockingQueue = new LinkedBlockingQueue<>(parallelCount);
		consumers = createConsumers(consumer, word, parallelCount);
		try {
			while (!CollectionUtils.isEmpty(wordBlockingQueue)) {
				Thread.yield();
			}
			shutdown(consumers);
		} catch (InterruptedException e) {
			throw new RuntimeException(String.format("Interrupted: %s", e.getMessage()), e);
		}
		consumers = null;
	}

	private boolean shutdown(ScheduledExecutorService executorService) throws InterruptedException {
		executorService.shutdown();
		return executorService.awaitTermination(24, TimeUnit.HOURS);
	}

	private ScheduledExecutorService createConsumers(Consumer<String> consumer, String word, int parallelCount) {
		ScheduledExecutorService consumers = new ScheduledThreadPoolExecutor(parallelCount);
		try {
			wordBlockingQueue.put(word);
		} catch (InterruptedException e) {
			throw new RuntimeException(String.format("Interrupted: %s", e.getMessage()), e);
		}
		for (int i = 0; i < parallelCount; i++) {
			consumers.submit(() -> consume(consumer));
		}
		return consumers;
	}

	private void consume(Consumer<String> consumer) {
		boolean finished = false;
		while (!finished) {
			try {
				String polledValue = pollBlockingQueue();
				if (polledValue != null) {
					consumer.consume(polledValue);
				} else {
					finished = true;
				}
			} catch (InterruptedException e) {
				finished = true;
			}
		}
	}

	private String pollBlockingQueue() throws InterruptedException {
		String value;
		do {
			value = wordBlockingQueue.poll(1, TimeUnit.MILLISECONDS);
		} while (value == null && !consumers.isShutdown());
		return value;
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
