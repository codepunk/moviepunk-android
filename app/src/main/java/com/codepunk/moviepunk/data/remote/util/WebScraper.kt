package com.codepunk.moviepunk.data.remote.util

import android.net.Uri
import androidx.core.net.toUri
import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import com.codepunk.moviepunk.data.remote.response.CuratedContentResponse
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.codepunk.moviepunk.domain.model.CuratedContentType
import com.helger.css.decl.CSSExpressionMemberTermURI
import com.helger.css.decl.CSSStyleRule
import com.helger.css.decl.visit.CSSVisitor
import com.helger.css.decl.visit.DefaultCSSVisitor
import com.helger.css.reader.CSSReader
import com.helger.css.writer.CSSWriterSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import org.jsoup.Jsoup
import javax.inject.Inject

class WebScraper @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    // region Methods

    /**
     * Returns an [okhttp3.Response] containing the result of [urlString] formatted
     * as a text string.
     */
    private suspend fun fetchUrlText(
        urlString: String
    ): okhttp3.Response = withContext(ioDispatcher) {
        val request = Request.Builder().url(urlString).build()
        okHttpClient.newCall(request).execute()
    }

    suspend fun getCssUris(
        urlString: String
    ): Response<List<Uri>> {
        // 1. Fetch the raw HTML text using urlString
        val response = fetchUrlText(urlString)

        // 2. Handle the unsuccessful case by creating a Retrofit error response
        if (!response.isSuccessful) {
            val responseBody = response.body
            checkNotNull(responseBody) { "Response body is null" }
            return Response.error(response.code, responseBody)
        }

        // 3. Get the response body as an HTML string
        val html = response.body?.string()
        checkNotNull(html) { "Html response body is null" }

        // 4. Parse HTML string to get list of index CSS file HREFs
        val document = Jsoup.parse(html)
        val elements = document.select(STYLE_SHEET)
        val cssUris = elements.filter { element ->
            val uri = element.attribute(HREF)?.value?.toUri() ?: return@filter false
            INDEX_FILENAME_REGEX.matches(uri.lastPathSegment.orEmpty())
        }.mapNotNull { element ->
            element.attribute(HREF)?.value?.toUri()
        }

        return Response.success(cssUris)
    }

    suspend fun scrapeUrlForCuratedContent(
        baseUrl: String,
        cssHref: String,
        regEx: Regex,
        type: CuratedContentType
    ): Response<CuratedContentResponse> {
        // 1. Fetch the raw CSS text using CSS href
        val response = fetchUrlText(baseUrl + cssHref)

        // 2. Handle the unsuccessful case by creating a Retrofit error response
        if (!response.isSuccessful) {
            val responseBody = response.body
            checkNotNull(responseBody) { "Response body is null" }
            return Response.error(response.code, responseBody)
        }

        // 3. Get the response body as a CSS string
        val cssString = response.body?.string()
        checkNotNull(cssString) { "CSS response body is null" }

        // 4. Parse CSS string to get curated content items
        val css = CSSReader.readFromString(cssString) ?: run {
            // If we couldn't read bodyString as CSS, treat it as an error
            val errorBody = "".toResponseBody(CSS_MEDIA_TYPE)
            return Response.error(404, errorBody)
        }

        val curatedContent = mutableListOf<CuratedContentItemDto>()
        CSSVisitor.visitCSS(
            css,
            object : DefaultCSSVisitor() {
                override fun onBeginStyleRule(aStyleRule: CSSStyleRule) {
                    val bgImageDeclaration = aStyleRule.allDeclarations.find { declaration ->
                        declaration.property == BACKGROUND_IMAGE &&
                                declaration.expression.allMembers.any { member ->
                                    member is CSSExpressionMemberTermURI
                                }
                    } ?: return

                    val cssMember = bgImageDeclaration.expression.allMembers
                        .filterIsInstance<CSSExpressionMemberTermURI>()
                        .firstOrNull() ?: return

                    val label = aStyleRule.getSelectorsAsCSSString(
                        CSSWriterSettings.DEFAULT_SETTINGS,
                        0
                    )
                    if (!regEx.matches(label)) {
                        return
                    }

                    curatedContent.add(
                        CuratedContentItemDto(
                            label = label,
                            type = type,
                            href = cssHref,
                            url = cssMember.uri.uri
                        )
                    )
                }
            }
        )

        return Response.success(
            CuratedContentResponse(content = curatedContent.toList())
        )
    }

    suspend fun scrapeUrlForFeaturedContent(
        baseUrl: String,
        cssHref: String
    ): Response<CuratedContentResponse> = scrapeUrlForCuratedContent(
        baseUrl = baseUrl,
        cssHref = cssHref,
        regEx = FEATURED_BACKGROUND_REGEX,
        type = CuratedContentType.FEATURED
    )

    suspend fun scrapeUrlForCommunityContent(
        baseUrl: String,
        cssHref: String
    ): Response<CuratedContentResponse> = scrapeUrlForCuratedContent(
        baseUrl = baseUrl,
        cssHref = cssHref,
        regEx = COMMUNITY_BACKGROUND_REGEX,
        type = CuratedContentType.COMMUNITY
    )

    // endregion Methods

    // region Companion object

    companion object {

        // region Constants

        private const val HREF = "href"
        private const val STYLE_SHEET = "link[rel=stylesheet]"
        private const val BACKGROUND_IMAGE = "background-image"

        // endregion Constants

        // region Variables

        private val CSS_MEDIA_TYPE = "text/css".toMediaType()

        private val INDEX_FILENAME_REGEX = "(index-)([a-zA-Z0-9_.-]+)(\\.css)".toRegex()
        private val FEATURED_BACKGROUND_REGEX = "section\\.new_index\\.?(background_)([0-9]+)".toRegex()
        private val COMMUNITY_BACKGROUND_REGEX = "section\\.inner_content\\.bg_image\\.community".toRegex()

        // endregion Variables

    }

    // endregion Companion object

}