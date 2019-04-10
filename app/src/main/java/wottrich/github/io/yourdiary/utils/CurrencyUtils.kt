package wottrich.github.io.yourdiary.utils

import wottrich.github.io.yourdiary.extensions.*
import java.util.*

class CurrencyUtils {

    companion object {
        fun formatToLocale(text: String, locale: Locale?) : String {
            val l = locale ?: return ""
            if (l.country == "" || l.language == "") return ""

            val format = decimalFormat(l) ?: return ""
            val symbols = symbolsFormat(format)
            format.positivePrefix = ""

            val symbol = symbol(symbols)

            val removeSymbol = removeSymbol(text, symbol)
            val clean2 = removeSymbol.replace(".", "").replace(",", "")
            val clean = cleanText(clean2)

            val parsed = if (!clean.isEmpty()) clean.toDouble() else 0.00

            format.decimalFormatSymbols = symbols
            val formatFinal = format.format(parsed / 100).replace("\\s", "")

            return "$symbol $formatFinal"
        }
    }
}