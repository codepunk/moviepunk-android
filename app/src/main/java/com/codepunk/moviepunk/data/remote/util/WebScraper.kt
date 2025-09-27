package com.codepunk.moviepunk.data.remote.util

import androidx.core.net.toUri
import com.codepunk.moviepunk.BuildConfig
import com.codepunk.moviepunk.data.remote.dto.HashedImageDto
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.helger.css.decl.*
import com.helger.css.decl.visit.CSSVisitor
import com.helger.css.decl.visit.DefaultCSSVisitor
import com.helger.css.reader.CSSReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import javax.inject.Inject

/**
 * Utility class that scrapes the TMDB website for the CSS document that contains
 * the curated list of backgrounds to display on the home screen. This is a brittle
 * implementation as it relies on a certain syntax and format being present in the
 * TMDB website, but it was mostly an exercise to see if it was possible.
 */
class WebScraper @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    // region Methods

    /**
     * Returns a [Response] containing the result of [urlString] formatted as a text string.
     * This method also takes a [mediaType], which is used to raise errors should a failure occur.
     */
    private suspend fun fetchUrlText(
        urlString: String,
        mediaType: MediaType
    ): Response<String> = withContext(ioDispatcher) {
        val request = Request.Builder().url(urlString).build()
        return@withContext okHttpClient.newCall(request).execute().use { rawResponse ->
            if (rawResponse.isSuccessful) {
                val body = rawResponse.body?.string().orEmpty()
                Response.success(body)
            } else {
                val body = rawResponse.message.toResponseBody(mediaType)
                Response.error(body, rawResponse)
            }
        }
    }

    /**
     * Returns a [Response] containing the result of [urlString] formatted as an HTML text string.
     */
    suspend fun fetchUrlTextHtml(urlString: String): Response<String> = fetchUrlText(
        urlString = urlString,
        mediaType = HTML_MEDIA_TYPE
    )

    /**
     * Returns a [Response] containing the result of [urlString] formatted as a CSS text string.
     */
    suspend fun fetchUrlTextCss(urlString: String): Response<String> = fetchUrlText(
        urlString = urlString,
        mediaType = CSS_MEDIA_TYPE
    )

    /**
     * Scrapes the document found at [urlString] for background images used on the home page.
     * These background images are formatted as a list of [HashedImageDto] instances.
     */
    suspend fun scrapeTmdbWallpaper(
        urlString: String
    ): Response<List<HashedImageDto>> = withContext(ioDispatcher) {
        // Attempt to fetch the HTML text found at the supplied URL
        val htmlResponse = fetchUrlTextHtml(urlString)
        if (!htmlResponse.isSuccessful) {
            return@withContext Response.error(
                htmlResponse.ensureErrorBody(HTML_MEDIA_TYPE),
                htmlResponse.raw()
            )
        }
        val htmlString = htmlResponse.body().orEmpty()

        // Attempt to find a CSS document referenced in the HTML with a format of
        // "index-(hash).css", if one exists. Otherwise, return a 404 "Not Found" error
        val indexCssPath = findIndexCssUrl(htmlString)
            ?: return@withContext Response.error(
                404,
                "Index CSS Url not found".toResponseBody(CSS_MEDIA_TYPE)
            )
        val indexCssUrlString = BuildConfig.TMDB_URL + indexCssPath

        // Attempt to fetch the CSS text found at the resulting CSS document name
        val cssResponse = fetchUrlTextCss(indexCssUrlString)
        if (!cssResponse.isSuccessful) {
            return@withContext Response.error(
                cssResponse.ensureErrorBody(CSS_MEDIA_TYPE),
                cssResponse.raw()
            )
        }

        // Extract the hash from the CSS document name
        val matchResult = INDEX_FILENAME_REGEX.find(indexCssUrlString)
        val hash = matchResult?.destructured?.component2().orEmpty()
        val cssString = cssResponse.body().orEmpty()

        // Extract background DTOs from the CSS string
        val backgroundDtos = findBackgrounds(
            hash = hash,
            cssString = cssString
        )

        // If backgrounds were found, return them. Otherwise, return a 404 "Not Found" error
        return@withContext if (backgroundDtos.isEmpty()) {
            Response.error(
                404,
                "No backgrounds found".toResponseBody(CSS_MEDIA_TYPE)
            )
        } else {
            Response.success(backgroundDtos)
        }
    }

    /**
     * Returns the URL string of the CSS document with a name in the form of
     * "index-(hash).css", if such a document exists
    **/
    private fun findIndexCssUrl(htmlString: String): String? {
        val doc = Jsoup.parse(htmlString)

        // Get a list of CSS files embedded in the HTML
        val cssUrls = doc.select(STYLE_SHEET)
            .mapNotNull { it.attribute(HREF)?.value }

        // Find a cssFile that matches "index-[hash].css"
        return cssUrls.find {
            val cssUrl = it.toUri().lastPathSegment.orEmpty()
            INDEX_FILENAME_REGEX.matches(cssUrl)
        }
    }

    /**
     * Returns a list of backgrounds found in the passed [cssString]. The resulting
     * list of [HashedImageDto] is created using [hash] so the app can check for future updates
     * to the curated list.
     */
    private fun findBackgrounds(
        hash: String,
        cssString: String
    ): List<HashedImageDto> {
        val backgroundImageDtos: MutableList<HashedImageDto> = mutableListOf()
        CSSReader.readFromString(cssString)?.also { css ->
            CSSVisitor.visitCSS(
                css,
                object : DefaultCSSVisitor() {
                    override fun onBeginStyleRule(aStyleRule: CSSStyleRule) {
                        // If we arrive here, we are in a "background" style rule
                        backgroundImageDtos.addAll(
                            aStyleRule.allDeclarations.mapNotNull { declaration ->
                                declaration.toHashedImageDtos(hash)
                            }.flatten()
                        )
                    }
                }
            )
        }
        return backgroundImageDtos.toList()
    }

    /**
     * Scrapes the document found at [urlString] for the current hash used in the Image CSS
     * document.
     */
    suspend fun scrapeTmdbWallpaperHash(
        urlString: String
    ): Response<String> = withContext(ioDispatcher) {
        // Attempt to fetch the HTML text found at the supplied URL
        val htmlResponse = fetchUrlTextHtml(urlString)
        if (!htmlResponse.isSuccessful) {
            return@withContext Response.error(
                htmlResponse.ensureErrorBody(HTML_MEDIA_TYPE),
                htmlResponse.raw()
            )
        }
        val htmlString = htmlResponse.body().orEmpty()

        // Attempt to find a CSS document referenced in the HTML with a format of
        // "index-(hash).css", if one exists. Otherwise, return a 404 "Not Found" error
        val indexCssPath = findIndexCssUrl(htmlString)
            ?: return@withContext Response.error(
                404,
                "Index CSS Url not found".toResponseBody(CSS_MEDIA_TYPE)
            )

        // Extract the hash from the CSS document name. Given that we just called
        // findIndexCssUrl, matchResult should always return a valid hash
        val matchResult = INDEX_FILENAME_REGEX.find(indexCssPath)
        Response.success(matchResult?.destructured?.component2().orEmpty())
    }

    /**
     * Converts a [CSSDeclaration] to a list of found [HashedImageDto]. If no backgrounds
     * are found in the [CSSDeclaration], returns an empty list
     */
    private fun CSSDeclaration.toHashedImageDtos(
        hash: String
    ): List<HashedImageDto> {
        if (property != BACKGROUND_IMAGE) return emptyList()
        val backgroundImageDtos: MutableList<HashedImageDto> = mutableListOf()
        expression.allMembers.mapNotNull {
            it as? CSSExpressionMemberFunction
        }.filter { memberFunction ->
            memberFunction.functionName == IMAGE_SET
        }.forEach { imageSet ->
            val urlStrings: MutableList<String> = mutableListOf()
            val densities: MutableList<Int> = mutableListOf()
            imageSet.expression?.allMembers?.forEach { member ->
                when (member) {
                    is CSSExpressionMemberTermURI -> {
                        urlStrings.add(member.uri.uri)
                    }
                    is CSSExpressionMemberTermSimple -> {
                        val matchResult = DENSITY_REGEX.find(member.value)
                        val density = matchResult?.destructured?.component1()?.toInt() ?: 1
                        densities.add(density)
                    }
                }
            }
            backgroundImageDtos.add(
                HashedImageDto(
                    hash = hash,
                    imageUrls = densities.zip(urlStrings).toMap()
                )
            )
        }
        return backgroundImageDtos.toList()
    }

    /**
     * Ensures that a valid error [ResponseBody] is returned from a [Response],
     * using the supplied [mediaType] to create one if necessary
     */
    private fun <T> Response<T>.ensureErrorBody(mediaType: MediaType): ResponseBody =
        errorBody() ?: message().toResponseBody(mediaType)

    // endregion Methods

    // region Companion object

    companion object {

        // region Constants

        private const val HREF = "href"
        private const val STYLE_SHEET = "link[rel=stylesheet]"
        private const val BACKGROUND_IMAGE = "background-image"
        private const val IMAGE_SET = "image-set"

        // endregion Constants

        // region Variables

        private val CSS_MEDIA_TYPE = "text/css".toMediaType()
        private val HTML_MEDIA_TYPE = "text/html".toMediaType()
        private val INDEX_FILENAME_REGEX = "(index-)([a-zA-Z0-9_.-]+)(\\.css)".toRegex()
        private val DENSITY_REGEX = "([0-9])[xX]".toRegex()

        // endregion Variables

    }

    // endregion Companion object

}
