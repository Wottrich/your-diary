package wottrich.github.io.yourdiary

fun main() {

    val symbol = "R$"
    val text = "R$ 0,00"

    for (char in symbol) {
        //println(char.toString())
        text.replace(char.toString(), "")
    }

    println(text)

}