package wottrich.github.io.yourdiary.dialog

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_spending.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.OnCalendarPicker
import wottrich.github.io.yourdiary.extensions.cleanText
import wottrich.github.io.yourdiary.extensions.convertToDouble
import wottrich.github.io.yourdiary.extensions.showPicker
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import java.util.*

@SuppressLint("ValidFragment")
class SpendingDialog (var onSpending: (Spending) -> Unit) : BaseDialog (R.layout.dialog_spending), View.OnClickListener, OnCalendarPicker {

    private val viewModel: SpendingDialogViewModel by lazy {SpendingDialogViewModel()}

    override fun initValues() {
        textWatcher()
        baseView.ivClose.setOnClickListener(this)
        baseView.btnDate.setOnClickListener(this)
        baseView.btnRegister.setOnClickListener(this)
    }

    private fun textWatcher () {
        baseView.etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validButton()
            }
        })
        baseView.etPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                baseView.etPrice.removeTextChangedListener(this)
                baseView.etPrice.setText(CurrencyUtils.formatToLocale(s.toString(), Locale("pt", "BR")), TextView.BufferType.EDITABLE)
                baseView.etPrice.setSelection(baseView.etPrice.text.length)
                baseView.etPrice.addTextChangedListener(this)
                validButton()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnRegister -> {
                Spending(
                        baseView.etTitle.text.toString(),
                        baseView.etDescription.text.toString(),
                        convertToDouble(cleanText(baseView.etPrice.text.toString()), Locale("pt", "BR")),
                        viewModel.date
                ).also {
                    onSpending(it)
                    dismiss()
                }
            }
            R.id.btnDate -> {
                context?.let {
                    showPicker(it).show()
                }
            }
            R.id.ivClose -> {
                dismiss()
            }
        }
    }

    override fun onDate(date: Date, dateString: String) {
        viewModel.date = date
        baseView.btnDate.text = dateString
        validButton()
    }

    private fun validButton () {
        baseView.btnRegister.isEnabled = viewModel.date != null
                && !baseView.etTitle.text.toString().isEmpty()
                && convertToDouble(baseView.etPrice.text.toString(), Locale("pt", "BR")) > 0.00
    }

}