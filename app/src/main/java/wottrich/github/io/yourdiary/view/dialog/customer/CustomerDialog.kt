package wottrich.github.io.yourdiary.view.dialog.customer

import android.annotation.SuppressLint
import android.support.v7.widget.Toolbar

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.dialog_customer_new.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.enumerators.CustomerType
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.utils.KeyboardUtils

@SuppressLint("ValidFragment")
class CustomerDialog(var onCustomer: () -> Unit, var type: CustomerType, var id: Long = -1) : BaseDialog(R.layout.dialog_customer_new), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    private val viewModel: CustomerDialogViewModel by lazy {
        CustomerDialogViewModel(type, id)
    }

    lateinit var toolbar: Toolbar

    override fun initValues() {
        this.parent = baseView.constDialog
        baseView.btnDeleteClient.setOnClickListener(this)

        //menu
        toolbar = baseView.toolbar
        toolbar.inflateMenu(R.menu.ok_option)
        toolbar.setNavigationOnClickListener {
            KeyboardUtils.hideKeyboard(requireActivity(), baseView)
            baseView.postDelayed(this::dismiss, 10)
            dismissAnimation()
        }
        toolbar.setOnMenuItemClickListener(this)

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
                    this.toolbar.title = getString(R.string.dialog_customer_edit_customer)
                    this.btnDeleteClient.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun saveUser () {
        if (baseView.etClientName.text.isEmpty()) {
            Toast.makeText(activity, getString(R.string.dialog_customer_name_empty), Toast.LENGTH_SHORT).show()
            return
        }
        if (viewModel.type == CustomerType.NEW) {
            Customer.newCustomer(Customer(baseView.etClientName.text.toString()))
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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnRegisterClient -> {
                saveUser()
            }
            R.id.btnDeleteClient -> {
                viewModel.takeIf { id != -1L }?.id?.let {
                    KeyboardUtils.hideKeyboard(requireActivity(), baseView)
                    Customer.deleteCustomer(it)
                    onCustomer()
                    dismiss()
                    return
                }
                Toast.makeText(activity, getString(R.string.dialog_customer_error_delete), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onMenuItemClick(itemMenu: MenuItem?): Boolean {
        return when (itemMenu?.itemId) {
            R.id.itOk -> {
                saveUser()
                true
            }
            else -> false
        }

    }
}