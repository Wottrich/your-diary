package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_customer.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.extensions.lastDateOrder
import wottrich.github.io.yourdiary.extensions.totalPriceFromSelectedCustomer
import wottrich.github.io.yourdiary.model.Customer

class SelectCustomerAdapter(
    var context: Context,
    var inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var customers = getUser().customers
    var onSelectedCustomer: ((Customer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomerViewHolder(inflater.inflate(R.layout.row_customer, parent, false))
    }

    override fun getItemCount(): Int = customers.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = (holder as CustomerViewHolder).itemView
        val customer = customers[holder.adapterPosition]
        view.tvNameCustomer.text = customer.name
        view.tvCountOrder.text = String.format("%d Pedidos", customer.orders.size)
        view.tvPriceOrder.text = customer.totalPriceFromSelectedCustomer()
        view.tvDateLastOrder.text = customer.lastDateOrder()
        if (customer.selected) {
            holder.itemView.background = context.getDrawable(R.drawable.shape_row_customer_selected)
        } else {
            holder.itemView.background = context.getDrawable(R.color.transparent)
        }
        holder.itemView.setOnClickListener {
            onSelectedCustomer?.invoke(customer)
        }
    }


    class CustomerViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}
