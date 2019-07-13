package wottrich.github.io.yourdiary.view.dialog.customer

import android.annotation.SuppressLint

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_customer.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.enumerators.CustomerType
import wottrich.github.io.yourdiary.extensions.isNotNullOrEmpty
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.utils.KeyboardUtils

@SuppressLint("ValidFragment")
class CustomerDialog(var onCustomer: () -> Unit, var type: CustomerType, var id: Long = -1) : BaseDialog(R.layout.dialog_customer), View.OnClickListener {

    private val viewModel: CustomerDialogViewModel by lazy {
        CustomerDialogViewModel(type, id)
    }

    override fun initValues() {
        this.parent = baseView.constDialog
        baseView.btnRegisterClient.setOnClickListener(this)
        baseView.ivClose.setOnClickListener(this)
        baseView.btnDeleteClient.setOnClickListener(this)
        textWatcher()
        configureScreen()
    }

    private fun configureScreen () {
        baseView.run {
            when (viewModel.type) {
                CustomerType.NEW -> {
                    this.btnDeleteClient.visibility = View.GONE
                }
                CustomerType.EDIT -> {
                    viewModel.customer?.let {
                        this.etClientName.setText(it.name)
                    }
                    this.titleToolbar.text = getString(R.string.dialog_customer_edit_customer)
                    this.btnRegisterClient.isEnabled = true
                    this.btnRegisterClient.text = getString(R.string.dialog_customer_edit_save)
                    this.btnDeleteClient.visibility = View.VISIBLE
                }
            }
        }
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
                if (viewModel.type == CustomerType.NEW) {
                    Customer.changeCustomer(Customer(baseView.etClientName.text.toString()))
                    Toast.makeText(activity, getString(R.string.dialog_spending_register_success), Toast.LENGTH_SHORT).show()
                    onCustomer()
                    KeyboardUtils.hideKeyboard(requireActivity(), baseView)
                    baseView.postDelayed(this::dismiss, 10)
                } else {
                    viewModel.customer?.name = baseView.etClientName.text.toString()
                    put(viewModel.customer)
                    onCustomer()
                    KeyboardUtils.hideKeyboard(requireActivity(), baseView)
                    baseView.postDelayed(this::dismiss, 10)
                }
            }
            R.id.btnDeleteClient -> {
                viewModel.takeIf { id != -1L }?.id?.let {
                    Customer.deleteCustomer(it)
                    onCustomer()
                    dismiss()
                    return
                }
                Toast.makeText(activity, getString(R.string.dialog_customer_error_delete), Toast.LENGTH_SHORT).show()
            }
            R.id.ivClose -> {
                KeyboardUtils.hideKeyboard(requireActivity(), baseView)
                baseView.postDelayed(this::dismiss, 10)
                dismissAnimation()
            }
        }
    }
}