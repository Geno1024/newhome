package g.ufi

import java.io.InputStream

fun InputStream.readUntil(vararg b: Int) = mutableListOf<Byte>().apply {
    var c = read()
    while (c !in b)
    {
        add(c.toByte())
        c = read()
    }
}.toByteArray()

fun InputStream.readUntilSeq(bs: ByteArray) = mutableListOf<Byte>().apply {
    do
    {
        add(read().toByte())
    } while (!takeLast(bs.size).toByteArray().contentEquals(bs))
}.toByteArray()
