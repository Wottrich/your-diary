package wottrich.github.io.yourdiary.view.dialog


import kotlinx.android.synthetic.main.dialog_select_customer.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SelectCustomerAdapter
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.model.Customer

class SelectCustomerDialog : BaseDialog(R.layout.dialog_select_customer) {

    private val adapter by lazy {
        val lazyAdapter = SelectCustomerAdapter(this.requireContext())
        lazyAdapter.onSelectedCustomer = this::onSelectedCustomer
        lazyAdapter
    }

    override fun initValues() {

        baseView.rvCustomers.adapter = adapter

    }

    private fun onSelectedCustomer (customer: Customer) {

    }

}