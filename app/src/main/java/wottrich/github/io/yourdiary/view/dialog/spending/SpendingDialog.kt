package wottrich.github.io.yourdiary.view.dialog.spending

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_spending.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import java.util.*
import kotlin.properties.Delegates

@SuppressLint("ValidFragment")
class SpendingDialog (var onSpending: () -> Unit) : BaseDialog (R.layout.dialog_spending), View.OnClickListener, OnCalendarPicker {

    private val viewModel: SpendingDialogViewModel by lazy { SpendingDialogViewModel() }

    //private var priceObserver by Delegates.observable("") { property, oldValue, newValue ->
    //Toast.makeText(activity, "Change to $newValue", Toast.LENGTH_SHORT).show()
    //}

    override fun initValues() {
        this.parent = baseView.constDialog
        baseView.btnDate.text = viewModel.date?.getDateString()
        textWatcher()
        baseView.ivClose.setOnClickListener(this)
        baseView.btnDate.setOnClickListener(this)
        baseView.btnRegister.setOnClickListener(this)
    }

    private fun textWatcher () {
        baseView.etTitle.addTextChangedListener(object : TextWatcher {
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
                baseView.etPrice.setText(CurrencyUtils.formatToLocale(s.toString(), locale), TextView.BufferType.EDITABLE)
                baseView.etPrice.setSelection(baseView.etPrice.text.length)
                baseView.etPrice.addTextChangedListener(this)
                //priceObserver = baseView.etPrice.text.toString()
                validButton()
            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnRegister -> {
                put(Spending(
                    baseView.etTitle.text.toString(),
                    baseView.etDescription.text.toString(),
                    convertToDouble(baseView.etPrice.text.toString(), locale),
                    viewModel.date
                ))

                Toast.makeText(activity, getString(R.string.dialog_spending_register_success), Toast.LENGTH_SHORT).show()
                onSpending()
                dismiss()
            }
            R.id.btnDate -> {
                context?.let {
                    showPicker(it).show()
                }
            }
            R.id.ivClose -> {
                dismissAnimation()
            }
        }
    }

    override fun onDate(date: Date, dateString: String) {
        viewModel.date = date
        baseView.btnDate.text = dateString
        validButton()
    }

    private fun validButton () {
        baseView.btnRegister.isEnabled = !baseView.etTitle.text.toString().isEmpty()
                && !baseView.etPrice.text.toString().isEmpty()
                && convertToDouble(baseView.etPrice.text.toString(), Locale("pt", "BR")) > 0.00
    }

}