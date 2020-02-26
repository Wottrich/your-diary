package wottrich.github.io.yourdiary.widget

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.month_year_picker.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.getActualMonthByNumber
import kotlin.math.max

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-22
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */

open class DatePickerMonthYearDialog : AlertDialog, DialogInterface.OnClickListener {

    private val keyYear = "year"
    private val keyMonth = "month"

    private var delegate: DatePickerMonthYearDelegate? = null
    private var onDateSetListener: OnDateChangedListener? = null
    private var onDateResetListener: OnDateResetListener? = null

    constructor(
        context: Context?,
        listener: OnDateChangedListener?,
        resetListener: OnDateResetListener?,
        monthYear: MonthYear
    ) : super(context) {
        init(context, 0, listener, resetListener, monthYear)
    }

    constructor(
        context: Context?,
        themeResId: Int,
        listener: OnDateChangedListener?,
        resetListener: OnDateResetListener?,
        monthYear: MonthYear
    ) : super(context, themeResId) {
        init(context, themeResId, listener, resetListener, monthYear)
    }

    @SuppressLint("InflateParams")
    private fun init(
        context: Context?,
        themeResId: Int,
        listener: OnDateChangedListener?,
        resetListener: OnDateResetListener?,
        monthYear: MonthYear
    ) {

        this.onDateSetListener = listener
        this.onDateResetListener = resetListener

        val themeContext = context ?: return
        val inflater = LayoutInflater.from(context) ?: return

        val view = inflater.inflate(R.layout.month_year_picker, null)
        setView(view)

        setButton(DialogInterface.BUTTON_POSITIVE, themeContext.getString(R.string.ok_option), this)
        setButton(DialogInterface.BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this)
        setButton(DialogInterface.BUTTON_NEUTRAL, themeContext.getString(R.string.reset), this)

        this.delegate = DatePickerMonthYearDelegate(view)
        this.delegate?.init(monthYear = monthYear, onDateChangeListener = listener)

    }

    open fun setMinDate(minDate: Long) {
        this.delegate?.setMinDate(minDate)
    }

    open fun setMaxDate(maxDate: Long) {
        this.delegate?.setMaxDate(maxDate)
    }

    override fun onSaveInstanceState(): Bundle {
        val bundle = super.onSaveInstanceState()

        val pickerDelegate = this.delegate ?: return bundle
        val monthYear = pickerDelegate.getMonthYear() ?: return bundle

        bundle.putInt(keyYear, monthYear.year)
        bundle.putInt(keyMonth, monthYear.month.realNum)

        return bundle
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val year = savedInstanceState.getInt(keyMonth)
        val month = savedInstanceState.getInt(keyMonth)

        val monthByNumber = getActualMonthByNumber(month)
        this.delegate?.init(MonthYear(monthByNumber, year), this.onDateSetListener)

    }

    override fun onClick(dialog: DialogInterface?, which: Int) {

        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                this.onDateSetListener?.invoke(this.delegate?.getMonthYear())
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                cancel()
            }
            DialogInterface.BUTTON_NEUTRAL -> {
                this.onDateResetListener?.invoke()
            }
        }

    }

}