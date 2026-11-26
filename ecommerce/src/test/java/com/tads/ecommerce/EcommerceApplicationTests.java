package com.tads.ecommerce;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EcommerceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void clientsCrudAndSeededDataAreAvailable() throws Exception {
		mockMvc.perform(get("/clients")
						.param("page", "0")
						.param("linesPerPage", "3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(10)))
				.andExpect(jsonPath("$.content.length()").value(3));

		mockMvc.perform(get("/clients/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Mariana Oliveira"))
				.andExpect(jsonPath("$.birthDate").value("1988-04-17"));

		String newClient = """
				{
					"name": "Ana Carvalho",
					"cpf": "456.789.123-00",
					"income": 7200.50,
					"birthDate": "1991-10-12",
					"children": 1
				}
				""";

		String location = mockMvc.perform(post("/clients")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newClient))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.id").isNumber())
				.andExpect(jsonPath("$.name").value("Ana Carvalho"))
				.andReturn()
				.getResponse()
				.getHeader("Location");

		String id = location.substring(location.lastIndexOf('/') + 1);
		String updatedClient = """
				{
					"name": "Ana Carvalho Mendes",
					"cpf": "456.789.123-00",
					"income": 8100.00,
					"birthDate": "1991-10-12",
					"children": 2
				}
				""";

		mockMvc.perform(put("/clients/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updatedClient))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Ana Carvalho Mendes"))
				.andExpect(jsonPath("$.income").value(8100.00))
				.andExpect(jsonPath("$.children").value(2));

		mockMvc.perform(delete("/clients/{id}", id))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/clients/{id}", id))
				.andExpect(status().isNotFound());
	}
}
