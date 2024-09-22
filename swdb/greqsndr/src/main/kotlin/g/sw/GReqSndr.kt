package g.sw

import g.ufi.GNewHome
import g.ufi.getopt

object GReqSndr
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        val options = args.getopt()

        if (options.contains("-i"))
        {

        }

        else
        {
            val svc = options["--svc"]?.get(0) ?: ""
            val path = options["--path"]?.get(0) ?: ""
            val params = options["--params"] ?: emptyList()
            val response  = with(
                GNewHome.Request(
                    svc, path, params
                )
            ) {
                println(toString())
                send()
            }
            println(response)
        }
    }
}
