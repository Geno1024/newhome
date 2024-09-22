@file:Suppress("unused")

package g.ufi

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class HTTP
{
    data class Request(
        var method: Method,
        var url: String,
        var version: Version,
        var headers: List<HeaderLine> = emptyList(),
        var body: ByteArray?
    )
    {
        override fun toString() = "$method $url ${version.string}\n" +
            headers.joinToString { "$it\n" }

        fun writeTo(outputStream: OutputStream)
        {
            outputStream.write("$method $url ${version.string}\n".toByteArray())
            headers.forEach { outputStream.write("$it\n".toByteArray()) }
            outputStream.write(byteArrayOf(13, 10))
            if (body != null)
            {
                outputStream.write(body!!)
                outputStream.write(byteArrayOf(13, 10))
            }
        }

        fun send(host: String, port: Int)
        {
            val socket = Socket(host, port)
            writeTo(socket.getOutputStream())
        }

        // <editor-fold desc="Equals and HashCode">
        override fun equals(other: Any?): Boolean
        {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Request

            if (method != other.method) return false
            if (url != other.url) return false
            if (version != other.version) return false
            if (headers != other.headers) return false
            if (body != null)
            {
                if (other.body == null) return false
                if (!body.contentEquals(other.body)) return false
            }
            else if (other.body != null) return false

            return true
        }

        override fun hashCode(): Int
        {
            var result = method.hashCode()
            result = 31 * result + url.hashCode()
            result = 31 * result + version.hashCode()
            result = 31 * result + headers.hashCode()
            result = 31 * result + (body?.contentHashCode() ?: 0)
            return result
        }
        // </editor-fold>
    }

    data class Response(
        var version: Version,
        var code: Int,
        var message: String,
        var headers: MutableList<HeaderLine> = mutableListOf(),
        var body: ByteArray?
    )
    {
        override fun toString(): String = "${version.string} $code $message\n" +
            headers.joinToString { "$it\n" }

        companion object
        {
            fun parseFrom(inputStream: InputStream): Response
            {
                val vcm = inputStream.readLine()
                val version = Version.parse(vcm.split(" ")[0])?:Version.HTTP10
                val code = vcm.split(" ")[1].toInt()
                val message = vcm.split(" ", limit = 3).getOrElse(2) { "" }
                val headers = mutableListOf<HeaderLine>()
                var line = ""
                while (inputStream.readLine().also { line = it }.isNotEmpty())
                {
                    headers.add(HeaderLine.parse(line))
                }
                val body = inputStream.readBytes()
                return Response(version, code, message, headers, body)
            }
        }

        // <editor-fold desc="Equals and HashCode">
        override fun equals(other: Any?): Boolean
        {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Response

            if (version != other.version) return false
            if (code != other.code) return false
            if (message != other.message) return false
            if (headers != other.headers) return false
            if (body != null)
            {
                if (other.body == null) return false
                if (!body.contentEquals(other.body)) return false
            }
            else if (other.body != null) return false

            return true
        }

        override fun hashCode(): Int
        {
            var result = version.hashCode()
            result = 31 * result + code
            result = 31 * result + message.hashCode()
            result = 31 * result + headers.hashCode()
            result = 31 * result + (body?.contentHashCode() ?: 0)
            return result
        }
        // </editor-fold>
    }

    enum class Method
    {
        GET,
        POST,
        PUT,
        DELETE,
        OPTIONS,
        CONNECT,
        TRACE
    }

    enum class Version(val string: String)
    {
        HTTP09("HTTP/0.9"),
        HTTP10("HTTP/1.0"),
        HTTP11("HTTP/1.1");

        companion object
        {
            fun parse(s: String) = entries.firstOrNull { it.string == s }
        }
    }

    data class HeaderLine(
        var key: String,
        var value: String
    )
    {

        companion object
        {
            fun parse(line: String) = line.trim().split(":").let {
                HeaderLine(it[0].trim(), it[1].trim())
            }
        }

        override fun toString(): String = "$key: $value"
    }
}
