package wottrich.github.io.yourdiary.utils

import wottrich.github.io.yourdiary.extensions.*
import java.util.*

class CurrencyUtils {

    companion object {
        fun formatToLocale(text: String, locale: Locale? = _locale) : String {
            val l = locale ?: return ""
            if (l.country == "" || l.language == "") return ""

            val format = decimalFormat(l) ?: return ""
            val symbols = symbolsFormat(format)
            format.positivePrefix = ""

            val symbol = symbol(symbols)

            val removeSymbol = removeSymbol(text, symbol)
            val clean2 = removeSymbol.replace(".", "").replace(",", "")
            val clean3 = cleanText(clean2)
            val clean = clean3.replace("[\\s]".toRegex(), "")

            val parsed = if (clean.isNotEmpty()) clean.toDouble() else 0.00

            if (parsed == 0.0)
                return ""

            format.decimalFormatSymbols = symbols
            val formatFinal = format.format(parsed / 100).replace("\\s", "")

            return "$symbol $formatFinal"
        }
    }
}