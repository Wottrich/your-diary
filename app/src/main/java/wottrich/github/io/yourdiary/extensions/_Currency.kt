package wottrich.github.io.yourdiary.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*


var locale = Locale("pt", "BR")

fun convertToDouble(value: String, l: Locale): Double {
    val format = NumberFormat.getCurrencyInstance(l) as DecimalFormat
    val symbols = format.decimalFormatSymbols
    format.positivePrefix = ""

    val symbol = symbols.currencySymbol.trim()
    symbols.currencySymbol = ""

    val removeSymbol = value.replace(symbol, "").replace(" ", "")
    val clean2 = removeSymbol.replace(".", "").replace(",", "")
    val clean = cleanText(clean2)
    print("$clean $value")

    val parsed = BigDecimal(clean)
            .setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

    return parsed.toDouble()
}

fun convertToDouble(value: String): Double {
    val clean = cleanText(value)
    print("$clean $value")

    val parsed = BigDecimal(clean)
            .setScale(2, BigDecimal.ROUND_FLOOR)
            .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

    return parsed.toDouble()
}

fun getLocale(locale: Locale) = locale.country


fun cleanText(text: String): String {
    return text.replace("[,.\\s]", "")
}

fun String.removeDot () : String {
    return this.replace(".", "")
}

fun removeSymbol(text: String, symbol: String): String {
    return text.replace(symbol, "").replace(" ", "")
}

fun decimalFormat(l: Locale): DecimalFormat? {
    return NumberFormat.getCurrencyInstance(l) as? DecimalFormat
}

fun symbolsFormat(format: DecimalFormat): DecimalFormatSymbols {
    return format.decimalFormatSymbols
}

fun symbol(symbols: DecimalFormatSymbols) : String {
    val symbol = symbols.currencySymbol.trim()
    symbols.currencySymbol = ""
    return symbol
}

fun Double.addSymbol (l: Locale = locale) : String {
    decimalFormat(l)?.also {
        val symbols = symbolsFormat(it)
        val symbol = symbol(symbols)
        return String.format("$symbol %.2f", this)
    }
    return ""
}

fun Double.format () : String {
    val format = NumberFormat.getCurrencyInstance(locale)
    return format.format(this)
}

fun removeAllAndFormat(text: String, l: Locale) : Double {
    val format: DecimalFormat = decimalFormat(l) ?: throw Throwable("Error to add value")
    val symbols = symbolsFormat(format)
    val symbol = symbol(symbols)
    val text1 = removeSymbol(text, symbol)
    val text2 = text1.replace(".", "").replace(",", "")
    val finalText = cleanText(text2)

    return if (!finalText.isEmpty()) finalText.toDouble().div(10) else throw  Throwable("Erro to add value")
}