package wottrich.github.io.yourdiary.extensions

import wottrich.github.io.yourdiary.R
import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker




const val actualDay = Calendar.DAY_OF_MONTH
const val actualMonth = Calendar.MONTH
const val actualYear = Calendar.YEAR

enum class Month {
    JANUARY {
        override var num: Int = 1
        override var realNum = 0
        override var nameMonth: Int = R.string.month_january
    },
    FEBRUARY {
        override var num: Int = 2
        override var realNum = 1
        override var nameMonth: Int = R.string.month_february
    },
    MARCH {
        override var num: Int = 3
        override var realNum = 2
        override var nameMonth: Int = R.string.month_march
    },
    APRIL {
        override var num: Int = 4
        override var realNum = 3
        override var nameMonth: Int = R.string.month_april
    },
    MAY {
        override var num: Int = 5
        override var realNum = 4
        override var nameMonth: Int = R.string.month_may
    },
    JUNE {
        override var num: Int = 6
        override var realNum = 5
        override var nameMonth: Int = R.string.month_june
    },
    JULY {
        override var num: Int = 7
        override var realNum = 6
        override var nameMonth: Int = R.string.month_july
    },
    AUGUST {
        override var num: Int = 8
        override var realNum = 7
        override var nameMonth: Int = R.string.month_august
    },
    SEPTEMBER {
        override var num: Int = 9
        override var realNum = 8
        override var nameMonth: Int = R.string.month_september
    },
    OCTOBER {
        override var num: Int = 10
        override var realNum = 9
        override var nameMonth: Int = R.string.month_october
    },
    NOVEMBER {
        override var num: Int = 11
        override var realNum = 10
        override var nameMonth: Int = R.string.month_november
    },
    DECEMBER {
        override var num: Int = 12
        override var realNum = 11
        override var nameMonth: Int = R.string.month_december
    },
    EMPTY_MONTH {
        override var num: Int = -1
        override var realNum = -1
        override var nameMonth: Int = R.string.month_empty
    };

    abstract var num: Int
    abstract var realNum: Int
    abstract var nameMonth: Int

}

fun String.getDate(pattern: String = "dd/MM/yyyy"): Date {
    try {
        return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (d: ParseException) {
        throw IllegalArgumentException(
            String.format(
                Locale.getDefault(),
                "Não foi possível realizar o parse da data %s com o padrão %s",
                this,
                pattern
            )
        )
    }
}

fun Date.getDateString(pattern: String = "dd/MM/yyyy", locale: Locale = _locale): String {
    return SimpleDateFormat(pattern, locale).format(this)
}

fun Date.exDay(): String {
    return SimpleDateFormat("dd", _locale).format(this)
}

fun Date.exMonth(): String {
    return SimpleDateFormat("MM", _locale).format(this)
}

fun Date.exYear(): String {
    return SimpleDateFormat("yyyy", _locale).format(this)
}

/**
 * day      =   false | true  | false | false | true  | true | false
 * month    =   false | false | true  | false | true  | true | true
 * year     =   false | false | false | true  | false | true | true
 */
fun Date.compareActualDate(
    day: Boolean = false,
    month: Boolean = false,
    year: Boolean = false
): Boolean {
    val dayDate = this.exDay()
    val monthDate = this.exMonth()
    val yearDate = this.exYear()

    val actualDate = Date()
    val actualDay = actualDate.exDay()
    val actualMonth = actualDate.exMonth()
    val actualYear = actualDate.exYear()

    return if (day && month && year) {
        dayDate == actualDay && monthDate == actualMonth && yearDate == actualYear
    } else if (!day && month && year) {
        monthDate == actualMonth && yearDate == actualYear
    } else if (!day && !month && year) {
        yearDate == actualYear
    } else if (day && month && !year) {
        dayDate == actualDay && monthDate == actualMonth
    } else if (day && !month && !year) {
        dayDate == actualDay
    } else if (!day && month && !year) {
        monthDate == actualMonth
    } else if (day && !month && year) {
        dayDate == actualDay && yearDate == actualYear
    } else {
        false
    }


}

fun Date.getTomorrow(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, 1)
    return calendar.time
}

fun Date.getYesterday(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return calendar.time
}

fun Date.getFirstDayOfMonth (): Date {

    val calendar = Calendar.getInstance()
    calendar.time = this

    val actualDay = calendar.get(Calendar.DAY_OF_MONTH)
    val minusDays = 1 - actualDay

    calendar.add(Calendar.DAY_OF_YEAR, minusDays)

    return calendar.time
}

fun Date.reInit(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}

