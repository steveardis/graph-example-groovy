package com.ardisconsulting.graph.service

import com.ardisconsulting.graph.service.TestService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus

// -------------------------------------------------------------------------------------------------

/**
 * Ideas for these tests came from:
 * - https://www.concretepage.com/spring-4/spring-4-security-junit-test-with-withmockuser-and-withuserdetails-annotation-example-using-webappconfiguration
 * - https://www.baeldung.com/spring-security-granted-authority-vs-role
 */
@CompileStatic
@Service
@Slf4j
class TestService {

	def unprotected() {
		return generateResponse()
	}

	def protectedByAuthentication() {
		return generateResponse()
	}

	@PreAuthorize('hasRole(\'TEST_1\')')
	def protectedByRole() {
		return generateResponse()
	}

	@PreAuthorize('hasAuthority(\'TEST_1\')')
	def protectedByAuthority() {
		return generateResponse()
	}

	@PostAuthorize('returnObject.username == authentication.name')
	def protectedByPostAuthorize() {
		return new User([ username: 'test_1' ])
	}

	private static generateResponse() {
		return [ date : new Date() ]
	}

	private class User {
		String username
	}
}
