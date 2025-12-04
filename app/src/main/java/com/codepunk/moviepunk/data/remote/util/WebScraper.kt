package com.codepunk.moviepunk.data.remote.util

import androidx.core.net.toUri
import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import com.codepunk.moviepunk.data.remote.response.CuratedContentResponse
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.helger.css.decl.CSSExpressionMemberTermURI
import com.helger.css.decl.CSSStyleRule
import com.helger.css.decl.ICSSSelectorMember
import com.helger.css.decl.visit.CSSVisitor
import com.helger.css.decl.visit.DefaultCSSVisitor
import com.helger.css.reader.CSSReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.Response as OkhttpResponse
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Response
import javax.inject.Inject

class WebScraper @Inject constructor(
    private val okHttpClient: OkHttpClient,
    @param:IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    // region Methods

    /**
     * Returns a [Response] containing the result of [urlString] formatted as a text string.
     */
    private suspend fun fetchUrlText(
        urlString: String
    ): OkhttpResponse = withContext(ioDispatcher) {
        val request = Request.Builder().url(urlString).build()
        okHttpClient.newCall(request).execute()
    }

    /**
     * Returns whether the given element represents an index CSS file link, based on whether
     * the link represents a mobile CSS file or not.
     */
    private fun Element.isIndexCssLink(isMobile: Boolean = true): Boolean {
        val uri = attribute(HREF)?.value?.toUri() ?: return false
        if (isMobile && !uri.pathSegments.contains("mobile")) return false
        return INDEX_FILENAME_REGEX.matches(uri.lastPathSegment.orEmpty())
    }

    /**
     * Returns the URL string of the CSS document with a name in the form of
     * "index-(hash).css", if such a document exists
     **/
    private fun getIndexCssHref(htmlString: String): String? = Jsoup.parse(htmlString)
        .select(STYLE_SHEET)
        .find { it.isIndexCssLink() }
        ?.attribute(HREF)
        ?.value

    /**
     * Scrapes the given [urlString] for the index CSS file href.
     */
    suspend fun scrapeUrlForIndexCssHref(urlString: String): String? {
        val response = fetchUrlText(urlString)
        if (!response.isSuccessful) return null
        val body = response.body ?: return null
        return getIndexCssHref(body.string())
    }

    /**
     * Extracts the background name (if one exists) from a [CSSStyleRule]. A background name
     * is a [ICSSSelectorMember] with a format of "background_n", where "n" is a number
     */
    private fun CSSStyleRule.findBackgroundName(): String? {
        allSelectors.forEach { selector ->
            selector.allMembers.lastOrNull?.let { lastMember ->
                BACKGROUND_REGEX.find(lastMember.asCSSString)
            }?.run {
                return value
            }
        }
        return null
    }

    /* TODO Can CSSVisitor.visitCSS section below be cleaned up? */
    suspend fun scrapeUrlForCuratedContent(
        baseUrl: String,
        cssHref: String
    ): Response<CuratedContentResponse> {
        // 1. Fetch the raw text using your existing OkHttp call
        val okhttpResponse = fetchUrlText(baseUrl + cssHref)

        // 2. Handle the unsuccessful case by creating a Retrofit error response
        if (!okhttpResponse.isSuccessful) {
            // You can create a ResponseBody for the error from the OkHttp response body
            val errorBody = okhttpResponse.body?.let {
                it.string().toResponseBody(it.contentType())
            } ?: "".toResponseBody(null) // Or provide an empty body

            // Return a Retrofit error response, passing the code, message, and body
            return Response.error(okhttpResponse.code, errorBody)
        }

        // 3. Handle the successful case
        val bodyString = okhttpResponse.body?.string() ?: run {
            // If the body is null even on a successful response, treat it as an error.
            // A 204 No Content response is a common case for this.
            val errorBody = "".toResponseBody(null)
            return Response.error(okhttpResponse.code, errorBody)
        }

        // 4. Parse the successful response body to get your data
        val curatedContent = mutableListOf<CuratedContentItemDto>()
        CSSReader.readFromString(bodyString)?.apply {
            CSSVisitor.visitCSS(
                this,
                object : DefaultCSSVisitor() {
                    override fun onBeginStyleRule(aStyleRule: CSSStyleRule) {
                        val cssUri = aStyleRule.allDeclarations.mapNotNull { declaration ->
                            declaration.expression.allMembers
                                .filter { it is CSSExpressionMemberTermURI }
                                .map { it as CSSExpressionMemberTermURI }
                        }.flatten().firstOrNull()?.uri ?: return
                        val backgroundName = aStyleRule.findBackgroundName() ?: return
                        val result = BACKGROUND_REGEX.find(backgroundName) ?: return
                        val backgroundId = result.destructured.component2().toIntOrNull() ?: return

                        curatedContent.add(
                            CuratedContentItemDto(
                                id = backgroundId,
                                href = cssHref,
                                url = cssUri.uri
                            )
                        )
                    }
                }
            )
        }

        // 5. Return a Retrofit success response with the parsed data
        return Response.success(
            CuratedContentResponse(content = curatedContent.toList())
        )
    }

    // endregion Methods

    // region Companion object

    companion object {

        // region Constants

        private const val HREF = "href"
        private const val STYLE_SHEET = "link[rel=stylesheet]"

        // endregion Constants

        // region Variables

        private val INDEX_FILENAME_REGEX = "(index-)([a-zA-Z0-9_.-]+)(\\.css)".toRegex()
        private val BACKGROUND_REGEX = "\\.?(background_)([0-9]+)".toRegex()

        // endregion Variables

    }

    // endregion Companion object

}