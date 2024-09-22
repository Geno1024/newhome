package g.ufi

import java.io.InputStream

fun InputStream.readLine(): String = readUntil('\n')

fun InputStream.readUntil(c: Char): String
{
    val buf = StringBuffer()
    var thiz = 0
    while (true)
    {
        thiz = read()
        if ((thiz == -1) or (thiz.toChar() == c)) break
        buf.append(thiz.toChar())
    }
    return buf.toString()
}

fun Array<String>.getopt() = fold(Pair(emptyMap<String, List<String>>(), "")) { (map, lastKey), elem ->
    if (elem.startsWith("-"))  Pair(map + (elem to emptyList()), elem)
    else Pair(map + (lastKey to map.getOrElse(lastKey) { emptyList() } + elem), lastKey)
}.first
