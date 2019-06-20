package wottrich.github.io.yourdiary.extensions

import android.app.DatePickerDialog
import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val actualDay = Calendar.DAY_OF_MONTH
const val actualMonth = Calendar.MONTH
const val actualYear = Calendar.YEAR

enum class Month {
    JANUARY     { override var num: Int = 1 },
    FEBRUARY    { override var num: Int = 2 },
    MARCH       { override var num: Int = 3 },
    APRIL       { override var num: Int = 4 },
    MAY         { override var num: Int = 5 },
    JUNE        { override var num: Int = 6 },
    JULY        { override var num: Int = 7 },
    AUGUST      { override var num: Int = 8 },
    SEPTEMBER   { override var num: Int = 9 },
    OCTOBER     { override var num: Int = 10 },
    NOVEMBER    { override var num: Int = 11 },
    DECEMBER    { override var num: Int = 12 };

    abstract var num : Int

}

fun String.getDate(pattern: String = "dd/MM/yyyy"): Date {
    try {
        return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (d: ParseException) {
        throw IllegalArgumentException(String.format(Locale.getDefault(), "Não foi possível realizar o parse da data %s com o padrão %s", this, pattern))
    }
}

fun Date.getDateString (pattern: String = "dd/MM/yyyy", locale: Locale = _locale): String {
    return SimpleDateFormat(pattern, locale).format(this)
}

fun OnCalendarPicker.showPicker(context: Context) : DatePickerDialog {
    DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val monthCorrect = if (month.toString().length == 1) "0$month" else month.toString()
                this.onDate("$dayOfMonth/$monthCorrect/$year".getDate(), "$dayOfMonth/$monthCorrect/$year")
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    ).let { picker ->
        return picker
    }
}

fun Calendar.actualMonth () : String {
    return when (this.get(Calendar.MONTH) + 1) {
        Month.JANUARY.num -> "Janeiro"
        Month.FEBRUARY.num -> "Fevereiro"
        Month.MARCH.num -> "Março"
        Month.APRIL.num -> "Abril"
        Month.MAY.num -> "Maio"
        Month.JUNE.num -> "Junho"
        Month.JULY.num -> "Julho"
        Month.AUGUST.num -> "Agosto"
        Month.SEPTEMBER.num -> "Setembro"
        Month.OCTOBER.num -> "Outubro"
        Month.NOVEMBER.num -> "Novembro"
        Month.DECEMBER.num -> "Dezembro"
        else -> "Error"
    }
}

interface OnCalendarPicker {
    fun onDate(date: Date, dateString: String)
}