package com.gomezaddams

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.sessions.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.gson.*
import com.fasterxml.jackson.databind.*
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testMoviesLiveConnection() {
        withTestApplication({ module(testing = true) }) {
            // Check that an error is returned for an invalid option.
            handleRequest(HttpMethod.Get, "/movies?invalid").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status(), "somehow succeeded?")
            }

            // Check that we can specify a specific number of results.
            handleRequest(HttpMethod.Get, "/movies?search=disney&limit=3&width=0").apply {
                assertEquals(HttpStatusCode.OK, response.status(), "failed disney search")
                val json = JsonParser().parse(response.content) as? JsonArray
                assertNotNull(json)
                assert(json.count() == 3) //{ "expected 3 disney results" }
                val entry = json[0] as? JsonObject
                assertNotNull(entry)
                assertNotNull(entry["movie_id"]) // minimal check for the expected fields
                assertNotNull(entry["title"])
                val url = entry["poster_image_url"]
                assertNotNull(url)
                assertTrue(url.asString.contains(Regex("""/original/""")),
                    """didn't find the expected size 'original' in $url""")
                assertNotNull(entry["popularity_summary"])
            }

            // Check that we get the expected poster image width.
            handleRequest(HttpMethod.Get, "/movies?search=marvel&limit=2&width=185").apply {
                assertEquals(HttpStatusCode.OK, response.status(), "failed marvel search")
                val json = JsonParser().parse(response.content) as? JsonArray
                assertNotNull(json)
                assert(json.count() == 2) //{ "expected 2 marvel results" }
                val entry = json[0] as? JsonObject
                assertNotNull(entry)
                val url = entry["poster_image_url"]
                assertNotNull(url)
                assertTrue(url.asString.contains(Regex("""/w185/""")),
                    """didn't find the expected size 'w185' in $url""")
            }

            handleRequest(HttpMethod.Get, "/movies?search=avenger&limit=1").apply {
                assertEquals(HttpStatusCode.OK, response.status(), "failed avenger search")
                val json = JsonParser().parse(response.content) as? JsonArray
                assertNotNull(json)
                assert(json.count() == 1) //{ "expected 1 avenger results" }
            }

            // Check the default cap on the results count.
            handleRequest(HttpMethod.Get, "/movies?search=a").apply {
                assertEquals(HttpStatusCode.OK, response.status(), "failed default search")
                val json = JsonParser().parse(response.content) as? JsonArray
                assertNotNull(json)
                assert(json.count() == 10) //{ "expected a default cap of 10" }
            }

            // Check the upper cap on the results count.
            handleRequest(HttpMethod.Get, "/movies?search=e&limit=99999").apply {
                assertEquals(HttpStatusCode.OK, response.status(), "failed huge search")
                val json = JsonParser().parse(response.content) as? JsonArray
                assertNotNull(json)
                assert(json.count() <= MAX_RESULTS) //{ "expected a default cap of 10" }
            }
        }
    }
}
