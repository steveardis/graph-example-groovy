package com.ardisconsulting.graph.controller

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

// -------------------------------------------------------------------------------------------------

@CompileStatic
@RestController
@RequestMapping('/ping')
@Slf4j
class PingController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	def ping() {
		log.debug('"ping" request received')

		return [ date : new Date() ]
	}
}
