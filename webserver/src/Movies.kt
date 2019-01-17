package com.gomezaddams

import com.google.gson.*
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import java.net.URL

data class MovieInfo(
    val movie_id: Int,
    val title: String,
    val poster_image_url: String,
    val popularity_summary: String)

class Movies (private val callState: ApplicationCall) {
    // Given our object, created with state supplied by the app, look for any supported options
    // and other parameters. The "search" option is the only option required for this project.
    //
    // Exceptions are trapped in here so we can report any errors to the caller.
    //
    // Ideally this code will execute asynchronously and allow multiple simultaneous invocations
    // but I don't think the URL mechanism being used in here will yield properly. Will it?
    suspend fun evaluate() {
        try {
            val parameters = callState.request.queryParameters
            val searchParam = parameters["search"]
            if (searchParam != null && searchParam.isNotBlank()) {
                val maxResults = parameters["limit"]?.toIntOrNull() ?: DEFAULT_RESULTS
                val imageWidth = parameters["width"]?.toIntOrNull() ?: DEFAULT_WIDTH
                val searchResults = this.search(searchParam, maxResults, imageWidth)
                if (searchResults != null) {
                    callState.respond(searchResults)
                } else {
                    callState.respond(HttpStatusCode.BadRequest, "Bad search result")
                }
            } else {
                callState.respond(HttpStatusCode.BadRequest, "No recognized parameters")
            }
        } catch (exc: Exception) {
            // What does localization even mean on the server?
            callState.respond(HttpStatusCode.InternalServerError, exc.localizedMessage)
        }
    }

    // Perform the search, consisting of a GET via the tmdb.org api, json parsing of the result,
    // and then manual selection or fabrication of the fields of interest for return.
    //
    // A successful result returns a JSON array as the content. I don't know whether that violates
    // common conventions. Should a JSON (map/dictionary) object be the root of any json result?
    private fun search(query: String, limit: Int, width: Int) : List<MovieInfo>? {
        // This method is embracing exceptions instead of trapping them and returning null.
        // I'm still a huge fan of designing with algebraic data types instead of using exceptions.
        val maxResults = clamp(1, MAX_RESULTS, limit)
        val searchUrl = makeSearchUrl(query)

        // Using the built-in URL mechanism for the GET. There are more robust libraries available.
        val content = URL(searchUrl) // These Java functions probably don't play nicely with our coroutines
            .openStream()
            .bufferedReader()
            .use { it.readText() }
        // I assume we can assert non-null content here?

        // Assuming the fetched content is JSON, parse out the values we're interested in.
        val json = JsonParser().parse(content) as? JsonObject
        val results = json?.getAsJsonArray("results") ?: return null

        // LINQ-style extraction of results that pass muster (with a healthy dose of validation)
        return (0..(results.count() - 1))
            .map { results.get(it) } // Slightly awkward iteration over the JSON array.
            .map(fun(next: JsonElement): MovieInfo? {
                // The result field names and summary text should be in a configuration,
                // less hard-coded, more flexible and localizable.
                val result = next as? JsonObject ?: return null
                val movieId = result["id"]?.asNumberOrNull() ?: return null
                val title = result["title"]?.asStringOrNull() ?: return null
                val posterPath = result["poster_path"]?.asStringOrNull()
                val posterImageUrl = makePosterImageUrl(posterPath, width)
                val popularity = result["popularity"]?.asNumberOrNull() ?: 0.0
                val voteCount = result["vote_count"]?.asNumberOrNull() ?: 0
                //val voteAverage = result["vote_average"]?.asNumberOrNull() ?: 0.0
                val popularitySummary = """Popularity $popularity out of $voteCount votes"""

                // We pack the desired info into our own data class.
                return MovieInfo(movieId.toInt(), title, posterImageUrl, popularitySummary)
            })
            .filter { it != null } // filter out any nulls (this is a fine example of a pointless comment :)
            .take(maxResults)
            .map { it!! } // No nulls at this point. Force the issue.
    }
}

// Helper functions for extracting primitive values from the primitive JSON objects.
private fun <T> JsonElement.asPrimOrNull(isPrim: (JsonPrimitive) -> Boolean, asPrim: (JsonPrimitive) -> T) : T? {
    val prim = this as? JsonPrimitive
    if (prim != null && isPrim(prim)) {
        return asPrim(prim)
    }
    return null
}

private fun JsonElement.asNumberOrNull() : Number? =
    this.asPrimOrNull(JsonPrimitive::isNumber, JsonPrimitive::getAsNumber)

private fun JsonElement.asStringOrNull() : String? =
    this.asPrimOrNull(JsonPrimitive::isString, JsonPrimitive::getAsString)

// I like this function for wrapping calls that may throw exceptions, but it's pointless in this file.
private fun <T> successOrNull(block: () -> T) : T? {
    return try {
        block()
    } catch (exc: Exception) {
        null
    }
}

// How does the Math library not have a clamp function?? I'm a Kotlin noob.
private fun clamp(min: Int, max: Int, value: Int) : Int =
    Math.max(min, Math.min(max, value)) // no Math.clamp already??
