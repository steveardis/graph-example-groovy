package com.ardisconsulting.graph.controller

import com.ardisconsulting.graph.service.TestService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

// -------------------------------------------------------------------------------------------------

@CompileStatic
@RequestMapping('/test')
@RestController
class TestController {

	@Autowired
	private TestService testService

	// ---------------------------------------------------------------------------------------------

	@RequestMapping(value = '/unprotected', method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def unprotected() {
		return testService.unprotected()
	}

	@RequestMapping(value = '/protected-by-authentication', method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def protectedByAuthentication() {
		return testService.protectedByAuthentication()
	}

	@RequestMapping(value = '/protected-by-role', method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def protectedByRole() {
		return testService.protectedByRole()
	}

	@RequestMapping(value = '/protected-by-authority', method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def protectedByAuthority() {
		return testService.protectedByAuthority()
	}

	@RequestMapping(value = '/protected-by-post-authorize', method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def protectedByPostAuthorize() {
		return testService.protectedByPostAuthorize()
	}
}
