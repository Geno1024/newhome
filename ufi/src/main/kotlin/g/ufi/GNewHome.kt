package g.ufi

import java.io.BufferedReader
import java.io.InputStream
import java.net.Socket

class GNewHome
{
    companion object
    {
        val defaultTcpPort = "rd".toByteArray().fold(0) { acc, byte -> acc * 256 + byte }
    }

    data class Request(
        var origin: String,
        var svc: String,
        var path: String,
        var params: List<String> = emptyList()
    )
    {
        constructor(socket: Socket) : this(
            socket.inetAddress.hostAddress,
            socket.localAddress.hostAddress,
            socket.getInputStream()
        )

        constructor(origin: String, svc: String, input: InputStream) : this(
            origin,
            svc,
            input.bufferedReader()
        )

        constructor(origin: String, svc: String, br: BufferedReader) : this(
            origin,
            svc,
            br.readLine().toString(),
            br.readLine().toIntOrNull()?:0,
            br
        )

        constructor(origin: String, svc: String, path: String, paramLength: Int, br: BufferedReader) : this(
            origin,
            svc,
            path,
            (0 until paramLength).map { br.readLine() }
        )

        constructor(svc: String, path: String, params: List<String>) : this(
            "",
            svc,
            path,
            params
        )

        override fun toString(): String = "$path\n" +
            "${params.size}\n" +
            "${params.joinToString("\n")}\n"

        fun send(port: Int = defaultTcpPort): String = with(Socket(svc, port)) {
            getOutputStream().write(this@Request.toString().toByteArray())
            getInputStream().bufferedReader().readText()
        }
    }

    data class Response(
        var endpoint: String,
        var code: Int,
        var message: String,
        var details: String = ""
    )
    {
        constructor(code: Int, message: String, details: String) : this(
            "",
            code,
            message,
            details
        )
    }
}
