package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_order.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getDateString
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order

class OrderAdapter(private var _orders: List<Order>?, var context: Context, val onClickOrder: (order: Order?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflate: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder(inflate.inflate(R.layout.row_order, viewGroup, false))
    }

    override fun getItemCount(): Int = _orders?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val orders = _orders ?: return
        with(holder as OrderViewHolder) {
            val order = orders[this.adapterPosition]
            this.itemView.let { view ->
                view.tvTitle.text = order.title
                view.tvTitle.visibility = if (order.title.isEmpty()) View.GONE else View.VISIBLE
                view.tvPriceOrder.text = order.price.format()
                view.tvDateOrder.text = order.date.getDateString()
                view.tvDescription.text = order.description
                view.tvDescription.visibility = if (order.description.isEmpty()) View.GONE else View.VISIBLE

                view.setOnClickListener {
                    onClickOrder(_orders?.get(this.adapterPosition))
                }
            }
        }
    }

    fun updateList() {
        _orders = Customer.selectedCustomer()?.orders
        notifyDataSetChanged()
    }

    class OrderViewHolder (var view: View) : RecyclerView.ViewHolder(view)

}