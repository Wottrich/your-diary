package wottrich.github.io.yourdiary.widget

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import wottrich.github.io.yourdiary.extensions.actualYear
import wottrich.github.io.yourdiary.extensions.getActualMonthByMonth
import wottrich.github.io.yourdiary.extensions.getActualMonthByNumber
import wottrich.github.io.yourdiary.extensions.getActualYear
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.util.*

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-22
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */
 
class DatePickerMonthYearDialogFragment : DialogFragment() {

    companion object {
        const val NULL_INT = -1
        const val ARG_MONTH = "month"
        const val ARG_YEAR = "year"
        const val ARG_MIN_DATE = "min_date"
        const val ARG_MAX_DATE = "max_date"

        fun getInstance (
            monthYear: MonthYear = MonthYear(getActualMonthByMonth(), getActualYear())
        ) : DatePickerMonthYearDialogFragment {
            return getInstance(monthYear, minDate = -1, maxDate = -1)
        }

        fun getInstance (
            monthYear: MonthYear = MonthYear(getActualMonthByMonth(), getActualYear()),
            minDate: Long = -1,
            maxDate: Long = -1
        ) : DatePickerMonthYearDialogFragment {
            val datePickerMonthYearDialogFragment = DatePickerMonthYearDialogFragment()

            val bundle = Bundle()
            bundle.putInt(ARG_MONTH, monthYear.month.realNum)
            bundle.putInt(ARG_YEAR, monthYear.year)
            bundle.putLong(ARG_MIN_DATE, minDate)
            bundle.putLong(ARG_MAX_DATE, maxDate)
            datePickerMonthYearDialogFragment.arguments = bundle

            return datePickerMonthYearDialogFragment
        }

    }

    var mOnDateSetListener: OnDateChangedListener? = null
    var mOnDateResetListener: OnDateResetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val arguments = arguments ?: throw NullPointerException("Arguments by fragment is null")

        val year = arguments.getInt(ARG_YEAR)
        val month = arguments.getInt(ARG_MONTH)
        val minDate = arguments.getLong(ARG_MIN_DATE)
        val maxDate = arguments.getLong(ARG_MAX_DATE)

        val monthYear = MonthYear(getActualMonthByNumber(month), year)

        checkForValidDate(monthYear, minDate, maxDate)

        val datePickerDialog = DatePickerMonthYearDialog(
            activity,
            this.mOnDateSetListener,
            this.mOnDateResetListener,
            monthYear
        )

        if (minDate != -1L) {
            datePickerDialog.setMinDate(minDate)
        }

        if (maxDate != -1L) {
            datePickerDialog.setMaxDate(maxDate)
        }

        return datePickerDialog
    }

    private fun checkForValidDate (monthYear: MonthYear, minDate: Long, maxDate: Long) {

        val calendar = Calendar.getInstance() ?: throw NullPointerException("Calendar is null")

        calendar.add(Calendar.YEAR, monthYear.year)
        calendar.add(Calendar.MONTH, monthYear.month.realNum)

        if (calendar.timeInMillis < minDate ) {
            throw IllegalArgumentException("The min date should be less than initial date set")
        } else if (calendar.timeInMillis < maxDate) {
            throw IllegalArgumentException("The max date should not be less than current date.")
        }

    }

    fun DatePickerMonthYearDialogFragment.showPicker(supportFragmentManager: FragmentManager) {
        this@DatePickerMonthYearDialogFragment.show(supportFragmentManager, "DatePickerMonthYearDialogFragment")
    }

}