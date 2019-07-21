package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_customer.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.User

class CustomerAdapter(
    private var context: Context?,
    private var inflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var customer = getUser().customers
    var onClick: View.OnClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomerViewHolder(inflater.inflate(R.layout.row_customer, viewGroup, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = (holder as CustomerViewHolder).itemView
        val customer = customer[holder.adapterPosition]
        view.tvNameCustomer.text = customer.name
        view.tvCountOrder.text = String.format("%d Pedidos", customer.orders.size)
        view.tvPriceOrder.text = customer.totalPriceFromSelectedCustomer()
        view.tvDateLastOrder.text = customer.lastDateOrder()
        if (customer.selected) {
            holder.itemView.background = context?.getDrawable(R.drawable.shape_row_customer_selected)
        } else {
            holder.itemView.background = context?.getDrawable(R.color.transparent)
        }

        holder.itemView.setOnClickListener {
            Customer.changeCustomer(customer)
            onClick?.onClick(it)
        }
    }

    override fun getItemCount(): Int = customer.size

    fun updateList() {
        customer = getUser().customers
        notifyDataSetChanged()
    }


    class CustomerViewHolder(var view: View) : RecyclerView.ViewHolder(view)

}