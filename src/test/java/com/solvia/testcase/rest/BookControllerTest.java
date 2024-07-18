package com.solvia.testcase.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvia.testcase.domain.Book;
import com.solvia.testcase.repository.BookRepository;
import com.solvia.testcase.service.BookService;

/**
 * Testing with TestRestTemplate and @Testcontainers (image mysql:8.0-debian)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create-drop" })
@AutoConfigureMockMvc
public class BookControllerTest {

	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String BASEURI;

	@Autowired
	BookRepository bookRepository;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BookService bookService;

	@Container
	@ServiceConnection
	static MySQLContainer<?> postgres = new MySQLContainer<>("mysql:8.0-debian");

	@Autowired
	private MockMvc restBarrierMessageLogMockMvc;

	@BeforeEach
	void testSetUp() {
		BASEURI = "http://localhost:" + port;
	}

	public List<Book> getMockBookList() {
		List<Book> bookList = new ArrayList<Book>();
		Book book = new Book();
		book.setTitle("book1");
		bookList.add(book);

		return bookList;
	}

	@Test
	void testFindAll() throws Exception {
		when(bookService.findAll()).thenReturn(getMockBookList());

		MvcResult result = restBarrierMessageLogMockMvc
				.perform(get(BASEURI + "/books"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		String response = result.getResponse().getContentAsString();
		List<Book> myObjects = objectMapper.readValue(response, new TypeReference<List<Book>>() {});
		assertEquals(1, myObjects.size());
		assertEquals("book1", myObjects.get(0).getTitle());

	}

	@Test
	//@WithMockUser(authorities="USER")
	public void testDeleteById() throws Exception {

		MvcResult result = restBarrierMessageLogMockMvc
				.perform(delete(BASEURI + "/books?id=1"))
				.andExpect(status().is2xxSuccessful())
				.andReturn();

		assertEquals(204, result.getResponse().getStatus());
	}

	@Test
	public void testCreate() throws JsonProcessingException, Exception {

		Book book = new Book();
		book.setTitle("test");
		book.setIsbn("isbn");

		MvcResult result = restBarrierMessageLogMockMvc
				.perform(post(BASEURI + "/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(book))
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

	}

}