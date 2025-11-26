package com.codepunk.moviepunk.data.remote.util

import androidx.core.net.toUri
import com.codepunk.moviepunk.data.remote.dto.CuratedContentItemDto
import com.codepunk.moviepunk.di.qualifier.IoDispatcher
import com.helger.css.decl.CSSDeclaration
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
import okhttp3.Response
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import javax.inject.Inject

/*
 * TODO Thoughts
 *  I think I need a 2 versions of css/hash etc.
 *  One that accepts a urlString
 *  Another that accepts the hash directly
 */

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
    ): Response = withContext(ioDispatcher) {
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

    /* TODO Clean this up */
    suspend fun scrapeUrlForCuratedContent(
        baseUrl: String,
        cssHref: String
    ): List<CuratedContentItemDto> {
        val response = fetchUrlText(baseUrl + cssHref)
        if (!response.isSuccessful) return emptyList()
        val body = response.body ?: return emptyList()
        val css = CSSReader.readFromString(body.string()) ?: return emptyList()
        val curatedContent = mutableListOf<CuratedContentItemDto>()
        // TODO Clean up this logic
        CSSVisitor.visitCSS(
            css,
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
        return curatedContent
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