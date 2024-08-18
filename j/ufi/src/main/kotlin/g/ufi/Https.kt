package g.ufi

import java.net.Socket
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object Https
{
    data class Request(
        var method: Method,
        var url: String,
        var version: Version = Version.HTTP10,
        var headers: List<Header> = emptyList(),
        var body: ByteArray = ByteArray(0)
    )
    {

        fun send(host: String, port: Short): Response = with (URL("https://$host:$port$url").openConnection() as HttpsURLConnection) c@ {
            requestMethod = method.name
            headers.forEach { header ->
                setRequestProperty(header.key, header.value.joinToString("; "))
            }
            if (body.isNotEmpty())
            {
                doOutput = true
                outputStream.write(body)
            }
            Response(
                Version.HTTP11,
                responseCode,
                responseMessage,
                headerFields.filter { it.key != null } .map { Header(it.key, it.value) },
                inputStream.readAllBytes()
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
