package sample.kotlin.project.data.sources.search

import android.net.Uri
import retrofit2.Response

internal class PageLinks<T>(response: Response<T>) {

    internal var first: Int? = null
    internal var last: Int? = null
    internal var next: Int? = null
    internal var prev: Int? = null

    companion object {
        private const val DELIMITER_LINKS = ","
        private const val DELIMITER_LINK_PARAM = ";"
        private const val HEADER_LINK = "Link"
        private const val HEADER_NEXT = "X-Next"
        private const val HEADER_LAST = "X-Last"
        private const val META_REL = "rel"
        private const val META_LAST = "last"
        private const val META_NEXT = "next"
        private const val META_FIRST = "first"
        private const val META_PREV = "prev"
    }

    init {
        val linkHeader = response.headers()[HEADER_LINK]
        if (linkHeader != null) {
            val links = linkHeader.split(DELIMITER_LINKS).toTypedArray()
            for (link in links) {
                val segments = link.split(DELIMITER_LINK_PARAM).toTypedArray()
                if (segments.size < 2) {
                    continue
                }
                var linkPart = segments[0].trim()
                if (!linkPart.startsWith("<") || !linkPart.endsWith(">")) {
                    continue
                }
                linkPart = linkPart.substring(1, linkPart.length - 1)
                for (i in 1 until segments.size) {
                    val rel = segments[i].trim().split("=").toTypedArray()
                    if (rel.size < 2 || META_REL != rel[0]) {
                        continue
                    }
                    var relValue = rel[1]
                    if (relValue.startsWith("\"") && relValue.endsWith("\"")) {
                        relValue = relValue.substring(1, relValue.length - 1)
                    }
                    when (true) {
                        META_FIRST == relValue -> first = extractParam(linkPart)
                        META_LAST == relValue -> last = extractParam(linkPart)
                        META_NEXT == relValue -> next = extractParam(linkPart)
                        META_PREV == relValue -> prev = extractParam(linkPart)
                    }
                }
            }
        } else {
            next = extractParam(response.headers()[HEADER_NEXT]!!)
            last = extractParam(response.headers()[HEADER_LAST]!!)
        }
    }

    private fun extractParam(link: String) = Uri.parse(link).getQueryParameter("page")!!.toInt()
}
