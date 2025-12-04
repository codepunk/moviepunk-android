package com.codepunk.moviepunk.util.http

import androidx.collection.SparseArrayCompat
import androidx.collection.getOrElse

/**
 * A convenience class for categorizing, working with and looking up HTTP status codes.
 */
class HttpStatus private constructor(

    /**
     * The integer value of the HTTP status code.
     */
    val code: Int,

    /**
     * A short reason phrase describing the HTTP status code.
     */
    val reasonPhrase: String = "",

) {

    // region Properties

    /**
     * The HTTP status code category (i.e. Information, Success, Server Error, etc.).
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val responseCategory: ResponseCategory = when (code) {
        in 100 until 200 -> ResponseCategory.INFORMATION
        in 200 until 300 -> ResponseCategory.SUCCESS
        in 300 until 400 -> ResponseCategory.REDIRECTION
        in 400 until 500 -> ResponseCategory.CLIENT_ERROR
        in 500 until 600 -> ResponseCategory.SERVER_ERROR
        else -> ResponseCategory.UNKNOWN
    }

    /**
     * A description of the HTTP status code that includes the integer value and reason phrase.
     */
    @Suppress("unused")
    val description: String by lazy {
        val phrase = when {
            reasonPhrase.isBlank() -> responseCategory.description
            else -> reasonPhrase
        }
        "$code $phrase"
    }

    // endregion Properties

    // region Methods

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpStatus

        if (code != other.code) return false
        if (reasonPhrase != other.reasonPhrase) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + reasonPhrase.hashCode()
        return result
    }

    override fun toString(): String {
        return "HttpStatus(code=$code, reasonPhrase='$reasonPhrase')"
    }

    // endregion Methods

    // region Nested & inner classes

    /**
     * An enum class representing the category of this HTTP status code.
     */
    enum class ResponseCategory(

        /**
         * A code for this category, in the form of a string '1xx', '2xx', etc.
         */
        val code: String,

        /**
         * A short description of this category.
         */
        val description: String

    ) {

        // region Values

        /**
         * The request was received, continuing process.
         */
        INFORMATION("1xx", "Information"),

        /**
         * The request was successfully received, understood, and accepted.
         */
        SUCCESS("2xx", "Success"),

        /**
         * Further action needs to be taken in order to complete the request.
         */
        REDIRECTION("3xx", "Redirection"),

        /**
         * The request contains bad syntax or cannot be fulfilled.
         */
        CLIENT_ERROR("4xx", "Client Error"),

        /**
         * The server failed to fulfill an apparently valid request.
         */
        SERVER_ERROR("5xx", "Server Error"),

        /**
         * The category of the HTTP status code could not be determined.
         */
        UNKNOWN("?", "Unknown");

        // endregion Values

        // region Methods

        override fun toString(): String {
            return "${javaClass.simpleName}(code='$code', description='$description')"
        }

        // endregion Methods

    }

    // endregion Nested & inner lasses

    // region Companion object

    @Suppress("unused")
    companion object {

        // region Constants

        /**
         * Reason phrase for an unrecognized HTTP code.
         */
        private const val UNRECOGNIZED = "Unrecognized status"

        // endregion Constants

        // region Properties

        /**
         * A [SparseArrayCompat] of HTTP status codes for speedy lookup.
         */
        @JvmStatic
        private val lookupArray = SparseArrayCompat<HttpStatus>()

        /**
         * The server has received the request headers and the client should proceed to send the
         * request body.
         */
        @JvmStatic
        val CONTINUE: HttpStatus = HttpStatus(
            code = 100,
            reasonPhrase = "Continue"
        ).apply { lookupArray.append(code, this) }

        /**
         * The requester has asked the server to switch protocols and the server has agreed to do so.
         */
        @JvmStatic
        val SWITCHING_PROTOCOLS: HttpStatus = HttpStatus(
            code = 101,
            reasonPhrase = "Switching protocols"
        ).apply { lookupArray.append(code, this) }

        /**
         * Standard response for successful HTTP requests.
         */
        @JvmStatic
        val OK = HttpStatus(
            code = 200,
            reasonPhrase = "OK"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request has been fulfilled, resulting in the creation of a new resource.
         */
        @JvmStatic
        val CREATED = HttpStatus(
            code = 201,
            reasonPhrase = "Created"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request has been accepted for processing, but the processing has not been completed.
         */
        @JvmStatic
        val ACCEPTED = HttpStatus(
            code = 202,
            reasonPhrase = "Accepted"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server is a transforming proxy (e.g. a Web accelerator) that received a 200 OK from
         * its origin, but is returning a modified version of the origin's response.
         */
        @JvmStatic
        val NON_AUTHORITATIVE_INFORMATION = HttpStatus(
            code = 203,
            reasonPhrase = "Non-Authoritative Information"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server successfully processed the request and is not returning any content.
         */
        @JvmStatic
        val NO_CONTENT = HttpStatus(
            code = 204,
            reasonPhrase = "No Content"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server successfully processed the request, but is not returning any content. Unlike
         * a NO_CONTENT response, this response requires that the requester reset the document
         * view.
         */
        @JvmStatic
        val RESET_CONTENT = HttpStatus(
            code = 205,
            reasonPhrase = "Reset Content"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server is delivering only part of the resource (byte serving) due to a range header
         * sent by the client.
         */
        @JvmStatic
        val PARTIAL_CONTENT = HttpStatus(
            code = 206,
            reasonPhrase = "Partial Content"
        ).apply { lookupArray.append(code, this) }

        /**
         * Indicates multiple options for the resource from which the client may choose (via
         * agent-driven content negotiation).
         */
        @JvmStatic
        val MULTIPLE_CHOICES = HttpStatus(
            code = 300,
            reasonPhrase = "Multiple Choices"
        ).apply { lookupArray.append(code, this) }

        /**
         * This and all future requests should be directed to the given URI.
         */
        @JvmStatic
        val MOVED_PERMANENTLY = HttpStatus(
            code = 300,
            reasonPhrase = "Moved Permanently"
        ).apply { lookupArray.append(code, this) }

        /**
         * Tells the client to look at (browse to) another url.
         */
        @JvmStatic
        val FOUND = HttpStatus(
            code = 302,
            reasonPhrase = "Found"
        ).apply { lookupArray.append(code, this) }

        /**
         * The response to the request can be found under another URI using the GET method.
         */
        @JvmStatic
        val SEE_OTHER = HttpStatus(
            code = 303,
            reasonPhrase = "See Other"
        ).apply { lookupArray.append(code, this) }

        /**
         * Indicates that the resource has not been modified since the version specified by the
         * request headers If-Modified-Since or If-None-Match.
         */
        @JvmStatic
        val NOT_MODIFIED = HttpStatus(
            code = 304,
            reasonPhrase = "Not Modified"
        ).apply { lookupArray.append(code, this) }

        /**
         * The requested resource is available only through a proxy, the address for which is
         * provided in the response.
         */
        @JvmStatic
        val USE_PROXY = HttpStatus(
            code = 305,
            reasonPhrase = "Use Proxy"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server cannot or will not process the request due to an apparent client error.
         */
        @JvmStatic
        val BAD_REQUEST = HttpStatus(
            code = 400,
            reasonPhrase = "Bad Request"
        ).apply { lookupArray.append(code, this) }

        /**
         * Similar to 403 Forbidden, but specifically for use when authentication is required and
         * has failed or has not yet been provided.
         */
        @JvmStatic
        val UNAUTHORIZED = HttpStatus(
            code = 401,
            reasonPhrase = "Unauthorized"
        ).apply { lookupArray.append(code, this) }

        /**
         * Reserved for future use. The original intention was that this code might be used as part
         * of some form of digital cash or micropayment scheme but that has not yet happened, and
         * this code is not usually used.
         */
        @JvmStatic
        val PAYMENT_REQUIRED = HttpStatus(
            code = 402,
            reasonPhrase = "Payment Required"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request was valid, but the server is refusing action.
         */
        @JvmStatic
        val FORBIDDEN = HttpStatus(
            code = 403,
            reasonPhrase = "Forbidden"
        ).apply { lookupArray.append(code, this) }

        /**
         * The requested resource could not be found but may be available in the future.
         */
        @JvmStatic
        val NOT_FOUND = HttpStatus(
            code = 404,
            reasonPhrase = "Not Found"
        ).apply { lookupArray.append(code, this) }

        /**
         * A request method is not supported for the requested resource.
         */
        @JvmStatic
        val METHOD_NOT_ALLOWED = HttpStatus(
            code = 405,
            reasonPhrase = "Method Not Allowed"
        ).apply { lookupArray.append(code, this) }

        /**
         * The requested resource is capable of generating only content not acceptable according to
         * the Accept headers sent in the request.
         */
        @JvmStatic
        val NOT_ACCEPTABLE = HttpStatus(
            code = 406,
            reasonPhrase = "Not Acceptable"
        ).apply { lookupArray.append(code, this) }

        /**
         * The client must first authenticate itself with the proxy.
         */
        @JvmStatic
        val PROXY_AUTHENTICATION_REQUIRED = HttpStatus(
            code = 407,
            reasonPhrase = "Proxy Authentication Required"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server timed out waiting for the request.
         */
        @JvmStatic
        val REQUEST_TIMEOUT = HttpStatus(
            code = 408,
            reasonPhrase = "Request Timeout"
        ).apply { lookupArray.append(code, this) }

        /**
         * Indicates that the request could not be processed because of conflict in the current
         * state of the resource.
         */
        @JvmStatic
        val CONFLICT = HttpStatus(
            code = 409,
            reasonPhrase ="Conflict"
        ).apply { lookupArray.append(code, this) }

        /**
         * Indicates that the resource requested is no longer available and will not be available
         * again.
         */
        @JvmStatic
        val GONE = HttpStatus(
            code = 410,
            reasonPhrase = "Gone"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request did not specify the length of its content, which is required by the requested
         * resource.
         */
        @JvmStatic
        val LENGTH_REQUIRED = HttpStatus(
            code = 411,
            reasonPhrase = "Length Required"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server does not meet one of the preconditions that the requester put on the request.
         */
        @JvmStatic
        val PRECONDITION_FAILED = HttpStatus(
            code = 412,
            reasonPhrase = "Precondition Failed"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request is larger than the server is willing or able to process.
         */
        @JvmStatic
        val PAYLOAD_TOO_LARGE = HttpStatus(
            code = 413,
            reasonPhrase = "Payload Too Large"
        ).apply { lookupArray.append(code, this) }

        /**
         * The URI provided was too long for the server to process.
         */
        @JvmStatic
        val REQUEST_URI_TOO_LONG = HttpStatus(
            code = 414,
            reasonPhrase = "Request-URI Too Long"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request entity has a media type which the server or resource does not support.
         */
        @JvmStatic
        val UNSUPPORTED_MEDIA_TYPE = HttpStatus(
            code = 415,
            reasonPhrase = "Unsupported Media Type"
        ).apply { lookupArray.append(code, this) }

        /**
         * The client has asked for a portion of the file (byte serving) but the server cannot
         * supply that portion.
         */
        @JvmStatic
        val REQUESTED_RANGE_NOT_SATISFIABLE = HttpStatus(
            code = 416,
            reasonPhrase = "Requested Range Not Satisfiable"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server cannot meet the requirements of the Expect request-header field.
         */
        @JvmStatic
        val EXPECTATION_FAILED = HttpStatus(
            code = 417,
            reasonPhrase = "Expectation Failed"
        ).apply { lookupArray.append(code, this) }

        /**
         * This code was defined in 1998 as an April Fools' joke and is not expected to be
         * implemented by actual HTTP servers.
         */
        @JvmStatic
        val IM_A_TEAPOT = HttpStatus(
            code = 418,
            reasonPhrase = "I'm a Teapot"
        ).apply { lookupArray.append(code, this) }

        /**
         * The request was well-formed but was unable to be followed due to semantic errors.
         */
        @JvmStatic
        val UNPROCESSABLE_ENTITY = HttpStatus(
            code = 422,
            reasonPhrase = "Unprocessable Entity"
        ).apply { lookupArray.append(code, this) }

        /**
         * A generic error message, given when an unexpected condition was encountered and no more
         * specific message is suitable.
         */
        @JvmStatic
        val INTERNAL_SERVER_ERROR = HttpStatus(
            code = 500,
            reasonPhrase = "Internal Server Error"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server either does not recognize the request method, or it lacks the ability to
         * fulfill the request.
         */
        @JvmStatic
        val NOT_IMPLEMENTED = HttpStatus(
            code = 501,
            reasonPhrase = "Not Implemented"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server was acting as a gateway or proxy and received an invalid response from the
         * upstream server.
         */
        @JvmStatic
        val BAD_GATEWAY = HttpStatus(
            code = 502,
            reasonPhrase = "Bad Gateway"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server is currently unavailable (because it is overloaded or down for maintenance).
         */
        @JvmStatic
        val SERVICE_UNAVAILABLE = HttpStatus(
            code = 503,
            reasonPhrase = "Service Unavailable"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server was acting as a gateway or proxy and did not receive a timely response from
         * the upstream server.
         */
        @JvmStatic
        val GATEWAY_TIMEOUT = HttpStatus(
            code = 504,
            reasonPhrase = "Gateway Timeout"
        ).apply { lookupArray.append(code, this) }

        /**
         * The server does not support the HTTP protocol version used in the request.
         */
        @JvmStatic
        val HTTP_VERSION_NOT_SUPPORTED = HttpStatus(
            code = 505,
            reasonPhrase = "HTTP Version Not Supported"
        ).apply { lookupArray.append(code, this) }

        // endregion Properties

        // region Methods

        /**
         * Returns a predefined [HttpStatus] if the [code] matches one of the predefined values,
         * otherwise it creates a new HttpStatus and attempts to match the category of the
         * given code.
         */
        @JvmStatic
        fun of(
            code: Int
        ): HttpStatus = lookupArray.getOrElse(code) {
            HttpStatus(code = code, reasonPhrase = UNRECOGNIZED)
        }

        // endregion Methods

    }

    // endregion Companion object

}