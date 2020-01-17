package wottrich.github.io.yourdiary

import wottrich.github.io.yourdiary.extensions._locale
import wottrich.github.io.yourdiary.extensions.convertToDouble
import wottrich.github.io.yourdiary.extensions.format


fun main() {

    val a = "9.03"
    val b = convertToDouble(a, _locale)
    val c = b.format()

    println(c)

}