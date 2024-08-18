package g.sw.stellar

import g.ufi.Https

object Stellar
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        Https.Request(
            Https.Method.GET,
            "/",
            Https.Version.HTTP10,
            emptyList()
        ).send("www.baidu.com", 443).body.decodeToString().apply(::println)
    }
}
