package wottrich.github.io.yourdiary.widget

import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.NumberPicker
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import java.lang.NullPointerException
import java.text.DateFormatSymbols
import java.util.*

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-22
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */

data class MonthYear(var month: Month, var year: Int)
typealias OnDateChangedListener = ((MonthYear?) -> Unit)

open class DatePickerMonthYearDelegate(parent: View) {

    companion object {
        const val DEFAULT_START_YEAR = 1900
        const val DEFAULT_END_YEAR = 2100
    }

    private var mMonthSpinner: NumberPicker? = null
    private var mYearSpinner: NumberPicker? = null

    private var mTempDate: Calendar? = null
    private var mCurrenteDate: Calendar? = null
    private var mMinDate: Calendar? = null
    private var mMaxDate: Calendar? = null

    private var mShortMonths: Array<String>? = null
    private var mNumberOfMonths: Int = 0

    private var mCurrentLocale: Locale? = null

    private var mOnDateChangedListener: OnDateChangedListener? = null

    init {

        //current location
        setCurrentLocale(Locale.getDefault())

        //on change listener
        val onChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->

            val tempDate = this.mTempDate ?: throw NullPointerException("TempDate is null")

            tempDate.timeInMillis = mCurrenteDate?.timeInMillis ?: 0

            if (picker == mMonthSpinner) {

                if (newVal > 11 || newVal < 0) {
                    tempDate.set(Calendar.MONTH, Month.JANUARY.realNum)
                } else {
                    tempDate.set(Calendar.MONTH, newVal)
                }

            } else if (picker == mYearSpinner) {
                if (newVal in DEFAULT_START_YEAR until DEFAULT_END_YEAR) {
                    tempDate.set(Calendar.YEAR, newVal)
                }
            } else {
                throw IllegalArgumentException()
            }

            //SET DATE HERE
            setDate(getMonthYear(tempDate.get(Calendar.MONTH), tempDate.get(Calendar.YEAR)))
            updateSpinners()
            //notifyDataChange()

        }

        //config month spinner
        this.mMonthSpinner = parent.findViewById(R.id.month) as? NumberPicker
        this.mMonthSpinner?.let {
            it.minValue = 0
            it.maxValue = mNumberOfMonths - 1
            it.displayedValues = this.mShortMonths
            it.setOnLongPressUpdateInterval(200)
            it.setOnValueChangedListener(onChangeListener)
        }

        //config year spinner
        this.mYearSpinner = parent.findViewById(R.id.year) as? NumberPicker
        this.mYearSpinner?.let {
            it.setOnLongPressUpdateInterval(100)
            it.setOnValueChangedListener(onChangeListener)
        }

        //clear temp date, define default year and set min date
        this.mTempDate?.apply {
            clear()
            set(DEFAULT_START_YEAR, 0, 1)
            this@DatePickerMonthYearDelegate.setMinDate(this.timeInMillis)
        }

        //clear temp date, define default year and set max date
        this.mTempDate?.apply {
            clear()
            set(DEFAULT_END_YEAR, 11, 31)
            this@DatePickerMonthYearDelegate.setMaxDate(this.timeInMillis)
        }

