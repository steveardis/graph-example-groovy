package com.ardisconsulting.graph

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test
import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.Session
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.testcontainers.containers.Neo4jContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat

// -------------------------------------------------------------------------------------------------

@CompileStatic
@Testcontainers
public class Neo4jExampleTests {

    @Container
    private static final Neo4jContainer neo4jContainer = new Neo4jContainer()
            .withAdminPassword(null)

    // ---------------------------------------------------------------------------------------------

    @Test
    void testSomethingUsingBolt() {
        Driver driver = GraphDatabase.driver(neo4jContainer.boltUrl, AuthTokens.none())
        Session session = driver.session()

        long one = session.run('RETURN 1', [:]).next().get(0).asLong()

        assertThat(one, is(1 as long))
    }

    @Test
    void testSomethingUsingHttp() throws IOException {
        HttpURLConnection con = new URL("${neo4jContainer.httpUrl}/db/data/transaction/commit").openConnection() as HttpURLConnection
        con.setRequestMethod(HttpMethod.POST.toString())
        con.setRequestProperty(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        con.setDoOutput(true)

        def request = [ statements: [ [ statement: 'RETURN 1' ] ] ]
        con.getOutputStream().write(JsonOutput.toJson(request).bytes)

        assertThat(con.getResponseCode(), is(HttpStatus.OK.value()))

        def response = new JsonSlurper().parseText(con.getInputStream().getText())
        def expectedResponse = [
            results: [ [ columns: [ '1' ], data: [ [ row: [ 1 ], meta: [ null ] ] ] ] ],
            errors: []
        ]

        assertThat(response as Map, is(expectedResponse as Map))
    }
}
