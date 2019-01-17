package com.gomezaddams

import java.net.URLEncoder

internal const val API_KEY = """ce8ca68431253f9d497f9c472cd76174"""

internal const val LANGUAGE = "en-US"
internal const val DEFAULT_WIDTH = 185
internal const val DEFAULT_RESULTS = 10
internal const val MAX_RESULTS = 100

internal const val BASE_SEARCH_URL = """https://api.themoviedb.org/3/search/movie?page=1&include_adult=false&language=$LANGUAGE&api_key=$API_KEY"""
internal const val BASE_IMAGE_URL = """https://image.tmdb.org/t/p""" // append width first "/w$DEFAULT_WIDTH"

// The last item in each size list determines the size used when a zero width is requested.
// These values were taken from a discussion on https://www.themoviedb.org/talk/53c11d4ec3a3684cf4006400
//internal val BACKDROP_SIZES = arrayOf("w300", "w780", "w1280", "original")
//internal val LOGO_SIZES = arrayOf("w45", "w92", "w154", "w185", "w500", "original")
internal val POSTER_SIZES = arrayOf("w92", "w154", "w185", "w342", "w500", "w780", "original")
//internal val PROFILE_SIZES = arrayOf("w45", "w185", "h632", "original")
//internal val STILL_SIZES = arrayOf("w92", "w185", "w300", "original")

fun makeSearchUrl(query: String) : String =
    """$BASE_SEARCH_URL&query=${URLEncoder.encode(query, "UTF-8")}"""

fun makePosterImageUrl(posterPath: String?, width: Int = DEFAULT_WIDTH) : String {
    return if (posterPath?.isNotBlank() == true) {
        val posterSize = if (width > 0) {
            // Select the first size if the requested size is too small,
            // otherwise select the matching or smaller size.
            POSTER_SIZES
                .lastOrNull { it.substring(1).toIntOrNull() ?: (width + 1) <= width }
                ?: POSTER_SIZES.first()
        } else {
            // Select the last ("original") size for a requested width of zero
            POSTER_SIZES.last()
        }
        """$BASE_IMAGE_URL/$posterSize/${posterPath.removePrefix("/")}"""
    } else {
        ""
    }
}