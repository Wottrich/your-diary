package wottrich.github.io.yourdiary.view.holders

import android.content.Context
import android.view.View
import kotlinx.android.synthetic.main.row_flow.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.convertToDouble
import wottrich.github.io.yourdiary.extensions.totalPriceFromSelectedCustomer
import wottrich.github.io.yourdiary.generics.BaseViewHolder
import wottrich.github.io.yourdiary.model.Customer

class FlowViewHolder(var context: Context, var view: View) : BaseViewHolder(view) {

    fun initValues(customer: Customer?, onSpendClick: (() -> Unit)?, onCustomerClick: (() -> Unit)?) {
        if (customer != null) {
            view.tvSelectedCustomer.text = customer.name
            view.tvSelectedCustomerPrice.text = customer.totalPriceFromSelectedCustomer()
            view.tvSelectedCustomerPrice.visibility = View.VISIBLE
        } else {
            view.tvSelectedCustomer.text = context.getString(R.string.flow_view_holder_no_selected_customer)
            view.tvSelectedCustomerPrice.visibility = View.GONE
        }

        view.rootSpend.setOnClickListener{onSpendClick?.invoke()}
        view.rootCustomer.setOnClickListener{onCustomerClick?.invoke()}
    }

}