package com.ardisconsulting.graph.controller

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// -------------------------------------------------------------------------------------------------

@AutoConfigureMockMvc
@CompileStatic
@SpringBootTest
class PingControllerTests {

	@Autowired
	private MockMvc mvc;

	// ---------------------------------------------------------------------------------------------

	@Test
	void ping() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/ping').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.date').isNotEmpty())
	}
}
