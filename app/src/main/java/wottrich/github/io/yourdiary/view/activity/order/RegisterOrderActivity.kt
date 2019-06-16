package wottrich.github.io.yourdiary.view.activity.order

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_register_order.*
import kotlinx.android.synthetic.main.activity_register_order.btnDate
import kotlinx.android.synthetic.main.activity_register_order.etTitle
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.OrderType
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import java.util.*

class RegisterOrderActivity : BaseActivity(R.layout.activity_register_order), View.OnClickListener, OnCalendarPicker {

    private val viewModel: RegisterOrderViewModel by lazy {
        RegisterOrderViewModel(
            intent.getLongExtra("userId", -1),
            intent.getLongExtra("orderId", -1),
            intent.getSerializableExtra("orderType") as OrderType
        )
    }

    override fun initValues() {
        etTitle setText viewModel.order?.title
        etPrice setText viewModel.order?.price?.format()
        etDescription setText viewModel.order?.description
        btnDate.text = viewModel.order?.date?.getDateString()
        addListeners()
    }

    private fun priceChange () {
        etPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etPrice.removeTextChangedListener(this)
                etPrice setText CurrencyUtils.formatToLocale(s.toString())
                etPrice.setSelection(etPrice.text.length)
                etPrice.addTextChangedListener(this)
                viewModel.changePrice(convertToDouble(etPrice.text.toString(), _locale))
            }

        })
    }

    private fun addListeners () {
        etTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeTitle(etTitle.text.toString())
            }
        })
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeDescription(etDescription.text.toString())
            }
        })
        priceChange()
        btnDate.setOnClickListener(this)
        toolbar.setNavigationOnClickListener {
            KeyboardUtils.hideKeyboard(this)
            viewModel.saveData()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnDate -> {
                showPicker(this).show()
            }
        }
    }

    override fun onDate(date: Date, dateString: String) {
        viewModel.changeDate(date)
    }

    override fun onBackPressed() {
        KeyboardUtils.hideKeyboard(this)
        viewModel.saveData()
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    /*
    object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                baseView.etPrice.removeTextChangedListener(this)
                baseView.etPrice.setText(CurrencyUtils.formatToLocale(s.toString(), _locale), TextView.BufferType.EDITABLE)
                baseView.etPrice.setSelection(baseView.etPrice.text.length)
                baseView.etPrice.addTextChangedListener(this)
                //priceObserver = baseView.etPrice.text.toString()
                validButton()
            }
        }
    */

}
