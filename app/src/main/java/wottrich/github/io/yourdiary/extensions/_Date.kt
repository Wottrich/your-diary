package wottrich.github.io.yourdiary.extensions

import android.app.DatePickerDialog
import android.content.Context
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.sign

const val actualDay = Calendar.DAY_OF_MONTH
const val actualMonth = Calendar.MONTH
const val actualYear = Calendar.YEAR

fun String.getDateFrom( pattern: String): Date {
    try {
        return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (d: ParseException) {
        throw IllegalArgumentException(String.format(Locale.getDefault(), "Não foi possível realizar o parse da data %s com o padrão %s", this, pattern))
    }
}

fun OnCalendarPicker.showPicker(context: Context) : DatePickerDialog {
    DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val monthCorrect = if (month.toString().length == 1) "0$month" else month.toString()
                this.onDate("$dayOfMonth/$monthCorrect/$year".getDateFrom("dd/MM/yyyy"), "$dayOfMonth/$monthCorrect/$year")
            }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    ).let { picker ->
        //picker.datePicker.minDate = Date().time
        return picker
    }
}

interface OnCalendarPicker {
    fun onDate(date: Date, dateString: String)
}