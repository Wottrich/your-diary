package wottrich.github.io.yourdiary.view.dialog


import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.dialog_select_customer.view.*
import kotlinx.android.synthetic.main.dialog_select_customer.view.llParent
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SelectCustomerAdapter
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.model.Customer

class SelectCustomerDialog(var onSelectedCustomerCallback: (Customer) -> Unit) : BaseDialog(R.layout.dialog_select_customer) {

    private var selectedCustomer: Customer? = null

    private val adapter by lazy {
        val lazyAdapter = SelectCustomerAdapter(this.requireContext())
        lazyAdapter.onSelectedCustomer = this::onSelectedCustomer
        lazyAdapter
    }

    private lateinit var toolBar: Toolbar

    override fun initValues() {

        parent = baseView.llParent

        baseView.rvCustomers.adapter = adapter
        toolBar = baseView.toolbar
        toolBar.setNavigationOnClickListener {
            dismissAnimation()
        }

        toolBar.inflateMenu(R.menu.ok_option)
        toolBar.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.itOk -> {
                    if (selectedCustomer != null) {

                        val selectedUser = Customer.selectedCustomer()
                        selectedUser?.selected = false
                        put(selectedUser)

                        onSelectedCustomerCallback(selectedCustomer!!)
                        dismissAnimation()
                        true
                    } else {
                        Toast.makeText(context, "Selecione um Cliente", Toast.LENGTH_SHORT).show()
                        false
                    }
                }
                else -> false
            }

        }

    }

    private fun onSelectedCustomer (customer: Customer) {

        selectedCustomer = customer
        adapter.notifyDataSetChanged()

    }

}