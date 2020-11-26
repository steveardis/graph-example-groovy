package com.ardisconsulting.graph.controller

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

// -------------------------------------------------------------------------------------------------

@AutoConfigureMockMvc
@CompileStatic
@SpringBootTest
class TestControllerTests {

	@Autowired
	private MockMvc mvc;

	// ---------------------------------------------------------------------------------------------

	@Test
	void unprotected() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/unprotected').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.date').isNotEmpty())
	}

	@Test
	@WithMockUser
	void protectedByAuthenticationSucceeds() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-authentication').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.date').isNotEmpty())
	}

	@Test
	void protectedByAuthenticationFailsWithUnauthorizedWhenNoUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-authentication').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
	}

	@Test
	@WithMockUser(roles = [ 'TEST_1' ])
	void protectedByRoleSucceeds() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-role').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.date').isNotEmpty())
	}

	@Test
	void protectedByRoleFailsWithUnauthorizedWhenNoUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-role').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
	}

	@Test
	@WithMockUser(roles = [ 'TEST_2' ])
	void protectedByRoleFailsWithForbiddenWhenWrongRole() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-role').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
	}

	@Test
	@WithMockUser(authorities =  [ 'TEST_1' ])
	void protectedByAuthoritySucceeds() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-authority').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.date').isNotEmpty())
	}

	@Test
	void protectedByAuthorityFailsWithUnauthorizedWhenNoUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-authority').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
	}

	@Test
	@WithMockUser(authorities = [ 'TEST_2' ])
	void protectedByAuthorityFailsWithForbiddenWhenWrongAuthority() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-authority').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
	}

	@Test
	@WithMockUser(username = 'test_1')
	void protectedByPostAuthorizeSucceeds() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-post-authorize').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath('$.username').value('test_1'))
	}

	@Test
	void protectedByPostAuthorizeWithUnauthorizedWhenNoUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-post-authorize').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isUnauthorized())
	}

	@Test
	@WithMockUser(username = 'test_2')
	void protectedByPostAuthorizeWithForbiddenWhenWrongUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get('/test/protected-by-post-authorize').accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
	}
}
