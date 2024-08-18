package g.ufi

import java.net.Socket

object Http
{
    data class Request(
        var method: Method,
        var url: String,
        var version: Version = Version.HTTP10,
        var headers: List<Header> = emptyList(),
        var body: ByteArray = ByteArray(0)
    )
    {
        fun toByteArray() = "$method $url ${version.presentation}\r\n${headers.joinToString("\r\n", transform = Header::toString)}\r\n\r\n".toByteArray(Charsets.UTF_8) + body

        fun send(host: String, port: Short): Response = with (Socket(host, port.toInt())) {
            getOutputStream().write(toByteArray())
            val r = getInputStream()
            Response(
                Version.of(r.readUntil(0x20).decodeToString()),
                r.readUntil(0x20).decodeToString().toInt(),
                r.readUntil(0xd, 0xa).decodeToString(),
                r.readUntilSeq(byteArrayOf(0xd, 0xa, 0xd, 0xa)).decodeToString().trim().split("\r\n").map { line ->
                    with (line.split(":", limit = 2)) {
                        this[0].trim() to this[1].trim()
                    }
                }.groupBy(Pair<String, String>::first)
                    .map { line ->
                        Header(line.key, line.value.map(Pair<String, String>::second))
                    },
                r.readAllBytes()
            )
        }
    }

    data class Response(
        var version: Version = Version.HTTP10,
        var code: Int,
        var message: String,
        var headers: List<Header> = emptyList(),
        var body: ByteArray = ByteArray(0)
    )
    {

    }

    enum class Method
    {
        GET,
        POST,
        PUT,
        DELETE
    }

    enum class Version(val presentation: String)
    {
        HTTP09("HTTP/0.9"),
        HTTP10("HTTP/1.0"),
        HTTP11("HTTP/1.1");

        companion object
        {
            fun of(presentation: String) = entries.firstOrNull { v -> v.presentation == presentation } ?: HTTP09
        }
    }

    data class Header(
        var key: String,
        var value: List<String>
    )
    {
        override fun toString(): String = "$key: $value"
    }
}
