package org.synechron.wordcounter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.synechron.wordcounter.service.impl.AddAndCountWordService;
import org.synechron.wordcounter.service.impl.WordCountServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Word Count Service Implementation - Test cases")
public class WordCountServiceImplTest {
	@MockBean
	private AddAndCountWordService service;

	private WordCountServiceImpl underTest;

	@BeforeAll
	public void init() {
		underTest = new WordCountServiceImpl(service);
	}

	@Test
	@DisplayName("Get Count of the given valid word")
	public void testGetCountOfWord_WithValidWord() {
		when(service.getCountOfTheWord(anyString())).thenReturn(1);
		int result = underTest.getCount("Apple");
		assertThat(result).isEqualTo(1);
	}

	@Test
	@DisplayName("Throws exeception for invalid word")
	public void testGetCountOfWord_WithInValidWord() {
		assertThrows(IllegalArgumentException.class, () -> underTest.getCount("1$Apple"));
	}

	@Test
	@DisplayName("Add the given valid word")
	public void testAddWords_WithValidWord() {
		underTest.addWords("Apple");
		verify(service, times(1)).addWord(anyString());
	}

	@Test
	@DisplayName("Add the given valid words")
	public void testAddWords_WithValidWords() {
		underTest.addWords("Java Maven");
		verify(service, times(1)).addWord(anyString());
	}
}
