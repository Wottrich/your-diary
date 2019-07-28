package wottrich.github.io.yourdiary.view.activity.register

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_order.*
import kotlinx.android.synthetic.main.activity_register_order.btnDate
import kotlinx.android.synthetic.main.activity_register_order.etTitle
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import java.util.*

class RegisterActivity : BaseActivity(R.layout.activity_register_order), View.OnClickListener,
    OnCalendarPicker,
    Toolbar.OnMenuItemClickListener {

    private val viewModel: RegisterViewModel by lazy {
        return@lazy if (!intent.getBooleanExtra("isSpending", false)) {
            RegisterViewModel(
                intent.getLongExtra("userId", -1),
                intent.getLongExtra("orderId", -1),
                intent.getSerializableExtra("registerType") as RegisterType
            )
        } else {
            RegisterViewModel(
                intent.getLongExtra("spendingId", -1),
                intent.getSerializableExtra("registerType") as RegisterType
            )
        }
    }

    override fun initValues() {
        etTitle setText viewModel.getTitle()
        if (viewModel.type == RegisterType.EDIT) {
            etPrice setText viewModel.getPrice().format()
        }
        etDescription setText viewModel.getDescription()
        btnDate.text = viewModel.getDate().getDateString()

        if (viewModel.isSpending) toolbar.setTitle(R.string.activity_register_spending)
        else toolbar.setTitle(R.string.activity_register_order)

        addListeners()
    }

    private fun priceChange() {
        etPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etPrice.removeTextChangedListener(this)
                etPrice setText CurrencyUtils.formatToLocale(s.toString())
                etPrice.setSelection(etPrice.text.length)
                etPrice.addTextChangedListener(this)
                if (etPrice.text.isNotEmpty())
                    viewModel.changePrice(convertToDouble(etPrice.text.toString(), _locale))
                else
                    viewModel.changePrice(0.0)
            }

        })
    }

    private fun addListeners() {
        etTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeTitle(etTitle.text.toString())
            }
        })
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeDescription(etDescription.text.toString())
            }
        })
        priceChange()
        btnDate.setOnClickListener(this)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        toolbar.inflateMenu(R.menu.ok_option)
        toolbar.setOnMenuItemClickListener(this)
    }

    private fun saveAndExit() {
        KeyboardUtils.hideKeyboard(this, root)
        viewModel.saveData { message, success ->
            if (success) {
                setResult(Activity.RESULT_OK)
                finish()
            } else if (!success && message != null) {
                Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnDate -> {
                showPicker(this).show()
            }
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.itOk -> {
                saveAndExit()
                true
            }
            else -> false
        }
    }

    override fun onDate(date: Date, dateString: String) {
        viewModel.changeDate(date)
        btnDate.text = dateString
    }

}