fun getActualMonthByMonth(): Month {

    val calendar = Calendar.getInstance()

    return when (calendar.get(Calendar.MONTH)) {
        Month.JANUARY.realNum -> Month.JANUARY
        Month.FEBRUARY.realNum -> Month.FEBRUARY
        Month.MARCH.realNum -> Month.MARCH
        Month.APRIL.realNum -> Month.APRIL
        Month.MAY.realNum -> Month.MAY
        Month.JUNE.realNum -> Month.JUNE
        Month.JULY.realNum -> Month.JULY
        Month.AUGUST.realNum -> Month.AUGUST
        Month.SEPTEMBER.realNum -> Month.SEPTEMBER
        Month.OCTOBER.realNum -> Month.OCTOBER
        Month.NOVEMBER.realNum -> Month.NOVEMBER
        Month.DECEMBER.realNum -> Month.DECEMBER
        else -> Month.EMPTY_MONTH
    }

}

fun getActualMonthByNumber(number: Int): Month {

    return when (number) {
        Month.JANUARY.realNum -> Month.JANUARY
        Month.FEBRUARY.realNum -> Month.FEBRUARY
        Month.MARCH.realNum -> Month.MARCH
        Month.APRIL.realNum -> Month.APRIL
        Month.MAY.realNum -> Month.MAY
        Month.JUNE.realNum -> Month.JUNE
        Month.JULY.realNum -> Month.JULY
        Month.AUGUST.realNum -> Month.AUGUST
        Month.SEPTEMBER.realNum -> Month.SEPTEMBER
        Month.OCTOBER.realNum -> Month.OCTOBER
        Month.NOVEMBER.realNum -> Month.NOVEMBER
        Month.DECEMBER.realNum -> Month.DECEMBER
        else -> Month.EMPTY_MONTH
    }

}

fun getActualYear(): Int {

    val calendar = Calendar.getInstance() ?: return 0
    return calendar.get(Calendar.YEAR)

}

fun Date.withoutTime(): Date {

    return this.getDateString().getDate()

}

fun Context.datePickerWithoutDay(month: Int, year: Int): DatePickerDialog? {

    val picker = DatePickerDialog(this, null, year, month, 1)
//
//    val datePickerViewGroup = picker.datePicker as ViewGroup
//    val resourceId = Resources.getSystem().getIdentifier("day", "id", "android")

//    datePickerViewGroup.findViewById<ViewGroup>(resourceId).visibility = View.GONE

    try {
        val datePickerFields = picker::class.java.declaredFields
        for (item in datePickerFields) {

            val field = item ?: continue

            if (field.name == "mDatePicker") {

                field.isAccessible = true
                val datePicker = field.get(picker)
                val datePickerFieldInternal = field.type.declaredFields

                for (itemInternal in datePickerFieldInternal) {

                    val fieldInternal = itemInternal ?: continue
                    val name = fieldInternal.name

                    print(name)

                    if (name == "mDaySpinner" || name == "mDayPicker") {

                        fieldInternal.isAccessible = true
                        val dayPicker = fieldInternal.get(datePicker)
                        (dayPicker as? View)?.visibility = View.GONE

                    }

                }

            }


        }
    } catch (e: Exception) {
    }

    return picker

}

fun Context.showCalenderStackOverFlow (onDateSetListener: DatePickerDialog.OnDateSetListener) : DatePickerDialog {
    val dpd = DatePickerDialog(this, onDateSetListener, getActualYear(), getActualMonthByMonth().num, 1)
    try {
        val datePickerDialogFields = dpd.javaClass.declaredFields
        for (datePickerDialogField in datePickerDialogFields) {
            if (datePickerDialogField.name == "mDatePicker") {
                datePickerDialogField.isAccessible = true
                val datePicker = datePickerDialogField.get(dpd) as DatePicker
                val datePickerFields = datePickerDialogField.type.declaredFields
                for (datePickerField in datePickerFields) {
                    val name = datePickerField.name
                    if (name == "mDayPicker" || name == "mDaySpinner") {
                        datePickerField.isAccessible = true
                        var dayPicker = Any()
                        dayPicker = datePickerField.get(datePicker)
                        (dayPicker as View).visibility = View.GONE
                    }
                }
            }

        }
    } catch (ex: Exception) {
    }

    return dpd
}


fun OnCalendarPicker.showPicker(context: Context): DatePickerDialog {
    DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val realMonth = month + 1
            val monthCorrect =
                if (realMonth.toString().length == 1) "0$realMonth" else realMonth.toString()
            val dayCorrect =
                if (dayOfMonth.toString().length == 1) "0$dayOfMonth" else dayOfMonth.toString()
            this.onDate(
                "$dayCorrect/$monthCorrect/$year".getDate(),
                "$dayCorrect/$monthCorrect/$year"
            )
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    ).let { picker ->
        return picker
    }
}

fun Calendar.actualMonth(): String {
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

class MonthModel(var id: Long, var name: String, var month: Month)

object DateHelper {

    fun getAllMonths(): List<Month> {
        return listOf(
            Month.JANUARY, Month.FEBRUARY, Month.MARCH,
            Month.APRIL, Month.MAY, Month.JUNE,
            Month.JULY, Month.AUGUST, Month.SEPTEMBER,
            Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER
        )
    }

}