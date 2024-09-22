package g.sw

import g.ufi.getopt

object Mgmts
{
    @JvmStatic
    fun main(args: Array<String>)
    {
        val options = args.getopt()
        val path = options["--path"]?.get(0) ?: ""
        val params = options["--params"]?: emptyList()
        println(path)
        println(params)
    }
}
