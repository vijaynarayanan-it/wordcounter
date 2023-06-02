package org.synechron.wordcounter.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.synechron.wordcounter.service.WordCountService;

@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Word Counter Controller test cases")
public class WordCounterControllerTest {
	private MockMvc mockMvc;

	@Mock
	private WordCountService wordCountService;

	@BeforeAll
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(new WordCountController(wordCountService)).build();
	}

	@Test
	@DisplayName("Test Adding functionality with valid words")
	public void testAddWordsWithValidWords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wordcounter/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("[\"Java\", \"Javascript\"]"))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		verify(wordCountService, times(1)).addWords("Java", "Javascript");
	}

	@Test
	@DisplayName("Test Adding functionality with invalid words")
	public void testAddWordsWithInvalidWords() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/wordcounter/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content("[\"342Java\", \"1$Javascript\"]"))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		verify(wordCountService, times(1)).addWords("Java", "Javascript");
	}

	@Test
	@DisplayName("Test the given valid word count")
	public void testWordCountWithAvailableWord() throws Exception {
		Mockito.when(wordCountService.getCount(anyString())).thenReturn(1);

		mockMvc.perform(get("/api/v1/wordcounter/{word}", "Java")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isFound())
				.andExpect(MockMvcResultMatchers.content().string("1"))
				.andDo(print());
	}

	@Test
	@DisplayName("Failure test case for getWordCount method with unavailable word")
	public void testWordCountWithUnavailableWord() throws Exception {
		Mockito.when(wordCountService.getCount(anyString())).thenReturn(0);

		mockMvc.perform(get("/api/v1/wordcounter/{word}", "Java")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound())
				.andDo(print());
	}
}