        //Set actual time to current date
        this.mCurrenteDate?.timeInMillis = System.currentTimeMillis()
    }

    open fun init(
        monthYear: MonthYear? = MonthYear(getActualMonthByMonth(), getActualYear()),
        onDateChangeListener: OnDateChangedListener?
    ) {

        setDate(monthYear)
        updateSpinners()
        this.mOnDateChangedListener = onDateChangeListener

    }


    private fun setCurrentLocale (locale : Locale) {
        if (locale != mCurrentLocale) {
            this.mCurrentLocale = locale
        }

        this.mTempDate = getCalendarForLocation(this.mTempDate, locale)
        this.mMinDate = getCalendarForLocation(this.mMinDate, locale)
        this.mMaxDate = getCalendarForLocation(this.mMaxDate, locale)
        this.mCurrenteDate = getCalendarForLocation(this.mCurrenteDate, locale)

        this.mNumberOfMonths = (mTempDate?.getActualMaximum(Calendar.MONTH) ?: 0)  + 1
        this.mShortMonths = DateFormatSymbols().shortMonths

        if (usingNumericMonths()) {

            this.mShortMonths = arrayOf(mNumberOfMonths.toString())

            for (i in 0 until mNumberOfMonths) {
                val shortMonths = this.mShortMonths ?: continue
                shortMonths[i] = String.format("%d", i + 1)
            }

        }

    }

    private fun updateSpinners () {

        val monthSpinner = this.mMonthSpinner ?: throw NullPointerException("Month Spinner is null")
        val yearSpinner = this.mYearSpinner ?: throw NullPointerException("Year Spinner is null")
        val currentDate = this.mCurrenteDate ?: throw NullPointerException("Current Date is null")
        val shortMonths = this.mShortMonths ?: throw NullPointerException("Short Month is null")
        val minDate = this.mMinDate ?: throw NullPointerException("Min Date is null")
        val maxDate = this.mMaxDate ?: throw NullPointerException("Min Date is null")

        when (currentDate) {
            minDate -> {
                monthSpinner.displayedValues = null
                monthSpinner.minValue = currentDate.get(Calendar.MONTH)
                monthSpinner.maxValue = currentDate.getActualMaximum(Calendar.MONTH)
                monthSpinner.wrapSelectorWheel = false
            }
            maxDate -> {
                monthSpinner.displayedValues = null
                monthSpinner.minValue = currentDate.getActualMinimum(Calendar.MONTH)
                monthSpinner.maxValue = currentDate.get(Calendar.MONTH)
                monthSpinner.wrapSelectorWheel = false
            }
            else -> {
                monthSpinner.displayedValues = null
                monthSpinner.minValue = 0
                monthSpinner.maxValue = 11
                monthSpinner.wrapSelectorWheel = true
            }
        }

        val displayedValues = Arrays.copyOfRange(
            shortMonths,
            monthSpinner.minValue,
            monthSpinner.maxValue + 1
        )
        monthSpinner.displayedValues = displayedValues

        yearSpinner.minValue = minDate.get(Calendar.YEAR)
        yearSpinner.maxValue = maxDate.get(Calendar.YEAR)
        yearSpinner.wrapSelectorWheel = false

        yearSpinner.value = currentDate.get(Calendar.YEAR)
        monthSpinner.value = currentDate.get(Calendar.MONTH)

    }

    fun setMinDate (minDate: Long) {

        val currentDate = this.mCurrenteDate ?: throw NullPointerException("Current Date is null")
        val tempDate = this.mTempDate ?: throw NullPointerException("TempDate is null")
        val minDateCalendar = this.mMinDate ?: throw NullPointerException("Min Date is null")

        tempDate.timeInMillis = minDate

        if (tempDate.get(Calendar.YEAR) == minDateCalendar.get(Calendar.YEAR)
            && tempDate.get(Calendar.DAY_OF_YEAR) != minDateCalendar.get(Calendar.DAY_OF_YEAR)) {
            return
        }

        minDateCalendar.timeInMillis = minDate
        if(currentDate.before(minDateCalendar)) {
            currentDate.timeInMillis = minDateCalendar.timeInMillis
        }

        updateSpinners()
    }

    fun setMaxDate (maxDate: Long) {

        val currentDate = this.mCurrenteDate ?: throw NullPointerException("Current Date is null")
        val tempDate = this.mTempDate ?: throw NullPointerException("TempDate is null")
        val maxDateCalendar = this.mMaxDate ?: throw NullPointerException("Min Date is null")

        tempDate.timeInMillis = maxDate

        if (tempDate.get(Calendar.YEAR) == maxDateCalendar.get(Calendar.YEAR)
            && tempDate.get(Calendar.DAY_OF_YEAR) != maxDateCalendar.get(Calendar.DAY_OF_YEAR)) {
            return
        }

        maxDateCalendar.timeInMillis = maxDate
        if (currentDate.after(maxDateCalendar)) {
            currentDate.timeInMillis = maxDateCalendar.timeInMillis
        }
        updateSpinners()

    }

    private fun setDate (monthYear: MonthYear?) {
        val currentDate = mCurrenteDate ?: throw NullPointerException("Current Date is null")
        val minDate = mMinDate ?: throw NullPointerException("Min Date is null")
        val maxDate = mMaxDate ?: throw NullPointerException("Max Date is null")
        val year = monthYear?.year ?: 1900
        val month = monthYear?.month?.realNum ?: 0

        currentDate.set(Calendar.YEAR, year)
        currentDate.set(Calendar.MONTH, month)

        if (currentDate.before(minDate)) {
            currentDate.timeInMillis = minDate.timeInMillis
        } else if (currentDate.after(maxDate)){
            currentDate.timeInMillis = maxDate.timeInMillis
        }
    }

    private fun usingNumericMonths () : Boolean {
        val shortMonth = mShortMonths ?: return false
        return Character.isDigit(shortMonth[Calendar.JANUARY][0])
    }

    private fun getCalendarForLocation (oldCalendar: Calendar?, locale: Locale) : Calendar {

        return if (oldCalendar == null) {
            Calendar.getInstance(locale)
        } else {
            val currentTimeMillis = oldCalendar.timeInMillis
            val newCalendar = Calendar.getInstance(locale)
            newCalendar.timeInMillis = currentTimeMillis
            newCalendar
        }

    }

    open fun getMonthYear (month: Int? = null, year: Int? = null) : MonthYear? {
        val currentDate = mCurrenteDate ?: return null

        val monthByNumber = getActualMonthByNumber(month ?: currentDate.get(Calendar.MONTH))
        val currentYear = year ?: currentDate.get(Calendar.YEAR)

        return MonthYear(monthByNumber, currentYear)
    }

    private fun notifyDataChange () {
        mOnDateChangedListener?.invoke(getMonthYear())
    }

}