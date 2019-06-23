package wottrich.github.io.yourdiary.view.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.dialog_month_picker.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.MonthAdapter
import wottrich.github.io.yourdiary.extensions.Month
import wottrich.github.io.yourdiary.generics.BaseDialog

@SuppressLint("ValidFragment")
class MonthPickerDialog(private var onSelectedMonth: (Month) -> Unit) : BaseDialog(R.layout.dialog_month_picker) {


    override fun initValues() {
        baseView.rvMonths.adapter = MonthAdapter(requireActivity(), this::onSelectedMonth)
        val manager = GridLayoutManager(activity, 3)
        baseView.rvMonths.layoutManager = manager
        baseView.btnNoFilter.setOnClickListener {
            this@MonthPickerDialog.onSelectedMonth.invoke(Month.EMPTY_MONTH)
            dismiss()
        }
    }

    private fun onSelectedMonth (month: Month) {
        this@MonthPickerDialog.onSelectedMonth.invoke(month)
        dismiss()
    }



}