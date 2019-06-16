package wottrich.github.io.yourdiary.view.dialog

import android.annotation.SuppressLint

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_customer.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.isNotNullOrEmpty
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.utils.KeyboardUtils

@SuppressLint("ValidFragment")
class CustomerDialog(var onCustomer: () -> Unit) : BaseDialog(R.layout.dialog_customer), View.OnClickListener {

    override fun initValues() {
        this.parent = baseView.constDialog
        baseView.btnRegisterClient.setOnClickListener(this)
        baseView.ivClose.setOnClickListener(this)
        textWatcher()
    }

    private fun textWatcher () {
        baseView.etClientName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                baseView.btnRegisterClient.isEnabled = baseView.etClientName.text.isNotNullOrEmpty()
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnRegisterClient -> {
                Customer.changeCustomer(Customer(baseView.etClientName.text.toString()))
                Toast.makeText(activity, getString(R.string.dialog_spending_register_success), Toast.LENGTH_SHORT).show()
                onCustomer()
                dismiss()
            }
            R.id.ivClose -> {
                KeyboardUtils.hideKeyboard(requireActivity())
                dismissAnimation()
            }
        }
    }
}